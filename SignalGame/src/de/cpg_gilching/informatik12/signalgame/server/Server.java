package de.cpg_gilching.informatik12.signalgame.server;

import java.util.ArrayList;

public class Server {
	
	int port;
	public boolean running;
	ArrayList<ClientAufServer> verbunden = new ArrayList<ClientAufServer>();
	
	public Server(int port) {
		System.out.println("Server mit Port " + port + " gestartet.");
		this.port = port;
		running = true;
		ServerAcceptClient acceptThread = new ServerAcceptClient(this, port);
		acceptThread.start();
	}
	
	public void serverStoppen() {
		running = false;
		System.out.println("Server wird gestoppt...");
	}
	
	public void hauptSchleife() {
		System.out.println("hauptSchleife() aufgerufen.");
		while (running) {
			try {
				Thread.sleep(100);
				synchronized (this) {
					for (int i = 0; i < verbunden.size(); i++) {
						int antwort = verbunden.get(i).getAntwort();
						if (antwort != -1) {
							// Platzhalter...
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void verbindeClient(ClientAufServer csa) {
		System.out.println("Client verbunden");
		verbunden.add(csa);
		csa.start();
	}
}
