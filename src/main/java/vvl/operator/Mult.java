package vvl.operator;

import java.math.BigInteger;
import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class Mult implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		var resultDouble = 1.0;
		var resBigInt = BigInteger.ONE;
		var isDouble = false;
		for (Object o : list) {
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			if (o instanceof Double) {
				resultDouble *= (Double) o;
				isDouble = true;
			} else {
				resBigInt = resBigInt.multiply((BigInteger) o);
			}
		}
		if (isDouble) {
			resultDouble *= resBigInt.doubleValue();
			return resultDouble;
		}
		return resBigInt;
	}

}
