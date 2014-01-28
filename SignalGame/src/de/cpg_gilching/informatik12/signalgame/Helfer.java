package de.cpg_gilching.informatik12.signalgame;

import java.util.List;
import java.util.Random;

public class Helfer {
	
	/**
	 * Eine {@link Random}-Instanz zum internen Erzeugen von Zufallszahlen.
	 */
	private static final Random rand = new Random();
	
	/**
	 * Gibt eine zufällige Ganzzahl zwischen zwei Werten zurück.
	 * 
	 * @param min
	 *            die kleinste mögliche Zahl (eingeschlossen)
	 * @param max
	 *            die größte mögliche Zahl (ausgeschlossen)
	 */
	public static int zufallsZahl(int min, int max) {
		return rand.nextInt(max - min) + min;
	}
	
	/**
	 * Gibt eine zufällige Ganzzahl zwischen 0 und max zurück.
	 * 
	 * @param max
	 *            die größte mögliche Zahl (ausgeschlossen)
	 */
	public static int zufallsZahl(int max) {
		return rand.nextInt(max);
	}
	
	/**
	 * Gibt ein zufälliges Element aus einer Liste zurück. Das Element kann
	 * optional automatisch aus der Liste entfernt werden.
	 * 
	 * @param liste
	 *            die Liste, aus der das Element genommen werden soll
	 * @param autoLöschen
	 *            true, wenn das gegebene Element automatisch entfernt werden
	 *            soll
	 * @return ein zufälliges Element
	 * @throws IllegalArgumentException
	 *             wenn die übergebene Liste leer oder null ist
	 */
	public static <T> T zufallsElement(List<T> liste, boolean autoLöschen) {
		if (liste == null || liste.isEmpty())
			throw new IllegalArgumentException("leere Liste");
		
		int index = Helfer.zufallsZahl(liste.size());
		
		if (autoLöschen) {
			return liste.remove(index);
		}
		else {
			return liste.get(index);
		}
	}
	
}
