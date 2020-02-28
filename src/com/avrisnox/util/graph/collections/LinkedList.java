package com.avrisnox.util.graph.collections;

import com.avrisnox.util.graph.components.Edge;
import com.avrisnox.util.graph.components.Vertex;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>{
	private Vertex<T> head;
	private Vertex<T> tail;
	private int size;

	public LinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	public LinkedList(T element) {
		this();
		Vertex<T> nv = new Vertex<T>(element);
		Edge<T, Boolean> ne = new Edge<T, Boolean>();
		ne.setElement(true);
		ne.setTo(new LinkedList<Vertex<T>>(nv));
		head = nv;
		tail = nv;
		size++;
	}

	public LinkedList(T... elements) {
		this();
	}

	public class LLIterator implements Iterator<T> {
		Vertex<T> current;

		public LLIterator() {
			current = head;
		}

		@Override
		public boolean hasNext() {
			return head.getNext() != null;
		}

		@Override
		public T next() {
			current = (Vertex<T>)head.getNext().getTo().getFirst();
			return current.getElement();
		}
	}
	@Override
	public Iterator<T> iterator() {
		return new LLIterator();
	}

	public int getSize() {
		return size;
	}

	public Vertex<T> getFirst() {
		return head;
	}

	public void addLast(T element) {
		Vertex<T> nv = new Vertex<T>(element);
		Edge<T, Boolean> ne = new Edge<T, Boolean>(tail);
		ne.setElement(true);
		ne.setTo(new LinkedList<Vertex<T>>(nv));
		tail.setNext(ne);
		tail = (Vertex<T>)tail.getNext().getTo().getFirst();
		size++;
	}

	public static void main(String... args) {
		LinkedList<Boolean> list = new LinkedList<Boolean>(true);
		list.addLast(false);
		list.addLast(false);
		list.addLast(true);
		System.out.println("Checkpoint 1: size = " + list.getSize());
		System.out.println();
		for(Boolean bool : list) {
			System.out.println("Item is: " + bool);
		}
		System.out.println("Checkpoint 2: completed list read. No faults.");
	}
}
