package symbolTable;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import inputHandler.TextLocation;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;

public class Binding {
	private Type type;
	private TextLocation textLocation;
	private MemoryLocation memoryLocation;
	private String lexeme;
	public enum Constancy {
		IS_CONSTANT,
		IS_VARIABLE,
	}
	private Constancy constancy;
	private FunctionSignature functionSignature; 
	private String functionLabel;


	
	public Binding(Type type, TextLocation location, MemoryLocation memoryLocation, String lexeme, Constancy constancy) {
		super();
		this.type = type;
		this.textLocation = location;
		this.memoryLocation = memoryLocation;
		this.lexeme = lexeme;
		this.constancy = constancy;
		this.functionSignature = FunctionSignature.nullInstance();
	}

	public Binding(Type type, TextLocation location, MemoryLocation memoryLocation, String lexeme, Constancy constancy, FunctionSignature functionSignature) {
		super();
		this.type = type;
		this.textLocation = location;
		this.memoryLocation = memoryLocation;
		this.lexeme = lexeme;
		this.constancy = constancy;
		this.functionSignature = functionSignature; 
	}
	

	public String toString() {
		return "[" + lexeme +
				" " + type +  // " " + textLocation +	
				" " + memoryLocation +
				"]";
	}	
	public String getLexeme() {
		return lexeme;
	}
	public Type getType() {
		return type;
	}
	public TextLocation getLocation() {
		return textLocation;
	}
	public MemoryLocation getMemoryLocation() {
		return memoryLocation;
	}
	public void generateAddress(ASMCodeFragment code) {
		memoryLocation.generateAddress(code, "%% " + lexeme);
	}
	public boolean isConstant() {
		return constancy == Constancy.IS_CONSTANT;
	}
	public FunctionSignature getFunctionSignature() {
		return this.functionSignature; 
	}
	public void setFunctionLabel(String label) {
		this.functionLabel = label; 
	}
	public String getFunctionLabel() {
		return functionLabel; 
	}
	
	
////////////////////////////////////////////////////////////////////////////////////
//Null Binding object
////////////////////////////////////////////////////////////////////////////////////

	public static Binding nullInstance() {
		return NullBinding.getInstance();
	}
	private static class NullBinding extends Binding {
		private static NullBinding instance=null;
		private NullBinding() {
			super(PrimitiveType.ERROR,
					TextLocation.nullInstance(),
					MemoryLocation.nullInstance(),
					"the-null-binding",
					null);
		}
		public static NullBinding getInstance() {
			if(instance==null)
				instance = new NullBinding();
			return instance;
		}
	}
}
