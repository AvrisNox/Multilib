package com.avrisnox.util.graph.components.middle;

import java.util.LinkedList;

/**Edge
 * Classifies an edge component.
 * @param <V> The content value for the vertices this
 * @param <E>
 */
public class Edge<V, E> {
	private E content;
	private LinkedList<Vertex<V, E>> in;
	private LinkedList<Vertex<V, E>> out;

	public Edge () {
		in = new LinkedList<Vertex<V, E>>();
		out = new LinkedList<Vertex<V, E>>();
	}

	public Edge (E content) {
		this();
		this.content = content;
	}

	public Edge(LinkedList<Vertex<V, E>> in, LinkedList<Vertex<V, E>> out) {
		this();
		this.in = in;
		this.out = out;
	}

	public Edge (E content, LinkedList<Vertex<V, E>> in, LinkedList<Vertex<V, E>> out) {
		this(in, out);
		this.content = content;
	}

	public void setContent(E content) {
		this.content = content;
	}

	public E getContent() {
		return content;
	}

	public void setIn(LinkedList<Vertex<V, E>> in) {
		this.in = in;
	}

	public LinkedList<Vertex<V, E>> getIn() {
		return in;
	}

	public void setOut(LinkedList<Vertex<V, E>> out) {
		this.out = out;
	}

	public LinkedList<Vertex<V, E>> getOut() {
		return out;
	}

	public boolean addIn(Vertex<V, E> add) {
		return add != null && addIn_NoValidate(add) && add.addNext_NoValidate(this);
	}

	public boolean addOut(Vertex<V, E> add) {
		return add != null && addOut_NoValidate(add) && add.addPrev_NoValidate(this);
	}

	public boolean removeIn(Vertex<V, E> rem) {
		return rem != null && removeIn_NoValidate(rem) && rem.removeNext_NoValidate(this);
	}

	public boolean removeOut(Vertex<V, E> rem) {
		return rem != null && removeOut_NoValidate(rem) && rem.removePrev_NoValidate(this);
	}

	public boolean addIn_NoValidate(Vertex<V, E> add) {
		return in.add(add);
	}

	public boolean addOut_NoValidate(Vertex<V, E> add) {
		return out.add(add);
	}

	public boolean removeIn_NoValidate(Vertex<V, E> rem) {
		return in.remove(rem);
	}

	public boolean removeOut_NoValidate(Vertex<V, E> rem) {
		return out.remove(rem);
	}
}
