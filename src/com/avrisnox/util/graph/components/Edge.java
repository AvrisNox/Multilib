package com.avrisnox.util.graph.components;

import com.avrisnox.util.graph.collections.LinkedList;

public class Edge<V, E> {
	LinkedList<Vertex<V>> to;
	Vertex<V> from;
	E element;

	public Edge() {
		to = new LinkedList<Vertex<V>>();
		from = null;
	}

	public Edge(Vertex<V> from) {
		this();
		this.from = from;
	}

	public Edge(LinkedList<Vertex<V>> to) {
		this();
		this.to = to;
	}

	public Edge(Vertex<V> from, LinkedList<Vertex<V>> to) {
		this(from);
		this.to = to;
	}

	public void setTo(LinkedList<Vertex<V>> to) {
		this.to = to;
	}

	public LinkedList<Vertex<V>> getTo() {
		return to;
	}

	public void setFrom(Vertex<V> from) {
		this.from = from;
	}

	public Vertex<V> getFrom() {
		return from;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public E getElement() {
		return element;
	}
}
