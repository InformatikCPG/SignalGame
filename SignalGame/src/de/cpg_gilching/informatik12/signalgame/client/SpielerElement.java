package de.cpg_gilching.informatik12.signalgame.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class SpielerElement extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel spielername;
	private JLabel punktanzahl;
	
	public SpielerElement(String spielernameneu, int startpunktanzahl) {
		spielername = new JLabel(spielernameneu);
		punktanzahl = new JLabel("" + startpunktanzahl);
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.black));
		//setBorder(BorderFactory.createDashedBorder(, thickness, length, spacing, rounded));
		add(this.spielername, BorderLayout.LINE_START);
		add(punktanzahl, BorderLayout.LINE_END);
		setName(spielernameneu);
		setMaximumSize(new Dimension(240,50));
		
		spielername.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		spielername.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		
		punktanzahl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		punktanzahl.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
	}
	
	public void punkteAktualisieren(int punktanzahlneu) {
		punktanzahl.setText("" + punktanzahlneu);
	}
	
}
