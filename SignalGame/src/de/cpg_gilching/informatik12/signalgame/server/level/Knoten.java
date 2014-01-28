package de.cpg_gilching.informatik12.signalgame.server.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Knoten implements Signalquelle {
	
	private boolean verarbeitet = false;
	private boolean berechnend = false;
	private boolean output;
	
	private List<Kante> inputs = new ArrayList<>();
	
	public Knoten() {
	}
	
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
	
	@Override
	public int getDepth() {
		if (berechnend) {
			System.err.println("circular dependency (@depth)!");
			return 0;
		}
		
		berechnend = true;
		
		int d = 0;
		for (Kante k : inputs)
			d = Math.max(d, k.quelle.getDepth());
		
		berechnend = false;
		
		return d + 1;
	}
	
	@Override
	public int getTotalAmount(Set<Knoten> bekannt) {
		bekannt.add(this);
		
		int a = 0;
		
		for (Kante k : inputs)
			if (!bekannt.contains(k.quelle))
				a += k.quelle.getTotalAmount(bekannt);
		
		return a + 1;
	}
	
	public void addInput(Kante input) {
		inputs.add(input);
	}
	
	public List<Kante> getInputs() {
		return inputs;
	}
	
	@Override
	public String toString() {
		return "Knoten@" + Integer.toString(hashCode(), 32);
	}
}
