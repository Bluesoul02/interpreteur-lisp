package vvl.operator;

import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.Cons;
import vvl.util.ConsList;

public class ConsOp implements Operator {

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		Object left;
		Object right;
		if (list.size() == 2) {
			left = list.get(0);
			right = list.get(1);
			if (left instanceof ConsList) {
				left = new LispImpl().evaluate(left);
			}
			if (right instanceof ConsList) {
				right = new LispImpl().evaluate(right);
			}
			if (left instanceof ConsList) {
				return ((ConsList<Object>) left).append(right);
			}
			if (right instanceof ConsList) {
				return ((ConsList<Object>) right).prepend(left);
			}
			return new Cons<>(left, right);
		} else
			throw new LispError("Invalid number of operands");
	}
}
