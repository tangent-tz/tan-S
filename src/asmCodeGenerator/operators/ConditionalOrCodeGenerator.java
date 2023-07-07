package asmCodeGenerator.operators;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import parseTree.ParseNode;
import parseTree.nodeTypes.OperatorNode;
import semanticAnalyzer.types.PrimitiveType;

import java.util.List;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class ConditionalOrCodeGenerator implements SimpleCodeGenerator {
    public ASMCodeFragment generate(ParseNode node, List<ASMCodeFragment> args) {
        return null;    //todo: returns null for now since we are not using this function anywhere
    }
    
    @Override
    public void generate(ASMCodeFragment code) {
        
    }

    @Override
    public void generate(OperatorNode node, ASMCodeFragment code) {

    }

    @Override
    public void generate(ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2) {

    }

    @Override
    public void generate(ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2, OperatorNode node) {
        Labeller labeller = new Labeller("conditional-OR");
        String startLabel = labeller.newLabel("arg1");
        String arg2Label  = labeller.newLabel("arg2");
        String falseLabel = labeller.newLabel("false");
        String trueLabel = labeller.newLabel("true");
        String joinLabel = labeller.newLabel("join");



        code.add(Label, startLabel);
        code.append(arg1);
        if((node.child(0).getType() == PrimitiveType.INTEGER ||node.child(0).getType() == PrimitiveType.CHARACTER) && node.child(1).getType()== PrimitiveType.FLOAT) {
            code.add(ConvertF);
        }
        code.append(arg2);
        if(node.child(0).getType() == PrimitiveType.FLOAT && (node.child(1).getType()== PrimitiveType.INTEGER||node.child(1).getType() == PrimitiveType.CHARACTER)) {
            code.add(ConvertF);
        }

        code.add(JumpTrue, trueLabel);

        code.add(Label, arg2Label);
        code.append(arg1);
        if((node.child(0).getType() == PrimitiveType.INTEGER ||node.child(0).getType() == PrimitiveType.CHARACTER) && node.child(1).getType()== PrimitiveType.FLOAT) {
            code.add(ConvertF);
        }
        code.append(arg2);
        if(node.child(0).getType() == PrimitiveType.FLOAT && (node.child(1).getType()== PrimitiveType.INTEGER||node.child(1).getType() == PrimitiveType.CHARACTER)) {
            code.add(ConvertF);
        }
        code.add(JumpTrue, trueLabel);

        code.add(Label, falseLabel);
        code.add(PushI, 0);
        code.add(Jump, joinLabel);

        code.add(Label, trueLabel);
        code.add(PushI, 1);

        code.add(Label, joinLabel);
    }
    
}
