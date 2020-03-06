package com.avrisnox.util.graph.components.middle;

import java.util.LinkedList;

public class Vertex<V, E> {
	private V content;
	private LinkedList<Edge<V, E>> prev;
	private LinkedList<Edge<V, E>> next;

	public Vertex() {
		prev = new LinkedList<Edge<V, E>>();
		next = new LinkedList<Edge<V, E>>();
	}

	public Vertex(V content) {
		this();
		this.content = content;
	}

	public Vertex(LinkedList<Edge<V, E>> prev, LinkedList<Edge<V, E>> next) {
		this();
		this.prev = prev;
		this.next = next;
	}

	public Vertex(V content, LinkedList<Edge<V, E>> prev, LinkedList<Edge<V, E>> next) {
		this(prev, next);
		this.content = content;
	}

	public void setContent(V content) {
		this.content = content;
	}

	public V getContent() {
		return content;
	}

	public void setPrev(LinkedList<Edge<V, E>> prev) {
		this.prev = prev;
	}

	public LinkedList<Edge<V, E>> getPrev() {
		return prev;
	}

	public void setNext(LinkedList<Edge<V, E>> next) {
		this.next = next;
	}

	public LinkedList<Edge<V, E>> getNext() {
		return next;
	}

	public boolean addPrev(Edge<V, E> add) {
		return add != null && addPrev_NoValidate(add) && add.addOut_NoValidate(this);
	}

	public boolean addNext(Edge<V, E> add) {
		return add != null && addNext_NoValidate(add) && add.addIn_NoValidate(this);
	}

	public boolean removePrev(Edge<V, E> rem) {
		return rem != null && removePrev_NoValidate(rem) && rem.removeOut_NoValidate(this);
	}

	public boolean removeNext(Edge<V, E> rem) {
		return rem != null && removeNext_NoValidate(rem) && rem.removeIn_NoValidate(this);
	}

	public boolean addPrev_NoValidate(Edge<V, E> add) {
		return prev.add(add);
	}

	public boolean addNext_NoValidate(Edge<V, E> add) {
		return next.add(add);
	}

	public boolean removePrev_NoValidate(Edge<V, E> rem) {
		return prev.remove(rem);
	}

	public boolean removeNext_NoValidate(Edge<V, E> rem) {
		return next.remove(rem);
	}

	public LinkedList<Vertex<V, E>> getPreviousVertex() {
		LinkedList<Vertex<V, E>> all = new LinkedList<Vertex<V, E>>();
		for (Edge<V, E> edge : prev)
			all.addAll(edge.getIn());
		return all;
	}

	public LinkedList<Vertex<V, E>> getNextVertex() {
		LinkedList<Vertex<V, E>> all = new LinkedList<Vertex<V, E>>();
		for (Edge<V, E> edge : next)
			all.addAll(edge.getOut());
		return all;
	}
}
