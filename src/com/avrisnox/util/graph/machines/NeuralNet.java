package com.avrisnox.util.graph.machines;

import com.avrisnox.util.graph.components.middle.Edge;
import com.avrisnox.util.graph.components.middle.Vertex;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.stream.DoubleStream;

public class NeuralNet {
	public static final int INPUT_HEIGHT = 0;
	public static final int HIDDEN_LAYER_HEIGHT = 0;
	public static final int HIDDEN_LAYER_DEPTH = 0;
	public static final int OUTPUT_HEIGHT = 0;

	Vertex<Double, Double>[] input_layer;
	Vertex<Double, Double>[][] hidden_layers;
	Vertex<Double, Double>[] output_layer;

	public NeuralNet() {
		this(INPUT_HEIGHT, OUTPUT_HEIGHT);
	}

	public NeuralNet(int input_height, int output_height) {
		this(input_height, HIDDEN_LAYER_HEIGHT, output_height);
	}

	public NeuralNet(int input_height, int hidden_layer_height, int output_height) {
		this(input_height, hidden_layer_height, HIDDEN_LAYER_DEPTH, output_height);
	}

	public NeuralNet(int input_height, int hidden_layer_height, int hidden_layer_depth, int output_height) {
		input_layer = (Vertex<Double, Double>[])new Vertex[input_height];
		hidden_layers = (Vertex<Double, Double>[][])new Vertex[hidden_layer_height][hidden_layer_depth];
		output_layer = (Vertex<Double, Double>[])new Vertex[output_height];
		init_edges(null);
	}

	public void init(DoubleStream edges, DoubleStream inputs) {
		init_edges(edges);
		Iterator<Double> consumer = (inputs == null ? null : inputs.iterator());
		for(Vertex<Double, Double> v : input_layer)
			v.setContent(gtNxtFrmStrm(consumer));
	}

	public void init_edges(DoubleStream edges) {
		Iterator<Double> consumer = (edges == null ? null : edges.iterator());

		for(int i = -1; i < hidden_layers.length; i++) {
			Vertex<Double, Double>[] from = (i == -1 ? input_layer : hidden_layers[i]);
			Vertex<Double, Double>[] to = (i + 1 == hidden_layers.length ? output_layer : hidden_layers[i + 1]);

			for(Vertex<Double, Double> in : from)
				for(Vertex<Double, Double> out : to) {
					Edge<Double, Double> e = new Edge<Double, Double>();
					e.setContent(gtNxtFrmStrm(consumer));
					in.addNext(e);
					out.addPrev(e);
				}
		}
	}

	private double gtNxtFrmStrm(Iterator<Double> stream) {
		if(stream != null && stream.hasNext()) return stream.next();
		else return Math.random();
	}

	public Vertex<Double, Double>[] getInputLayer() {
		return input_layer;
	}

	public Vertex<Double, Double>[] getOutputLayer() {
		return output_layer;
	}

	public void forward_feed() {
		/* Assume input_layer is updated: run input->hidden if hidden.length > 0, else run input->output */

		/* Assume user will get output_layer and do with it what they want */
	}

	public void backward_propagate(Vertex<Double, Double>[] expected) {
		/* Received array are expected values for output - use this to propagate backward */
	}
}
