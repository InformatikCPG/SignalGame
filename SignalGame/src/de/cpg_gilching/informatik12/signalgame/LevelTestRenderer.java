package de.cpg_gilching.informatik12.signalgame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer;
import de.cpg_gilching.informatik12.signalgame.server.LevelGenerator;
import de.cpg_gilching.informatik12.signalgame.shared.level.Knoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class LevelTestRenderer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		LevelTestRenderer r = new LevelTestRenderer();
		
		JFrame f = new JFrame("SignalGame test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(r);
		f.pack();
		
		f.setVisible(true);
		
		r.requestFocusInWindow();
	}
	
	private LevelGenerator gen = new LevelGenerator();
	private Level lvl = gen.generiereLevel();
	
	public LevelTestRenderer() {
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
	public void paint(Graphics g) {
		g.drawImage(new LevelRenderer(lvl, getWidth(), getHeight()).renderBild(), 0, 0, null);
	}
	
}
