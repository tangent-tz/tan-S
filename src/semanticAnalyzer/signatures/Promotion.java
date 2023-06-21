package semanticAnalyzer.signatures;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;

import static semanticAnalyzer.types.PrimitiveType.*;

public enum Promotion {
    CHAR_TO_INT(CHARACTER, INTEGER, ASMOpcode.Nop),
    CHAR_TO_FLOAT(CHARACTER, FLOAT, ASMOpcode.ConvertF),
    INT_TO_FLOAT(INTEGER, FLOAT, ASMOpcode.ConvertF),
    NONE(NO_TYPE, NO_TYPE, ASMOpcode.Nop) {
        boolean appliesTo(Type type) {
            return true;
        }
        Type apply(Type type){
            return type;
        }
        boolean isNull() {
            return true;
        }
    };


    Type fromType;
    Type toTYpe;
    ASMOpcode opcode;

    //constructor:
    Promotion(PrimitiveType fromType, PrimitiveType toType, ASMOpcode opcode) {
        this.fromType = fromType;
        this.toTYpe = toType;
        this.opcode = opcode;
    }

    boolean appliesTo(Type type) {
        return fromType = type;
    }

    Type apply(Type type) {
        assert(appliesTo(type));
        return toTYpe;
    }

    boolean isNull() {
        return false;
    }

    ASMCodeFragment codeFor() {
        ASMCodeFragment result = new ASMCodeFragment(ASMCodeFragment.CodeType.GENERATES_VALUE);
        result.add(opcode);
        return result;
    }


}
