package org.bugflux.lock;

public class BroadcastSignal extends Signal {
	public BroadcastSignal() {
		super();
	}

	@Override
	public synchronized void send() {
		arrived = true;
		notifyAll();
	}

	@Override
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
