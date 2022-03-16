package vvl.exceptions;

import vvl.lisp.LispError;

public class InvalidIdentifierException extends LispError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidIdentifierException(String id) {
		super(id.concat(" is not a valid identifier"));
	}

}
