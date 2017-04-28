package de.mgi.mms.util.collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * If you want to sort a set of object by a criteria, create a SortMap instance
 * and put your to be sorted element with their key in
 * 
 * It is the same behavior as the SortedMap but the values will be unique. So it
 * will ensure that you'll find a value under a key just once
 * 
 * @author Johannes.Hoehne
 * 
 * @param <M>
 * @param <N>
 */
public class SortUniqueMap<M, N> {

	private Map<M, Set<N>> container = new HashMap<M, Set<N>>();

	public void put(M key, N value) {
		checkKey(key);

		this.container.get(key).add(value);
	}

	public void checkKey(M key) {
		if (!this.container.containsKey(key)) {
			this.container.put(key, new HashSet<N>());
		}
	}

	public void putAll(M key, List<N> values) {
		checkKey(key);
		this.container.get(key).addAll(values);

	}

	public Map<M, Set<N>> getContainer() {
		return container;
	}

}
