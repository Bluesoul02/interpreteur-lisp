package vvl.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ConsListImpl<E> implements ConsList<E> {

	private Cons<E, ConsList<E>> cons;
	private int size;

	public ConsListImpl(Cons<E, ConsList<E>> cons) {
		super();
		this.cons = cons;
		size = 1;
		if (cons.right() != null)
			size = cons.right().size() + 1;
	}

	public ConsListImpl() {
		super();
		size = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new ConsListIterator<>(this);
	}

	@Override
	public ConsList<E> prepend(E e) {
		return new ConsListImpl<>(new Cons<>(e, this));
	}

	@Override
	public ConsList<E> append(E e) {
		if (isEmpty()) {
			return new ConsListImpl<>(new Cons<>(e, null));
		} else {
			var consList = new ConsListImpl<>(new Cons<>(e, null));
			ConsListIterator<E> iterator = (ConsListIterator<E>) this.iterator();
			ArrayList<E> list = new ArrayList<>();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
			for (var i = list.size() - 1; i >= 0; i--) {
				consList = (ConsListImpl<E>) consList.prepend(list.get(i));
			}
			return consList;
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E car() {
		if (cons == null)
			throw new NoSuchElementException("impossible d'appeler car() sur une liste vide");
		return cons.left();
	}

	@Override
	public ConsList<E> cdr() {
		return cons != null && cons.right() != null ? cons.right() : ConsListFactory.nil();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public <T> ConsList<T> map(Function<E, T> f) {
		Iterator<E> iterator = this.iterator();
		ConsList<T> list = new ConsListImpl<>();
		while (iterator.hasNext()) {
			list = list.append(f.apply(iterator.next()));
		}
		return list;
	}

	@Override
	public String toString() {
		var list = "(";
		if (!isEmpty()) {
			E next;
			Iterator<E> iterator = this.iterator();
			while (iterator.hasNext()) {
				next = iterator.next();
				if (next != null)
					list = list.concat(next.toString());
				else
					list = list.concat("null");
				if (iterator.hasNext())
					list = list.concat(" ");
			}
		}
		list = list.concat(")");
		return list;
	}

	@Override
	public E reduce(E identity, BinaryOperator<E> accumulator) {
		var result = identity;
		ConsListIterator<E> iterator = (ConsListIterator<E>) this.iterator();
		while (iterator.hasNext()) {
			result = accumulator.apply(result, iterator.next());
		}
		return result;
	}

	@Override
	public Object[] toArray() {
		var array = new Object[size()];
		var i = 0;
		ConsListIterator<E> iterator = (ConsListIterator<E>) this.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			array[i++] = ((ConsListImpl<E>) iterator.getCurrent()).car();
		}
		return array;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		ConsListImpl<E> l = (ConsListImpl<E>) o;
		if (this.size() != l.size())
			return false;
		if (!isEmpty() && !l.isEmpty()) {
			ConsListIterator<E> iterator = (ConsListIterator<E>) iterator();
			ConsListIterator<E> oIterator = (ConsListIterator<E>) l.iterator();
			while (iterator.hasNext()) {
				if (!oIterator.hasNext())
					return false;
				if (!iterator.next().equals(oIterator.next()))
					return false;
			}
		}
		return true;
	}

	public Cons<E, ConsList<E>> getCons() {
		return cons;
	}

	public void setCons(Cons<E, ConsList<E>> cons) {
		this.cons = cons;
	}

}
