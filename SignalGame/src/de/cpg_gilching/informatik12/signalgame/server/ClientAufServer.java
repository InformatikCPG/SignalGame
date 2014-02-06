package de.cpg_gilching.informatik12.signalgame.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientAufServer extends Thread {
	
	private Server server;
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private int antwort;
	
	private String name = "Unbekannt";
	
	ClientAufServer(Server server, Socket socket) {
		this.server = server;
		try {
			this.socket = socket;
			InputStream is = socket.getInputStream();
			dataIn = new DataInputStream(is);
			OutputStream os = socket.getOutputStream();
			dataOut = new DataOutputStream(os);
			setDaemon(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized String getSpielerName() {
		return name;
	}
	
	public synchronized void setSpielerName(String name) {
		this.name = name;
	}
	
	public synchronized int getAntwort() {
		int altAntwort = antwort;
		antwort = -1;
		return altAntwort;
	}
	
	public synchronized void setAntwort(int antwort) {
		this.antwort = antwort;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				int id = dataIn.readInt();
				
				switch (id) {
				case 0:
					setSpielerName(dataIn.readUTF());
					System.out.println("Name wurde auf " + getSpielerName() + " gesetzt.");
					
					server.getPunktetafel().clientHinzufügen(this);
					//					server.getAusgabe().sendeNeuenSpieler(getSpielerName(), 0);
					break;
				case 1:
					setAntwort(dataIn.readInt());
					break;
				default:
					System.out.println("Falsche ID von Client!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendeNeuenSpieler(String spielername, int punktestand) {
		try {
			dataOut.writeInt(1);
			dataOut.writeUTF(spielername);
			dataOut.writeInt(punktestand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
