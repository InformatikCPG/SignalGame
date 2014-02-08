package de.cpg_gilching.informatik12.signalgame.test;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.HashSet;

import de.cpg_gilching.informatik12.signalgame.server.level.Knoten;
import de.cpg_gilching.informatik12.signalgame.server.level.LevelGenerator;

public class StatistikTest {
	
	
	public static void main(String[] args) {
		int iterations = 500000;
		LevelGenerator generator = new LevelGenerator();
		HashSet<Knoten> bekannt = new HashSet<Knoten>();
		
		int[] res = new int[128];
		
		System.out.println("starte simulation ...");
		
		for (int i = 0; i < iterations; i++) {
			Knoten root = generator.generiereWurzel();
			
			bekannt.clear();
			int anzahl = root.getGesamtAnzahl(bekannt);
			
			if (anzahl >= res.length)
				System.err.println("overflow!");
			else
				res[anzahl]++;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < res.length; i++) {
			sb.append(i).append('\t').append(res[i]).append("\r\n");
		}
		
		StringSelection sel = new StringSelection(sb.toString());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, sel);
		
		System.out.println("fertig, ergebnisse in der zwischenablage!");
	}
	
}
