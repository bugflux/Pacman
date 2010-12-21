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
}
