package org.bugflux.pacman;

import java.awt.Color;

import org.bugflux.pacman.entities.Walkable;

public class Pacman extends Walker {
	public Pacman(Walkable w, Coord c) {
		super(w, c, new _PacmanGelem());
	}
}

class _PacmanGelem extends pt.ua.games.PacmanGelem {
	public _PacmanGelem() {
		super(Color.yellow, 75.0);
	}
}