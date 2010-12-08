package org.bugflux.pacman.concurrent;

import org.bugflux.pacman.Walkable.Direction;


public interface MoveEventSender {
	public Direction get();
}
