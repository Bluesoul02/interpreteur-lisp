package vvl.operators;

import java.util.ArrayList;

import vvl.lisp.LispError;

public interface Operator {
	Object apply(ArrayList<Object> list) throws LispError;
}
