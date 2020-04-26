package com.avrisnox.util.graph.collections;

import java.util.*;

/**NMap
 * For use on lists of objects.
 * Maps all objects in a list back to that list.
 */
public class NMap implements Collection {
	HashMap<Object, Set<List<Object>>> mapping;

	/**NMap()
	 * Basic constructor - takes no arguments and simply initializes the mapping.
	 */
	public NMap() {
		mapping = new HashMap<>();
	}

	public NMap(Collection<List<Object>> list) {
		this();
		putAll(list);
	}

	public int size() {
		return valueSet().size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		if(o instanceof List) {

		} else if (o instanceof Set) {

		}
	}

	@Override
	public Iterator iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@Override
	public boolean add(Object o) {
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		return false;
	}

	@Override
	public Object[] toArray(Object[] a) {
		return new Object[0];
	}

	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	public boolean containsValue(Object value) {
		return containsKey(value);
	}

	public Set<List<Object>> get(Object key) {
		return mapping.get(key);
	}

	public void put(List<Object> tuple) {
		for(Object o : tuple) {
			Set<List<Object>> set = mapping.get(o);
			if (set == null) set = new HashSet<>();
			set.add(tuple);
			mapping.put(o, set);
		}
	}

	public void putAll(Collection<List<Object>> tupleSet) {
		for(List<Object> tuple : tupleSet) put(tuple);
	}

	public boolean remove(List<Object> tuple) {
		boolean found = false;
		for(Object o : tuple) {
			Set<List<Object>> set = mapping.get(o);
			if(set == null || !set.contains(tuple)) continue;
			found = found || set.remove(tuple);

			if(set.size() == 0) mapping.remove(o);
			else mapping.put(o, set);
		}
		return found;
	}

	public void clear() {
		mapping = new HashMap<>();
	}

	public Set<Object> keySet() {
		return mapping.keySet();
	}

	public Set<List<Object>> valueSet() {
		Set<List<Object>> result = new HashSet<>();
		for(Set<List<Object>> set : mapping.values())
			result.addAll(set);
		return result;
	}
}
