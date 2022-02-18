package vvl.lisp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import vvl.operator.And;
import vvl.operator.Car;
import vvl.operator.ConsOp;
import vvl.operator.Div;
import vvl.operator.Equals;
import vvl.operator.GreaterThan;
import vvl.operator.GreaterThanOrEquals;
import vvl.operator.If;
import vvl.operator.LesserThan;
import vvl.operator.LesserThanOrEquals;
import vvl.operator.ListOp;
import vvl.operator.Minus;
import vvl.operator.Mult;
import vvl.operator.Not;
import vvl.operator.Operator;
import vvl.operator.Or;
import vvl.operator.Plus;
import vvl.operator.Quote;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.ConsListIterator;

public class LispImpl implements Lisp {
	private HashMap<String, Operator> operators;

	public LispImpl() {
		operators = new HashMap<>();
		operators.put("+", new Plus());
		operators.put("-", new Minus());
		operators.put("*", new Mult());
		operators.put("/", new Div());
		operators.put(">", new GreaterThan());
		operators.put("<", new LesserThan());
		operators.put(">=", new GreaterThanOrEquals());
		operators.put("<=", new LesserThanOrEquals());
		operators.put("=", new Equals());
		operators.put("or", new Or());
		operators.put("not", new Not());
		operators.put("and", new And());
		operators.put("quote", new Quote());
		operators.put("if", new If());
		operators.put("cons", new ConsOp());
		operators.put("list", new ListOp());
		operators.put("car", new Car());
	}

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

	private Object eval(ConsList<Object> consList) throws LispError {
		ConsListIterator<Object> iterator = (ConsListIterator<Object>) consList.iterator();
		Object o;
		String operator = (String) iterator.next();
		ArrayList<Object> operands = new ArrayList<>();

		while (iterator.hasNext()) {
			o = iterator.next();
			operands.add(o);
		}
		if (operator != null)
			return operators.get(operator).apply(operands);
		return consList;
	}
}
