package de.cpg_gilching.informatik12.signalgame.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import de.cpg_gilching.informatik12.signalgame.client.level.AntwortRenderer;
import de.cpg_gilching.informatik12.signalgame.shared.Helfer;
import de.cpg_gilching.informatik12.signalgame.shared.level.AntwortKnoten;

public class SpielerElement extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel spielername;
	private JLabel punktanzahl;
	private JLabel tippAnzeige;
	
	public SpielerElement(String spielernameneu, int startpunktanzahl) {
		spielername = new JLabel(spielernameneu);
		punktanzahl = new JLabel("" + startpunktanzahl);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.black));
		//setBorder(BorderFactory.createDashedBorder(, thickness, length, spacing, rounded));
		
		add(this.spielername);
		add(Box.createHorizontalGlue());
		tippAnzeige = new JLabel();
		tippAnzeige.setVisible(false);
		add(tippAnzeige);
		add(punktanzahl);
		
		setName(spielernameneu);
		setMaximumSize(new Dimension(290, 70));
		
		spielername.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		spielername.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		
		punktanzahl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		punktanzahl.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
	}
	
	public void punkteAktualisieren(int punktanzahlneu) {
		punktanzahl.setText("" + punktanzahlneu);
	}
	
	public void tippAnzeigen(boolean t) {
		tippAnzeige.setIcon(new ImageIcon(Helfer.bildLaden("gesucht.png")));
		tippAnzeige.setVisible(t);
	}
	
	public void antwortAnzeigen(AntwortKnoten antwort) {
		tippAnzeige.setIcon(new ImageIcon(new AntwortRenderer(antwort, null).renderBild()));
	}
	
}
