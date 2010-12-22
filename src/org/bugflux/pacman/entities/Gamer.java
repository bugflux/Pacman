package org.bugflux.pacman.entities;

public interface Gamer {
	
	/**
	 * Declares game-over to an active entity in a game.
	 * This entity must then cease activity in the world.
	 * 
	 */
	public abstract void gameOver();
}
