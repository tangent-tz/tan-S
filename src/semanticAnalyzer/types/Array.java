package semanticAnalyzer.types;

import java.util.Set;

public class Array implements Type {
    Type subtype;
    static final int SIZE = 4; 
    
    public Array(Type type) {
        this.subtype = type; 
    }
    
    @Override 
    public int getSize() {
        return SIZE; 
    }

    @Override
    public String infoString() {
        return null;
    }

    @Override
    public boolean equivalent(Type otherType) {
        if(!(otherType instanceof Array)) {
            return false; 
        }
        
        return this.subtype.equivalent(otherType); 
        
    }

    @Override
    public void addTypeVariables(Set<TypeVariable> typeVariables) {
        
    }

    @Override
    public Type concreteType() {
        return null;
        //todo: ??
    }

}
