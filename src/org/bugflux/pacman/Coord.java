package org.bugflux.pacman;

public class Coord {
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
	public boolean equals(Object o) {
		Coord c = (Coord)o;
		return r() == c.r() && c() == c.c(); 
	}
}
