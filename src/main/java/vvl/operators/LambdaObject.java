package vvl.operators;

import java.util.ArrayList;
import java.util.Iterator;

import vvl.exceptions.InvalidNumberOfOperands;
import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;

public class LambdaObject implements Operator {
	private ConsList<?> param;
	private Object fct;

	public LambdaObject(ConsList<?> param, Object fct) {
		super();
		this.param = param;
		this.fct = fct;
	}

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		if (!(fct instanceof ConsList))
			return fct;
		if (param.isEmpty())
			return lispImpl.evaluate(fct);
		if (param.size() != list.size())
			throw new InvalidNumberOfOperands();

		ConsList<?> currParam = param;
		Object id;
		Iterator<?> iterator = ((ConsList<?>) fct).iterator();
		ConsList<Object> res = ConsListFactory.nil();
		Object o;
		for (Object object : list) {
			id = currParam.car();
			currParam = currParam.cdr();
			while (iterator.hasNext()) {
				o = iterator.next();
				if (o.equals(id)) {
					res = res.append(object);
					if (param.size() > 1)
						break;
				} else {
					res = res.append(o);
				}
			}
		}
		return lispImpl.evaluate(res);
	}

	@Override
	public String toString() {
		return "lambda " + param.toString() + " " + fct.toString();
	}

}
