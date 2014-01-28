package de.cpg_gilching.informatik12.signalgame.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientAufServer extends Thread {
	
	private Socket socket;
	private DataInputStream dataIn;
	private int antwort;
	
	private String name = "Unbekannt";
	
	ClientAufServer(Socket socket) {
		try {
			this.socket = socket;
			InputStream is = socket.getInputStream();
			dataIn = new DataInputStream(is);
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
}
