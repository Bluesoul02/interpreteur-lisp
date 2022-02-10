package vvl.operator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class Div implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		Double resultDouble;
		var isDouble = false;
		if (list.isEmpty())
			throw new LispError("Invalid number of operands");
		else if (list.get(0) instanceof Double) {
			resultDouble = (double) list.get(0);
			isDouble = true;
		} else
			resultDouble = ((BigInteger) list.get(0)).doubleValue();
		Object o;
		for (int i = 1; i < list.size(); i++) {
			o = list.get(i);
			if (o.equals(BigInteger.ZERO)) throw new LispError("Division by zero");
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			if (o instanceof Double d) {
				resultDouble /= d;
				isDouble = true;
			} else {
				resultDouble /= ((BigInteger) o).doubleValue();
			}
		}
		if (isDouble)
			return resultDouble;
		return BigDecimal.valueOf(resultDouble).toBigInteger();
	}

}
