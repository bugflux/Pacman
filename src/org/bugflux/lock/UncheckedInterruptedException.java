package org.bugflux.lock;

public class UncheckedInterruptedException extends RuntimeException {
	private static final long serialVersionUID = -4051054475259405121L;

	public UncheckedInterruptedException(Throwable t) {
		super(t);
	}
}
