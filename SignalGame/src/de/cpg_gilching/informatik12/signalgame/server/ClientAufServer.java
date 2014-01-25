package de.cpg_gilching.informatik12.signalgame.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientAufServer extends Thread {

	private Socket socket;
	private DataInputStream in;
	private int antwort;

	private String name = "Unbekannt";

	ClientAufServer(Socket socket) {
		try {
			this.socket = socket;
			InputStream is = socket.getInputStream();
			in = new DataInputStream(is);
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
				int id = in.readInt();

				switch (id) {
				case 0:
					setSpielerName(in.readUTF());
					break;
				case 1:
					setAntwort(in.readInt());
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
