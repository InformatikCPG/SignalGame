package de.cpg_gilching.informatik12.signalgame.server;

import java.util.ArrayList;

public class Server {

	int port;
	public boolean running;
	ArrayList<ClientAufServer> verbunden = new ArrayList<ClientAufServer>();

	public Server(int port) {
		this.port = port;
		running = true;
		ServerAcceptClient acceptThread = new ServerAcceptClient(this, port);
		acceptThread.start();
	}

	public void serverStoppen() {
		running = false;
	}

	public void hauptSchleife() {
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
		verbunden.add(csa);
	}
}
