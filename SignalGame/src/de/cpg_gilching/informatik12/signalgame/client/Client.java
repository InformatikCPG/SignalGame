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
				
				case 10:
					Level level = Level.empfange(dataIn);
					clientFenster.antwortenEinfuegen(level.antworten);
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
}
