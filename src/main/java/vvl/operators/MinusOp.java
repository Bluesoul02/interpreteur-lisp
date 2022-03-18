package vvl.operators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import vvl.exceptions.InvalidNumberOfOperands;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class MinusOp implements Operator {

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		Double resultDouble;
		var isDouble = false;
		Object o;
		if (list.isEmpty() || list.size() > 2)
			throw new InvalidNumberOfOperands();

		o = list.get(0);
		if (o instanceof ConsList)
			o = lispImpl.evaluate(o);
		if (o instanceof Double) {
			if (list.size() == 1)
				return -(double) o;
			resultDouble = (double) o;
			isDouble = true;
		} else {
			if (list.size() == 1)
				return ((BigInteger) o).negate();
			resultDouble = ((BigInteger) o).doubleValue();
		}
		o = list.get(1);
		if (o instanceof ConsList) {
			o = lispImpl.evaluate(o);
		}
		if (o instanceof Double) {
			resultDouble -= (Double) o;
			isDouble = true;
		} else {
			resultDouble -= ((BigInteger) o).doubleValue();
		}
		if (isDouble)
			return resultDouble;
		return BigDecimal.valueOf(resultDouble).toBigInteger();
	}

}