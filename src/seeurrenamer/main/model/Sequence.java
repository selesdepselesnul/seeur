package seeurrenamer.main.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class Sequence<T> implements Function<PairPath, Path> {

	private T startValue;
	private T endValue;
	private T currentValue;
	private Function<T, T> adderMethod;
	private String toStringValue;

	public Sequence(T startValue, T endValue, Function<T, T> adderMethod,
			String toStringValue) {
		this.startValue = startValue;
		this.currentValue = startValue;
		this.endValue = endValue;
		this.adderMethod = adderMethod;
		this.toStringValue = toStringValue;
	}

	@Override
	public String toString() {
		return this.toStringValue;
	}

	@Override
	public Path apply(PairPath pairPath) {

		System.out.println(this.currentValue.toString());
		if (this.currentValue.equals(this.endValue)) {
			this.currentValue = this.startValue;
		}
		this.currentValue = this.adderMethod.apply(this.currentValue);
		return Paths.get(this.currentValue + "."
				+ pairPath.getBefore().toString());
	}

}
