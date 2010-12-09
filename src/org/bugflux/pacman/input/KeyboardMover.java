package org.bugflux.pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.bugflux.pacman.Mover;
import org.bugflux.pacman.Walker;
import org.bugflux.pacman.Walkable.Direction;


public class KeyboardMover extends Mover implements KeyListener {

	public KeyboardMover(Walker w) {
		super(w);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_UP:    move(Direction.UP);    break;
			case java.awt.event.KeyEvent.VK_DOWN:  move(Direction.DOWN);  break;
			case java.awt.event.KeyEvent.VK_RIGHT: move(Direction.RIGHT); break;
			case java.awt.event.KeyEvent.VK_LEFT:  move(Direction.LEFT);  break;
			default: // not supported
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}
}
