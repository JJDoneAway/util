package de.mgi.mms.util.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * If you want to sort a set of object by a criteria, create a SortMap instance
 * and put your to be sorted element with their key in
 * 
 * @author Johannes.Hoehne
 * 
 * @param <M>
 * @param <N>
 */
public class SortMap<M, N> {

	private Map<M, List<N>>	container	= new HashMap<M, List<N>>();
		
	public void put(M key, N value) {
		checkKey(key);

		this.container.get(key).add(value);
	}

	public void checkKey(M key) {
		if ( !this.container.containsKey(key) ) {
			this.container.put(key, new ArrayList<N>());
		}
	}

	public void putAll(M key, List<N> values) {
		checkKey(key);
		this.container.get(key).addAll(values);

		
	}

	public Map<M, List<N>> getContainer() {
		return container;
	}

}
