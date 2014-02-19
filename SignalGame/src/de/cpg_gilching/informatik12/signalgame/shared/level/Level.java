package de.cpg_gilching.informatik12.signalgame.shared.level;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class Level implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Knoten wurzel;
	public AntwortKnoten[] antworten;
	private int richtigeAntwort;
	
	public Level(Knoten wurzel, AntwortKnoten[] antworten, int richtig) {
		this.wurzel = wurzel;
		this.antworten = antworten;
		this.richtigeAntwort = richtig;
	}
	
	public boolean istRichtig(int antwort) {
		return antwort == richtigeAntwort;
	}
	
	public void sende(OutputStream out) throws IOException {
		ObjectOutputStream oout = new ObjectOutputStream(out);
		oout.writeObject(this);
	}
	
	
	public static Level empfange(InputStream in) throws IOException {
		ObjectInputStream oin = new ObjectInputStream(in);
		
		try {
			Object o = oin.readObject();
			if (!(o instanceof Level))
				throw new IOException("ungültiger Datentyp empfangen!");
			
			return (Level) o;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int getRichtigeAntwort() {
		return richtigeAntwort;
	}
	
}
