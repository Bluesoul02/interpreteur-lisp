package vvl.operators;

import java.util.ArrayList;

import vvl.exceptions.InvalidNumberOfOperands;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;

public class Quote implements Operator {

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		if (list.size() == 1)
			return list.get(0).toString();
		else
			throw new InvalidNumberOfOperands();
	}
}
