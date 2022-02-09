package vvl.operator;

import java.math.BigInteger;
import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class Div implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		var resultDouble = 0.0;
		var resBigInt = BigInteger.ZERO;
		var isDouble = false;
		for (Object o : list) {
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			if (o instanceof Double) {
				if (!isDouble)
					resultDouble = resBigInt.doubleValue();
				if (o.equals(0.0))
					throw new LispError("Division by zero");
				resultDouble /= (Double) o;
				isDouble = true;
			} else {
				if (o.equals(BigInteger.ZERO))
					throw new LispError("Division by zero");
				if (isDouble) {
					resultDouble /= ((BigInteger)o).doubleValue();
				} else
					resBigInt = resBigInt.divide((BigInteger) o);
			}
		}
		if (isDouble)
			return resultDouble;
		return resBigInt;
	}

}
