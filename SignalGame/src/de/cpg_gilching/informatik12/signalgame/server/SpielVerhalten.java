package de.cpg_gilching.informatik12.signalgame.server;

import java.util.ArrayList;

import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class SpielVerhalten {
	
	private Server server;
	private LevelGenerator lg;
	private Level aktLevel;
	private boolean gestartet;
	
	public SpielVerhalten(Server server) {
		this.server = server;
		lg = new LevelGenerator();
		gestartet = false;
	}
	
	public boolean alleBereitPrüfen() {
		ArrayList<ClientAufServer> verbunden = server.getVerbunden();
		for (int i = 0; i < verbunden.size(); i++) {
			if (!verbunden.get(i).istBereit()) {
				return false;
			}
		}
		return true;
	}
	
	public void neueFrage() {
		aktLevel = lg.generiereLevel();
	}
	
	public void antwortEmpfangen() {
		
	}
	
	public void spielTick() {
		if (!gestartet && alleBereitPrüfen()) {
			gestartet = true;
			neueFrage();
		}
	}
}
