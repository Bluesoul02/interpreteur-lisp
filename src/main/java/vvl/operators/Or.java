package vvl.operators;

import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class Or implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		LispBoolean res = LispBoolean.FALSE;
		for (Object o : list) {
			if (o instanceof ConsList) {
				ArrayList<Object> array = new ArrayList<>();
				array.add(new LispImpl().evaluate(o));
				if (apply(array).equals(LispBoolean.TRUE))
					return LispBoolean.TRUE;
			} else if (o instanceof LispBoolean) {
				if (o.equals(LispBoolean.TRUE))
					return o;
			}
			else
				throw new LispError("Not a Boolean");
		}
		return res;
	}

}
