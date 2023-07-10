package asmCodeGenerator.operators;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import parseTree.ParseNode;
import parseTree.nodeTypes.OperatorNode;

import java.util.List;

import static asmCodeGenerator.Macros.*;
import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class ArrayLengthCodeGenerator implements SimpleCodeGenerator {

    @Override
    public ASMCodeFragment generate(ParseNode node, List<ASMCodeFragment> args) {
        return null;
    }

    @Override
    public void generate(ASMCodeFragment code) {
        Labeller labeller = new Labeller("lengthArray"); 
        String baseAddressLabel = labeller.newLabel("baseAddress"); 
        
        // storing array base address into a temp memory location: currently, the stack: [... baseAddress]
        code.add(DLabel, baseAddressLabel);
        code.add(DataI, 0); //clear 4 bytes with all zeroes
        code.add(PushD, baseAddressLabel);
        code.add(Exchange);
        code.add(StoreI); 			// [...]

        loadIFrom(code, baseAddressLabel); 	// [... baseAddress]
        code.add(PushI, 12);
        code.add(Add);						// [... baseAddress+12]
        code.add(LoadI); 					// [... arrayLength]
    }

    @Override
    public void generate(OperatorNode node, ASMCodeFragment code) {

    }

    @Override
    public void generate(ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2) {

    }

    @Override
    public void generate(ASMCodeFragment code, ASMCodeFragment arg1, ASMCodeFragment arg2, OperatorNode node) {

    }
}
