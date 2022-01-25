package vvl.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConsListIterator<E> implements Iterator<E> {

	private ConsListImpl<E> current;
	private boolean first;

	public ConsListIterator(ConsListImpl<E> list) {
		this.current = list;
		first = true;
	}

	@Override
	public boolean hasNext() {
		return current.getCons() != null && ((current.getCons().right() != null
				&& (((ConsListImpl<E>) current.getCons().right()).getCons()) != null) || first);
	}

	@Override
	public E next() {
		if (first) {
			first = false;
			return current.car();
		} else if (!hasNext())
			throw new NoSuchElementException("il n'y a pas d'élément suivant");
		current = (ConsListImpl<E>) current.getCons().right();
		return current.car();
	}

	public ConsList<E> getCurrent() {
		return current;
	}

}
