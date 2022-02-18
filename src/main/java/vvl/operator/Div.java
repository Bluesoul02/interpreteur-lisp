package vvl.operator;

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
		Object o;

		if (list.isEmpty())
			throw new LispError("Invalid number of operands");

		o = list.get(0);
		if (o instanceof ConsList)
			o = new LispImpl().evaluate(o);

		if (o instanceof Double) {
			resultDouble = (double) o;
			isDouble = true;
		} else
			resultDouble = ((BigInteger) o).doubleValue();

		for (int i = 1; i < list.size(); i++) {
			o = list.get(i);
			if (o.equals(BigInteger.ZERO))
				throw new LispError("Division by zero");
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			if (o instanceof Double) {
				resultDouble /= (Double) o;
				isDouble = true;
			} else {
				resultDouble /= ((BigInteger) o).doubleValue();
			}
		}
		if (isDouble)
			return resultDouble;
		return doubleToBigInt(resultDouble);
	}
	
	private BigInteger doubleToBigInt(Double d) {
		long bits = Double.doubleToLongBits(d);
		int exp = ((int)(bits >> 52) & 0x7ff) - 1075;
		BigInteger m = BigInteger.valueOf((bits & (1L << 52) - 1) | (1L << 52)).shiftLeft(exp);
		return (bits >= 0) ? m : m.negate();
	}

}
