package vvl.util;

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
	
	@SafeVarargs
	public ConsListImpl(E... ts) {
		super();
		for (E e : ts) {
			append(e);
		}
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
			ConsList<E> head = getHead();
			ConsListImpl<E> list = new ConsListImpl<E>();
			list.setCons(new Cons<E, ConsList<E>>(e, head));
			((ConsListImpl<E>) head).setPrev(list);
			return list;
		}
	}

	@Override
	public ConsList<E> append(E e) {
		if (isEmpty()) {
			cons = new Cons<E, ConsList<E>>(e, null);
		}
		else {
			ConsListIterator<E> iterator = (ConsListIterator<E>) this.iterator();
			while (iterator.hasNext()) iterator.next();
			ConsListImpl<E> consList = new ConsListImpl<E>();
			consList.setCons(new Cons<E, ConsList<E>>(e, null));
			consList.setPrev((ConsListImpl<E>) iterator.getCurrent());
			((ConsListImpl<E>) iterator.getCurrent()).getCons().setRight(consList);
		}
		return this;
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
	
	public void setPrev(ConsListImpl<E> prev) {
		this.prev = prev;
	}

	@Override
	public int size() {
		if (isEmpty()) return 0;
		ConsList<E> head = getHead();
		int size = 1;
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
			if (this != null) list = list.concat(car().toString());
			while (iterator.hasNext()) {
				list = list.concat(" ");
				next = iterator.next();
				if (next != null) list = list.concat(next.toString());
				else list = list.concat("null");
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
        result = accumulator.apply(result, car());
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
        array[i++] = ((ConsListImpl<E>) iterator.getCurrent()).car();
		while (iterator.hasNext()) {
	        iterator.next();
	        array[i++] = ((ConsListImpl<E>) iterator.getCurrent()).car();
		}
        return array;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof @SuppressWarnings("rawtypes") ConsList l) {
			//ConsList<E> l = (ConsList<E>) o;
			if (size() != l.size()) return false;
			if (car() == null) {
				if (l.car() == null) {
					return cdr().equals(l.cdr());
				}
				else return false;
			}
			return car().equals(l.car()) && cdr().equals(l.cdr());
		}
		return false;
	}

	public Cons<E, ConsList<E>> getCons() {
		return cons;
	}
	
	public void setCons(Cons<E, ConsList<E>> cons) {
		this.cons = cons;
	}

}
