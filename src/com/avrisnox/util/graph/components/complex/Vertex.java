package com.avrisnox.util.graph.components.complex;

import java.util.LinkedList;

/**Vertex
 * Classifies a complex vertex component.
 * @param <VP> The type for the vertex BEFORE this vertex
 * @param <EP> The type for the edge INTO this vertex
 * @param <V> The type of this vertex
 * @param <EN> The type for the edge OUT OF this vertex
 * @param <VN> The type for the vertex AFTER this vertex
 */
public class Vertex<VP, EP, V, EN, VN> {
	private V content;
	private LinkedList<Edge<?, VP, EP, V, EN>> prev;
	private LinkedList<Edge<EP, V, EN, VN, ?>> next;

	public Vertex() {
		prev = new LinkedList<Edge<?, VP, EP, V, EN>>();
		next = new LinkedList<Edge<EP, V, EN, VN, ?>>();
	}

	public Vertex(V content) {
		this();
		this.content = content;
	}

	public Vertex(LinkedList<Edge<?, VP, EP, V, EN>> prev, LinkedList<Edge<EP, V, EN, VN, ?>> next) {
		this();
		this.prev = prev;
		this.next = next;
	}

	public Vertex(V content, LinkedList<Edge<?, VP, EP, V, EN>> prev, LinkedList<Edge<EP, V, EN, VN, ?>> next) {
		this(prev, next);
		this.content = content;
	}

	public void setContent(V content) {
		this.content = content;
	}

	public V getContent() {
		return content;
	}

	public void setPrev(LinkedList<Edge<?, VP, EP, V, EN>> prev) {
		this.prev = prev;
	}

	public LinkedList<Edge<?, VP, EP, V, EN>> getPrev() {
		return prev;
	}

	public void setNext(LinkedList<Edge<EP, V, EN, VN, ?>> next) {
		this.next = next;
	}

	public LinkedList<Edge<EP, V, EN, VN, ?>> getNext() {
		return next;
	}

	public boolean addPrev(Edge<?, VP, EP, V, EN> add) {
		return add != null && addPrev_NoValidate(add) && add.addOut_NoValidate(this);
	}

	public boolean addNext(Edge<EP, V, EN, VN, ?> add) {
		return add != null && addNext_NoValidate(add) && add.addIn_NoValidate(this);
	}

	public boolean removePrev(Edge<?, VP, EP, V, EN> rem) {
		return rem != null && removePrev_NoValidate(rem) && rem.removeOut_NoValidate(this);
	}

	public boolean removeNext(Edge<EP, V, EN, VN, ?> rem) {
		return rem != null && removeNext_NoValidate(rem) && rem.removeIn_NoValidate(this);
	}

	public boolean addPrev_NoValidate(Edge<?, VP, EP, V, EN> add) {
		return prev.add(add);
	}

	public boolean addNext_NoValidate(Edge<EP, V, EN, VN, ?> add) {
		return next.add(add);
	}

	public boolean removePrev_NoValidate(Edge<?, VP, EP, V, EN> rem) {
		return prev.remove(rem);
	}

	public boolean removeNext_NoValidate(Edge<EP, V, EN, VN, ?> rem) {
		return next.remove(rem);
	}

	public LinkedList<Vertex<?, ?, VP, EP, V>> getPreviousVertex() {
		LinkedList<Vertex<?, ?, VP, EP, V>> all = new LinkedList<Vertex<?, ?, VP, EP, V>>();
		for (Edge<?, VP, EP, V, EN> edge : prev)
			all.addAll(edge.getIn());
		return all;
	}

	public LinkedList<Vertex<V, EN, VN, ?, ?>> getNextVertex() {
		LinkedList<Vertex<V, EN, VN, ?, ?>> all = new LinkedList<Vertex<V, EN, VN, ?, ?>>();
		for (Edge<EP, V, EN, VN, ?> edge : next)
			all.addAll(edge.getOut());
		return all;
	}
}
