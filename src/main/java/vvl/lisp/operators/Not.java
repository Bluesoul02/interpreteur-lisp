package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.lisp.exceptions.InvalidNumberOfOperands;
import vvl.util.ConsList;

public class Not implements Operator {

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		if (list.size() == 1) {
			Object o = list.get(0);
			if (o instanceof ConsList) {
				ArrayList<Object> array = new ArrayList<>();
				array.add(lispImpl.evaluate(o));
				return apply(array, lispImpl);
			} else if (!(o instanceof LispBoolean))
				throw new LispError("Not a Boolean");
			return o.equals(LispBoolean.TRUE) ? LispBoolean.FALSE : LispBoolean.TRUE;
		} else
			throw new InvalidNumberOfOperands();
	}
}
