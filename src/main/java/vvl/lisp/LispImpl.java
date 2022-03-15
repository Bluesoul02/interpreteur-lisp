package vvl.lisp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import vvl.lisp.exceptions.UndefinedException;
import vvl.lisp.operators.And;
import vvl.lisp.operators.Car;
import vvl.lisp.operators.Cdr;
import vvl.lisp.operators.ConsOp;
import vvl.lisp.operators.Define;
import vvl.lisp.operators.Div;
import vvl.lisp.operators.Equals;
import vvl.lisp.operators.GreaterThan;
import vvl.lisp.operators.GreaterThanOrEquals;
import vvl.lisp.operators.If;
import vvl.lisp.operators.Lambda;
import vvl.lisp.operators.LesserThan;
import vvl.lisp.operators.LesserThanOrEquals;
import vvl.lisp.operators.ListOp;
import vvl.lisp.operators.Minus;
import vvl.lisp.operators.Mult;
import vvl.lisp.operators.Not;
import vvl.lisp.operators.Operator;
import vvl.lisp.operators.Or;
import vvl.lisp.operators.Plus;
import vvl.lisp.operators.Quote;
import vvl.lisp.operators.SetOp;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.ConsListIterator;

public class LispImpl implements Lisp {
	private static final String DEFINE = "define";
	private static final String SET = "set!";
	private HashMap<String, Operator> operators;
	private HashMap<String, Object> vars;
	
	public LispImpl() {
		operators = new HashMap<>();
		vars = new HashMap<>();
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
		operators.put("cdr", new Cdr());
		operators.put("lambda", new Lambda());
		ArrayList<String> banWords = new ArrayList<>(operators.keySet());
		banWords.add("nil");
		banWords.add(DEFINE);
		banWords.add(SET);
		operators.put(DEFINE, new Define(vars, banWords));
		operators.put(SET, new SetOp(vars, banWords));
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
		if (!expr.contains("(") && length > 1)
			throw new LispError("Multiple elements must be in a list");
		else if (!expr.contains("("))
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
		else if (ex.equals("nil"))
			ex = ConsListFactory.nil();
		else if (ex instanceof String && vars.containsKey(ex))
			ex = vars.get(ex);
		else if (ex instanceof String)
			throw new UndefinedException((String) ex);
		return ex;
	}

	@SuppressWarnings("unchecked")
	private Object eval(ConsList<Object> consList) throws LispError {
		ConsListIterator<Object> iterator = (ConsListIterator<Object>) consList.iterator();
		Object o;
		String operator;
		if (iterator.hasNext())
			operator = (String) iterator.next();
		else
			return consList;
		ArrayList<Object> operands = new ArrayList<>();

		while (iterator.hasNext()) {
			o = iterator.next();
			if (vars.containsKey(o) && !operator.equals(DEFINE) && !operator.equals(SET))
				o = vars.get(o);
			operands.add(o);
		}
		if (operators.containsKey(operator)) {
			Object res = operators.get(operator).apply(operands, this);
			if (res instanceof HashMap<?, ?>) {
				vars = (HashMap<String, Object>) res;
				((SetOp) operators.get(SET)).update(vars);
				((Define) operators.get(DEFINE)).update(vars);
				return ((HashMap<?, ?>) res).get(operands.get(0));
			} else
				return res;
		} else
			throw new LispError(operator.concat(" is not a valid operator"));
	}
}
