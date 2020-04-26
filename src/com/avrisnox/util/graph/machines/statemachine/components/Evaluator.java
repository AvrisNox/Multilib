package com.avrisnox.util.graph.machines.statemachine.components;

public interface Evaluator<T> {
	public boolean evaluate(T predicate);
}
