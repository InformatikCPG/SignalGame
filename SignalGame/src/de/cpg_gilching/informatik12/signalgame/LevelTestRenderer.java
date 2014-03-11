package de.cpg_gilching.informatik12.signalgame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.cpg_gilching.informatik12.signalgame.client.level.AntwortRenderer;
import de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer;
import de.cpg_gilching.informatik12.signalgame.server.LevelGenerator;
import de.cpg_gilching.informatik12.signalgame.shared.level.Knoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class LevelTestRenderer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static JFrame f;
	
	public static void main(String[] args) {
		LevelTestRenderer r = new LevelTestRenderer();
		
		f = new JFrame("SignalGame test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(r);
		f.pack();
		
		f.setVisible(true);
		
		r.requestFocusInWindow();
	}
	
	private LevelGenerator gen = new LevelGenerator();
	private Level lvl = gen.generiereLevel();
	private boolean showAnswers = true;
	
	public LevelTestRenderer() {
		setPreferredSize(new Dimension(800, 600));
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (Character.toLowerCase(e.getKeyChar()) == 'r') {
					lvl = gen.setKnotenAnzahlMin(3).setKnotenAnzahlMax(16).setMinimalTiefe(1).setMaximalTiefe(6).setWurzelInputs(3).generiereLevel();
					System.out.println("Anzahl Knoten: " + lvl.wurzel.getGesamtAnzahl(new HashSet<Knoten>()));
					repaint();
				}
				
				else if (Character.toLowerCase(e.getKeyChar()) == 'c') {
					setPreferredSize(new Dimension(700, 450));
					f.pack();
				}
				
				else if (Character.toLowerCase(e.getKeyChar()) == 'a') {
					showAnswers = !showAnswers;
					repaint();
				}
				
				else if (Character.toLowerCase(e.getKeyChar()) == 's') {
					BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = img.createGraphics();
					paint(g);
					g.dispose();
					
					try {
						ImageIO.write(img, "PNG", new File("__output.png"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					System.out.println("Bild wurde gespeichert!");
				}
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(new LevelRenderer(lvl, getWidth(), getHeight(), true).renderBild(), 0, 0, null);
		
		if (showAnswers) {
			for (int i = 0; i < lvl.antworten.length; i++) {
				g.drawImage(new AntwortRenderer(lvl.antworten[i], null).renderBild(), 10 + 80 * i, getHeight() - 80, null);
			}
		}
	}
}
