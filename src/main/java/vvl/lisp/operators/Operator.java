package vvl.lisp.operators;

import java.util.ArrayList;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;

public interface Operator {
	Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError;
}
