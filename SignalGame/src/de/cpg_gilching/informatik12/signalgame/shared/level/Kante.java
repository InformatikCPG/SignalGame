package de.cpg_gilching.informatik12.signalgame.shared.level;

import java.io.Serializable;

public class Kante implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final Signalquelle quelle;
	public boolean zielzustand;
	
	public Kante(Signalquelle quelle, boolean zielzustand) {
		this.quelle = quelle;
		this.zielzustand = zielzustand;
	}
	
}
