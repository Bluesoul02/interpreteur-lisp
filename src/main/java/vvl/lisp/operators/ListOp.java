package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;
import vvl.util.ConsListImpl;

public class ListOp implements Operator{

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		ConsList<Object> consList = new ConsListImpl<>();
		
		for (Object o : list) {
			if (o instanceof ConsList) {
				o = new LispImpl().evaluate(o);
			}
			consList = consList.append(o);
		}
		return consList;
	}

}
