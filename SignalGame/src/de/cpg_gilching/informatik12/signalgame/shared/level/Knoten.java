package de.cpg_gilching.informatik12.signalgame.shared.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Knoten implements Signalquelle {
	
	private boolean verarbeitet = false;
	private boolean berechnend = false;
	private boolean output;
	
	private boolean erzwungen = false;
	
	private List<Kante> inputs = new ArrayList<>();
	
	public Knoten() {
	}
	
	/**
	 * Modifiziert die Input-Kanten so, dass der erwartete Wert immer dem tatsächlich vorhandenen entspricht.
	 */
	public void loeseInputs() {
		for (Kante k : inputs) {
			k.zielzustand = k.quelle.getOutput();
		}
		
		berechneNeu();
	}
	
	public void erzwingeOutput(boolean wert) {
		erzwungen = true;
		output = wert;
	}
	
	public void berechneNeu() {
		verarbeitet = false;
		
		for (Kante k : inputs)
			if (k.quelle instanceof Knoten)
				((Knoten) k.quelle).berechneNeu();
	}
	
	@Override
	public boolean getOutput() {
		if (erzwungen)
			return output;
		
		if (!verarbeitet) {
			if (berechnend) {
				System.err.println("circular dependency!");
				return false;
			}
			
			output = true;
			
			berechnend = true;
			for (Kante k : inputs) {
				if (k.quelle.getOutput() != k.zielzustand)
					output = false;
			}
			berechnend = false;
			
			verarbeitet = true;
		}
		
		return output;
	}
	
	@Override
	public int getTiefe() {
		if (berechnend) {
			System.err.println("circular dependency (@depth)!");
			return 0;
		}
		
		berechnend = true;
		
		int d = 0;
		for (Kante k : inputs)
			d = Math.max(d, k.quelle.getTiefe());
		
		berechnend = false;
		
		return d + 1;
	}
	
	@Override
	public int getGesamtAnzahl(Set<Knoten> bekannt) {
		bekannt.add(this);
		
		int a = 0;
		
		for (Kante k : inputs)
			if (!bekannt.contains(k.quelle))
				a += k.quelle.getGesamtAnzahl(bekannt);
		
		return a + 1;
	}
	
	public Knoten addInput(Kante input) {
		inputs.add(input);
		return this;
	}
	
	public List<Kante> getInputs() {
		return inputs;
	}
	
	@Override
	public String toString() {
		return "Knoten@" + Integer.toString(hashCode(), 32);
	}
}
