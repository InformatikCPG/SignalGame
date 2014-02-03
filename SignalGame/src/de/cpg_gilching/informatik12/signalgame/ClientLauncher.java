package de.cpg_gilching.informatik12.signalgame;

import de.cpg_gilching.informatik12.signalgame.client.ClientFenster;

public class ClientLauncher {

	public static void main(String[] args) {
		ClientFenster fenster = new ClientFenster();
		fenster.spielerEinfuegen("Hans", 1);
		fenster.spielerEinfuegen("Depp", 2);
		fenster.antwortenEinfuegen(new Object[4]);
	}

}
