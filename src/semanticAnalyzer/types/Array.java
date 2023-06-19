package semanticAnalyzer.types;

public class Array implements Type {
    Type subtype;

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String infoString() {
        return null;
    }

    @Override
    public boolean equivalent(Type otherType) {
        return false;
        //check if other type is array type
        //then check subtype matching
    }

    @Override
    public void addTypeVariables(Set<TypeVariable> tyvars) {

    }

    @Override
    public Type concreteType() {
        return null;
    }
}
