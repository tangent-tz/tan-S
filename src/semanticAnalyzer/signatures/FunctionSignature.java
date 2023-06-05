package semanticAnalyzer.signatures;

import java.util.List;

import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;

//immutable
public class FunctionSignature {
	private static final boolean ALL_TYPES_ACCEPT_ERROR_TYPES = true;
	private Type resultType;
	private Type[] paramTypes;
	Object whichVariant;
	
	
	///////////////////////////////////////////////////////////////
	// construction
	
	public FunctionSignature(Object whichVariant, Type ...types) {
		assert(types.length >= 1);
		storeParamTypes(types);
		resultType = types[types.length-1];
		this.whichVariant = whichVariant;
	}
	private void storeParamTypes(Type[] types) {
		paramTypes = new Type[types.length-1];
		for(int i=0; i<types.length-1; i++) {
			paramTypes[i] = types[i];
		}
	}
	
	
	///////////////////////////////////////////////////////////////
	// accessors
	
	public Object getVariant() {
		return whichVariant;
	}
	public Type resultType() {
		return resultType;
	}
	public boolean isNull() {
		return false;
	}
	
	
	///////////////////////////////////////////////////////////////
	// main query

	public boolean accepts(List<Type> types) {
		if(types.size() != paramTypes.length) {
			return false;
		}
		
		for(int i=0; i<paramTypes.length; i++) {
			if(!assignableTo(paramTypes[i], types.get(i))) {
				return false;
			}
		}		
		return true;
	}
	private boolean assignableTo(Type variableType, Type valueType) {
		if(valueType == PrimitiveType.ERROR && ALL_TYPES_ACCEPT_ERROR_TYPES) {
			return true;
		}	
		return variableType.equals(valueType);
	}
	
	// Null object pattern
	private static FunctionSignature neverMatchedSignature = new FunctionSignature(1, PrimitiveType.ERROR) {
		public boolean accepts(List<Type> types) {
			return false;
		}
		public boolean isNull() {
			return true;
		}
	};
	public static FunctionSignature nullInstance() {
		return neverMatchedSignature;
	}
	
	///////////////////////////////////////////////////////////////////
	// Signatures for tan-0 operators
	// this section will probably disappear in tan-1 (in favor of FunctionSignatures)
	
	private static FunctionSignature addSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER);
	private static FunctionSignature subtractIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER);
	private static FunctionSignature negateIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER);
	private static FunctionSignature addIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER);
	private static FunctionSignature addFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.FLOAT);
	private static FunctionSignature multiplyIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER);
	private static FunctionSignature greaterIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN);
	private static FunctionSignature subtractFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.FLOAT);
	private static FunctionSignature negateFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT);
	private static FunctionSignature multiplyFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.FLOAT);
	private static FunctionSignature lesserIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN);
	private static FunctionSignature equalsIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN);
	private static FunctionSignature notEqualsIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN);
	private static FunctionSignature lesserEqualIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN);
	private static FunctionSignature greaterEqualIntegerSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN);
	//..
	private static FunctionSignature greaterFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN);
	private static FunctionSignature lesserFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN);
	private static FunctionSignature equalsFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN);
	private static FunctionSignature notEqualsFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN);
	private static FunctionSignature lesserEqualFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN);
	private static FunctionSignature greaterEqualFloatSignature = new FunctionSignature(1, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN);




	// the switch here is ugly compared to polymorphism.  This should perhaps be a method on Lextant.
	public static FunctionSignature signatureOfInteger(Lextant lextant) {
		assert(lextant instanceof Punctuator);	
		Punctuator punctuator = (Punctuator)lextant;
		
		switch(punctuator) {
		case ADD:			return addIntegerSignature;
		case SUBTRACT:  	return subtractIntegerSignature;
		case MULTIPLY:		return multiplyIntegerSignature;
		case GREATER:		return greaterIntegerSignature;
		case LESSER:		return lesserIntegerSignature;
		case EQUALS:		return equalsIntegerSignature;
		case NOTEQUALS:		return notEqualsIntegerSignature;
		case GREATEREQUAL:	return greaterEqualIntegerSignature;
		case LESSEREQUAL:	return lesserEqualIntegerSignature;

		default:
			return neverMatchedSignature;
		}
	}
	public static FunctionSignature signatureOfFloat(Lextant lextant) {
		assert(lextant instanceof Punctuator);
		Punctuator punctuator = (Punctuator)lextant;

		switch(punctuator) {
			case ADD:			return addFloatSignature;
			case SUBTRACT:  	return subtractFloatSignature;
			case MULTIPLY:		return multiplyFloatSignature;
			case GREATER:		return greaterFloatSignature;
			case LESSER:		return lesserFloatSignature;
			case EQUALS:		return equalsFloatSignature;
			case NOTEQUALS:		return notEqualsFloatSignature;
			case GREATEREQUAL:	return greaterEqualFloatSignature;
			case LESSEREQUAL:	return lesserEqualFloatSignature;
			default:
				return neverMatchedSignature;
		}
	}
	public static FunctionSignature unarySignatureOfInteger(Lextant lextant) {
		assert(lextant instanceof Punctuator);
		Punctuator punctuator = (Punctuator)lextant;

		switch(punctuator) {
			case SUBTRACT:  return negateIntegerSignature;

			default:
				return neverMatchedSignature;
		}
	}
	public static FunctionSignature unarySignatureOfFloat(Lextant lextant) {
		assert (lextant instanceof Punctuator);
		Punctuator punctuator = (Punctuator) lextant;

		switch (punctuator) {
			case SUBTRACT:
				return negateFloatSignature;

			default:
				return neverMatchedSignature;
		}
	}

}