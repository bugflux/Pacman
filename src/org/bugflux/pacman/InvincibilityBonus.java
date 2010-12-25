package org.bugflux.pacman;

import java.awt.Color;

import pt.ua.gboard.CharGelem;

public class InvincibilityBonus extends GenericBonus {
	public InvincibilityBonus() {
		super(new CharGelem('o', Color.green), Property.Invincible);
	}
}
