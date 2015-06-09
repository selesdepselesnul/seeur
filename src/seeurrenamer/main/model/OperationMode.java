package seeurrenamer.main.model;

public enum OperationMode {
	INSERT_OR_OVERWRITE, SEARCH_AND_REPLACE;

	@Override
	public String toString() {
		return super.toString().toLowerCase().replace('_', ' ');
	}

	
}
