package de.cpg_gilching.informatik12.signalgame.shared.level;

import java.io.Serializable;
import java.util.Set;

public interface Signalquelle extends Serializable {
	
	public boolean getOutput();
	
	public int getTiefe();
	
	public int getGesamtAnzahl(Set<Knoten> bekannt);
	
}
