package com.avrisnox.util.graph.machines.neuralnet;

import com.avrisnox.util.graph.components.middle.Edge;
import com.avrisnox.util.graph.components.middle.Vertex;
import com.avrisnox.util.graph.machines.neuralnet.components.AbsGateFunction;
import com.avrisnox.util.graph.machines.neuralnet.components.DebugLevel;
import com.avrisnox.util.graph.machines.neuralnet.components.GateFunction;
import com.avrisnox.util.graph.machines.neuralnet.components.staticgates.SigmoidGate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.DoubleStream;

public class NeuralNet {
	public static final int INPUT_HEIGHT = 784;
	public static final int HIDDEN_LAYER_HEIGHT = 16;
	public static final int HIDDEN_LAYER_DEPTH = 2;
	public static final int OUTPUT_HEIGHT = 10;
	public DebugLevel debug_level = DebugLevel.NONE;
	public GateFunction gate_function = GateFunction.SIGMOID;

	Vertex<Double, Double>[] input_layer;
	Vertex<Double, Double>[][] hidden_layers;
	Vertex<Double, Double>[] output_layer;
	private AbsGateFunction gatefunc;

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

	public void setDebugLevel(DebugLevel debug_level) {
		this.debug_level = debug_level;
	}

	public void setGateFunction(GateFunction gate_function) {
		this.gate_function = gate_function;
		gatefunc = null;

		if(gate_function == GateFunction.SIGMOID) gatefunc = new SigmoidGate();
	}

	public void setGateFunction(AbsGateFunction gate_function) {
		this.gate_function = GateFunction.OTHER;
		gatefunc = gate_function;
	}

	public void init(DoubleStream edges, DoubleStream inputs) {
		init_edges(edges);
		Iterator<Double> consumer = (inputs == null ? null : inputs.iterator());
		for(Vertex<Double, Double> v : input_layer)
			v.setContent(gtNxtFrmStrm(consumer));

		/* Bias for input layer */


		/* Bias for each hidden layer */
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

	public Double[] forward_feed(Double[] input) {
		/* Received array are input values - use this to feed forward */
		if(input.length != input_layer.length)
			throw new IllegalArgumentException("Input length was not equal to input layer length - either too much or not enough data.");
		for(int i = 0; i < input.length; i++)
			input_layer[i].setContent(input[i]);

		/* Run input->hidden if hidden.length > 0, else run input->output */
		for(int i = 0; i <= hidden_layers.length; i++) {
			Vertex<Double, Double>[] to = (i == hidden_layers.length ? output_layer : hidden_layers[i]);

			for(Vertex<Double, Double> v : to) {
				double sum = 0.0;
				LinkedList<Edge<Double, Double>> from = v.getPrev();
				for(Edge<Double, Double> e : from) {
					sum += e.getIn().getFirst().getContent() * e.getContent();
					// TODO: This function is not right yet (probably - definitely for gating)
				}
				v.setContent(sum);
			}
		}

		/* Return output_layer so users can do with it what they want */
		Double[] output = new Double[output_layer.length];
		for(int i = 0; i < output_layer.length; i++)
			output[i] = output_layer[i].getContent();
		return output;
	}

	public void backward_propagate(Double[] expected) {
		// TODO
		/* Received array are expected values for output - use this to propagate backward */
	}
}
