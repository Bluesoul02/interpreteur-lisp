package vvl.operators;

import java.util.ArrayList;

import vvl.exceptions.NotABoolean;
import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class And implements Operator {

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		LispBoolean res = LispBoolean.TRUE;
		for (Object o : list) {
			if (o.equals(LispBoolean.FALSE))
				return o;
			else if (o instanceof ConsList) {
				ArrayList<Object> array = new ArrayList<>();
				array.add(lispImpl.evaluate(o));
				if (!((LispBoolean) apply(array, lispImpl)).value())
					return LispBoolean.FALSE;
			} else if (!(o instanceof LispBoolean)) throw new NotABoolean();
		}
		return res;
	}

}
