package de.cpg_gilching.informatik12.signalgame.shared.level;

public class Kante {
	
	public final Signalquelle quelle;
	public boolean zielzustand;
	
	public Kante(Signalquelle quelle, boolean zielzustand) {
		this.quelle = quelle;
		this.zielzustand = zielzustand;
	}
	
}
