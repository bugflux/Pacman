package org.bugflux.pacman.event;

import org.bugflux.pacman.entities.Walkable.Direction;


public interface MoveEventReceiver {
	public void put(Direction d);
}
