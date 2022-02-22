package vvl.operator;

import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;

public class GreaterThanOrEquals implements Operator {
	private GreaterThan gt;
	private Equals eq;
	
	public GreaterThanOrEquals() {
		gt = new GreaterThan();
		eq = new Equals();
	}

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		return LispBoolean.valueOf(gt.apply(list).equals(eq.apply(list)));
	}

}
