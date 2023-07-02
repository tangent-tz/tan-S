package asmCodeGenerator.operators;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import parseTree.ParseNode;
import parseTree.nodeTypes.OperatorNode;

import java.util.List;

public class IntToCharCodeGenerator implements SimpleCodeGenerator {
    public ASMCodeFragment generate(ParseNode node, List<ASMCodeFragment> args) {
        return null;    //todo: returns null for now since we are not using this function anywhere
    }

    public void generate(ASMCodeFragment code) {
        code.add(ASMOpcode.PushI, 127);
        code.add(ASMOpcode.BTAnd);
    }

    @Override
    public void generate(OperatorNode node, ASMCodeFragment code) {

    }
}
