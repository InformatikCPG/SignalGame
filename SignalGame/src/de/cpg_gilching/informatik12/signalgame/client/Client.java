package de.cpg_gilching.informatik12.signalgame.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class Client extends Thread {
	
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private String spielername;
	private ClientFenster clientFenster;

	
	private int markierteAntwort = -1;
	private int richtigeAntwort = -1;
	
	public Client(String IP, int port, String spielername, LoginFenster loginFenster) {
		try {
			System.out.println("Client verbindet zu " + IP + ":" + port + " mit dem Spielernamen " + spielername);
			socket = new Socket(IP, port);
			socket.setTcpNoDelay(true);
			InputStream is = socket.getInputStream();
			dataIn = new DataInputStream(is);
			
			OutputStream os = socket.getOutputStream();
			dataOut = new DataOutputStream(os);
			
			this.spielername = spielername;
			dataOut.writeInt(0);
			dataOut.writeUTF(spielername);
			System.out.println("Verbindung zum Server erfolgreich.");
			
			loginFenster.getFenster().dispose();
			clientFenster = new ClientFenster(this);
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(loginFenster.getFenster(), "Einigung zwischen Client und Server hat sich nicht etabliert.");
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				int id = dataIn.readInt();
				
				switch (id) {
				case 1:
					String spielername = dataIn.readUTF();
					int startpunktanzahl = dataIn.readInt();
					clientFenster.spielerEinfuegen(spielername, startpunktanzahl);
					break;
				
				case 2:
					String spielername2 = dataIn.readUTF();
					int neuePunkte = dataIn.readInt();
					clientFenster.spielerElementAktualisieren(spielername2, neuePunkte);
					break;
				
				case 3:
					String spielername3 = dataIn.readUTF();
					clientFenster.spielerHatBeantwortet(spielername3);
					break;
				
				case 4:
					int rA = dataIn.readInt();
					String spielername4 = dataIn.readUTF();
					clientFenster.antwortAnzeigen(spielername4, rA, richtigeAntwort);
					break;
				
				case 10:
					Level level = Level.empfange(dataIn);
					clientFenster.frageAnzeigen(level);
					clientFenster.antwortenEinfuegen(level.antworten);
					
					markierteAntwort = -1;
					richtigeAntwort = -1;
					break;
				
				case 11:
					markierteAntwort = dataIn.readInt();
					clientFenster.antwortMarkieren(markierteAntwort);
					break;
				
				case 12:
					richtigeAntwort = dataIn.readInt();
					clientFenster.korrigieren(markierteAntwort, richtigeAntwort);
					break;
				
				default:
					System.out.println("Falsche ID von Server!");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendeAntwort(int n) {
		try {
			dataOut.writeInt(1);
			dataOut.writeInt(n);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendeBereit() {
		try {
			dataOut.writeInt(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void starteTutorial() {
		new TutorialFenster();
	}
}
