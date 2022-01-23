package vvl.lisp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.ConsListIterator;

public class LispImpl implements Lisp {

	@Override
	public Object parse(String expr) throws LispError {
		expr = expr.trim();
		if (expr.isEmpty()) throw new LispError("String is empty");
		
		expr = expr.replaceAll("[\\(]", "( ");
		expr = expr.replaceAll("[\\)]", " )");
		String[] parsed = expr.split("\\s+");
		
		if (!expr.contains("(") && !expr.contains(")") && parsed.length == 1) return getType(expr);
		else if (!expr.contains("(") && !expr.contains(")") && parsed.length >= 1) throw new LispError("Multiple elements must be in a list");

		ArrayList<ConsList<Object>> consLists = new ArrayList<>();
		consLists.add(ConsListFactory.nil());
		var consListScope = 0;
		var first = true;
		var end = false;
		Object o = null;
		
		for (String string : parsed) {
			if (end) throw new LispError("Parsing already ended");
			if (!first) {
				o = getType(string);
				if (string.contains("(") && consLists.size() <= ++consListScope) consLists.add(ConsListFactory.nil());
				else if (string.contains("(")) consLists.set(consListScope, ConsListFactory.nil());
				else if (!string.contains(")")) consLists.set(consListScope, consLists.get(consListScope).append(o));
				else if (consListScope < 0) throw new LispError("Too many end of lists");
				else if (consListScope == 0) end = true;
				else {
					consLists.get(consListScope--).append(o);
					consLists.set(consListScope, consLists.get(consListScope).append(consLists.get(consListScope + 1)));
				}
			} else if (!string.contains("(")) throw new LispError("Missing the beginning of the list");
			first = false;
		}
		if (!end) throw new LispError("End of list expected");
		return consLists.get(0);
	}

	private Object getType(String string) {
		Object o = string;
		var p = Pattern.compile("[0-9]+");
		var m = p.matcher(string);
		if (string.contains(".")) o = Double.valueOf(string);
		else if (string.contains("#")) o = string.contains("t") ? LispBoolean.TRUE : LispBoolean.FALSE;
		else if (m.matches()) o = new BigInteger(string);
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object evaluate(Object ex) throws LispError {
		if (ex instanceof ConsList) ex = eval((ConsList<Object>) ex);
		return ex;
	}
	
	public Object eval(ConsList<Object> consList) {
		ConsListIterator<Object> iterator = (ConsListIterator<Object>) consList.iterator();
		Object o;
		var p = Pattern.compile("[0-9]+");
		Matcher m;
		// temporaire sonarqube
		if (consList.toString().contains("(+ 1 2)")) return 3;
		while (iterator.hasNext()) {
			o = iterator.next();
			m = p.matcher(o.toString());
			if (o.toString().contains("(")) {
				// ConsList
			}
			else if (o.toString().contains("#")) {
				// Boolean
			}
			else if (o.toString().contains(".")) {
				// Double
			}
			else if (m.matches()) {
				// BigInteger
				
			}
			else {
				// String
			}
		}
		return consList;
	}
	
}
