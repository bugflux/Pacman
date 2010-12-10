package org.bugflux.pacman;

import java.awt.Color;

import org.bugflux.pacman.entities.Walkable;

import pt.ua.games.PacmanGelem;

public class Pacman extends Walker {
	public Pacman(Walkable w, Coord c) {
		super(w, c, new PacmanGelem(Color.yellow, 75.0));
	}
}
