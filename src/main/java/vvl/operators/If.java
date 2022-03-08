package vvl.operators;

import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class If implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		Object o;
		if (list.size() != 3)
			throw new LispError("Invalid number of operands");

		o = list.get(0);
		if (o instanceof ConsList)
			o = new LispImpl().evaluate(o);
		if (o instanceof LispBoolean) {
			if (o.equals(LispBoolean.TRUE)) {
				if (list.get(1) instanceof ConsList)
					return new LispImpl().evaluate(list.get(1));
				return list.get(1);
			} else {
				if (list.get(2) instanceof ConsList)
					return new LispImpl().evaluate(list.get(2));
				return list.get(2);
			}
		} else
			throw new LispError("Not a Boolean");
	}

}
