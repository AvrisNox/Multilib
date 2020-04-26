package com.avrisnox.util.graph.machines.statemachine;

import com.avrisnox.util.graph.components.middle.Edge;
import com.avrisnox.util.graph.components.middle.Vertex;
import com.avrisnox.util.graph.machines.statemachine.components.Evaluator;

import java.util.*;

/**StateMachine
 * Operational state machine based on middle graph components in util.graph.
 * @param <Action> The type indicating objects that describe state transitions
 */
public class StateMachine<Action> {
	Vertex<Boolean, Evaluator<Action>> q0;
	Set<Vertex<Boolean, Evaluator<Action>>> current = null;

	/**
	 * StateMachine(Vertex)
	 * Solitary constructor for the state machine: only the initial vertex must be passed.
	 * NOTE: If the entire machine is not defined from here, you will get unexpected results when running.
	 *
	 * @param initial The initial (start) state of the state machine
	 */
	public StateMachine(Vertex<Boolean, Evaluator<Action>> initial) {
		q0 = initial;
	}

	/**
	 * getCurrent()
	 * Gets the set of all current states.
	 * In a non-deterministic machine, may be many.
	 * In a deterministic machine, may be one.
	 * In any machine, may be empty (bad transition) or null (reset/not started).
	 *
	 * @return The set of all current states
	 */
	public Set<Vertex<Boolean, Evaluator<Action>>> getCurrent() {
		return current;
	}

	/**
	 * reset()
	 * Resets the current states to null.
	 * States will properly represent a location in the machine after any other action is performed.
	 */
	public void reset() {
		current = null;
	}

	/**
	 * test_savestate(List)
	 * Runs this state machine along the actions and returns the result, resetting the machine to the original state after completion.
	 *
	 * @param actions The list of actions to perform
	 * @return True if the machine accepts the list of actions
	 */
	public boolean test_savestate(List<Action> actions) {
		Set<Vertex<Boolean, Evaluator<Action>>> tmp = current;
		boolean result = test(actions);
		current = tmp;
		return result;
	}

	/**
	 * test(List)
	 * Runs this state machine along the actions and returns the result.
	 *
	 * @param actions The list of actions to perform
	 * @return True if the machine accepts the list of actions
	 */
	public boolean test(List<Action> actions) {
		current = null;
		return run(actions);
	}

	/**
	 * run_savestate(List)
	 * From the set of current states, runs this state machine along the actions and returns the result, resetting the machine to the original state after completion.
	 *
	 * @param actions The list of actions to perform
	 * @return True if the machine accepts the list of actions from its current position
	 */
	public boolean run_savestate(List<Action> actions) {
		Set<Vertex<Boolean, Evaluator<Action>>> tmp = current;
		for (Action a : actions)
			step(a);

		boolean result = evaluate();
		current = tmp;
		return result;
	}

	/**
	 * run(List)
	 * From the set of current states, runs this state machine along the actions and returns the result.
	 *
	 * @param actions The list of actions to perform
	 * @return True if the machine accepts the list of actions from its current position
	 */
	public boolean run(List<Action> actions) {
		for (Action a : actions)
			step(a);

		return evaluate();
	}

	/**
	 * step(Action)
	 * Performs a single step in the machine from the current state using the action.
	 * NOTE: This will modify current to be many, one, or none states.
	 *
	 * @param action The action to perform
	 */
	public void step(Action action) {
		current = astep(action);
	}

	/**
	 * astep(Action)
	 * Collects the set of states reachable from the current state through the single action.
	 *
	 * @param action The action to perform
	 * @return The set containing every state reachable from the current one through the single action
	 */
	public Set<Vertex<Boolean, Evaluator<Action>>> astep(Action action) {
		Set<Vertex<Boolean, Evaluator<Action>>> result;

		if (current == null) {
			current = new HashSet<>();
			current.add(q0);
		}

		if (action != null) {
			result = new HashSet<>();
			for (Vertex<Boolean, Evaluator<Action>> c : current)
				for (Edge<Boolean, Evaluator<Action>> e : c.getNext())
					if (e.getContent().evaluate(action))
						result.addAll(e.getOut());
		} else result = current;

		Set<Vertex<Boolean, Evaluator<Action>>> change = new HashSet<>(result);
		Set<Vertex<Boolean, Evaluator<Action>>> visits = new HashSet<>();
		while (change.size() != 0) {
			Vertex<Boolean, Evaluator<Action>> next = change.iterator().next();
			change.remove(next);

			if (!visits.contains(next)) {
				for (Edge<Boolean, Evaluator<Action>> e : next.getNext())
					if (e.getContent() == null) {
						change.addAll(e.getOut());
						result.addAll(e.getOut());
					}
				visits.add(next);
			}
		}

		return result;
	}

	/**
	 * vstep(Vertex, Action)
	 * Collects the set of states reachable from the given state through the single action.
	 *
	 * @param v      The state to start in
	 * @param action The action to perform
	 * @return The set containing every state reachable from the current one through the single action
	 */
	public Set<Vertex<Boolean, Evaluator<Action>>> vstep(Vertex<Boolean, Evaluator<Action>> v, Action action) {
		if (v == null) return null;

		Set<Vertex<Boolean, Evaluator<Action>>> result = new HashSet<>();
		Set<Vertex<Boolean, Evaluator<Action>>> change = new HashSet<>();
		Set<Vertex<Boolean, Evaluator<Action>>> visits = new HashSet<>();

		// Start with v
		change.add(v);
		// Get all lambda transitions
		while (change.size() != 0) {
			Vertex<Boolean, Evaluator<Action>> next = change.iterator().next();
			change.remove(next);

			if (!visits.contains(next)) {
				for (Edge<Boolean, Evaluator<Action>> e : next.getNext())
					if (e.getContent() == null)
						change.addAll(e.getOut());
				visits.add(next);
			}
		}

		// Get all action transitions
		change = new HashSet<>(visits);
		visits = new HashSet<>();
		while (change.size() != 0) {
			Vertex<Boolean, Evaluator<Action>> next = change.iterator().next();
			change.remove(next);

			if (!visits.contains(next)) {
				for (Edge<Boolean, Evaluator<Action>> e : next.getNext())
					if (e.getContent() != null && e.getContent().evaluate(action))
						result.addAll(e.getOut());
				visits.add(next);
			}
		}

		// Get all lambda transitions
		change = new HashSet<>(result);
		visits = new HashSet<>();
		while (change.size() != 0) {
			Vertex<Boolean, Evaluator<Action>> next = change.iterator().next();
			change.remove(next);

			if (!visits.contains(next)) {
				for (Edge<Boolean, Evaluator<Action>> e : next.getNext())
					if (e.getContent() == null) {
						change.addAll(e.getOut());
						result.addAll(e.getOut());
					}
				visits.add(next);
			}
		}

		return result;
	}

	/**
	 * evaluate()
	 * Evaluates the current states to see if any are accepting.
	 *
	 * @return True if at least 1 current state is an accepting state
	 */
	public boolean evaluate() {
		for (Vertex<Boolean, Evaluator<Action>> v : current) if (vevaluate(v)) return true;
		return false;
	}

	/**
	 * vevaluate(Vertex)
	 * Evaluates the given state to see if it is accepting.
	 *
	 * @param v The state to evaluate for acceptance
	 * @return True if the given state is an accepting state
	 */
	public boolean vevaluate(Vertex<Boolean, Evaluator<Action>> v) {
		return v.getContent();
	}
}
