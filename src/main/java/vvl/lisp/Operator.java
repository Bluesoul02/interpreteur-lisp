package vvl.lisp;

import java.util.ArrayList;

public interface Operator {
	Object apply(ArrayList<Object> list) throws LispError;
}
