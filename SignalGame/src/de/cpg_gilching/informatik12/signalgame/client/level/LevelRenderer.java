package de.cpg_gilching.informatik12.signalgame.client.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cpg_gilching.informatik12.signalgame.shared.Helfer;
import de.cpg_gilching.informatik12.signalgame.shared.level.Kante;
import de.cpg_gilching.informatik12.signalgame.shared.level.Knoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Level;
import de.cpg_gilching.informatik12.signalgame.shared.level.Signalquelle;

public class LevelRenderer {
	
	private static final int KNOTEN_GROESSE = 25;
	
	private Level level;
	
	private BufferedImage bild;
	private Graphics2D g;
	
	private Map<Signalquelle, Punkt> gezeichnet = new HashMap<>();
	
	public LevelRenderer(Level level, int breite, int hoehe) {
		this.level = level;
		this.bild = new BufferedImage(breite, hoehe, BufferedImage.TYPE_INT_ARGB);
		this.g = bild.createGraphics();
	}
	
	public Image renderBild() {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, bild.getWidth(), bild.getHeight());
		
		gezeichnet.clear();
		
		drawRecursive(level.wurzel, bild.getWidth() - 100, bild.getHeight() / 2);
		
		return bild;
	}
	
	private void drawRecursive(Knoten node, int sx, int sy) {
		drawKnoten(sx, sy, node.getOutput(), node == level.wurzel);
		
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
	
	
	private static class Punkt {
		int x;
		int y;
		
		public Punkt(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}
