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

	public boolean equals(Coord x) {
		return x.r() == r && x.c() == c;
	}
}
