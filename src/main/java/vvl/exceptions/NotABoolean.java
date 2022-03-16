package vvl.exceptions;

import vvl.lisp.LispError;

public class NotABoolean extends LispError {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotABoolean() {
		super("Not a Boolean");
	}
}
