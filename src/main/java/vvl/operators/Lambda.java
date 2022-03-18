package vvl.operators;

import java.util.ArrayList;

import vvl.exceptions.InvalidNumberOfOperands;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class Lambda implements Operator {

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		if(list.size() == 2) {
			return new LambdaObject((ConsList<?>) list.get(0), list.get(1));
		} else 
			throw new InvalidNumberOfOperands();
	}

}
