package de.mgi.mms.util.tuples;

/**
 * a unmodifiable 2-tuple
 * 
 * @author Johannes.Hoehne
 * 
 * @param <M>
 * @param <N>
 */
public class Tuple<M, N> {

	private M first;
	private N second;

	public Tuple(M first, N second) {
		if (first == null || second == null) {
			throw new IllegalArgumentException("All members must not be null: (" + first + ", " + second + ")");
		}
		this.first = first;
		this.second = second;
	}

	public M getFirst() {
		return first;
	}

	public N getSecond() {
		return second;
	}

	public String toString() {

		String retValue = "";
		retValue = "Tuple (" + this.first + ", " + this.second + ")";

		return retValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (first.hashCode());
		result = prime * result + (second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Tuple other = (Tuple) obj;

		if (!first.equals(other.first))
			return false;

		if (!second.equals(other.second))
			return false;

		return true;
	}

}
