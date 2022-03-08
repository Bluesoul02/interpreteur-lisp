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

	@Override
	public Object apply(ArrayList<Object> list) throws LispError {
		if (list.size() == 2) {
			Object o = list.get(1);
			if (o instanceof ConsList)
				o = new LispImpl().evaluate(o);
			vars.put(list.get(0).toString(), o);
			return vars;
		} else
			throw new LispError("Invalids number of operands");
	}

}
