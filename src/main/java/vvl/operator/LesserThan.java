package vvl.operator;

import java.math.BigInteger;
import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class LesserThan implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		var result = true;
		Object o;
		if (list.isEmpty())
			throw new LispError("Invalid number of operands");
		
		o = list.get(0);
		if (o instanceof ConsList)
			o = new LispImpl().evaluate(o);
		var prev = o instanceof Double ? (Double) o : ((BigInteger) o).doubleValue();
		for (int i = 1; i < list.size(); i++) {
			o = list.get(i);
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			if (o instanceof Double) {
				result = prev < (Double) o;
				prev = (Double) o;
			} else {
				Double d = ((BigInteger) o).doubleValue();
				result = prev < d;
				prev = d;
			}
		}
		return LispBoolean.valueOf(result);
	}

}
