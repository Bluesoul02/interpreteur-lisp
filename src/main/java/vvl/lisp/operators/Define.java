package vvl.lisp.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.lisp.exceptions.InvalidIdentifierException;
import vvl.lisp.exceptions.InvalidNumberOfOperands;
import vvl.util.ConsList;

public class Define implements Operator {
	private HashMap<String, Object> vars;
	private Set<String> banWords;

	public Define(Map<String, Object> vars, Map<String, Operator> operators) {
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
			if (vars.containsKey(id) || banWords.contains(id) || !(id instanceof String))
				throw new InvalidIdentifierException(id.toString());
			var o = list.get(1);
			if (o instanceof ConsList)
				o = new LispImpl(vars).evaluate(o);

			// retrieving potential new vars
			if (o instanceof HashMap)
				vars = (HashMap<String, Object>) o;
			else
				vars.put((String) id, o);

			return vars;
		} else
			throw new InvalidNumberOfOperands();
	}

}
