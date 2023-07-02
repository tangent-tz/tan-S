package asmCodeGenerator.operators;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import parseTree.ParseNode;
import parseTree.nodeTypes.OperatorNode;

import java.util.List;

public class IntToBoolCodeGenerator implements SimpleCodeGenerator {
    public ASMCodeFragment generate(ParseNode node, List<ASMCodeFragment> args) {
        return null;    //todo: returns null for now since we are not using this function anywhere
    }

    public void generate(ASMCodeFragment code) {
        Labeller labeller = new Labeller("castIntToBool");
        String trueLabel = labeller.newLabel("true");
        String joinLabel = labeller.newLabel("join");

        code.add(ASMOpcode.JumpTrue, trueLabel);
        code.add(ASMOpcode.PushI, 0);
        code.add(ASMOpcode.Jump, joinLabel);
        code.add(ASMOpcode.Label, trueLabel);
        code.add(ASMOpcode.PushI, 1);
        code.add(ASMOpcode.Label, joinLabel);
    }

    @Override
    public void generate(OperatorNode node, ASMCodeFragment code) {

    }

    @Override
    public void generate(OperatorNode node, ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2) {

    }
}
