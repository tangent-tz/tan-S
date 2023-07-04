package semanticAnalyzer.types;


public enum PrimitiveType implements Type {
	BOOLEAN(1, "Boolean"),
	CHARACTER(1, "Character"),
	INTEGER(4, "Integer"),
	FLOAT(8, "Float"),
	ERROR(0, "Error"),			// use as a value when a syntax error has occurred
	NO_TYPE(0, "");		// use as a value when no type has been assigned.

	private int sizeInBytes;
	private String infoString;
	
	private PrimitiveType(int size) {
		this.sizeInBytes = size;
		this.infoString = toString();
	}
	private PrimitiveType(int size, String infoString) {
		this.sizeInBytes = size;
		this.infoString = infoString;
	}
	public int getSize() {
		return sizeInBytes;
	}
	public String infoString() {
		return infoString;
	}
}
