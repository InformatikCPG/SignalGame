package de.cpg_gilching.informatik12.signalgame.server.level;

import java.util.Set;

public interface Signalquelle {
	
	public boolean getOutput();
	
	public int getDepth();
	
	public int getTotalAmount(Set<Knoten> bekannt);
	
}
