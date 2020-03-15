package com.avrisnox.util.graph.components.complex;

import java.util.LinkedList;

/**Edge
 * Classifies a complex edge component.
 * @param <EP> The type for the edge BEFORE this edge
 * @param <VP> The type for the vertex this edge is FROM
 * @param <E> The type of this edge
 * @param <VN> The type for the vertex this edge is TO
 * @param <EN> The type for the edge AFTER this edge
 */
public class Edge<EP, VP, E, VN, EN> {
	private E content;
	private LinkedList<Vertex<?, EP, VP, E, VN>> in;
	private LinkedList<Vertex<VP, E, VN, EN, ?>> out;

	public Edge () {
		in = new LinkedList<Vertex<?, EP, VP, E, VN>>();
		out = new LinkedList<Vertex<VP, E, VN, EN, ?>>();
	}

	public Edge (E content) {
		this();
		this.content = content;
	}

	public Edge(LinkedList<Vertex<?, EP, VP, E, VN>> in, LinkedList<Vertex<VP, E, VN, EN, ?>> out) {
		this();
		this.in = in;
		this.out = out;
	}

	public Edge (E content, LinkedList<Vertex<?, EP, VP, E, VN>> in, LinkedList<Vertex<VP, E, VN, EN, ?>> out) {
		this(in, out);
		this.content = content;
	}

	public void setContent(E content) {
		this.content = content;
	}

	public E getContent() {
		return content;
	}

	public void setIn(LinkedList<Vertex<?, EP, VP, E, VN>> in) {
		this.in = in;
	}

	public LinkedList<Vertex<?, EP, VP, E, VN>> getIn() {
		return in;
	}

	public void setOut(LinkedList<Vertex<VP, E, VN, EN, ?>> out) {
		this.out = out;
	}

	public LinkedList<Vertex<VP, E, VN, EN, ?>> getOut() {
		return out;
	}

	public boolean addIn(Vertex<?, EP, VP, E, VN> add) {
		return add != null && addIn_NoValidate(add) && add.addNext_NoValidate(this);
	}

	public boolean addOut(Vertex<VP, E, VN, EN, ?> add) {
		return add != null && addOut_NoValidate(add) && add.addPrev_NoValidate(this);
	}

	public boolean removeIn(Vertex<?, EP, VP, E, VN> rem) {
		return rem != null && removeIn_NoValidate(rem) && rem.removeNext_NoValidate(this);
	}

	public boolean removeOut(Vertex<VP, E, VN, EN, ?> rem) {
		return rem != null && removeOut_NoValidate(rem) && rem.removePrev_NoValidate(this);
	}

	public boolean addIn_NoValidate(Vertex<?, EP, VP, E, VN> add) {
		return in.add(add);
	}

	public boolean addOut_NoValidate(Vertex<VP, E, VN, EN, ?> add) {
		return out.add(add);
	}

	public boolean removeIn_NoValidate(Vertex<?, EP, VP, E, VN> rem) {
		return in.remove(rem);
	}

	public boolean removeOut_NoValidate(Vertex<VP, E, VN, EN, ?> rem) {
		return out.remove(rem);
	}
}
