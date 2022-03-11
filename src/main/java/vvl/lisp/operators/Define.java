package vvl.lisp.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.lisp.exceptions.InvalidIdentifierException;
import vvl.lisp.exceptions.InvalidNumberOfOperands;
import vvl.util.ConsList;

public class Define implements Operator {
	private HashMap<String, Object> vars;
	private ArrayList<String> banWords;

	public Define(Map<String, Object> vars, List<String> operators) {
		this.vars = (HashMap<String, Object>) vars;
		banWords = (ArrayList<String>) operators;
		banWords.add("nil");
	}

	public void update(Map<String, Object> vars) {
		this.vars = (HashMap<String, Object>) vars;
	}

	@Override
	public Object apply(ArrayList<Object> list, LispImpl lispImpl) throws LispError {
		if (list.size() == 2) {
			var id = list.get(0);
			if (vars.containsKey(id) || banWords.contains(id) || !(id instanceof String))
				throw new InvalidIdentifierException(id.toString());
			var o = list.get(1);
			if (o instanceof ConsList)
				o = lispImpl.evaluate(o);

			vars.put((String) id, o);

			return vars;
		} else
			throw new InvalidNumberOfOperands();
	}

}
