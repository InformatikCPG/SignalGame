package de.cpg_gilching.informatik12.signalgame.shared.level;

public class Level {
	
	public Knoten wurzel;
	public AntwortKnoten[] antworten;
	
	public Level(Knoten wurzel, AntwortKnoten[] antworten) {
		this.wurzel = wurzel;
		this.antworten = antworten;
	}
	
}