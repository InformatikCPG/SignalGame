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
	private boolean timerAktiv;
	private int fragenCounter;
	
	public SpielVerhalten(Server server) {
		this.server = server;
		lg = new LevelGenerator();
		gestartet = false;
		geblockt = new ArrayList<ClientAufServer>();
		verbunden = server.getVerbunden();
		richtigBeantwortet = new ArrayList<ClientAufServer>();
		fragenCounter = 0;
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
		fragenCounter++;
		timerAktiv = false;
		
		switch (fragenCounter) {
		case 1:
			lg.setWurzelInputs(1);
			lg.setKnotenAnzahlMin(2);
			lg.setKnotenAnzahlMax(2);
			break;
		
		case 5:
			lg.setWurzelInputs(2);
			lg.setKnotenAnzahlMin(3);
			lg.setKnotenAnzahlMax(5);
			break;
		
		case 10:
			lg.setKnotenAnzahlMin(4);
			lg.setKnotenAnzahlMax(7);
			break;
		
		case 20:
			lg.setKnotenAnzahlMin(5);
			lg.setKnotenAnzahlMax(10);
			lg.setMaximalTiefe(8);
			break;
		}
		
		aktLevel = lg.generiereLevel();
		
		for (int i = 0; i < verbunden.size(); i++) {
			verbunden.get(i).setAntwort(-1);
			verbunden.get(i).sendeLevel(aktLevel);
		}
		
		geblockt.clear();
		richtigBeantwortet.clear();
	}
	
	public void checkAntwort(int antwort, ClientAufServer spieler) {
		boolean checkAntwort = aktLevel.istRichtig(antwort);
		if (!geblockt.contains(spieler)) {
			spieler.sendeMarkierung(antwort);
			geblockt.add(spieler);
			
			if (checkAntwort) {
				richtigBeantwortet.add(spieler);
			}
			
			for (int i = 0; i < verbunden.size(); i++) {
				verbunden.get(i).sendeHatBeantwortet(spieler.getSpielerName());
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
			
			if (geblockt.size() == verbunden.size() && !timerAktiv) {
				timerAktiv = true;
				
				for (int i = 0; i < verbunden.size(); i++) {
					if (!richtigBeantwortet.contains(verbunden.get(i))) {
						server.getPunktetafel().punkteGeben(verbunden.get(i), -1);
					}
					
					verbunden.get(i).sendeRichtigeAntwort(aktLevel.getRichtigeAntwort());
					
					for (int a = 0; a < verbunden.size(); a++) {
						verbunden.get(a).sendeGeantworteteAntwort(verbunden.get(i).getAntwort(), verbunden.get(i).getSpielerName());
					}
				}
				
				for (int i = 0; i < richtigBeantwortet.size(); i++) {
					server.getPunktetafel().punkteGeben(richtigBeantwortet.get(i), verbunden.size() - i);
				}
			}
			
			if (timerAktiv) {
				timer++;
				
				if (timer > 30) {
					neueFrage();
					timer = 0;
				}
			}
		}
	}
}
