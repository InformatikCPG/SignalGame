package de.cpg_gilching.informatik12.signalgame.shared.level;

import java.util.Set;

public interface Signalquelle {
	
	public boolean getOutput();
	
	public int getTiefe();
	
	public int getGesamtAnzahl(Set<Knoten> bekannt);
	
}
