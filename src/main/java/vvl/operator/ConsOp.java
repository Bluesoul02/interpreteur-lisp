package vvl.operator;

import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.Cons;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;

public class ConsOp implements Operator {

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		Object left;
		Object right;
		if (list.size() == 2) {
			left = list.get(0);
			right = list.get(1);
			if (left.toString().equals("nil"))
				left = ConsListFactory.nil();
			if (right.toString().equals("nil"))
				right = ConsListFactory.nil();
			if (left instanceof ConsList && !left.toString().equals("()")) {
				left = new LispImpl().evaluate(left);
			}
			if (right instanceof ConsList && !right.toString().equals("()")) {
				right = new LispImpl().evaluate(right);
			}
			if (right instanceof ConsList) {
				return ((ConsList<Object>) right).prepend(left);
			}
			return new Cons<>(left, right);
		} else
			throw new LispError("Invalid number of operands");
	}
}
