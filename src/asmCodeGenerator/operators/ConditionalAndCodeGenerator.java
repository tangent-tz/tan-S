package asmCodeGenerator.operators;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import parseTree.ParseNode;
import parseTree.nodeTypes.OperatorNode;
import semanticAnalyzer.types.PrimitiveType;

import java.util.List;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class ConditionalAndCodeGenerator implements SimpleCodeGenerator {
    public ASMCodeFragment generate(ParseNode node, List<ASMCodeFragment> args) {
        return null;    //todo: returns null for now since we are not using this function anywhere
    }

    @Override
    public void generate(ASMCodeFragment code) {

    }

    @Override
    public void generate(OperatorNode node, ASMCodeFragment code) {

    }

    public void generate(ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2) {
        Labeller labeller = new Labeller("conditional-AND");
        String startLabel = labeller.newLabel("arg1");
        String arg2Label  = labeller.newLabel("arg2");
        String trueLabel  = labeller.newLabel("true");
        String falseLabel = labeller.newLabel("false");
        String joinLabel  = labeller.newLabel("join");

        code.add(Label, startLabel);
        code.append(arg1);
        code.add(JumpFalse, falseLabel);

        code.add(Label, arg2Label);
        code.append(arg2);
        code.add(JumpTrue, trueLabel);

        code.add(Label, falseLabel);
        code.add(PushI, 0);
        code.add(Jump, joinLabel);

        code.add(Label, trueLabel);
        code.add(PushI, 1);
        code.add(Label, joinLabel);
        
    }

    @Override
    public void generate(ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2, OperatorNode node) {

    }

}
