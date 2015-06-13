package seeurrenamer.main.model;

import java.util.function.Function;

public class Sequence<T> {

	private T startValue;
	private T endValue;
	private T currentValue;
	private Function<T, T> adderMethod;
	private String toStringValue;

	public Sequence(T startValue, T endValue, Function<T, T> adderMethod, String toStringValue) {
		this.startValue = startValue;
		this.currentValue = startValue;
		this.endValue = endValue;
		this.adderMethod = adderMethod;
		this.toStringValue = toStringValue;
	}

	public void add() {
		this.currentValue = this.adderMethod.apply(this.currentValue);
		if (this.currentValue.equals(this.endValue)) {
			this.currentValue = this.startValue;
		}
	}

	public T getCurrentValue() {
		return this.currentValue;
	}

	@Override
	public String toString() {
		return this.toStringValue;
	}
	
	

}
