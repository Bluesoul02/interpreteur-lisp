package vvl.lisp.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.lisp.exceptions.InvalidIdentifierException;
import vvl.lisp.exceptions.InvalidNumberOfOperands;
import vvl.lisp.exceptions.UndefinedException;
import vvl.util.ConsList;

public class SetOp implements Operator {
	private HashMap<String, Object> vars;
	private Set<String> banWords;

	public SetOp(Map<String, Object> vars, Map<String, Operator> operators) {
		this.vars = (HashMap<String, Object>) vars;
		operators.put("nil", null);
		banWords = operators.keySet();
	}
	
	public void update(Map<String, Object> vars) {
		this.vars = (HashMap<String, Object>) vars;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		if (list.size() == 2) {
			var id = list.get(0);

			if (!(id instanceof String) || banWords.contains(id))
				throw new InvalidIdentifierException(id.toString());

			if (!vars.containsKey(id))
				throw new UndefinedException((String) id);

			var o = list.get(1);
			if (o instanceof ConsList)
				o = new LispImpl(vars).evaluate(o);

			// retrieving potential update on vars or new vars
			if (o instanceof HashMap)
				vars = (HashMap<String, Object>) o;
			else
				vars.replace((String) id, o);

			return vars;
		} else
			throw new InvalidNumberOfOperands();
	}

}
