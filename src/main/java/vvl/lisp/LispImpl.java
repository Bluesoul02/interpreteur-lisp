package vvl.lisp;

import vvl.util.ConsList;

public class LispImpl implements Lisp {

	@Override
	public Object parse(String expr) throws LispError {
		expr = expr.replaceAll("\\(", "( ");
		expr = expr.replaceAll("\\)", " )");
		String[] parsed = expr.split(" +");
		// solution 1 : parser normalement puis utiliser asList
		// solution 2 : remplir consList pendant le parsing
		ConsList<Object> consList = null;
		for (String string : parsed) {
			System.out.println("Lisp : " + string);
		}
		return consList;
	}

	@Override
	public Object evaluate(Object ex) throws LispError {
		// TODO Auto-generated method stub
		return null;
	}
	
}
