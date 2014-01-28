package de.cpg_gilching.informatik12.signalgame.server.level;

public class Kante {
	
	public final Signalquelle quelle;
	public final boolean zielzustand;
	
	public Kante(Signalquelle quelle, boolean zielzustand) {
		this.quelle = quelle;
		this.zielzustand = zielzustand;
	}
	
}
