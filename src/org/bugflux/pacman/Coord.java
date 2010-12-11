package org.bugflux.pacman;

public class Coord implements Comparable<Coord> {
	private int r, c;

	public Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}

	public int r() {
		return r;
	}

	public int c() {
		return c;
	}

	@Override
	public int compareTo(Coord c) {
		return (r() - c.r()) + (c() - c.c()); 
	}
}
