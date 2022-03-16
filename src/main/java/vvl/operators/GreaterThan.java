package vvl.operators;

import java.math.BigInteger;
import java.util.ArrayList;

import vvl.exceptions.InvalidNumberOfOperands;
import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class GreaterThan implements Operator {

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		var result = true;
		if (list.isEmpty())
			throw new InvalidNumberOfOperands();
		Double prev;
		var res = list.get(0);
		if (res instanceof ConsList) {
			res = lispImpl.evaluate(list.get(0));
		}
		prev = res instanceof Double ? (Double) res : ((BigInteger) res).doubleValue();
		Object o;
		for (var i = 1; i < list.size(); i++) {
			o = list.get(i);
			if (o instanceof ConsList) {
				o = lispImpl.evaluate(o);
			}
			if (o instanceof Double) {
				result = prev > (Double) o;
				prev = (Double) o;
			} else {
				Double d = ((BigInteger) o).doubleValue();
				result = prev > d;
				prev = d;
			}
		}
		return LispBoolean.valueOf(result);
	}

}
