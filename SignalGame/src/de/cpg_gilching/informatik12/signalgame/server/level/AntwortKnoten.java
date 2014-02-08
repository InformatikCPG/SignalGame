package de.cpg_gilching.informatik12.signalgame.server.level;

public class AntwortKnoten {
	
	private transient int addIndex = 0;
	
	private boolean[] inputs;
	
	public AntwortKnoten(int anzInputs) {
		inputs = new boolean[anzInputs];
	}
	
	public AntwortKnoten add(boolean wert) {
		inputs[addIndex++] = wert;
		return this;
	}
	
	public boolean getInput(int i) {
		return inputs[i];
	}
	
	public boolean[] getInputs() {
		return inputs;
	}
	
	public int getAnzahlInputs() {
		return inputs.length;
	}
	
}
