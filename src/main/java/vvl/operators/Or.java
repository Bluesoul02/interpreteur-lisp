package vvl.operators;

import java.util.ArrayList;

import vvl.exceptions.NotABoolean;
import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class Or implements Operator {

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		LispBoolean res = LispBoolean.FALSE;
		for (Object o : list) {
			if (o instanceof ConsList) {
				ArrayList<Object> array = new ArrayList<>();
				array.add(lispImpl.evaluate(o));
				if (((LispBoolean) apply(array, lispImpl)).value())
					return LispBoolean.TRUE;
			} else if (o instanceof LispBoolean) {
				if (((LispBoolean) o).value())
					return o;
			} else
				throw new NotABoolean();
		}
		return res;
	}

}
