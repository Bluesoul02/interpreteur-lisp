package vvl.operators;

import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;

public class LesserThanOrEquals implements Operator {

	private LesserThan lt;
	private Equals eq;
	
	public LesserThanOrEquals() {
		lt = new LesserThan();
		eq = new Equals();
	}

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		return LispBoolean.valueOf(lt.apply(list).equals(LispBoolean.TRUE) || eq.apply(list).equals(LispBoolean.TRUE));
	}

}
