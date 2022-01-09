package vvl.util;

import java.util.Iterator;

public class ConsListIterator<E> implements Iterator<E>{

	private ConsListImpl<E> current;
	
	public ConsListIterator(ConsListImpl<E> list) {
		this.current = list;
	}

	@Override
	public boolean hasNext() {
		return current.getCons().right() != null;
	}

	@Override
	public E next() {
		current = (ConsListImpl<E>) current.getCons().right();
		return current.car();
	}

}
