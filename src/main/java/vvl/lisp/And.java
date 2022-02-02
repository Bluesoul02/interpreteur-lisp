package vvl.lisp;

import java.util.ArrayList;

import vvl.util.ConsList;

public class And implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		LispBoolean res = LispBoolean.TRUE;
		for (Object o : list) {
			if (o.equals(LispBoolean.FALSE))
				return o;
			else if (o instanceof ConsList) {
				ArrayList<Object> array = new ArrayList<>();
				array.add(new LispImpl().evaluate(o));
				if (apply(array).equals(LispBoolean.FALSE)) return LispBoolean.FALSE;
			}
		}
		return res;
	}

}
