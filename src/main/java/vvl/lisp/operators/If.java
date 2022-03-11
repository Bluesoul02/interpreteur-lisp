package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.lisp.exceptions.InvalidNumberOfOperands;
import vvl.lisp.exceptions.NotABoolean;
import vvl.util.ConsList;

public class If implements Operator {

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		Object o;
		if (list.size() != 3)
			throw new InvalidNumberOfOperands();

		o = list.get(0);
		if (o instanceof ConsList)
			o = lispImpl.evaluate(o);
		if (o instanceof LispBoolean) {
			if (o.equals(LispBoolean.TRUE)) {
				if (list.get(1) instanceof ConsList)
					return lispImpl.evaluate(list.get(1));
				return list.get(1);
			} else {
				if (list.get(2) instanceof ConsList)
					return lispImpl.evaluate(list.get(2));
				return list.get(2);
			}
		} else
			throw new NotABoolean();
	}

}
