package org.bugflux.lock;

public class Signal {
	protected boolean arrived;

	public Signal() {
		arrived = false;
	}

	public synchronized void send() {
		arrived = true;
		notifyAll();
	}

	public synchronized void await() {
		try {
			while(!arrived) {
				wait();
			}
			arrived = false;
		}
		catch(InterruptedException e) {
			throw new UncheckedInterruptedException(e);
		}
	}
}
