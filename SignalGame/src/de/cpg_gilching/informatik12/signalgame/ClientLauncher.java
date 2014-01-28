package de.cpg_gilching.informatik12.signalgame;

import de.cpg_gilching.informatik12.signalgame.client.Client;

public class ClientLauncher {

	public static void main(String[] args) {
		new Client("localhost", 80, "Taimonania").start();
	}

}
