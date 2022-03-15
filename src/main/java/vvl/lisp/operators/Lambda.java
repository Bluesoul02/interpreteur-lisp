package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.lisp.exceptions.InvalidNumberOfOperands;
import vvl.util.ConsList;

public class Lambda implements Operator{

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		if (list.size() == 2) {
			ConsList<?> params = (ConsList<?>) list.get(0);
			ConsList<?> fct = (ConsList<?>) list.get(1);
			return null;
		}
		else throw new InvalidNumberOfOperands();
	}
	
}
