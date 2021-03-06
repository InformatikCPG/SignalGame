package de.cpg_gilching.informatik12.signalgame.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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

import de.cpg_gilching.informatik12.signalgame.client.level.AntwortRenderer;
import de.cpg_gilching.informatik12.signalgame.client.level.LevelPanel;
import de.cpg_gilching.informatik12.signalgame.shared.Helfer;
import de.cpg_gilching.informatik12.signalgame.shared.level.AntwortKnoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class ClientFenster {
	
	private JFrame fenster;
	private JPanel mainPanel;
	private JPanel mainPanelMitte;
	private JPanel mainPanelUnten;
	private JPanel mainPanelLinks;
	private JPanel spielerPanel;
	private JPanel ladePanel;
	private JPanel tutorialPanel;
	private JPanel bereitPanel;
	private JPanel leeresPanel;
	private JPanel leeresPanel2;
	private AntwortKnoten antworten[];
	Client client;
	
	public ClientFenster(Client client) {
		
		this.client = client;
		
		spielerPanel = new JPanel();
		spielerPanel.setBackground(Color.darkGray);
		spielerPanel.setPreferredSize(new Dimension(300, 600));
		spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.Y_AXIS));
		
		JLabel ladengif = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Helfer.bildAlsURL("laden2.gif"))));
		
		ladePanel = new JPanel();
		ladePanel.setBackground(Color.darkGray);
		ladePanel.setPreferredSize(new Dimension(300, 70));
		ladePanel.setLayout(new BorderLayout());
			ladePanel.add(ladengif, BorderLayout.CENTER);
		ladePanel.setVisible(true);
		
		mainPanelLinks = new JPanel();
		mainPanelLinks.setBackground(Color.darkGray);
		mainPanelLinks.setPreferredSize(new Dimension(300, 600));
		mainPanelLinks.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		mainPanelLinks.setLayout(new BorderLayout());
			mainPanelLinks.add(spielerPanel, BorderLayout.PAGE_START);
			mainPanelLinks.add(ladePanel, BorderLayout.PAGE_END);
		
		final JButton tutorialBtn =  new JButton("Tutorial starten?");
		tutorialBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		tutorialBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tutorialBtn.setBackground(Color.yellow);
				ClientFenster.this.client.starteTutorial();
			}
		});
		tutorialPanel = new JPanel();
		tutorialPanel.setPreferredSize(new Dimension(300, 50));
		tutorialPanel.setLayout(new BorderLayout());
			tutorialPanel.add(tutorialBtn, BorderLayout.CENTER);
		
		final JButton bereitBtn = new JButton("Bereit?");
		bereitBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		bereitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientFenster.this.client.sendeBereit();
				bereitBtn.setBackground(Color.green);
			}
		});
		bereitPanel = new JPanel();
		bereitPanel.setPreferredSize(new Dimension(300, 50));
		bereitPanel.setLayout(new BorderLayout());
			bereitPanel.add(bereitBtn, BorderLayout.CENTER);
		
		leeresPanel = new JPanel();
		leeresPanel.setBackground(Color.darkGray);
		leeresPanel.setPreferredSize(new Dimension(300, 300));
		
		leeresPanel2 = new JPanel();
		leeresPanel2.setBackground(Color.darkGray);
		leeresPanel2.setPreferredSize(new Dimension(300, 50));
		
		mainPanelMitte = new JPanel();
		mainPanelMitte.setBackground(Color.darkGray);
		mainPanelMitte.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		mainPanelMitte.setLayout(new BoxLayout(mainPanelMitte, BoxLayout.Y_AXIS));
			mainPanelMitte.add(leeresPanel);
			mainPanelMitte.add(bereitPanel);
			mainPanelMitte.add(leeresPanel2);
			mainPanelMitte.add(tutorialPanel);
		
		mainPanelUnten = new JPanel();
		mainPanelUnten.setBackground(Color.darkGray);
		mainPanelUnten.setPreferredSize(new Dimension(900, 150));
		mainPanelUnten.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		mainPanelUnten.setLayout(new BoxLayout(mainPanelUnten, BoxLayout.X_AXIS));
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.darkGray);
		mainPanel.setLayout(new BorderLayout());
			mainPanel.add(mainPanelMitte, BorderLayout.CENTER);
			mainPanel.add(mainPanelUnten, BorderLayout.PAGE_END);
		
		fenster = new JFrame("Signalgame");
		fenster.setSize(1000, 600);
		fenster.setLocationRelativeTo(null);
		fenster.setResizable(true);
		fenster.setLayout(new BorderLayout());
		fenster.setMinimumSize(new Dimension(825, 450));
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenster.add(mainPanel, BorderLayout.CENTER);
			fenster.add(mainPanelLinks, BorderLayout.LINE_START);
		fenster.setVisible(true);
	}
	
	public void spielerEinfuegen(String spielername, int startpunktanzahl) {
		spielerPanel.add(new SpielerElement(spielername, startpunktanzahl));
	}
	
	public void spielerEntfernen(String spielername) {
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			if (spielerPanel.getComponent(i).getName().equals(spielername)) {
				spielerPanel.remove(i);
				spielerPanel.validate();
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
		mainPanelUnten.removeAll();
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
			
			mainPanelUnten.add(box);
		}
		
		mainPanelUnten.revalidate();
	}
	
	public void antwortenEntfernen() {
		mainPanelUnten.removeAll();
	}
	
	public void frageAnzeigen(Level level) {
		mainPanelMitte.removeAll();
		mainPanelMitte.add(new LevelPanel(level));
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			((SpielerElement) spielerPanel.getComponent(i)).tippAnzeigen(false);
		}
		ladePanel.setVisible(false);
	}
	
	public void antwortMarkieren(int antwort) {
		((JButton) ((JPanel) mainPanelUnten.getComponent(antwort)).getComponent(1)).setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		//Mouse-Hover wird für alle Elemente im mainPanel2 ausgeschaltet.
		for (int i = 0; i < mainPanelUnten.getComponentCount(); i++) {
			((JButton) ((JPanel) mainPanelUnten.getComponent(i)).getComponent(1)).setRolloverIcon(null);
		}
	}
	
	public void korrigieren(int antwort, int korrekt) {
		ladePanel.setVisible(true);
		if (antwort == korrekt) {
			((JButton) ((JPanel) mainPanelUnten.getComponent(antwort)).getComponent(1)).setBorder(BorderFactory.createLineBorder(Color.green, 5));
		}
		else if (antwort > -1) {
			((JButton) ((JPanel) mainPanelUnten.getComponent(antwort)).getComponent(1)).setBorder(BorderFactory.createLineBorder(Color.red, 5));
		}
		
		((JButton) ((JPanel) mainPanelUnten.getComponent(korrekt)).getComponent(1)).setBorder(BorderFactory.createLineBorder(Color.green, 5));
	}
	
	public void antwortAnzeigen(String spielername, int antwort, int richtigeAntwort) {
		AntwortKnoten knoten = antworten[antwort];
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			if (spielerPanel.getComponent(i).getName().equals(spielername)) {
				if (antwort == richtigeAntwort) {
					((SpielerElement) spielerPanel.getComponent(i)).antwortAnzeigen(knoten, true);
				}
				else {
					((SpielerElement) spielerPanel.getComponent(i)).antwortAnzeigen(knoten, false);
				}
			}
		}
	}
}
