package org.bugflux.pacman.entities;

import pt.ua.gboard.Gelem;

public interface Bonus {
	public static enum Property { Invincibility, FreezeOpposing };
	
	/**
	 * Get the property of this Bonus.
	 * 
	 * @return the Bonus' Property.
	 */
	public abstract Property property();
	
	/**
	 * Get this Bonus' Gelem for representation in a GBoard.
	 * 
	 * @return this Bonus' Gelem.
	 */
	public abstract Gelem gelem();
}
