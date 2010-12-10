package org.bugflux.pacman.input;

import java.util.Random;

import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;

public class AutonomousMover extends Thread {
	protected final Mover m;
	
	public AutonomousMover(Mover m) {
		this.m = m;
	}
	
	public void run() {
		while(true) {
			Random r = new Random();
			Direction d[] = { Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT };
			m.tryMove(d[r.nextInt(d.length)]);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
