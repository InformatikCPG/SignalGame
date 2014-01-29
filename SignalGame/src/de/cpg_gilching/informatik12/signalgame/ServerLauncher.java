package de.cpg_gilching.informatik12.signalgame;

import de.cpg_gilching.informatik12.signalgame.server.Server;

public class ServerLauncher {

	public static void main(String[] args) {
		new Server(1337).hauptSchleife();
	}

}
