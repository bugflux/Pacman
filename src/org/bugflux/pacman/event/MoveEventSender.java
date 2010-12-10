package org.bugflux.pacman.event;

import org.bugflux.pacman.entities.Walkable.Direction;


public interface MoveEventSender {
	public Direction get();
}
