package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.lisp.exceptions.InvalidNumberOfOperands;
import vvl.util.Cons;
import vvl.util.ConsList;

public class Cdr implements Operator{

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		if (list.size() == 1) {
			var o = list.get(0);
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			if (o instanceof Cons)
				return ((Cons<?, ?>) o).right();
			else if (o instanceof ConsList)
				return ((ConsList<?>) o).cdr();
			throw new LispError("Not a Cons");
		} else
			throw new InvalidNumberOfOperands();
	}
	
}
