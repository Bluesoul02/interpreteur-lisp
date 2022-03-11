package vvl.lisp.exceptions;

import vvl.lisp.LispError;

public class InvalidNumberOfOperands extends LispError {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidNumberOfOperands() {
		super("Invalid number of operands");
	}
}
