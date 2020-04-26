package com.avrisnox.util.graph.machines.neuralnetcomponents;

public abstract class AbsGateFunction {
	public double modInput(double content) {
		return content;
	}

	public double modHidden(double content, int layer) {
		return modForward(content);
	}

	public double modOutput(double content) {
		return modForward(content);
	}

	public abstract double modForward(double content);

	public double reverse_modInput(double content) {
		return content;
	}

	public double reverse_modHidden(double content, int layer) {
		return modBackward(content);
	}

	public double reverse_modOutput(double content) {
		return modBackward(content);
	}

	public abstract double modBackward(double content);
}
