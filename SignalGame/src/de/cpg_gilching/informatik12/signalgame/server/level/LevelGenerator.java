package de.cpg_gilching.informatik12.signalgame.server.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.cpg_gilching.informatik12.signalgame.Helfer;

public class LevelGenerator {
	
	private Random rand = new Random();
	
	private Statikquelle statikInput = new Statikquelle();
	
	private Set<Signalquelle> printed = new HashSet<>();
	
	private List<Knoten> _inputsGenerieren(Knoten k, int d, List<Knoten> crossInputs) {
		// Abbruchbedingung
		if (d >= Helfer.zufallsZahl(2, 4)) {
			k.addInput(new Kante(statikInput, rand.nextBoolean()));
			return Collections.emptyList();
		}
		
		// Verzweigungen erstellen
		int a = Helfer.zufallsZahl(2, 5) / 2; // 67%->1, 33%->2
		
		if (d == 0)
			a = 2;
		
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
	
	public Knoten generiere() {
		Knoten end = new Knoten();
		_inputsGenerieren(end, 0, Collections.<Knoten> emptyList());
		
		//		print(end, true, 0);
		
		//		System.out.println("berechne ergebnis ...");
		//		System.out.println("ergebnis: " + end.getOutput());
		
		return end;
	}
	
	public void generiere2() {
		Knoten end = new Knoten();
		
		List<Knoten> knoten = new ArrayList<>();
		List<Signalquelle> quellen = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			knoten.add(new Knoten());
		}
		knoten.add(end);
		
		quellen.addAll(knoten);
		quellen.add(new Statikquelle());
		
		for (Knoten k : knoten) {
			k.addInput(new Kante(findeQuelle(k, quellen), rand.nextBoolean()));
			
			if (rand.nextBoolean())
				k.addInput(new Kante(findeQuelle(k, quellen), rand.nextBoolean()));
		}
		
		print(end, true, 0);
		
		System.out.println("berechne ergebnis ...");
		System.out.println("ergebnis: " + end.getOutput());
	}
	
	private Signalquelle findeQuelle(Knoten k, List<Signalquelle> quellen) {
		Signalquelle elem;
		while (true) {
			elem = Helfer.zufallsElement(quellen, false);
			
			if (elem == k)
				continue;
			
			boolean drin = false;
			for (Kante kante : k.getInputs()) {
				if (kante.quelle == elem)
					drin = true;
			}
			
			if (!drin)
				break;
		}
		
		return elem;
	}
	
	public void print(Signalquelle q, boolean wert, int depth) {
		if (depth >= 10) {
			System.out.println("                    ...");
			return;
		}
		
		for (int i = 0; i < depth; i++)
			System.out.print("  ");
		
		System.out.print(q + " (depth: " + q.getDepth() + ") :: " + wert);
		
		if (!printed.add(q)) {
			System.out.println(" >>");
			return;
		}
		System.out.println();
		
		if (q instanceof Knoten)
			for (Kante kante : ((Knoten) q).getInputs())
				print(kante.quelle, kante.zielzustand, depth + 1);
	}
}
