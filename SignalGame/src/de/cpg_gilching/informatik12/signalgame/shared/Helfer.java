package de.cpg_gilching.informatik12.signalgame.shared;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

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
	
	public static <T> void mischeArray(T[] array) {
		for (int i = 0, n = array.length; i < n; i++) {
			int swap1 = rand.nextInt(n);
			int swap2 = rand.nextInt(n);
			
			T temp = array[swap1];
			array[swap1] = array[swap2];
			array[swap2] = temp;
		}
	}
	
	
	/**
	 * Lädt ein Bild aus dem "bilder"-Verzeichnis oder aus der JAR-Datei.
	 * 
	 * @param name der Name der Bilddatei, relativ zum "bilder"-Verzeichnis
	 * @return ein BufferedImage mit den Daten des geladenen Bilds; im Fehlerfall wird ein leeres Bild zurückgegeben
	 */
	public static BufferedImage bildLaden(String name) {
		InputStream bildStream = null;
		try {
			bildStream = Helfer.class.getResourceAsStream("/bilder/" + name);
			if (bildStream == null) {
				File bildDatei = new File("bilder", name);
				if (bildDatei.isFile()) {
					bildStream = new FileInputStream(bildDatei);
				}
				else {
					System.err.println("Bild " + name + " wurde nicht gefunden!");
					return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				}
			}
			
			BufferedImage bild = ImageIO.read(bildStream);
			bildStream.close();
			return bild;
		} catch (IOException e) {
			System.err.println("Fehler beim Laden von Bild " + name);
			e.printStackTrace();
			
			return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		} finally {
			if (bildStream != null)
				try {
					bildStream.close();
				} catch (IOException e) {
					System.err.println("Fehler beim Schließen des Streams von Bild " + name);
					e.printStackTrace();
					return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				}
		}
	}
	
}
