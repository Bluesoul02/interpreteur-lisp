package vvl.lisp;

import java.math.BigInteger;
import java.util.ArrayList;

import vvl.util.ConsList;

public class Plus implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		var resultDouble = 0.0;
		var resBigInt = BigInteger.ZERO;
		var isDouble = false;
		for (Object o : list) {
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			} else if (o instanceof Double) {
				resultDouble += (Double) o;
				isDouble = true;
			} else {
				resBigInt = resBigInt.add((BigInteger) o);
			}
		}
		if (isDouble) {
			resultDouble += resBigInt.doubleValue();
			return resultDouble;
		}
		return resBigInt;
	}
}
