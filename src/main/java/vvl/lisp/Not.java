package vvl.lisp;

import java.util.ArrayList;

import vvl.util.ConsList;

public class Not implements Operator{

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		if (list.size() == 1) {
			Object o = list.get(0);
			if (o instanceof ConsList) {
				ArrayList<Object> array = new ArrayList<>();
				array.add(new LispImpl().evaluate(o));
				return apply(array).equals(LispBoolean.TRUE) ? LispBoolean.FALSE : LispBoolean.TRUE;
			}
			return list.get(0).equals(LispBoolean.TRUE) ? LispBoolean.FALSE : LispBoolean.TRUE;
		} else return "Invalid number of operands";
	}
}
