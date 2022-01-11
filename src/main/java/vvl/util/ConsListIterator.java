package vvl.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConsListIterator<E> implements Iterator<E>{

	private ConsListImpl<E> current;
	
	public ConsListIterator(ConsListImpl<E> list) {
		this.current = list;
	}

	@Override
	public boolean hasNext() {
		return current.getCons() != null && current.getCons().right() != null;
	}

	@Override
	public E next() {
		if (!hasNext())
			throw new NoSuchElementException("il n'y a pas d'élément suivant");
		current = (ConsListImpl<E>) current.getCons().right();
		return current.getCons().left();
	}
	
	public ConsList<E> getCurrent() {
		return current;
	}

}
