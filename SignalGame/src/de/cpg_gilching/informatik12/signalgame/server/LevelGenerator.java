package de.cpg_gilching.informatik12.signalgame.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import de.cpg_gilching.informatik12.signalgame.shared.Helfer;
import de.cpg_gilching.informatik12.signalgame.shared.level.AntwortKnoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Kante;
import de.cpg_gilching.informatik12.signalgame.shared.level.Knoten;
import de.cpg_gilching.informatik12.signalgame.shared.level.Level;
import de.cpg_gilching.informatik12.signalgame.shared.level.Statikquelle;

public class LevelGenerator {
	
	// Einstellungen
	private int minimalTiefe = 1;
	private int maximalTiefe = 6;
	private int knotenAnzahl = -1;
	private int wurzelInputs = -1;
	
	// zum Generieren
	private Random rand = new Random();
	
	public LevelGenerator() {
	}
	
	public LevelGenerator setMinimalTiefe(int tiefe) {
		minimalTiefe = tiefe;
		return this;
	}
	
	public LevelGenerator setMaximalTiefe(int tiefe) {
		maximalTiefe = tiefe;
		return this;
	}
	
	public LevelGenerator setKnotenAnzahl(int anzahl) {
		knotenAnzahl = anzahl;
		return this;
	}
	
	public LevelGenerator setWurzelInputs(int anzahl) {
		if (anzahl != -1 && (anzahl < 1 || anzahl > 3))
			throw new IllegalArgumentException("anzahl der inputs der Wurzel muss zwischen 1 und 3 liegen!");
		
		wurzelInputs = anzahl;
		return this;
	}
	
	public Level generiereLevel() {
		Knoten wurzel;
		int anzahl;
		int versuche = 0;
		HashSet<Knoten> alleKnoten;
		
		// Es werden so lange Aufgaben generiert, bis eines mit der richtigen Anzahl an Knoten herauskommt.
		// Ist dies nach 200 Versuchen immer noch nicht der Fall, wird abgebrochen und das letzte Ergebnis trotzdem verwendet.
		do {
			wurzel = generiereWurzel();
			alleKnoten = new HashSet<>();
			anzahl = wurzel.getGesamtAnzahl(alleKnoten);
			versuche++;
		} while (knotenAnzahl > 0 && anzahl != knotenAnzahl && versuche < 200);
		
		// Der Zielknoten soll immer an sein.
		wurzel.loeseInputs();
		assert wurzel.getOutput() == true;
		
		//		Knoten gesucht = generiereGesucht(wurzel, alleKnoten);
		//		
		//		if (gesucht == null) {
		//			// Es wurde kein geeigneter Knoten gefunden, neuer Versuch
		//			return generiereLevel();
		//		}
		
		AntwortKnoten[] antworten = generiereAntworten(wurzel);
		Helfer.mischeArray(antworten);
		
		System.out.format("Level mit %d Knoten in %d Versuchen generiert.%n", anzahl, versuche);
		
		return new Level(wurzel, antworten);
	}
	
	public Knoten generiereWurzel() {
		Knoten wurzel = new Knoten();
		_inputsGenerieren(wurzel, 0, Collections.<Knoten> emptyList());
		return wurzel;
	}
	
	private List<Knoten> _inputsGenerieren(Knoten k, int d, List<Knoten> crossInputs) {
		// Abbruchbedingung
		if (d >= Helfer.zufallsZahl(minimalTiefe, maximalTiefe - 1)) {
			k.addInput(new Kante(Statikquelle.INSTANZ, rand.nextBoolean()));
			return Collections.emptyList();
		}
		
		// Verzweigungen erstellen
		int a = Helfer.zufallsZahl(2, 5) / 2; // 67%->1, 33%->2
		
		if (d == 0 && wurzelInputs != -1) // wurzel
			a = wurzelInputs;
		
		List<Knoten> zweigCrossInputs = new ArrayList<>();
		List<Knoten> neueKnoten = new ArrayList<>();
		
		for (int i = 0; i < a; i++) {
			Knoten input;
			
			if (!crossInputs.isEmpty() && rand.nextInt(3) == 0) {
				input = Helfer.zufallsElement(crossInputs, true);
			}
			else {
				input = new Knoten();
				List<Knoten> neuGeneriert = _inputsGenerieren(input, d + 1, zweigCrossInputs);
				
				neueKnoten.addAll(neuGeneriert);
				zweigCrossInputs.addAll(neuGeneriert);
			}
			
			neueKnoten.add(input);
			
			k.addInput(new Kante(input, rand.nextBoolean()));
		}
		
		return neueKnoten;
	}
	
	//	private Knoten generiereGesucht(Knoten wurzel, HashSet<Knoten> alleKnoten) {
	//		// Finde einen geeigneten Knoten, nach dem man suchen muss.
	//		// Der Knoten kann nur verwendet werden, wenn durch das Umschalten des Outputs des Knotens das Level nicht mehr gelöst wird.
	//		ArrayList<Knoten> knotenListe = new ArrayList<>(alleKnoten);
	//		Knoten gesucht = null;
	//		while (gesucht == null && !knotenListe.isEmpty()) {
	//			Knoten kandidat = Helfer.zufallsElement(knotenListe, true);
	//			
	//			// richtigen Outputwert auslesen
	//			boolean kandidatOut = kandidat.getOutput();
	//			
	//			// Outputwert umdrehen
	//			kandidat.erzwingeOutput(!kandidatOut);
	//			
	//			// Output des Levels überprüfen
	//			wurzel.berechneNeu();
	//			if (wurzel.getOutput() == false) {
	//				// Knoten ist geeignet!
	//				gesucht = kandidat;
	//			}
	//			
	//			// Knoten wieder auf Normalzustand setzen
	//			kandidat.erzwingeOutput(kandidatOut);
	//			wurzel.berechneNeu();
	//		}
	//		
	//		return gesucht;
	//	}
	
	private AntwortKnoten[] generiereAntworten(Knoten richtig) {
		// Antwortmöglichkeiten generieren
		AntwortKnoten[] antworten = new AntwortKnoten[4];
		List<Kante> richtigInputs = richtig.getInputs();
		
		switch (richtigInputs.size()) {
		case 1:
			// richtig
			antworten[0] = new AntwortKnoten(1).add(richtigInputs.get(0).zielzustand);
			// invertiert
			antworten[1] = new AntwortKnoten(1).add(!richtigInputs.get(0).zielzustand);
			// sinnloses mit 2 inputs
			antworten[2] = new AntwortKnoten(2).add(rand.nextBoolean()).add(rand.nextBoolean());
			// sinnloses mit 2 inputs, muss aber anders sein als das drüber
			antworten[3] = new AntwortKnoten(2).add(!antworten[2].getInput(0)).add(rand.nextBoolean());
			break;
		
		case 2:
			// richtig
			antworten[0] = new AntwortKnoten(2).add(richtigInputs.get(0).zielzustand).add(richtigInputs.get(1).zielzustand);
			// alle anderen invertierten Möglichkeiten
			antworten[1] = new AntwortKnoten(2).add(!richtigInputs.get(0).zielzustand).add(richtigInputs.get(1).zielzustand);
			antworten[2] = new AntwortKnoten(2).add(!richtigInputs.get(0).zielzustand).add(!richtigInputs.get(1).zielzustand);
			antworten[3] = new AntwortKnoten(2).add(richtigInputs.get(0).zielzustand).add(!richtigInputs.get(1).zielzustand);
			break;
		
		case 3:
			boolean r1 = richtigInputs.get(0).zielzustand;
			boolean r2 = richtigInputs.get(1).zielzustand;
			boolean r3 = richtigInputs.get(2).zielzustand;
			
			// @formatter:off
			AntwortKnoten[] möglichkeiten = {
					new AntwortKnoten(3).add(!r1).add(r2).add(r3),
					new AntwortKnoten(3).add(r1).add(!r2).add(r3),
					new AntwortKnoten(3).add(r1).add(r2).add(!r3),
					new AntwortKnoten(3).add(!r1).add(!r2).add(r3),
					new AntwortKnoten(3).add(!r1).add(r2).add(!r3),
					new AntwortKnoten(3).add(r1).add(!r2).add(!r3),
					new AntwortKnoten(3).add(!r1).add(!r2).add(!r3)
			};
			// @formatter:on
			List<AntwortKnoten> möglichkeitenL = new ArrayList<>(Arrays.asList(möglichkeiten));
			
			// richtig
			antworten[0] = new AntwortKnoten(3).add(r1).add(r2).add(r3);
			// 3 ausgewählte aus allen Möglichkeiten
			antworten[1] = Helfer.zufallsElement(möglichkeitenL, true);
			antworten[2] = Helfer.zufallsElement(möglichkeitenL, true);
			antworten[3] = Helfer.zufallsElement(möglichkeitenL, true);
			break;
		
		default:
			throw new IllegalStateException("knoten mit ungültiger Anzahl an Inputs!");
		}
		
		return antworten;
	}
	
}