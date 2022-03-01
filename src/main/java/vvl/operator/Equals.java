package vvl.operator;

import java.math.BigInteger;
import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class Equals implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		var result = true;
		if (list.isEmpty())
			throw new LispError("Invalid number of operands");
		Object prev;
		if (list.get(0) instanceof ConsList) {
			prev = new LispImpl().evaluate(list.get(0));
		} else  {
			prev = list.get(0) instanceof Double ? list.get(0) : ((BigInteger) list.get(0)).doubleValue();
		}
		Object o;
		for (var i = 1; i < list.size(); i++) {
			o = list.get(i);
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			if (o instanceof Double && prev instanceof Double) {
				result = prev.equals(o);
			} else if (prev instanceof Double) {
				result = prev.equals(((BigInteger) o).doubleValue());
			} else {
				result = prev.equals(o);
			}
		}
		return LispBoolean.valueOf(result);
	}

}
