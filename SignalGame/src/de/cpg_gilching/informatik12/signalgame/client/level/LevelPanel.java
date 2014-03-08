package de.cpg_gilching.informatik12.signalgame.client.level;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class LevelPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Level level;
	private Image levelBild;
	
	public LevelPanel(Level level) {
		this.level = level;
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				doRender();
			}
		});
	}
	
	private void doRender() {
		levelBild = new LevelRenderer(level, getWidth(), getHeight()).renderBild();
	}
	
	@Override
	public void paint(Graphics g) {
		if (levelBild == null)
			doRender();
		
		g.drawImage(levelBild, 0, 0, null);
	}
	
}
