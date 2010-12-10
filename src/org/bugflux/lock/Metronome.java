package org.bugflux.lock;

public class Metronome extends BroadcastSignal implements Runnable {
	protected int ms;

	public Metronome(int ms) {
		this.ms = ms;
	}

	public void run() {
		while(true) {
			send();

			try {
				Thread.sleep(ms);
			}
			catch(InterruptedException e) {
				throw new UncheckedInterruptedException(e);
			}
		}
	}

	public int ms() {
		return ms;
	}

	public void start() {
		new Thread(this).start();
	}
}
