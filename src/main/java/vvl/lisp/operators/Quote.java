package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.exceptions.InvalidNumberOfOperands;

public class Quote implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		if (list.size() == 1)
			return list.get(0).toString();
		else
			throw new InvalidNumberOfOperands();
	}
}
