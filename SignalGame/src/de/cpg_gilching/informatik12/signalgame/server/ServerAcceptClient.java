package de.cpg_gilching.informatik12.signalgame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAcceptClient extends Thread {
	
	Server server;
	int port;
	
	ServerAcceptClient(Server server, int port) {
		this.server = server;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket s = new ServerSocket(port);
			
			while (server.running) {
				Socket socket = s.accept();
				socket.setTcpNoDelay(true);
				ClientAufServer csa = new ClientAufServer(server, socket);
				server.verbindeClient(csa);
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			server.serverStoppen();
		}
	}
}
