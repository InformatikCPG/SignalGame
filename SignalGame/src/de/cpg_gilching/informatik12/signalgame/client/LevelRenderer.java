package de.cpg_gilching.informatik12.signalgame.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.cpg_gilching.informatik12.signalgame.server.LevelGenerator;
import de.cpg_gilching.informatik12.signalgame.shared.Helfer;
import de.cpg_gilching.informatik12.signalgame.shared.level.AntwortKnoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Kante;
import de.cpg_gilching.informatik12.signalgame.shared.level.Knoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Level;
import de.cpg_gilching.informatik12.signalgame.shared.level.Signalquelle;

public class LevelRenderer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		LevelRenderer r = new LevelRenderer();
		
		JFrame f = new JFrame("SignalGame test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(r);
		f.pack();
		
		f.setVisible(true);
		
		r.requestFocusInWindow();
	}
	
	private static final int KNOTEN_GROESSE = 25;
	
	private LevelGenerator gen = new LevelGenerator();
	private Level lvl = gen.generiereLevel();
	
	private Graphics2D g = null;
	
	private Map<Signalquelle, Punkt> gezeichnet = new HashMap<>();
	
	public LevelRenderer() {
		setPreferredSize(new Dimension(800, 600));
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (Character.toLowerCase(e.getKeyChar()) == 'r') {
					lvl = gen.setKnotenAnzahl(7).setMinimalTiefe(1).setMaximalTiefe(12).setWurzelInputs(3).generiereLevel();
					System.out.println("Anzahl Knoten: " + lvl.wurzel.getGesamtAnzahl(new HashSet<Knoten>()));
					repaint();
				}
			}
		});
	}
	
	@Override
	public void paint(Graphics graw) {
		g = (Graphics2D) graw;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		gezeichnet.clear();
		
		drawRecursive(lvl.wurzel, getWidth() - 100, getHeight() / 2);
		drawAntworten(lvl.antworten);
	}
	
	private void drawRecursive(Knoten node, int sx, int sy) {
		drawKnoten(sx, sy, node.getOutput(), node == lvl.wurzel);
		
		int yscale = (int) (Math.pow(1.8, node.getTiefe()) * 20);
		
		List<Kante> inputs = node.getInputs();
		
		//		int verzweigungen = 0;
		//		for (Kante k : inputs)
		//			if (!gezeichnet.containsKey(k.quelle))
		//				verzweigungen++;
		int verzweigungen = inputs.size();
		
		for (int i = 0, s = inputs.size(); i < s; i++) {
			Kante k = inputs.get(i);
			
			if (k.quelle instanceof Knoten) {
				if (!gezeichnet.containsKey(k.quelle)) {
					int nx = sx - 150 + Helfer.zufallsZahl(-10, 11);
					int ny = sy + yscale / 2 + Math.round((i - (verzweigungen / 2.0f)) * yscale);
					
					gezeichnet.put(k.quelle, new Punkt(nx, ny));
					
					drawRecursive((Knoten) k.quelle, nx, ny);
				}
				
				// Koordinaten des Input-Knotens auslesen
				Punkt p = gezeichnet.get(k.quelle);
				
				
				int seite;
				switch (s) {
				case 1:
					seite = 1;
					break;
				case 2:
					seite = (i == 0 ? 0 : 2);
					break;
				default:
					seite = i;
					break;
				}
				
				drawVerbindung(p.x, p.y, sx, sy, seite);
			}
		}
	}
	
	private void drawKnoten(int x, int y, boolean state, boolean istGesucht) {
		g.setColor(state ? Color.green : Color.red);
		g.fillRect(x - KNOTEN_GROESSE, y - KNOTEN_GROESSE, KNOTEN_GROESSE * 2, KNOTEN_GROESSE * 2);
		
		if (istGesucht) {
			g.setColor(Color.blue);
			g.drawOval(x - 2, y - 2, 4, 4);
		}
	}
	
	private void drawVerbindung(int vonX, int vonY, int nachX, int nachY, int seite) {
		final int dknoten = KNOTEN_GROESSE * 3 / 2;
		
		Punkt nachKante, nachAbstand;
		switch (seite) {
		case 0:
			nachKante = new Punkt(nachX, nachY - KNOTEN_GROESSE);
			nachAbstand = new Punkt(nachX, nachY - dknoten);
			break;
		case 1:
			nachKante = new Punkt(nachX - KNOTEN_GROESSE, nachY);
			nachAbstand = new Punkt(nachX - dknoten, nachY);
			break;
		case 2:
			nachKante = new Punkt(nachX, nachY + KNOTEN_GROESSE);
			nachAbstand = new Punkt(nachX, nachY + dknoten);
			break;
		default:
			throw new IllegalArgumentException("ungültige seite");
		}
		
		Path2D.Float path = new Path2D.Float();
		path.moveTo(vonX + KNOTEN_GROESSE, vonY);
		path.lineTo(vonX + dknoten, vonY);
		//		path.lineTo(vonX + dknoten, nachAbstand.y);
		path.lineTo(nachAbstand.x, nachAbstand.y);
		path.lineTo(nachKante.x, nachKante.y);
		
		
		g.setColor(Color.black);
		g.draw(path);
	}
	
	private void drawAntworten(AntwortKnoten[] antworten) {
		g.setColor(Color.black);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		
		for (int i = 0; i < antworten.length; i++) {
			AntwortKnoten a = antworten[i];
			
			String s = "";
			for (boolean b : a.getInputs())
				s += (b ? "Y" : "N");
			
			g.drawString(s, 20 + i * 80, getHeight() - 20);
		}
	}
	
	private static class Punkt {
		int x;
		int y;
		
		public Punkt(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}
