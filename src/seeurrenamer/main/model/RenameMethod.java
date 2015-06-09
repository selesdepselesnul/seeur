package seeurrenamer.main.model;

public enum RenameMethod {
	NAME, SUFFIX, NAME_AND_SUFFIX;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase().replace('_', ' ');
	}


}
