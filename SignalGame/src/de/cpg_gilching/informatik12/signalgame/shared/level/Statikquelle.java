package de.cpg_gilching.informatik12.signalgame.shared.level;

import java.util.Set;


public class Statikquelle implements Signalquelle {
	
	/**
	 * Singleton, da diese Klasse zustandslos ist und deswegen das Objekt wiederverwendet werden kann.
	 */
	public final static Statikquelle INSTANZ = new Statikquelle();
	
	private Statikquelle() {
	}
	
	@Override
	public boolean getOutput() {
		return true; // immer an
	}
	
	@Override
	public int getTiefe() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Statikquelle@" + Integer.toString(hashCode(), 32);
	}
	
	@Override
	public int getGesamtAnzahl(Set<Knoten> bekannt) {
		return 0;
	}
	
}
