package org.bugflux.pacman;

import java.awt.Color;

import pt.ua.gboard.CharGelem;

public class FreezeOpposingBonus extends GenericBonus {

	public FreezeOpposingBonus() {
		super(new CharGelem('X', Color.red), Property.FreezeOpposing, 15);
	}

}
