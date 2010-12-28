package org.bugflux.pacman;

import java.awt.Color;

import pt.ua.gboard.CharGelem;

public class InvincibilityBonus extends GenericBonus {
	public InvincibilityBonus() {
		super(new CharGelem('O', Color.green), Property.Invincibility, 10);
	}
}
