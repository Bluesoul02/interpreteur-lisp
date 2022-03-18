package vvl.operators;

import java.util.ArrayList;
import java.util.Iterator;

import vvl.exceptions.InvalidNumberOfOperands;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;

public class MapOp implements Operator{

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		if (list.size() == 2) {
			LambdaObject fct = (LambdaObject) lispImpl.evaluate(list.get(0));
			var lst = list.get(1);
			Iterator<?> iterator = ((ConsList<?>) lst).iterator();
			Object x;
			ArrayList<Object> param;
			ConsList<Object> res = ConsListFactory.nil();
			while (iterator.hasNext()) {
				param = new ArrayList<>();
				x = iterator.next();
				param.add(x);
				res = res.append(fct.apply(param, lispImpl));
			}
			return res;
		} else 
			throw new InvalidNumberOfOperands();
	}

}
