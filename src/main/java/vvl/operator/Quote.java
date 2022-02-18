package vvl.operator;

import java.util.ArrayList;

import vvl.lisp.LispError;

public class Quote implements Operator {

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		if (list.size() == 1)
			return list.get(0).toString();
		else
			throw new LispError("Invalid number of operands");
	}
}
