package de.cpg_gilching.informatik12.signalgame.server;

import java.util.ArrayList;

public class Server {
	
	private int port;
	public boolean running;
	
	private ArrayList<ClientAufServer> verbunden = new ArrayList<ClientAufServer>();
	private ArrayList<ClientAufServer> neueSpieler = new ArrayList<ClientAufServer>();
	
	private Punktetafel punktetafel;
	
	public Server(int port) {
		System.out.println("Server mit Port " + port + " gestartet.");
		this.port = port;
		running = true;
		punktetafel = new Punktetafel();
		
		ServerAcceptClient acceptThread = new ServerAcceptClient(this, this.port);
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
				
				// temporäre Liste in die verbunden-Liste integrieren
				neueSpielerKopieren();
				
				for (int i = 0; i < verbunden.size(); i++) {
					int antwort = verbunden.get(i).getAntwort();
					if (antwort != -1) {
						// Platzhalter...
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void neueSpielerKopieren() {
		verbunden.addAll(neueSpieler);
		neueSpieler.clear();
	}
	
	public synchronized void verbindeClient(ClientAufServer csa) {
		System.out.println("Client verbunden");
		neueSpieler.add(csa);
		csa.start();
	}
	
	public Punktetafel getPunktetafel() {
		return punktetafel;
	}
	
}
