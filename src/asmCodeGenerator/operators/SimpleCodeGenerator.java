package asmCodeGenerator.operators;

import java.util.List;

import parseTree.ParseNode;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import parseTree.nodeTypes.OperatorNode;


public interface SimpleCodeGenerator {
	public ASMCodeFragment generate(ParseNode node, List<ASMCodeFragment> args); 	//todo: this might be useful in the future???
	public void generate(ASMCodeFragment code);
	public void generate(OperatorNode node, ASMCodeFragment code);
}
