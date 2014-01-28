package de.cpg_gilching.informatik12.signalgame.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends Thread {
	
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private String spielername;
	
	public Client(String IP, int port, String spielername) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				int id = dataIn.readInt();
				
				/*
				 * switch (id) { case 0: setSpielerName(dataIn.readUTF());
				 * break; case 1: setAntwort(dataIn.readInt()); break; default:
				 * System.out.println("Falsche ID von Client!"); }
				 */
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
