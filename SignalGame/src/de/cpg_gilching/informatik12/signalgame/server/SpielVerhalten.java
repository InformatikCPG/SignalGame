package de.cpg_gilching.informatik12.signalgame.server;

import java.util.ArrayList;

import de.cpg_gilching.informatik12.signalgame.shared.level.Level;

public class SpielVerhalten {
	
	private Server server;
	private LevelGenerator lg;
	private Level aktLevel;
	private boolean gestartet;
	private ArrayList<ClientAufServer> geblockt;
	private ArrayList<ClientAufServer> verbunden;
	private ArrayList<ClientAufServer> richtigBeantwortet;
	private int timer;
	
	public SpielVerhalten(Server server) {
		this.server = server;
		lg = new LevelGenerator();
		gestartet = false;
		geblockt = new ArrayList<ClientAufServer>();
		verbunden = server.getVerbunden();
		richtigBeantwortet = new ArrayList<ClientAufServer>();
	}
	
	public boolean alleBereitPrüfen() {
		
		if (verbunden.size() < 2) {
			return false;
		}
		
		for (int i = 0; i < verbunden.size(); i++) {
			if (!verbunden.get(i).istBereit()) {
				return false;
			}
		}
		return true;
	}
	
	public void neueFrage() {
		aktLevel = lg.generiereLevel();
		
		for (int i = 0; i < verbunden.size(); i++) {
			verbunden.get(i).sendeLevel(aktLevel);
		}
		geblockt.clear();
		richtigBeantwortet.clear();
	}
	
	public void checkAntwort(int antwort, ClientAufServer spieler) {
		boolean checkAntwort = aktLevel.istRichtig(antwort);
		if (!geblockt.contains(spieler)) {
			spieler.sendeErgebnis(checkAntwort);
			geblockt.add(spieler);
			
			if (checkAntwort) {
				richtigBeantwortet.add(spieler);
			}
		}
	}
	
	public void spielTick() {
		
		if (!gestartet && alleBereitPrüfen()) {
			System.out.println("Spiel wird gestartet!");
			gestartet = true;
			neueFrage();
		}
		
		if (gestartet) {
			for (int i = 0; i < verbunden.size(); i++) {
				int antwort = verbunden.get(i).getAntwort();
				if (antwort != -1) {
					checkAntwort(antwort, verbunden.get(i));
				}
			}
			
			if (geblockt.size() + 1 == verbunden.size()) {
				for (int i = 0; i < verbunden.size(); i++) {
					if (!geblockt.contains(verbunden.get(i))) {
						geblockt.add(verbunden.get(i));
					}
					verbunden.get(i).sendeRichtigeAntwort(aktLevel.getRichtigeAntwort());
				}
				
				for (int i = 0; i < richtigBeantwortet.size(); i++) {
					server.getPunktetafel().punkteGeben(richtigBeantwortet.get(i), verbunden.size() - 1 - i);
				}
			}
			
			if (geblockt.size() == verbunden.size()) {
				timer++;
				
				if (timer > 30) {
					neueFrage();
					timer = 0;
				}
			}
		}
	}
}
