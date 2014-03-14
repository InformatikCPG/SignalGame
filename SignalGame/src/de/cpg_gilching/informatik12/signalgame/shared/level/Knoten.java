package de.cpg_gilching.informatik12.signalgame.shared.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Knoten implements Signalquelle {
	private static final long serialVersionUID = 1L;
	
	private transient boolean verarbeitet = false;
	private transient boolean berechnend = false;
	private transient boolean output;
	
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
	
	public void berechneNeu() {
		verarbeitet = false;
		
		for (Kante k : inputs)
			if (k.quelle instanceof Knoten)
				((Knoten) k.quelle).berechneNeu();
	}
	
	/**
	 * Berechnet rekursiv den Output-Zustand des Knotens, basierend auf seinen Inputs.<br>
	 * Der Wert wird gecacht, bis {@link #berechneNeu()} aufgerufen wird.
	 */
	@Override
	public boolean getOutput() {
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
	
	/**
	 * Gibt rekursiv die Tiefe dieses Knotens zurück (= der maximale Abstand eines Kindelements).<br>
	 * In dieser Version unbenutzt.
	 */
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
	
	/**
	 * Zählt rekursiv die gesamte Anzahl der Knoten, die ab diesem Knoten enthalten sind.
	 * 
	 * @param bekannt eine Menge von Knoten, die bereits gezählt wurden; dieses wird mit allen besuchten Knoten erweitert
	 */
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
