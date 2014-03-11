package de.cpg_gilching.informatik12.signalgame.server;

import java.util.ArrayList;

public class Server {
	
	private int port;
	public boolean running;
	private SpielVerhalten spielVerhalten;
	
	private ArrayList<ClientAufServer> verbunden = new ArrayList<ClientAufServer>();
	private ArrayList<ClientAufServer> neueSpieler = new ArrayList<ClientAufServer>();
	private ArrayList<ClientAufServer> getrennteSpieler = new ArrayList<ClientAufServer>();
	
	private Punktetafel punktetafel;
	
	public Server(int port) {
		System.out.println("Server mit Port " + port + " gestartet.");
		this.port = port;
		running = true;
		punktetafel = new Punktetafel();
		spielVerhalten = new SpielVerhalten(this);
		
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
				spielerKopieren();
				spielVerhalten.spielTick();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void spielerKopieren() {
		verbunden.addAll(neueSpieler);
		neueSpieler.clear();
		
		for (int i = 0; i < getrennteSpieler.size(); i++) {
			verbunden.remove(getrennteSpieler.get(i));
			punktetafel.clientEntfernen(getrennteSpieler.get(i));
			spielVerhalten.behandleDisconnect(getrennteSpieler.get(i));
		}
		getrennteSpieler.clear();
	}
	
	public synchronized void verbindeClient(ClientAufServer csa) {
		System.out.println("Client verbunden");
		neueSpieler.add(csa);
		csa.start();
	}
	
	public synchronized void trenneClient(ClientAufServer csa) {
		System.out.println("Trenne Client " + csa.getSpielerName());
		getrennteSpieler.add(csa);
	}

	public Punktetafel getPunktetafel() {
		return punktetafel;
	}
	
	public ArrayList<ClientAufServer> getVerbunden() {
		return verbunden;
	}
	
	public SpielVerhalten getSpielVerhalten() {
		return spielVerhalten;
	}

}
