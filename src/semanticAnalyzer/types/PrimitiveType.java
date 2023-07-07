package semanticAnalyzer.types;


import java.util.List;
import java.util.Set;

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
	public boolean equivalent(Type otherType) {
		return this == otherType; 
		//TODO: might need promotion here???
	}
	public void addTypeVariables(Set<TypeVariable> typeVariables) {
		//TODO: ???
		
	}
	
	@Override
	public Type concreteType() {
		return this; 
	}
	
	@Override
	public Type getSubtype() {
		return PrimitiveType.NO_TYPE; 
	}
	public int getArrayLength() {
		return -1; 
	}
}
