package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;

public class LesserThanOrEquals implements Operator {

	private LesserThan lt;
	private Equals eq;
	
	public LesserThanOrEquals() {
		lt = new LesserThan();
		eq = new Equals();
	}

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		return LispBoolean.valueOf(lt.apply(list, lispImpl).equals(LispBoolean.TRUE) || eq.apply(list, lispImpl).equals(LispBoolean.TRUE));
	}

}
