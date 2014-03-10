package de.cpg_gilching.informatik12.signalgame.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.cpg_gilching.informatik12.signalgame.client.level.LevelRenderer;
import de.cpg_gilching.informatik12.signalgame.shared.Helfer;

public class TutorialFenster {
	
	private JFrame fenster;
	private JPanel mainPanel;
	private JPanel mainPanelMitte;
	private JPanel mainPanelUnten;
	private JButton weiterBtn;
	private JButton zurueckBtn;
	private int zaehler;
	private BufferedImage[] bilder;
	
	public TutorialFenster() {
		
		zaehler = 0;
		bilderLaden();
		
		weiterBtn = new JButton("Weiter!");
		weiterBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		weiterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				weiterBtn.setBackground(Color.green);
				weiter();
			}
		});
		zurueckBtn = new JButton("Zurück!");
		zurueckBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		zurueckBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zurueckBtn.setBackground(Color.red);
				zurueck();
			}
		});
		
		mainPanelUnten = new JPanel();
		mainPanelUnten.setLayout(new BorderLayout());
		mainPanelUnten.setPreferredSize(new Dimension(700,50));
		mainPanelUnten.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			mainPanelUnten.add(weiterBtn, BorderLayout.LINE_END);
			mainPanelUnten.add(zurueckBtn, BorderLayout.LINE_START);
		
		mainPanelMitte = new JPanel();
		mainPanelMitte.setLayout(new BorderLayout());
		mainPanelMitte.setBorder(BorderFactory.createLineBorder(Color.black, 2));
				
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
			mainPanel.add(mainPanelMitte, BorderLayout.CENTER);
			mainPanel.add(mainPanelUnten, BorderLayout.PAGE_END);
		
		fenster = new JFrame("Tutorial - Signalgame");
		fenster.setSize(new Dimension(710, 510));
		fenster.setLocationRelativeTo(null);
		fenster.setResizable(false);
		fenster.setLayout(new BorderLayout());
		fenster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fenster.add(mainPanel);
		fenster.setVisible(true);
		
		aktualisieren();
	}
	
	public void weiter() {
		if(zaehler < bilder.length -1) {
			zaehler++;
		}
		else {
		}
		aktualisieren();
	}
	public void zurueck() {
		if(zaehler > 0) {
			zaehler = zaehler - 1;
		}
		else {
		}
		aktualisieren();
	}
	
	public void aktualisieren() {
		JLabel bild = new JLabel(new ImageIcon(bilder[zaehler]));
		mainPanelMitte.add(bild, BorderLayout.CENTER);
		if(zaehler == bilder.length -1) {
			weiterBtn.setVisible(false);
		}
	}
	
	public void bilderLaden() {
		bilder = new BufferedImage[1];
		bilder[0] = Helfer.bildLaden("tutorial0.png");
		//for() ausfüllen
		//for(int i=0; i<1; i++) {
		//	bilder[i] = Helfer.bildLaden("tutorial" + i + ".png");
		//}
	}
	
}
