package de.cpg_gilching.informatik12.signalgame.shared.level;

import java.io.Serializable;
import java.util.Set;

/**
 * Abstrahiertes interface für einen Input eines Knotens.
 * <p/>
 * Dies kann entweder ein echter {@link Knoten} sein, oder eine {@link Statikquelle} (der Input des ganzen Levels).
 */
public interface Signalquelle extends Serializable {
	
	public boolean getOutput();
	
	public int getTiefe();
	
	public int getGesamtAnzahl(Set<Knoten> bekannt);
	
}
