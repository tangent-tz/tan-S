//package semanticAnalyzer.types;
//
//import java.util.Set;
//
//public class Array implements Type {
//    Type subtype;
//    static final int SIZE = 4;
//
//    public Array(Type type) {
//        this.subtype = type;
//    }
//
//    @Override
//    public int getSize() {
//        return SIZE;
//    }
//
//    public Type getSubType() {
//        return subtype;
//    }
//
//    @Override
//    public String infoString() {
//        return "[" + subtype.infoString() + "]";
//    }
//
//    @Override
//    public boolean equivalent(Type otherType) {
//        if(!(otherType instanceof Array)) {
//            return false;
//        }
//
//        Array otherArray = (Array)otherType;
//        return this.subtype.equivalent(otherArray.subtype);
//    }
//
//    @Override
//    public void addTypeVariables(Set<TypeVariable> typeVariables) {
//        subtype.addTypeVariables(typeVariables);
//    }
//
//    @Override
//    public Type concreteType() {
//        return this;
//    }
//}
