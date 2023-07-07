package asmCodeGenerator.operators;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import lexicalAnalyzer.Lextant;
import parseTree.ParseNode;
import parseTree.nodeTypes.OperatorNode;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.ReferenceType;

import java.util.List;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class EqualCodeGenerator implements SimpleCodeGenerator {
    public ASMCodeFragment generate(ParseNode node, List<ASMCodeFragment> args) {
        return null;    //todo: returns null for now since we are not using this function anywhere
    }

    @Override
    public void generate(ASMCodeFragment code) {

    }

    public void generate(OperatorNode node, ASMCodeFragment code) {
        Labeller labeller = new Labeller("compare");

        String startLabel = labeller.newLabel("arg1");
        String arg2Label  = labeller.newLabel("arg2");
        String subLabel   = labeller.newLabel("sub");
        String trueLabel  = labeller.newLabel("true");
        String falseLabel = labeller.newLabel("false");
        String joinLabel  = labeller.newLabel("join");
        code.add(Label, startLabel);
        code.add(Label, arg2Label);
        code.add(Label, subLabel);

        if(node.child(0).getType() == PrimitiveType.INTEGER && node.child(1).getType() == PrimitiveType.INTEGER ||
                node.child(0).getType() == PrimitiveType.CHARACTER && node.child(1).getType() == PrimitiveType.CHARACTER ||
                node.child(0).getType() == PrimitiveType.BOOLEAN && node.child(1).getType() == PrimitiveType.BOOLEAN ||
                node.child(0).getType() == ReferenceType.STRING && node.child(1).getType() == ReferenceType.STRING)
        {
            code.add(Subtract);

            code.add(JumpFalse, trueLabel);
            code.add(Jump,falseLabel);

            code.add(Label, trueLabel);
            code.add(PushI, 1);
            code.add(Jump, joinLabel);
            code.add(Label, falseLabel);
            code.add(PushI, 0);
            code.add(Jump, joinLabel);
            code.add(Label, joinLabel);
        }
        else if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.FLOAT)
        {
            code.add(FSubtract);

            code.add(JumpFZero, trueLabel);
            code.add(Jump,falseLabel);

            code.add(Label, trueLabel);
            code.add(PushI, 1);
            code.add(Jump, joinLabel);
            code.add(Label, falseLabel);
            code.add(PushI, 0);
            code.add(Jump, joinLabel);
            code.add(Label, joinLabel);
        }
    }

    @Override
    public void generate(ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2) {

    }

    @Override
    public void generate(ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2, OperatorNode node) {

    }
}
