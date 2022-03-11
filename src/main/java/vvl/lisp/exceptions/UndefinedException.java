package vvl.lisp.exceptions;

import vvl.lisp.LispError;

public class UndefinedException extends LispError {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UndefinedException(String id) {
		super(id.concat(" is undefined"));
	}
}
