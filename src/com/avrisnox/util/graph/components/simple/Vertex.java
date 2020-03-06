package com.avrisnox.util.graph.components.simple;

import java.util.LinkedList;

/**Vertex
 * Classifies an vertex component.
 * @param <T> The type of content stored in the vertices
 */
public class Vertex<T> {
	private T content;
	private LinkedList<Edge<T>> prev;
	private LinkedList<Edge<T>> next;

	/**
	 * Vertex()
	 * Simple initializer.
	 */
	public Vertex() {
		prev = new LinkedList<Edge<T>>();
		next = new LinkedList<Edge<T>>();
	}

	/**
	 * Vertex(T)
	 * Initializes the vertex with content.
	 *
	 * @param content The content to store in this vertex
	 */
	public Vertex(T content) {
		this();
		this.content = content;
	}

	/**
	 * Vertex(LinkedList, LinkedList)
	 * Initializes the vertex with the previous and next lists.
	 *
	 * @param prev The edges coming into this vertex
	 * @param next The edges going out of this vertex
	 */
	public Vertex(LinkedList<Edge<T>> prev, LinkedList<Edge<T>> next) {
		this();
		this.prev = prev;
		this.next = next;
	}

	/**
	 * Vertex(T, LinkedList, LinkedList)
	 * Initializes the vertex with previous and next lists and content.
	 *
	 * @param content The content to store in this vertex
	 * @param prev    The edges coming into this vertex
	 * @param next    The edges going out of this vertex
	 */
	public Vertex(T content, LinkedList<Edge<T>> prev, LinkedList<Edge<T>> next) {
		this(prev, next);
		this.content = content;
	}

	/**
	 * setContent(T)
	 * Sets the content of this vertex.
	 *
	 * @param content The content to store in this vertex
	 */
	public void setContent(T content) {
		this.content = content;
	}

	/**
	 * getContent()
	 * Gets the content stored in this vertex.
	 *
	 * @return The content stored in this vertex
	 */
	public T getContent() {
		return content;
	}

	/**
	 * setPrev(LinkedList)
	 * Sets the previous edges list.
	 *
	 * @param prev The edges coming into this vertex
	 */
	public void setPrev(LinkedList<Edge<T>> prev) {
		this.prev = prev;
	}

	/**
	 * getPrev()
	 * Gets the previous edges list.
	 *
	 * @return The edges coming into this vertex
	 */
	public LinkedList<Edge<T>> getPrev() {
		return prev;
	}

	/**
	 * setNext(LinkedList)
	 * Sets the next edges list.
	 *
	 * @param next The edges going out of this vertex
	 */
	public void setNext(LinkedList<Edge<T>> next) {
		this.next = next;
	}

	/**
	 * getNext()
	 * Gets the next edges list.
	 *
	 * @return The edges going out of this vertex
	 */
	public LinkedList<Edge<T>> getNext() {
		return next;
	}

	/**
	 * addPrev(Edge)
	 * If the "added" edge is not null, adds it to the list of incoming edges and updates the edge to point to this.
	 *
	 * @param add The edge to add
	 * @return True if the operation could complete
	 */
	public boolean addPrev(Edge<T> add) {
		return add != null && addPrev_NoValidate(add) && add.addOut_NoValidate(this);
	}

	/**
	 * addNext(Edge)
	 * If the "added" edge is not null, adds it to the list of outgoing edges and updates the edge to point to this.
	 *
	 * @param add The edge to add
	 * @return True if the operation could complete
	 */
	public boolean addNext(Edge<T> add) {
		return add != null && addNext_NoValidate(add) && add.addIn_NoValidate(this);
	}

	/**
	 * removePrev(rem)
	 * If the "removed" edge is not null, removes it from the list of incoming edges and updates the edge to point to no
	 * longer point to this.
	 *
	 * @param rem The edge to remove
	 * @return True if the operation could complete
	 */
	public boolean removePrev(Edge<T> rem) {
		return rem != null && removePrev_NoValidate(rem) && rem.removeOut_NoValidate(this);
	}

	/**
	 * removeNext(Edge)
	 * If the "removed" edge is not null, removes it from the list of outgoing edges and updates the edge to point to no
	 * longer point to this.
	 *
	 * @param rem The edge to remove
	 * @return True if the operation could complete
	 */
	public boolean removeNext(Edge<T> rem) {
		return rem != null && removeNext_NoValidate(rem) && rem.removeIn_NoValidate(this);
	}

	/**
	 * addPrev_NoValidate(Edge)
	 * Adds an edge to the list of incoming edges.
	 *
	 * @param add The edge to add
	 * @return True if the operation could complete
	 */
	public boolean addPrev_NoValidate(Edge<T> add) {
		return prev.add(add);
	}

	/**
	 * addNext_NoValidate(Edge)
	 * Adds an edge to the list of outgoing edges.
	 *
	 * @param add The edge to add
	 * @return True if the operation could complete
	 */
	public boolean addNext_NoValidate(Edge<T> add) {
		return next.add(add);
	}

	/**
	 * removePrev_NoValidate(Edge)
	 * Removes an edge from the list of incoming edges.
	 *
	 * @param rem The edge to remove
	 * @return True if the operation could complete
	 */
	public boolean removePrev_NoValidate(Edge<T> rem) {
		return prev.remove(rem);
	}

	/**
	 * removeNext_NoValidate(Edge)
	 * Removes an edge from the list of outgoing edges.
	 *
	 * @param rem The edge to remove
	 * @return True if the operation could complete
	 */
	public boolean removeNext_NoValidate(Edge<T> rem) {
		return next.remove(rem);
	}

	/**
	 * getPreviousVertex()
	 * Traverses all incoming edges and all vertices that lead into those edges, then assembles a single list containing
	 * these vertices and returns it.
	 *
	 * @return List of all possible vertices that lead to this vertex
	 */
	public LinkedList<Vertex<T>> getPreviousVertex() {
		LinkedList<Vertex<T>> all = new LinkedList<Vertex<T>>();
		for (Edge<T> edge : prev)
			all.add(edge.getIn());
		return all;
	}

	/**
	 * getNextVertex()
	 * Traverses all outgoing edges and all vertices that those edges lead to, then assembles a single list containing
	 * these vertices and returns it.
	 *
	 * @return List of all possible vertices that this vertex leads to
	 */
	public LinkedList<Vertex<T>> getNextVertex() {
		LinkedList<Vertex<T>> all = new LinkedList<Vertex<T>>();
		for (Edge<T> edge : next)
			all.add(edge.getOut());
		return all;
	}
}
