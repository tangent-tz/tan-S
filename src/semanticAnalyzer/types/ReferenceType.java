package semanticAnalyzer.types;

import java.util.Set;

public enum ReferenceType implements Type {
    STRING(4);

    private int sizeInBytes;
    private String infoString;

    private ReferenceType(int size) {
        this.sizeInBytes = size;
        this.infoString = toString();
    }
    private ReferenceType(int size, String infoString) {
        this.sizeInBytes = size;
        this.infoString = infoString;
    }
    public int getSize() {
        return sizeInBytes;
    }
    public String infoString() {
        return infoString;
    }
    public boolean equivalent(Type otherType) {
        return this == otherType;
        //TODO: might need promotion here???
    }

    @Override
    public void addTypeVariables(Set<TypeVariable> typeVariables) {
        
    }

    @Override
    public Type concreteType() {
        return this;
    }
}
