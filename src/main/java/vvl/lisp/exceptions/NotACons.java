package vvl.lisp.exceptions;

import vvl.lisp.LispError;

public class NotACons extends LispError{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotACons() {
		super("Not a Cons");
	}
}
