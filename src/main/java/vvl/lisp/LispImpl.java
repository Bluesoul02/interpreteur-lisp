package vvl.lisp;

import java.math.BigInteger;
import java.util.ArrayList;

import vvl.util.ConsList;
import vvl.util.ConsListFactory;

public class LispImpl implements Lisp {

	@Override
	public Object parse(String expr) throws LispError {
		expr = expr.replaceAll("\\(", "( ");
		expr = expr.replaceAll("\\)", " )");
		String[] parsed = expr.split(" +");
		// solution 1 : parser normalement puis utiliser asList
		// solution 2 : remplir consList pendant le parsing
		ArrayList<ConsList<Object>> consLists = new ArrayList<ConsList<Object>>();
		consLists.add(ConsListFactory.nil());
		int consListScope = 0;
		boolean first = true;
		Object o = null;
		for (String string : parsed) {
			if (!first) {
				o = getType(string);
				if (string.equals("(")) {
					if (consLists.size() >= consListScope++) consLists.add(ConsListFactory.singleton(o));
					else consLists.set(++consListScope, ConsListFactory.singleton(o));
				}
				else if (string.equals(")")) {
					consLists.get(consListScope--).append(o);
					if (consListScope >= 0)
						consLists.set(consListScope, consLists.get(consListScope).append(consLists.get(consListScope)));
				}
				else {
					consLists.set(consListScope, consLists.get(consListScope).append(o));
				}
			}
			System.out.println("Lisp : " + string);
			first = false;
		}
		System.out.println(consLists.get(0).toString()); // need to fix lisp with multiple (
		return consLists.get(0);
	}

	private Object getType(String string) {
		Object o = string;
		if (string.contains(".")) o = Double.valueOf(string);
		else if (string.contains("#")) o = string.contains("t") ? LispBoolean.TRUE : LispBoolean.FALSE;
		return o;
	}

	@Override
	public Object evaluate(Object ex) throws LispError {
		// TODO Auto-generated method stub
		return null;
	}
	
}
