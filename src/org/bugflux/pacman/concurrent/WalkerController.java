package org.bugflux.pacman.concurrent;

import org.bugflux.pacman.Walker;

public class WalkerController extends Thread {
	protected Walker w;
	protected MoveEvent e;
	
	public WalkerController(Walker w, MoveEvent e) {
		this.w = w;
		this.e = e;
	}

	@Override
	public void run() {
		while(true) { // TODO stop
			w.tryMove(e.get());
		}
	}
}
