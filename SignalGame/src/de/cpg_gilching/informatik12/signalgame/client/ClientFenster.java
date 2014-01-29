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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.cpg_gilching.informatik12.signalgame.Helfer;

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
		fenster.setMinimumSize(new Dimension(550, 300));
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenster.setVisible(true);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.black);
		mainPanel.setLayout(new BorderLayout());
		fenster.add(mainPanel, BorderLayout.CENTER);
		
			mainPanel1 = new JPanel();
			mainPanel1.setBackground(Color.black);
			mainPanel1.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
			mainPanel.add(mainPanel1, BorderLayout.CENTER);
		
			mainPanel2 = new JPanel();
			mainPanel2.setBackground(Color.black);
			mainPanel2.setPreferredSize(new Dimension(900, 150));
			mainPanel2.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
			mainPanel.add(mainPanel2, BorderLayout.PAGE_END);
		
		spielerPanel = new JPanel();
		spielerPanel.setBackground(Color.black);
		spielerPanel.setPreferredSize(new Dimension(250,600));
		spielerPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
		spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.Y_AXIS));
		fenster.add(spielerPanel, BorderLayout.LINE_START);
		
		
	}
	
}
