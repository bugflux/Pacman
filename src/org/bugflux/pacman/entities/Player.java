package org.bugflux.pacman.entities;

import pt.ua.gboard.Gelem;

public interface Player {
	public static enum Team { GOOD, VILLAIN };

	/**
	 * 
	 * @return
	 */
	public abstract Team team();
	
	/**
	 * 
	 * @return
	 */
	public abstract Gelem gelem();
	
	/**
	 * 
	 */
	public abstract void die();
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isDead();
}
