package org.bugflux.pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;


public class KeyboardMover implements KeyListener {
	protected final Mover m;

	public KeyboardMover(Mover m) {
		this.m = m;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_UP:    m.tryMove(Direction.UP);    break;
			case java.awt.event.KeyEvent.VK_DOWN:  m.tryMove(Direction.DOWN);  break;
			case java.awt.event.KeyEvent.VK_RIGHT: m.tryMove(Direction.RIGHT); break;
			case java.awt.event.KeyEvent.VK_LEFT:  m.tryMove(Direction.LEFT);  break;
			default: // not supported
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}
}
