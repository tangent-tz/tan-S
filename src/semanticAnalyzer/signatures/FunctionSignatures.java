package semanticAnalyzer.signatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.operators.*;
import lexicalAnalyzer.Punctuator;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.ReferenceType;
import semanticAnalyzer.types.Type;



public class FunctionSignatures extends ArrayList<FunctionSignature> {
	private static final long serialVersionUID = -4907792488209670697L;
	private static Map<Object, FunctionSignatures> signaturesForKey = new HashMap<Object, FunctionSignatures>();
	
	Object key;
	
	public FunctionSignatures(Object key, FunctionSignature ...functionSignatures) {
		this.key = key;
		for(FunctionSignature functionSignature: functionSignatures) {
			add(functionSignature);
		}
		signaturesForKey.put(key, this);
	}
	
	public Object getKey() {
		return key;
	}
	public boolean hasKey(Object key) {
		return this.key.equals(key);
	}
	
	public FunctionSignature acceptingSignature(List<Type> types) {
		for(FunctionSignature functionSignature: this) {
			if(functionSignature.accepts(types)) {
				return functionSignature;
			}
		}
		return FunctionSignature.nullInstance();
	}
	public boolean accepts(List<Type> types) {
		return !acceptingSignature(types).isNull();
	}

	
	/////////////////////////////////////////////////////////////////////////////////
	// access to FunctionSignatures by key object.
	
	public static FunctionSignatures nullSignatures = new FunctionSignatures(0, FunctionSignature.nullInstance());

	public static FunctionSignatures signaturesOf(Object key) {
		if(signaturesForKey.containsKey(key)) {
			return signaturesForKey.get(key);
		}
		return nullSignatures;
	}
	public static FunctionSignature signature(Object key, List<Type> types) {
		FunctionSignatures signatures = FunctionSignatures.signaturesOf(key);
		return signatures.acceptingSignature(types);
	}

	
	
	/////////////////////////////////////////////////////////////////////////////////
	// Put the signatures for operators in the following static block.
	
	static {
		new FunctionSignatures(Punctuator.ADD,
		    new FunctionSignature(ASMOpcode.Add, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
		    new FunctionSignature(ASMOpcode.FAdd, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
			new FunctionSignature(ASMOpcode.FAdd, PrimitiveType.INTEGER, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
			new FunctionSignature(ASMOpcode.FAdd, PrimitiveType.FLOAT, PrimitiveType.INTEGER, PrimitiveType.FLOAT),
			new FunctionSignature(ASMOpcode.Add, PrimitiveType.CHARACTER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
			new FunctionSignature(ASMOpcode.Add, PrimitiveType.INTEGER, PrimitiveType.CHARACTER, PrimitiveType.INTEGER),
			new FunctionSignature(ASMOpcode.FAdd, PrimitiveType.CHARACTER, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
			new FunctionSignature(ASMOpcode.FAdd, PrimitiveType.FLOAT, PrimitiveType.CHARACTER, PrimitiveType.FLOAT),
			new FunctionSignature(ASMOpcode.Nop, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
			new FunctionSignature(ASMOpcode.Nop, PrimitiveType.INTEGER, PrimitiveType.INTEGER)
		);
		new FunctionSignatures(Punctuator.MULTIPLY,
				new FunctionSignature(ASMOpcode.Multiply, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.FMultiply, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.FMultiply, PrimitiveType.INTEGER, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.FMultiply, PrimitiveType.FLOAT, PrimitiveType.INTEGER, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.Multiply, PrimitiveType.CHARACTER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.Multiply, PrimitiveType.INTEGER, PrimitiveType.CHARACTER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.FMultiply, PrimitiveType.CHARACTER, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.FMultiply, PrimitiveType.FLOAT, PrimitiveType.CHARACTER, PrimitiveType.FLOAT)
		);
		new FunctionSignatures(Punctuator.SUBTRACT,
				new FunctionSignature(ASMOpcode.Subtract, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.FSubtract, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.FSubtract, PrimitiveType.INTEGER, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.FSubtract, PrimitiveType.FLOAT, PrimitiveType.INTEGER, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.FMultiply, PrimitiveType.FLOAT, PrimitiveType.INTEGER, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.Subtract, PrimitiveType.CHARACTER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.Subtract, PrimitiveType.INTEGER, PrimitiveType.CHARACTER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.FSubtract, PrimitiveType.CHARACTER, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.FSubtract, PrimitiveType.FLOAT, PrimitiveType.CHARACTER, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.FNegate, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.Negate, PrimitiveType.INTEGER, PrimitiveType.INTEGER)
		);
		new FunctionSignatures(Punctuator.DIVIDE,
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.INTEGER, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.FLOAT, PrimitiveType.INTEGER, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.CHARACTER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.INTEGER, PrimitiveType.CHARACTER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.CHARACTER, PrimitiveType.FLOAT, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.FLOAT, PrimitiveType.CHARACTER, PrimitiveType.FLOAT)
		);
		new FunctionSignatures(Punctuator.GREATER,
				new FunctionSignature(new GreaterThanCodeGenerator(), PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new GreaterThanCodeGenerator(), PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN),
				new FunctionSignature(new GreaterThanCodeGenerator(), PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN)

		);
		new FunctionSignatures(Punctuator.LESSER,
				new FunctionSignature(new LesserThanCodeGenerator(), PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new LesserThanCodeGenerator(), PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN),
				new FunctionSignature(new LesserThanCodeGenerator(), PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN)
		);
		new FunctionSignatures(Punctuator.GREATEREQUAL,
				new FunctionSignature(new GreaterEqualCodeGenerator(), PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new GreaterEqualCodeGenerator(), PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN),
				new FunctionSignature(new GreaterEqualCodeGenerator(), PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN)
		);
		new FunctionSignatures(Punctuator.LESSEREQUAL,
				new FunctionSignature(new LesserEqualCodeGenerator(), PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new LesserEqualCodeGenerator(), PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN),
				new FunctionSignature(new LesserEqualCodeGenerator(), PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN)
		);
		new FunctionSignatures(Punctuator.EQUALS,
				new FunctionSignature(new EqualCodeGenerator(), PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new EqualCodeGenerator(), PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN),
				new FunctionSignature(new EqualCodeGenerator(), PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new EqualCodeGenerator(), PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN),
				new FunctionSignature(new EqualCodeGenerator(), ReferenceType.STRING, ReferenceType.STRING, PrimitiveType.BOOLEAN)
		);
		new FunctionSignatures(Punctuator.NOTEQUALS,
				new FunctionSignature(new NotEqualCodeGenerator(), PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new NotEqualCodeGenerator(), PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.BOOLEAN),
				new FunctionSignature(new NotEqualCodeGenerator(), PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new NotEqualCodeGenerator(), PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN),
				new FunctionSignature(new NotEqualCodeGenerator(), ReferenceType.STRING, ReferenceType.STRING, PrimitiveType.BOOLEAN)
		);
		new FunctionSignatures(Punctuator.CONDITIONAL_AND,
				new FunctionSignature(new ConditionalAndCodeGenerator(), PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN)
		);
		new FunctionSignatures(Punctuator.CONDITIONAL_OR,
				new FunctionSignature(new ConditionalOrCodeGenerator(), PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN)
		);
		new FunctionSignatures(Punctuator.CAST,
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN),
				new FunctionSignature(new CharToBoolCodeGenerator(), PrimitiveType.BOOLEAN, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.CHARACTER),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.INTEGER, PrimitiveType.CHARACTER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.Nop, ReferenceType.STRING, ReferenceType.STRING, ReferenceType.STRING),
				new FunctionSignature(new IntToBoolCodeGenerator(), PrimitiveType.BOOLEAN, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
				new FunctionSignature(new IntToCharCodeGenerator(), PrimitiveType.CHARACTER, PrimitiveType.INTEGER, PrimitiveType.CHARACTER),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.ConvertF, PrimitiveType.FLOAT, PrimitiveType.INTEGER, PrimitiveType.FLOAT),
				new FunctionSignature(ASMOpcode.ConvertI, PrimitiveType.INTEGER, PrimitiveType.FLOAT, PrimitiveType.INTEGER),
				new FunctionSignature(ASMOpcode.Nop, PrimitiveType.FLOAT, PrimitiveType.FLOAT, PrimitiveType.FLOAT)
		);
		new FunctionSignatures(Punctuator.BOOLEAN_NOT,
				new FunctionSignature(ASMOpcode.BNegate, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN)
		);



	}

}
