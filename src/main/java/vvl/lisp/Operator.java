package vvl.lisp;

import java.util.ArrayList;

public interface Operator {
	<E> E eval(ArrayList<E> list);
}
