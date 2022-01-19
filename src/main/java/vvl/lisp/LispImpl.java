package vvl.lisp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vvl.util.ConsList;
import vvl.util.ConsListFactory;

public class LispImpl implements Lisp {

	@Override
	public Object parse(String expr) throws LispError {
		if (!expr.contains("(")) return getType(expr);
		
		expr = expr.replaceAll("\\(", "( ");
		expr = expr.replaceAll("\\)", " )");
		expr = expr.trim();
		String[] parsed = expr.split(" +");
		
		ArrayList<ConsList<Object>> consLists = new ArrayList<ConsList<Object>>();
		consLists.add(ConsListFactory.nil());
		int consListScope = 0;
		boolean first = true;
		Object o = null;
		
		for (String string : parsed) {
			if (!first) {
				o = getType(string);
				if (string.contains("(")) {
					if (consLists.size() <= ++consListScope) consLists.add(ConsListFactory.nil());
					else consLists.set(consListScope, ConsListFactory.nil());
				}
				else if (string.contains(")")) {
					consLists.get(consListScope--).append(o);
					if (consListScope >= 0)
						consLists.set(consListScope, consLists.get(consListScope).append(consLists.get(consListScope + 1)));
				}
				else {
					consLists.set(consListScope, consLists.get(consListScope).append(o));
				}
			}
			first = false;
		}
		return consLists.get(0);
	}

	private Object getType(String string) {
		Object o = string;
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(string);
		if (string.contains(".")) o = Double.valueOf(string);
		else if (string.contains("#")) o = string.contains("t") ? LispBoolean.TRUE : LispBoolean.FALSE;
		else if (m.matches()) o = BigInteger.valueOf(Long.valueOf(string));
		return o;
	}

	@Override
	public Object evaluate(Object ex) throws LispError {
		// TODO Auto-generated method stub
		return null;
	}
	
}
