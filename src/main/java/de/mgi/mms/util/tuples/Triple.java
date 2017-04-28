package de.mgi.mms.util.tuples;

/**
 * a unmodifiable 3-tuple
 * 
 * @author Johannes.Hoehne
 * 
 * @param <M>
 * @param <N>
 * @param <O>
 */
public class Triple<M, N, O> {

	private M first;
	private N second;
	private O third;

	public Triple(M first, N second, O third) {
		if (first == null || second == null || third == null) {
			throw new IllegalArgumentException("All members must not be null: (" + first + ", " + second + ", " + third
					+ ")");
		}
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public M getFirst() {
		return first;
	}

	public N getSecond() {
		return second;
	}

	public O getThird() {
		return third;
	}

	public String toString() {

		String retValue = "";
		retValue = "Triple (" + this.first + ", " + this.second + ", " + this.third + ")";

		return retValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (first.hashCode());
		result = prime * result + (second.hashCode());
		result = prime * result + (third.hashCode());
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
		Triple other = (Triple) obj;

		if (!first.equals(other.first))
			return false;

		if (!second.equals(other.second))
			return false;

		if (!third.equals(other.third))
			return false;

		return true;
	}

}
