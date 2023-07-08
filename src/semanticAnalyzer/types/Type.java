package semanticAnalyzer.types;

import java.util.List;
import java.util.Set;

public interface Type {
	/** returns the size of an instance of this type, in bytes.
	 * 
	 * @return number of bytes per instance
	 */
	public int getSize(); 
	
	/** Yields a printable string for information about this type.
	 * use this rather than toString() if you want an abbreviated string.
	 * In particular, this yields an empty string for PrimitiveType.NO_TYPE.
	 * 
	 * @return string representation of type.
	 */
	public String infoString();
	
	public boolean equivalent(Type otherType); 
	public void addTypeVariables(Set<TypeVariable> typeVariables); 
	public Type concreteType(); 
	public Type getSubtype();
	public int getArrayLength(); 
}
