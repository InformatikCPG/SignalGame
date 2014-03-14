package de.cpg_gilching.informatik12.signalgame.client.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.cpg_gilching.informatik12.signalgame.shared.Helfer;
import de.cpg_gilching.informatik12.signalgame.shared.level.Kante;
import de.cpg_gilching.informatik12.signalgame.shared.level.Knoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Level;
import de.cpg_gilching.informatik12.signalgame.shared.level.Signalquelle;

/**
 * Stellt ein {@link Level} dar.
 */
public class LevelRenderer {
	
	public static final int KNOTEN_GROESSE = 32;
	public static final int GAP = 32;
	
	public static BufferedImage bildGate = Helfer.bildLaden("gate.png");
	public static BufferedImage bildTrue = Helfer.bildLaden("true.png");
	public static BufferedImage bildFalse = Helfer.bildLaden("false.png");
	public static BufferedImage bildGesucht = Helfer.bildLaden("gesucht.png");
	
	private Level level;
	private boolean zeigeZustaende;
	
	private BufferedImage bild;
	private Graphics2D g;
	
	private Map<Signalquelle, Punkt> gezeichnet = new HashMap<>();
	private Map<Knoten, Integer> statikVerbindungen = new HashMap<>();
	
	
	public LevelRenderer(Level level, int breite, int hoehe) {
		this(level, breite, hoehe, false);
	}
	
	public LevelRenderer(Level level, int breite, int hoehe, boolean zeigeZustaende) {
		this.level = level;
		this.zeigeZustaende = zeigeZustaende;
		this.bild = new BufferedImage(breite, hoehe, BufferedImage.TYPE_INT_ARGB);
		this.g = bild.createGraphics();
	}
	
	public Image renderBild() {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(2.0f));
		
		g.setColor(Color.white);
		g.fillRect(0, 0, bild.getWidth(), bild.getHeight());
		
		gezeichnet.clear();
		statikVerbindungen.clear();
		
		int wurzelX = bild.getWidth() - 100;
		int wurzelY = bild.getHeight() / 2;

		drawRecursive(level.wurzel, wurzelX, wurzelY);
		drawVerbindung(wurzelX, wurzelY, bild.getWidth() + 100, bild.getHeight() / 2, 1, true);
		drawStatikquellen();
		
		return bild;
	}
	
	private void drawRecursive(Knoten node, int sx, int sy) {
		List<Kante> inputs = node.getInputs();
		
		drawKnoten(sx, sy, inputs, node == level.wurzel);
		
		int subHeight = getSubHeight(node);
		int accHeight = 0;
		
		for (int i = 0, s = inputs.size(); i < s; i++) {
			Kante k = inputs.get(i);
			
			int seite = getSeiteFromIndex(i, s);
			
			if (k.quelle instanceof Knoten) {
				if (!gezeichnet.containsKey(k.quelle)) {
					int currSub = getSubHeight((Knoten) k.quelle);
					
					int nx = sx - 120 + Helfer.zufallsZahl(-10, 11);
					int ny = sy - subHeight / 2 + accHeight + currSub / 2;

					accHeight += currSub + GAP;
					
					gezeichnet.put(k.quelle, new Punkt(nx, ny));
				}
				
				drawRecursive((Knoten) k.quelle, gezeichnet.get(k.quelle).x, gezeichnet.get(k.quelle).y);

				// Koordinaten des Input-Knotens auslesen
				Punkt p = gezeichnet.get(k.quelle);
				
				Boolean zustand = null;
				if (zeigeZustaende)
					zustand = ((Knoten) k.quelle).getOutput();
				
				drawVerbindung(p.x, p.y, sx, sy, seite, zustand);
			}
			
			else {
				// Statikquelle
				statikVerbindungen.put(node, seite);
			}
		}
	}
	
	private void drawKnoten(int x, int y, List<Kante> inputs, boolean istGesucht) {
		if (istGesucht) {
			g.drawImage(bildGesucht, x - KNOTEN_GROESSE, y - KNOTEN_GROESSE, null);
		}
		else {
			g.drawImage(bildGate, x - KNOTEN_GROESSE, y - KNOTEN_GROESSE, null);
			
			for (int i = 0; i < inputs.size(); i++) {
				Kante k = inputs.get(i);
				int seite = getSeiteFromIndex(i, inputs.size());
				
				Punkt pkt = getOffsetUnit(seite).mul(KNOTEN_GROESSE - 13).add(new Punkt(x, y));
				
				BufferedImage img = (k.zielzustand ? bildTrue : bildFalse);
				g.drawImage(img, pkt.x - img.getWidth() / 2, pkt.y - img.getHeight() / 2, null);
			}
		}
	}
	
	private void drawVerbindung(int vonX, int vonY, int nachX, int nachY, int seite, Boolean aktiv) {
		Punkt nachKante = new Punkt(nachX, nachY).add(getOffsetKante(seite));
		Punkt nachAbstand = new Punkt(nachX, nachY).add(getOffsetAbstand(seite));
		
		Path2D.Float path = new Path2D.Float();
		path.moveTo(vonX + KNOTEN_GROESSE, vonY);
		path.lineTo(vonX + KNOTEN_GROESSE * 3 / 2, vonY);
		//		path.lineTo(vonX + KNOTEN_GROESSE * 3 / 2, nachAbstand.y);
		path.lineTo(nachAbstand.x, nachAbstand.y);
		path.lineTo(nachKante.x, nachKante.y);
		
		
		g.setColor(aktiv == null ? Color.gray : (aktiv ? new Color(0x00CC33) : new Color(0xCC3300)));
		g.draw(path);
	}
	
	private void drawStatikquellen() {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for (Entry<Knoten, Integer> e : statikVerbindungen.entrySet()) {
			Punkt p = gezeichnet.get(e.getKey());
			Punkt offset = getOffsetAbstand(e.getValue());
			
			int x = p.x + offset.x;
			int y = p.y + offset.y;
			
			minX = Math.min(minX, x);
			minY = Math.min(minY, y);
			maxY = Math.max(maxY, y);
		}
		
		g.setColor(new Color(0x00CC33));
		
		for (Entry<Knoten, Integer> e : statikVerbindungen.entrySet()) {
			Punkt ziel = gezeichnet.get(e.getKey());
			Punkt offset1 = getOffsetAbstand(e.getValue());
			Punkt offset2 = getOffsetKante(e.getValue());
			
			Path2D.Float path = new Path2D.Float();
			path.moveTo(minX, ziel.y + offset1.y);
			path.lineTo(ziel.x + offset1.x, ziel.y + offset1.y);
			path.lineTo(ziel.x + offset2.x, ziel.y + offset2.y);
			g.draw(path);
		}
		
		g.drawLine(minX, minY, minX, maxY);
		g.drawLine(0, (minY + maxY) / 2, minX, (minY + maxY) / 2);
	}
	
	public static Punkt getOffsetKante(int seite) {
		return getOffsetUnit(seite).mul(KNOTEN_GROESSE);
	}
	
	public static Punkt getOffsetAbstand(int seite) {
		return getOffsetUnit(seite).mul(KNOTEN_GROESSE * 3 / 2);
	}
	
	public static Punkt getOffsetUnit(int seite) {
		switch (seite) {
		case 0:
			return new Punkt(0, -1);
		case 1:
			return new Punkt(-1, 0);
		case 2:
			return new Punkt(0, 1);
		default:
			throw new IllegalArgumentException("ungültige seite");
		}
	}
	
	public static int getSeiteFromIndex(int i, int gesamt) {
		int seite;
		switch (gesamt) {
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
		
		return seite;
	}
	
	private int getSubHeight(Knoten basis) {
		int hsum = 0;
		int gaps = -1;
		
		for (Kante k : basis.getInputs()) {
			if (k.quelle instanceof Knoten) {
				Knoten kind = (Knoten) k.quelle;
				hsum += getSubHeight(kind);
				gaps++;
			}
		}
		
		if (hsum == 0)
			hsum = KNOTEN_GROESSE * 2;
		else if (gaps > 0)
			hsum += gaps * GAP;
		
		return hsum;
	}

}
