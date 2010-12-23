package org.bugflux.pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Walkable;
import org.bugflux.pacman.entities.Walkable.Direction;


public class KeyboardMover implements KeyListener {
	protected final Controllable m;
	protected final Walkable game;

	public KeyboardMover(Walkable game, Controllable m) {
		assert m != null;
		assert game != null;

		this.game = game;
		this.m = m;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		synchronized(game) {
			if(!game.isOver()) {
				Direction d = null;
				boolean acted = true;

				switch(e.getKeyCode()) {
					case java.awt.event.KeyEvent.VK_UP:    d = Direction.UP; break;
					case java.awt.event.KeyEvent.VK_DOWN:  d = Direction.DOWN; break;
					case java.awt.event.KeyEvent.VK_RIGHT: d = Direction.RIGHT; break;
					case java.awt.event.KeyEvent.VK_LEFT:  d = Direction.LEFT; break;
					default: acted = false; // not supported
				}

				if(acted) {
					synchronized(m) {
						if(m.canMove(d)) {
							m.move(d);
						}
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}
}
