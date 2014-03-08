package de.cpg_gilching.informatik12.signalgame.client.level;

class Punkt {
	int x;
	int y;
	
	public Punkt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Punkt add(Punkt p) {
		x += p.x;
		y += p.y;
		return this;
	}
	
	public Punkt mul(int faktor) {
		x *= faktor;
		y *= faktor;
		return this;
	}
}