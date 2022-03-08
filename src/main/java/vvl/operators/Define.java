package vvl.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vvl.lisp.LispError;
import vvl.lisp.LispImpl;
import vvl.util.ConsList;

public class Define implements Operator {
	private HashMap<String, Object> vars;

	public Define(Map<String, Object> vars) {
		this.vars = (HashMap<String, Object>) vars;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		if (list.size() == 2) {
			var id = list.get(0).toString();
			if (vars.containsKey(id))
				throw new LispError(id.concat(" is not a valid identifier"));
			
			var o = list.get(1);
			if (o instanceof ConsList)
				o = new LispImpl(vars).evaluate(o);

			// retrieving potential new vars
			if (o instanceof HashMap)
				vars = (HashMap<String, Object>) o;
			else
				vars.put(id, o);
			
			return vars;
		} else
			throw new LispError("Invalids number of operands");
	}

}
