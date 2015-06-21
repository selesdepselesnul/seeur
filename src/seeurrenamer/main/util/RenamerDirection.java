package seeurrenamer.main.util;

public enum RenamerDirection {
	LEFT, RIGHT;

	@Override
	public String toString() {
		return "from " + super.toString().toLowerCase();
	}

}
