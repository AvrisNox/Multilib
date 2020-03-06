package com.avrisnox.util.graph.components.complex;

/*
	Complex components:
	Each edge and vertex make use of 5 generic types:
	Edge<EP, VP, E, VN, EN>
		EP: Edge Previous, the type of the previous edge (in.prev)
		VP: Vertex Previous, the type of the previous vertex (in)
		E:  Edge, the type of the current edge (this, in.next, out.prev)
		VN: Vertex Next, the type of the next vertex (out)
		EN: Edge Next, the type of the next edge (out.next)
	Vertex<VP, EP, V, EN, VN>
		VP: Vertex Previous, the type of the previous vertex (prev.in)
		EP: Edge Previous, the type of the previous vertex (prev)
		V:  Vertex, the type of the current edge (this, prev.out, next.in)
		EN: Edge Next, the type of the next vertex (next)
		VN: Vertex Next, the type of the next edge (next.out)
	// TODO: This needs a lot of testing...
	// TODO: Needs documentation
 */