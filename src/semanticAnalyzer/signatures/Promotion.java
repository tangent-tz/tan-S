package semanticAnalyzer.signatures;

import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;

import static semanticAnalyzer.types.PrimitiveType.*;

public enum Promotion {
    CHAR_TO_INT(CHARACTER, INTEGER),
    CHAR_TO_FLOAT(CHARACTER, FLOAT),
    INT_TO_FLOAT(INTEGER, FLOAT),
    NONE(NO_TYPE, NO_TYPE) {
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

    //constructor:
    Promotion(PrimitiveType fromType, PrimitiveType toType) {
        this.fromType = fromType;
        this.toTYpe = toType;
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



}
