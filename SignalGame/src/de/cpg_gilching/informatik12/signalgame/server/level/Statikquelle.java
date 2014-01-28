package de.cpg_gilching.informatik12.signalgame.server.level;

import java.util.Set;


public class Statikquelle implements Signalquelle {
	
	@Override
	public boolean getOutput() {
		return true; // immer an
	}
	
	@Override
	public int getDepth() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Statikquelle@" + Integer.toString(hashCode(), 32);
	}
	
	@Override
	public int getTotalAmount(Set<Knoten> bekannt) {
		return 0;
	}
	
}
