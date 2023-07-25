package semanticAnalyzer.types;

import java.util.Set;

public class Array implements Type {
    public static final int SIZE = 4;
    public static final int HEADER_TYPE_IDENTIFIER_SIZE = 4;
    public static final int HEADER_STATUS_SIZE = 4;
    public static final int HEADER_SUBTYPESIZE_SIZE = 4;
    public static final int HEADER_LENGTH_SIZE = 4;
    public static final int HEADER_SIZE = HEADER_TYPE_IDENTIFIER_SIZE 
                                    + HEADER_STATUS_SIZE
                                    + HEADER_SUBTYPESIZE_SIZE
                                    + HEADER_LENGTH_SIZE;


    public static final int HEADER_TYPE_IDENTIFIER_OFFSET = 0;
    public static final int HEADER_STATUS_OFFSET = HEADER_TYPE_IDENTIFIER_SIZE;
    public static final int HEADER_SUBTYPESIZE_OFFSET = HEADER_TYPE_IDENTIFIER_SIZE + HEADER_STATUS_SIZE;
    public static final int HEADER_LENGTH_OFFSET = HEADER_TYPE_IDENTIFIER_SIZE + HEADER_STATUS_SIZE + HEADER_SUBTYPESIZE_SIZE;

    
    
    Type subtype;
    int arrayLength;
    
    
    public Array(Type type, int length) {
        this.subtype = type;
        this.arrayLength = length; 
    }
    public Array(Type type) {
        this.subtype = type; 
        this.arrayLength = -1; 
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public Type getSubtype() {
        return subtype.concreteType();
    }

    @Override
    public String infoString() {
        return "[" + subtype.infoString() + "]";
    }

    @Override
    public boolean equivalent(Type otherType) {
        if(!(otherType instanceof Array)) {
            return false;
        }

        Array otherArray = (Array)otherType;
        return this.subtype.equivalent(otherArray.subtype);
    }

    @Override
    public void addTypeVariables(Set<TypeVariable> typeVariables) {
        subtype.addTypeVariables(typeVariables);
    }

    @Override
    public Type concreteType() {
        return new Array(subtype.concreteType()); 
    }

    public int getArrayLength() {
        return this.arrayLength; 
    }
}
