package vvl.lisp.exceptions;

import vvl.lisp.LispError;

public class NotANumber extends LispError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotANumber() {
		super("Not a number");
	}

}
