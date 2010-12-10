package org.bugflux.pacman.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.bugflux.pacman.entities.Walkable.Direction;

public class KeyboardMoveEvent extends EventWalkerMover implements KeyListener {
	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_UP:    put(Direction.UP);    break;
			case java.awt.event.KeyEvent.VK_DOWN:  put(Direction.DOWN);  break;
			case java.awt.event.KeyEvent.VK_RIGHT: put(Direction.RIGHT); break;
			case java.awt.event.KeyEvent.VK_LEFT:  put(Direction.LEFT);  break;
			default: // not supported
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
