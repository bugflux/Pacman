package org.bugflux.pacman.concurrent;

import org.bugflux.pacman.Walkable.Direction;


public interface MoveEventReceiver {
	public void put(Direction d);
}
