package vvl.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ConsListImpl<E> implements ConsList<E> {

	private ConsListImpl<E> prev;
	private Cons<E, ConsList<E>> cons;

	public ConsListImpl(Cons<E, ConsList<E>> cons) {
		super();
		this.cons = cons;
	}
	
	public ConsListImpl() {
		super();
	}

	@Override
	public Iterator<E> iterator() {
		return new ConsListIterator<E>(this);
	}

	@Override
	public ConsList<E> prepend(E e) {
		if (isEmpty()) {
			cons = new Cons<E, ConsList<E>>(e, null);
			return this;
		} 
		else {
			ConsListImpl<E> list = new ConsListImpl<E>(new Cons<E, ConsList<E>>(e, this));
			return list;
		}
	}

	@Override
	public ConsList<E> append(E e) {
		if (isEmpty()) {
			cons = new Cons<E, ConsList<E>>(e, null);
			return this;
		}
		else {
			ConsListImpl<E> consList = new ConsListImpl<E>(new Cons<E, ConsList<E>>(e, null));
			ConsListIterator<E> iterator = (ConsListIterator<E>) this.iterator();
			ArrayList<E> list = new ArrayList<E>();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
			for (int i = list.size() - 1; i >= 0 ; i--) {
				consList = (ConsListImpl<E>) consList.prepend(list.get(i));
			}
			return consList;
		}
	}

	@Override
	public boolean isEmpty() {
		return cons == null && prev == null;
	}

	@Override
	public E car() {
		if (cons == null)
			throw new NoSuchElementException("impossible d'appeler car() sur une liste vide");
		return cons.left();
	}

	@Override
	public ConsList<E> cdr() {
		return cons != null && cons.right() != null ? cons.right() : this;
	}
	
	public ConsListImpl<E> getHead() {
		if (prev == null) return this;
		ConsListImpl<E> previous = prev;
		previous = (ConsListImpl<E>) previous.getHead();
		return previous;
	}

	@Override
	public int size() {
		if (isEmpty()) return 0;
		ConsList<E> head = getHead();
		int size = 0;
		ConsListIterator<E> iterator = (ConsListIterator<E>) head.iterator();
		while (iterator.hasNext()) {
			size++;
			iterator.next();
		}
		return size;
	}

	@Override
	public <T> ConsList<T> map(Function<E, T> f) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		String list = "(";
		if (!isEmpty()) {
			E next;
			ConsList<E> head = getHead();
			ConsListIterator<E> iterator = (ConsListIterator<E>) head.iterator();
			while (iterator.hasNext()) {
				next = iterator.next();
				if (next != null) list = list.concat(next.toString());
				else list = list.concat("null");
				if (iterator.hasNext())list = list.concat(" ");
			}
		}
		list = list.concat(")");
		return list;
	}
	
	@Override
	public E reduce(E identity, BinaryOperator<E> accumulator) {
		E result = identity;
		ConsList<E> head = getHead();
		ConsListIterator<E> iterator = (ConsListIterator<E>) head.iterator();
		while (iterator.hasNext()) {
            result = accumulator.apply(result, iterator.next());
        }
        return result;
	}

	@Override
	public Object[] toArray() {
        Object[] array = new Object[size()];
        int i = 0;
		ConsList<E> head = getHead();
		ConsListIterator<E> iterator = (ConsListIterator<E>) head.iterator();
		while (iterator.hasNext()) {
	        iterator.next();
	        array[i++] = ((ConsListImpl<E>) iterator.getCurrent()).car();
		}
        return array;
	}
	
//	@Override
//	public boolean equals(Object o) {
//		if (o == null) return false;
//		if (o instanceof @SuppressWarnings("rawtypes") ConsList l) {
//			//ConsList<E> l = (ConsList<E>) o;
//			if (size() != l.size()) return false;
//			if (car() == null) {
//				if (l.car() == null) {
//					return cdr().equals(l.cdr());
//				}
//				else return false;
//			}
//			return car().equals(l.car()) && cdr().equals(l.cdr());
//		}
//		return false;
//	}

	public Cons<E, ConsList<E>> getCons() {
		return cons;
	}

	public void setCons(Cons<E, ConsList<E>> cons) {
		this.cons = cons;
	}

}
