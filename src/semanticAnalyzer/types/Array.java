package semanticAnalyzer.types;

import java.util.Set;

public enum Array implements Type {
    INTEGER_ARRAY(4, "Integer Array"),
    FLOAT_ARRAY(4, "Float Array");
    // Add other array types as needed

    private int sizeInBytes;
    private String infoString;

    Array(int size, String infoString) {
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
    }
    @Override
    public void addTypeVariables(Set<TypeVariable> typeVariables) {

    }
    @Override
    public Type concreteType() {
        return this;
    }
    }
