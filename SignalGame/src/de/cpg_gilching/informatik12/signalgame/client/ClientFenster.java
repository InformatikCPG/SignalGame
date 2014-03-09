package de.cpg_gilching.informatik12.signalgame.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.IconView;

import de.cpg_gilching.informatik12.signalgame.client.level.AntwortRenderer;
import de.cpg_gilching.informatik12.signalgame.client.level.LevelPanel;
import de.cpg_gilching.informatik12.signalgame.shared.Helfer;
import de.cpg_gilching.informatik12.signalgame.shared.level.AntwortKnoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class ClientFenster {
	
	private JFrame fenster;
	private JPanel mainPanel;
	private JPanel mainPanel1;
	private JPanel mainPanel2;
	private JPanel spielerPanel;
	private AntwortKnoten antworten[];
	Client client;
	
	public ClientFenster(Client client) {
		
		this.client = client;
		
		fenster = new JFrame("Signalgame");
		fenster.setSize(1100, 650);
		fenster.setLocationRelativeTo(null);
		fenster.setResizable(true);
		fenster.setLayout(new BorderLayout());
		fenster.setMinimumSize(new Dimension(825, 450));
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenster.setVisible(true);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.darkGray);
		mainPanel.setLayout(new BorderLayout());
		fenster.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel1 = new JPanel();
		mainPanel1.setBackground(Color.darkGray);
		mainPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		mainPanel1.setLayout(new BorderLayout());
		mainPanel.add(mainPanel1, BorderLayout.CENTER);
		
		//mainPanel1.add(new JLabel(new ImageIcon("res/Correct.png")));
		
		mainPanel2 = new JPanel();
		mainPanel2.setBackground(Color.darkGray);
		mainPanel2.setPreferredSize(new Dimension(900, 150));
		mainPanel2.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.X_AXIS));
		mainPanel.add(mainPanel2, BorderLayout.PAGE_END);
		
		spielerPanel = new JPanel();
		spielerPanel.setBackground(Color.darkGray);
		spielerPanel.setPreferredSize(new Dimension(300, 600));
		spielerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.Y_AXIS));
		fenster.add(spielerPanel, BorderLayout.LINE_START);
		
		//Helfer.bildLaden("laden.gif")
		JLabel laden = new JLabel(new ImageIcon(Helfer.bildLaden("laden.gif")));
		mainPanel1.add(laden);
		
		final JButton bereitBtn = new JButton("Bereit?");
		bereitBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		bereitBtn.setPreferredSize(new Dimension(100, 50));
		bereitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientFenster.this.client.sendeBereit();
				bereitBtn.setBackground(Color.green);
			}
		});
		mainPanel1.add(bereitBtn, BorderLayout.PAGE_END);
		
	}
	
	public void spielerEinfuegen(String spielername, int startpunktanzahl) {
		spielerPanel.add(new SpielerElement(spielername, startpunktanzahl));
	}
	
	public void spielerEntfernen(String spielername) {
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			if (spielerPanel.getComponent(i).getName().equals(spielername)) {
				spielerPanel.remove(i);
			}
		}
	}
	
	public void spielerElementAktualisieren(String spielername, int punktanzahlneu) {
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			if (spielerPanel.getComponent(i).getName().equals(spielername)) {
				((SpielerElement) spielerPanel.getComponent(i)).punkteAktualisieren(punktanzahlneu);
			}
		}
	}
	
	public void spielerHatBeantwortet(String spielername) {
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			if (spielerPanel.getComponent(i).getName().equals(spielername)) {
				((SpielerElement) spielerPanel.getComponent(i)).tippAnzeigen(true);
			}
		}
	}
	
	public void antwortenEinfuegen(AntwortKnoten[] antworten) {
		mainPanel2.removeAll();
		this.antworten = antworten;
		
		for (int i = 0; i < antworten.length; i++) {
			final int tempI = i;
			JPanel box = new JPanel();
			box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
			
			JButton button = new JButton();
			button.setIcon(new ImageIcon(new AntwortRenderer(antworten[i], null).renderBild()));
			button.setRolloverIcon(new ImageIcon(new AntwortRenderer(antworten[i], Color.blue).renderBild()));
			button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					client.sendeAntwort(tempI);
				}
			});
			
			box.add(Box.createHorizontalGlue());
			box.add(button);
			box.add(Box.createHorizontalGlue());
			box.setBackground(null);
			
			mainPanel2.add(box);
		}
		
		mainPanel2.revalidate();
	}
	
	public void antwortenEntfernen() {
		mainPanel2.removeAll();
	}
	
	public void frageAnzeigen(Level level) {
		mainPanel1.removeAll();
		mainPanel1.add(new LevelPanel(level));
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			((SpielerElement) spielerPanel.getComponent(i)).tippAnzeigen(false);
		}
	}
	
	public void antwortMarkieren(int antwort) {
		((JButton) ((JPanel) mainPanel2.getComponent(antwort)).getComponent(1)).setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		//Mouse-Hover wird für alle Elemente im mainPanel2 ausgeschaltet.
		for (int i = 0; i < mainPanel2.getComponentCount(); i++) {
			((JButton) ((JPanel) mainPanel2.getComponent(i)).getComponent(1)).setRolloverIcon(null);
		}
	}
	
	public void korrigieren(int antwort, int korrekt) {
		if (antwort == korrekt) {
			((JButton) ((JPanel) mainPanel2.getComponent(antwort)).getComponent(1)).setBorder(BorderFactory.createLineBorder(Color.green, 5));
		}
		else if (antwort > -1) {
			((JButton) ((JPanel) mainPanel2.getComponent(antwort)).getComponent(1)).setBorder(BorderFactory.createLineBorder(Color.red, 5));
		}
		
		((JButton) ((JPanel) mainPanel2.getComponent(korrekt)).getComponent(1)).setBorder(BorderFactory.createLineBorder(Color.green, 5));
	}
	
	public void antwortAnzeigen(String spielername, int antwort, int richtigeAntwort) {
		AntwortKnoten knoten = antworten[antwort];
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			if (spielerPanel.getComponent(i).getName().equals(spielername)) {
				if(antwort == richtigeAntwort) {
					((SpielerElement) spielerPanel.getComponent(i)).antwortAnzeigen(knoten, true);
				}
				else {
					((SpielerElement) spielerPanel.getComponent(i)).antwortAnzeigen(knoten, false);
				}
			}
		}
	}
}
