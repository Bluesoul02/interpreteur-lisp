package vvl.util;

import java.util.Iterator;
import java.util.function.Function;

public class ConsListImpl<E> implements ConsList<E> {
	Cons<E, ConsList<E>> cons;

	@Override
	public Iterator<E> iterator() {
		return new ConsListIterator<E>(this);
	}

	@Override
	public ConsList<E> prepend(E e) {
		ConsListImpl<E> list = new ConsListImpl<E>();
		list.setCons(new Cons<E, ConsList<E>>(e, this));
		return list;
	}

	@Override
	public ConsList<E> append(E e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return cons == null;
	}

	@Override
	public E car() {
		return cons.left();
	}

	@Override
	public ConsList<E> cdr() {
		return cons.right();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> ConsList<T> map(Function<E, T> f) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		if (cons != null)
			return cons.toString();
		else
			return "()";
	}

	public Cons<E, ConsList<E>> getCons() {
		return cons;
	}
	
	public void setCons(Cons<E, ConsList<E>> cons) {
		this.cons = cons;
	}

}
