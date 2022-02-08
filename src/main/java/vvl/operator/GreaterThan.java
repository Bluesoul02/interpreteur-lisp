package vvl.operator;

import java.math.BigInteger;
import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class GreaterThan implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		var result = true;
		if (list.isEmpty())
			throw new LispError("Invalid number of operands");
		var prev = list.get(0) instanceof Double ? (Double) list.get(0) : ((BigInteger) list.get(0)).doubleValue();
		Object o;
		for (int i = 1; i < list.size(); i++) {
			o = list.get(i);
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			if (o instanceof Double) {
				result = prev > (Double) o;
			} else {
				result = prev > ((BigInteger) o).doubleValue();
			}
		}
		return LispBoolean.valueOf(result);
	}

}