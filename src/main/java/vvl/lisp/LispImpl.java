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
		expr = parsePrep(expr);
		String[] parsed = expr.split("\\s+");

		if (!isList(expr, parsed.length))
			return getType(expr);
		ArrayList<ConsList<Object>> consLists = new ArrayList<>();
		consLists.add(ConsListFactory.nil());
		var consListScope = 0;
		var first = true;
		var end = false;
		Object o = null;

		for (String string : parsed) {
			hasEnded(end);
			if (!first) {
				o = getType(string);
				if (string.contains("(") && consLists.size() <= ++consListScope)
					consLists.add(ConsListFactory.nil());
				else if (string.contains("("))
					consLists.set(consListScope, ConsListFactory.nil());
				else if (!string.contains(")"))
					consLists.set(consListScope, consLists.get(consListScope).append(o));
				else if (consListScope < 0)
					throw new LispError("Too many end of lists");
				else if (consListScope == 0)
					end = true;
				else {
					consLists.get(consListScope--).append(o);
					consLists.set(consListScope, consLists.get(consListScope).append(consLists.get(consListScope + 1)));
				}
			} else if (!string.contains("("))
				throw new LispError("Missing the beginning of the list");
			first = false;
		}
		if (!end)
			throw new LispError("End of list expected");
		return consLists.get(0);
	}

	public String parsePrep(String expr) throws LispError {
		expr = expr.trim();
		if (expr.isEmpty())
			throw new LispError("String is empty");

		expr = expr.replaceAll("[\\(]", "( ");
		expr = expr.replaceAll("[\\)]", " )");
		return expr;
	}

	private void hasEnded(boolean end) throws LispError {
		if (end)
			throw new LispError("Parsing already ended");
	}

	private boolean isList(String expr, int length) throws LispError {
		if (!expr.contains("(") && !expr.contains(")") && length > 1)
			throw new LispError("Multiple elements must be in a list");
		else if (!expr.contains("(") && !expr.contains(")") && length == 1)
			return false;
		return true;
	}

	private Object getType(String string) {
		Object o = string;
		var p = Pattern.compile("[0-9]+");
		var m = p.matcher(string);
		if (string.contains("."))
			o = Double.valueOf(string);
		else if (string.contains("#"))
			o = LispBoolean.valueOf(string);
		else if (m.matches())
			o = new BigInteger(string);
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object evaluate(Object ex) throws LispError {
		if (ex instanceof ConsList)
			ex = eval((ConsList<Object>) ex);
		return ex;
	}

	@SuppressWarnings("unchecked")
	private Object eval(ConsList<Object> consList) throws LispError {
		ConsListIterator<Object> iterator = (ConsListIterator<Object>) consList.iterator();
		Object o;
		var p = Pattern.compile("[0-9]+");
		Matcher m;
		String operator = null;
		ArrayList<Object> operands = new ArrayList<>();

		while (iterator.hasNext()) {
			o = iterator.next();
			m = p.matcher(o.toString());
			if (o instanceof ConsList) {
				operands.add(eval((ConsList<Object>) o));
			} else if (o.toString().contains("#")) {
				// Boolean

			} else if (o.toString().contains(".")) {
				// Double
			} else if (m.matches()) {
				// BigInteger
				operands.add(o);
			} else {
				// String
				operator = (String) o;
			}
		}
		if (operator != null)
			return getOperator(operator).apply(operands);
		return consList;
	}

	private Operator getOperator(String op) {
		if (op.equals("+"))
			return new Plus();
		return new Plus();
	}

}
