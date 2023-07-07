package semanticAnalyzer.types;

import java.util.Set;

public class Array implements Type {
    static final int SIZE = 4;
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
        return subtype;
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
        return this;
    }

    public int getArrayLength() {
        return this.arrayLength; 
    }
}
