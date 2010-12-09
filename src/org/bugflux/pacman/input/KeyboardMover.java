package org.bugflux.pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.Walkable.Direction;


public class KeyboardMover implements KeyListener {
	protected final WalkerMover m;

	public KeyboardMover(WalkerMover m) {
		this.m = m;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_UP:    m.move(Direction.UP);    break;
			case java.awt.event.KeyEvent.VK_DOWN:  m.move(Direction.DOWN);  break;
			case java.awt.event.KeyEvent.VK_RIGHT: m.move(Direction.RIGHT); break;
			case java.awt.event.KeyEvent.VK_LEFT:  m.move(Direction.LEFT);  break;
			default: // not supported
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}
}
