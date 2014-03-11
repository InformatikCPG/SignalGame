package de.cpg_gilching.informatik12.signalgame;

import de.cpg_gilching.informatik12.signalgame.server.Server;

public class ServerLauncher {
	
	public static void main(String[] args) {
		int port = 1337;
		
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		
		new Server(port).hauptSchleife();
	}
	
}
