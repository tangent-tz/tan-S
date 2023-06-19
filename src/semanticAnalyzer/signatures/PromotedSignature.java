package semanticAnalyzer.signatures;


import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;

import java.util.ArrayList;
import java.util.List;

public class PromotedSignature {
    FunctionSignature signature;
    List<Promotion> promotions;
    List<Type> typeVariableSettings;

    public PromotedSignature(FunctionSignature signature, List<Promotion> promotions) {
        this.signature = signature;
        this.promotions = new ArrayList<Promotion>(promotions);
        this.typeVariableSettings = signature.typeVariableSettings();

    }

    static List<PromotedSignature> promotedSignatures(FunctionSignatures signatures, List<Type> types) {
        List<PromotedSignature> result = new ArrayList<PromotedSignature>();
        for(FunctionSignature signature : signatures) {
            result.addAll(findAll(signature, types));
        }
        return result;
    }

    private static List<PromotedSignature> findAll(FunctionSignature signature, List<Type> types) {
        List<PromotedSignature> promotedSignatures = new ArrayList<>();
        List<Promotion> promotions = new ArrayList<>();
        List<Type> promotedTypes = new ArrayList<>();

        for(int i=0; i < types.size(); i++) {
            promotions.add(Promotion.NONE);
            promotedTypes.add(PrimitiveType.NO_TYPE);
        }
        //recursive part:
        findAllRecursive(signature, types, promotions, promotedTypes, promotedSignatures, 0);
        return promotedSignatures;
    }

    private static void findAllRecursive(FunctionSignature signature, List<Type> types, List<Promotion> promotions, List<Type> promotedTypes,
                                         List<PromotedSignature> promotedSignatures, int index) {
        if(index >= types.size()) {
            if(signature.accepts(promotedTypes)) {
                promotedSignatures.add(new PromotedSignature(signature, promotions)); //must be a deep copy
            }
            return;
        }
        Type type = types.get(index);
        for(Promotion promotion: Promotion.values()) {
            if(promotion.appliesTo(type)) {
                promotedTypes.set(index, promotion.apply(type));
            }
            findAllRecursive(signature, types, promotions, promotedTypes, promotedSignatures, index+1);
        }
        return;
    }

    public int numPromotions() {
        int result = 0;
        for(Promotion promotion: promotions) {
            if(!promotion.isNull()) {
                result += 1;
            }
        }
        return result;
    } //use this to do bucket sort, each bucket should only have 1 item, if have more then error.

}
