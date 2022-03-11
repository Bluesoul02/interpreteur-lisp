package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;

public class GreaterThanOrEquals implements Operator {
	private GreaterThan gt;
	private Equals eq;
	
	public GreaterThanOrEquals() {
		gt = new GreaterThan();
		eq = new Equals();
	}

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		return LispBoolean.valueOf(gt.apply(list, lispImpl).equals(LispBoolean.TRUE) || eq.apply(list, lispImpl).equals(LispBoolean.TRUE));
	}

}
