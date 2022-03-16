package vvl.operators;

import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;
import vvl.util.ConsListImpl;

public class ListOp implements Operator{

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		ConsList<Object> consList = new ConsListImpl<>();
		
		for (Object o : list) {
			if (o instanceof ConsList) {
				o = lispImpl.evaluate(o);
			}
			consList = consList.append(o);
		}
		return consList;
	}

}
