package de.cpg_gilching.informatik12.signalgame.server;

import java.util.HashMap;
import java.util.Map;

public class Punktetafel {
	
	private Map<ClientAufServer, Integer> punkte = new HashMap<>();
	
	public void clientHinzufügen(ClientAufServer csa) {
		// nicht doppelt einfügen
		if (punkte.containsKey(csa)) {
			return;
		}
		
		punkte.put(csa, 0);
		
		for (ClientAufServer anderer : punkte.keySet()) {
			// den neuen Spieler über die bisherigen Spieler informieren
			csa.sendeNeuenSpieler(anderer.getSpielerName(), punkte.get(anderer));
			
			// alle anderen Spieler über den neuen Spieler informieren
			if (anderer != csa)
				anderer.sendeNeuenSpieler(csa.getSpielerName(), 0);
		}
	}
	
}
