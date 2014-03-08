package de.cpg_gilching.informatik12.signalgame.client.level;

import static de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer.KNOTEN_GROESSE;
import static de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer.bildFalse;
import static de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer.bildGate;
import static de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer.bildTrue;
import static de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer.getOffsetUnit;
import static de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer.getSeiteFromIndex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import de.cpg_gilching.informatik12.signalgame.shared.level.AntwortKnoten;

public class AntwortRenderer {
	
	private AntwortKnoten knoten;
	private BufferedImage bild = new BufferedImage(2 * KNOTEN_GROESSE + 8, 2 * KNOTEN_GROESSE + 8, BufferedImage.TYPE_INT_ARGB);
	private Color rahmen;
	
	public AntwortRenderer(AntwortKnoten knoten, Color rahmen) {
		this.knoten = knoten;
		this.rahmen = rahmen;
	}
	
	public Image renderBild() {
		Graphics2D g = bild.createGraphics();
		
		g.drawImage(bildGate, 4, 4, null);
		
		for (int i = 0; i < knoten.getAnzahlInputs(); i++) {
			boolean inputZustand = knoten.getInput(i);
			int seite = getSeiteFromIndex(i, knoten.getAnzahlInputs());
			
			Punkt pkt = getOffsetUnit(seite).mul(KNOTEN_GROESSE - 13).add(new Punkt(4 + KNOTEN_GROESSE, 4 + KNOTEN_GROESSE));
			
			BufferedImage img = (inputZustand ? bildTrue : bildFalse);
			g.drawImage(img, pkt.x - img.getWidth() / 2, pkt.y - img.getHeight() / 2, null);
		}
		
		if (rahmen != null) {
			g.setStroke(new BasicStroke(5.0f));
			g.setColor(rahmen);
			g.drawRect(2, 2, bild.getWidth() - 5, bild.getHeight() - 5);
		}
		
		return bild;
	}
	
}
