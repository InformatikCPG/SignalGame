package de.cpg_gilching.informatik12.signalgame.server;

import java.io.IOException;
import java.util.List;

public class ServerOutput {
	
	private List<ClientAufServer> empfaenger;
	
	public ServerOutput(List<ClientAufServer> empfaenger) {
		this.empfaenger = empfaenger;
	}
	
	public void sendeNeuenSpieler(String spielername, int punktestand) {
		for (ClientAufServer csa : empfaenger) {
			try {
				csa.getDataOut().writeInt(1);
				csa.getDataOut().writeUTF(spielername);
				csa.getDataOut().writeInt(punktestand);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
