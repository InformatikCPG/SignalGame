package de.cpg_gilching.informatik12.signalgame.client.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import de.cpg_gilching.informatik12.signalgame.shared.level.AntwortKnoten;

public class AntwortRenderer {
	
	private AntwortKnoten knoten;
	private BufferedImage bild = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
	
	public AntwortRenderer(AntwortKnoten knoten) {
		this.knoten = knoten;
	}
	
	public Image renderBild() {
		Graphics2D g = bild.createGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, bild.getWidth(), bild.getHeight());
		
		g.setColor(Color.black);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		
		String s = "";
		for (boolean b : knoten.getInputs())
			s += (b ? "Y" : "N");
		
		g.drawString(s, 32, 32);
		
		return bild;
	}
	
}
