package seeurrenamer.main.util;

/**
 * enum class that indicate where direction to start renaming
 * 
 * @author moch deden
 *
 */
public enum RenamerDirection {
	LEFT, RIGHT;

	@Override
	public String toString() {
		return "from " + super.toString().toLowerCase();
	}

}
