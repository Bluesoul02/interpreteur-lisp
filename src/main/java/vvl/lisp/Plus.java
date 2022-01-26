package vvl.lisp;

import java.util.ArrayList;

public class Plus implements Operator{

	@Override
	public Object apply(ArrayList<Object> list) {
		Double result = 0.0;
		for (Object o : list) {
			result = result + (Double) o;
		}
		return result;
	}
}
