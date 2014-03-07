package de.cpg_gilching.informatik12.signalgame.client;

import java.awt.BorderLayout;
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

import de.cpg_gilching.informatik12.signalgame.shared.Helfer;


/**
 * Das Fenster des Clients, das den Login-Bildschirm enthält.
 * Stellt die Verbindung zum Server her und startet bei Erfolg den {@link ControllerClient}.
 */
public class LoginFenster {
	
	private JFrame fenster;
	private JTextField eingabefeldBenutzername;
	private JTextField eingabefeldIPAdresse;
	private JTextField eingabefeldPort;
	
	public LoginFenster() {
		
		JPanel hauptPanel = new JPanel();
		hauptPanel.setLayout(new BoxLayout(hauptPanel, BoxLayout.Y_AXIS));
		hauptPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		loginScreenFüllen(hauptPanel);
		
		hauptPanel.setPreferredSize(new Dimension(300, hauptPanel.getPreferredSize().height));
		
		
		
		fenster = new JFrame("Signalgame: Login");
		//		frame.setResizable(false);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenster.setLayout(new BorderLayout());
		fenster.add(hauptPanel, BorderLayout.NORTH);
		fenster.pack();
		fenster.setMinimumSize(fenster.getSize());
		fenster.setLocationRelativeTo(null);
		fenster.setVisible(true);
		
	}
	
	private void loginScreenFüllen(JPanel hauptScreen) {
		//		JPanel hauptScreen = new JPanel();
		
		// =========== BENUTZERNAME ===============
		Box usernameBox = Box.createHorizontalBox();
		usernameBox.add(new JLabel("Benutzername:"));
		usernameBox.add(Box.createHorizontalStrut(10));
		
		eingabefeldBenutzername = new JTextField("Spieler" + Helfer.zufallsZahl(1000, 10000));
		
		usernameBox.add(eingabefeldBenutzername);
		// ========= BENUTZERNAME end =============
		
		// =========== IP-Adresse / Port ===============
		Box ipBox = Box.createHorizontalBox();
		ipBox.add(new JLabel("IP-Adresse:"));
		ipBox.add(Box.createHorizontalStrut(10));
		
		eingabefeldIPAdresse = new JTextField("localhost");
		
		eingabefeldPort = new JTextField("1337");
		
		
		ipBox.add(eingabefeldIPAdresse);
		ipBox.add(eingabefeldPort);
		// ========= IP-Adresse end =============
		
		
		// =========== Verbinden =================
		Box submitBox = Box.createHorizontalBox();
		
		JButton verbindenBtn = new JButton("Verbinden");
		verbindenBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginAbschicken();
			}
		});
		
		submitBox.add(Box.createHorizontalGlue());
		submitBox.add(verbindenBtn);
		submitBox.add(Box.createHorizontalGlue());
		// ======== Verbinden end ================
		
		
		
		usernameBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		ipBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		submitBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		hauptScreen.add(usernameBox);
		hauptScreen.add(Box.createVerticalStrut(10));
		hauptScreen.add(ipBox);
		hauptScreen.add(Box.createVerticalStrut(10));
		hauptScreen.add(submitBox);
		
		//		hauptScreen.add(hauptScreen, BorderLayout.CENTER);
	}
	
	public JFrame getFenster() {
		return fenster;
	}
	
	private void loginAbschicken() {
		Client client = new Client(eingabefeldIPAdresse.getText(), Integer.parseInt(eingabefeldPort.getText()), eingabefeldBenutzername.getText(), this);
		client.start();
	}
	
}
