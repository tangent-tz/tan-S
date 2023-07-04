package semanticAnalyzer.types;

import java.util.Set;

public class Array implements Type {
    Type subtype;
    
    public Array(Type type) {
        this.subtype = type; 
    }
    
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
