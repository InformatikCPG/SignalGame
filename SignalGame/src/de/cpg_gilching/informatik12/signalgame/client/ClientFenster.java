package de.cpg_gilching.informatik12.signalgame.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ClientFenster {

	private JFrame fenster;
	private JPanel mainPanel;
	private JPanel mainPanel1;
	private JPanel mainPanel2;
	private JPanel spielerPanel;
	
	public ClientFenster() {
		
		fenster = new JFrame("Signalgame");
		fenster.setSize(1100, 600);
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
			mainPanel.add(mainPanel1, BorderLayout.CENTER);
		
			mainPanel2 = new JPanel();
			mainPanel2.setBackground(Color.darkGray);
			mainPanel2.setPreferredSize(new Dimension(900, 150));
			mainPanel2.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.X_AXIS));
			mainPanel.add(mainPanel2, BorderLayout.PAGE_END);
		
		spielerPanel = new JPanel();
		spielerPanel.setBackground(Color.darkGray);
		spielerPanel.setPreferredSize(new Dimension(250,600));
		spielerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.Y_AXIS));
		fenster.add(spielerPanel, BorderLayout.LINE_START);
		
	}
	
	public void spielerEinfuegen(String spielername, int startpunktanzahl) {
		spielerPanel.add(new SpielerElement(spielername, startpunktanzahl));
	}
	
	public void spielerEntfernen(String spielername) {
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			if(spielerPanel.getComponent(i).getName().equals(spielername)){
				spielerPanel.remove(i);
			}
		}
	}
	
	public void spielerElementAktualisieren(String spielername, int punktanzahlneu){
		for (int i = 0; i < spielerPanel.getComponentCount(); i++) {
			if(spielerPanel.getComponent(i).getName().equals(spielername)){
				((SpielerElement) spielerPanel.getComponent(i)).punkteAktualisieren(punktanzahlneu);
			}
		}
	}
	
	public void antwortenEinfuegen(Object[] antworten) {
		mainPanel2.removeAll();
		for (int i = 0; i < antworten.length; i++) {
			
			JPanel box = new JPanel();
			box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
			
			JButton button = new JButton("test");
			//noch anpassen mit Object[i] --^
			button.setBackground(Color.red);
			button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.gray, Color.black));
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			box.add(Box.createHorizontalGlue());
			box.add(button);
			box.add(Box.createHorizontalGlue());
			box.setBackground(null);
			
			mainPanel2.add(box);
		}
	}
	
	public void antwortenEntfernen() {
		mainPanel2.removeAll();
	}
	
}
