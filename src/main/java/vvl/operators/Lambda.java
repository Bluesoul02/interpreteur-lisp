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
			ConsList<?> param = (ConsList<?>) list.get(0);
			ConsList<?> fct = (ConsList<?>) list.get(1);
			return lispImpl.evaluate(fct);
			
		} else 
			throw new InvalidNumberOfOperands();
	}

}
