package de.cpg_gilching.informatik12.signalgame.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class ClientAufServer extends Thread {
	
	private Server server;
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;

	private int antwort;
	private boolean bereit = false;
	private String name = "Unbekannt";
	
	private boolean verbunden = true;
	
	ClientAufServer(Server server, Socket socket) {
		this.server = server;
		antwort = -1;
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
		return altAntwort;
	}
	
	public synchronized void setAntwort(int antwort) {
		this.antwort = antwort;
	}
	
	public synchronized void setBereit(boolean bereit) {
		this.bereit = bereit;
	}
	
	public synchronized boolean istBereit() {
		return bereit;
	}
	
	@Override
	public void run() {
		try {
			while (verbunden) {
				int id = dataIn.readInt();
				
				switch (id) {
				case 0:
					String spielername = dataIn.readUTF();
					if (spielername.length() > 16) {
						spielername = spielername.substring(0, 16);
					}

					setSpielerName(spielername);
					System.out.println("Name wurde auf " + getSpielerName() + " gesetzt.");
					
					server.getPunktetafel().clientHinzufuegen(this);
					break;
				case 1:
					setAntwort(dataIn.readInt());
					break;
				case 2:
					setBereit(true);
					System.out.println(getSpielerName() + " ist bereit.");
					
					if (server.getSpielVerhalten().getAktLevel() != null) {
						sendeLevel(server.getSpielVerhalten().getAktLevel());
					}
					break;
				default:
					System.out.println("Falsche ID von Client!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	
	public void sendeNeuenSpieler(String spielername, int punktestand) {
		try {
			dataOut.writeInt(1);
			dataOut.writeUTF(spielername);
			dataOut.writeInt(punktestand);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	public void sendePunktestand(String spielername, int neuerPunktestand) {
		try {
			dataOut.writeInt(2);
			dataOut.writeUTF(spielername);
			dataOut.writeInt(neuerPunktestand);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	public void sendeLevel(Level level) {
		try {
			dataOut.writeInt(10);
			level.sende(dataOut);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	public void sendeMarkierung(int antwort) {
		try {
			dataOut.writeInt(11);
			dataOut.writeInt(antwort);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	public void sendeRichtigeAntwort(int rA) {
		try {
			dataOut.writeInt(12);
			dataOut.writeInt(rA);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	public void sendeHatBeantwortet(String spielername) {
		try {
			dataOut.writeInt(3);
			dataOut.writeUTF(spielername);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	public void sendeGeantworteteAntwort(int rA, String spielername) {
		try {
			dataOut.writeInt(4);
			dataOut.writeInt(rA);
			dataOut.writeUTF(spielername);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	public void sendeGetrenntenSpieler(String spielername) {
		try {
			dataOut.writeInt(5);
			dataOut.writeUTF(spielername);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			verbindungTrennen();
		}
	}
	
	private void verbindungTrennen() {
		if (verbunden) {
			verbunden = false;
			server.trenneClient(this);
		}
	}
}
