package com.avrisnox.util.graph.components.simple;

/**Edge
 * Classifies an edge component.
 * @param <T> The type of content stored in the vertices
 */
public class Edge<T> {
	private Vertex<T> in;
	private Vertex<T> out;

	/**
	 * Edge()
	 * Due to the nature of the simple edge, does nothing.
	 */
	public Edge() {

	}

	/**
	 * Edge(Vertex, Vertex)
	 * Creates an edge with the given in and out vertices.
	 *
	 * @param in  The vertex that directs into this edge
	 * @param out The vertex that this edge directs to
	 */
	public Edge(Vertex<T> in, Vertex<T> out) {
		this();
		this.in = in;
		this.out = out;
	}

	/**
	 * setIn(Vertex)
	 * Sets the in vertex.
	 *
	 * @param in The vertex that directs into this edge
	 */
	public void setIn(Vertex<T> in) {
		this.in = in;
	}

	/**
	 * getIn()
	 * Gets the in vertex.
	 *
	 * @return The vertex that directs into this edge
	 */
	public Vertex<T> getIn() {
		return in;
	}

	/**
	 * setOut(Vertex)
	 * Sets the out vertex.
	 *
	 * @param out The vertex that this edge directs to
	 */
	public void setOut(Vertex<T> out) {
		this.out = out;
	}

	/**
	 * getOut()
	 * Gets the out vertex.
	 *
	 * @return The vertex that this edge directs to
	 */
	public Vertex<T> getOut() {
		return out;
	}

	/**
	 * addIn(Vertex)
	 * If the "added" vertex is not null, sets in to it and adds this edge to the vertexes' next list.
	 *
	 * @param add The vertex to add
	 * @return True if the operation could complete
	 */
	public boolean addIn(Vertex<T> add) {
		return add != null && addIn_NoValidate(add) && add.addNext_NoValidate(this);
	}

	/**
	 * addOut(Vertex)
	 * If the "added" vertex is not null, sets out to it and adds this edge to the vertexes' prev list.
	 *
	 * @param add The vertex to add
	 * @return True if the operation could complete
	 */
	public boolean addOut(Vertex<T> add) {
		return add != null && addOut_NoValidate(add) && add.addPrev_NoValidate(this);
	}

	/**
	 * removeIn(Vertex)
	 * If the "removed" vertex was not null, sets in to null and removes this edge from the vertexes' next list.
	 *
	 * @param rem The vertex to remove
	 * @return True if the operation could complete
	 */
	public boolean removeIn(Vertex<T> rem) {
		return rem != null && removeIn_NoValidate(rem) && rem.removeNext_NoValidate(this);
	}

	/**
	 * removeOut(Vertex)
	 * If the "removed" vertex was not null, sets out to null and removes this edge from the vertexes' prev list.
	 *
	 * @param rem The vertex to remove
	 * @return True if the operation could complete
	 */
	public boolean removeOut(Vertex<T> rem) {
		return rem != null && removeOut_NoValidate(rem) && rem.removePrev_NoValidate(this);
	}

	/* NOTE: The following (no validate) functions are identical to the setters above with a couple exceptions (namely
	remove checking against the given vertex and all functions returning a boolean). While these functions could be
	eliminated functionally, they remain to allow for relatively easy swapping between vertex/edge complexity. */

	/**
	 * addIn_NoValidate(Vertex)
	 * Sets in to add and returns true.
	 *
	 * @param add The vertex to add
	 * @return True
	 */
	public boolean addIn_NoValidate(Vertex<T> add) {
		in = add;
		return true;
	}

	/**
	 * addOut_NoValidate(Vertex)
	 * Sets out to add and returns true.
	 *
	 * @param add The vertex to add
	 * @return True
	 */
	public boolean addOut_NoValidate(Vertex<T> add) {
		out = add;
		return true;
	}

	/**
	 * removeIn_NoValidate(Vertex)
	 * If rem is the same as the vertex currently in in, sets in to null and returns true; else, returns false.
	 *
	 * @param rem The vertex to remove
	 * @return True if the given vertex was the in vertex
	 */
	public boolean removeIn_NoValidate(Vertex<T> rem) {
		if (rem.equals(in)) {
			in = null;
			return true;
		}
		return false;
	}

	/**
	 * removeOut_NoValidate(Vertex)
	 * If rem is the same as the vertex currently in out, sets out to null and returns true; else, returns false.
	 *
	 * @param rem The vertex to remove
	 * @return True if the given vertex was the out vertex
	 */
	public boolean removeOut_NoValidate(Vertex<T> rem) {
		if (rem.equals(out)) {
			out = null;
			return true;
		}
		return false;
	}
}
