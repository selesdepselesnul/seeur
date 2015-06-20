package seeurrenamer.main.util;

public enum RenamingDirection {
	LEFT, RIGHT;

	@Override
	public String toString() {
		return "from " + super.toString().toLowerCase();
	}

}
