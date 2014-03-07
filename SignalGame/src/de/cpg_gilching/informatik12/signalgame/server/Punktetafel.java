package de.cpg_gilching.informatik12.signalgame.server;

import java.util.HashMap;
import java.util.Map;

public class Punktetafel {
	
	private Map<ClientAufServer, Integer> allePunkte = new HashMap<>();
	
	public void clientHinzufügen(ClientAufServer csa) {
		// nicht doppelt einfügen
		if (allePunkte.containsKey(csa)) {
			return;
		}
		
		allePunkte.put(csa, 0);
		
		for (ClientAufServer anderer : allePunkte.keySet()) {
			// den neuen Spieler über die bisherigen Spieler informieren
			csa.sendeNeuenSpieler(anderer.getSpielerName(), allePunkte.get(anderer));
			
			// alle anderen Spieler über den neuen Spieler informieren
			if (anderer != csa)
				anderer.sendeNeuenSpieler(csa.getSpielerName(), 0);
		}
	}
	
	public void punkteGeben(ClientAufServer csa, int punkte) {
		int neuePunkte = allePunkte.get(csa) + punkte;
		allePunkte.put(csa, neuePunkte);
		
		for (ClientAufServer anderer : allePunkte.keySet()) {
			anderer.sendePunktestand(csa.getSpielerName(), neuePunkte);
		}
	}
	
}
