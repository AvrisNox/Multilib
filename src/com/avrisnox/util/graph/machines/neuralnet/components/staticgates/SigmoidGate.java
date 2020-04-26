package com.avrisnox.util.graph.machines.neuralnet.components.staticgates;

import com.avrisnox.util.graph.machines.neuralnet.components.AbsGateFunction;

public class SigmoidGate extends AbsGateFunction {
	@Override
	public double modForward(double content) {
		return (1/(1 + Math.pow(Math.E, (-content))));
	}

	@Override
	public double modBackward(double content) {
		double sig = modForward(content);
		return sig * (1 - sig);
	}
}
