package semanticAnalyzer.types;

import java.util.Set;

public class TypeVariable implements Type {
    String name; 
    Type constraint; 
    
    public TypeVariable(String name) {
        this.name = name; 
        reset(); 
    }
    
    public void reset() {
        setConstraint(PrimitiveType.NO_TYPE); 
    }
    
    public Type getConstraint() {
        return constraint; 
    }
    
    public void setConstraint(Type type) {
        constraint = type; 
    }
    
    public boolean equivalent(Type otherType) {
        if(constraint == PrimitiveType.NO_TYPE) {
            setConstraint(otherType); 
            return true; 
        }
        return constraint.equivalent(otherType); 
    }

    @Override
    public void addTypeVariables(Set<TypeVariable> typeVariables) {
        typeVariables.add(this); 
    }

    @Override
    public int getSize() {
        return -1;
    }

    @Override
    public String infoString() {
        return "type variable: " + name + " " + constraint; 
    }
    
    public Type concreteType() {
        return constraint;
    }

    @Override
    public Type getSubtype() {
        return PrimitiveType.NO_TYPE;
    }
    public int getArrayLength() {
        return -1;
    }
}
