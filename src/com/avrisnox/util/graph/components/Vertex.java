package com.avrisnox.util.graph.components;

public class Vertex<T> {
	private T element;
	private Edge<T, ?> next;
	private Edge<T, ?> prev;

	public Vertex() { }
	public Vertex(T element){
		this();
		setElement(element);
	}
	public Vertex(Edge<T, ?> next, Edge<T, ?> prev){
		this();
		setNext(next);
		setPrev(prev);
	}
	public Vertex(Edge<T, ?> next, Edge<T, ?> prev, T element) {
		this(next, prev);
		setElement(element);
	}

	public void setElement(T element) {
		this.element = element;
	}

	public T getElement() {
		return element;
	}

	public void setNext(Edge<T, ?> next) {
		this.next = next;
	}

	public Edge<T, ?> getNext() {
		return next;
	}

	public void setPrev(Edge<T, ?> prev) {
		this.prev = prev;
	}

	public Edge<T, ?> getPrev() {
		return prev;
	}
}
