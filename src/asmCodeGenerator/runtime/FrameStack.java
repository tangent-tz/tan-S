package asmCodeGenerator.runtime;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import parseTree.ParseNode;
import semanticAnalyzer.types.Array;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.ReferenceType;
import semanticAnalyzer.types.Type;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;
import static asmCodeGenerator.codeStorage.ASMOpcode.Add;

public class FrameStack {
     public static final String FRAME_POINTER = RunTime.FRAME_POINTER; 
     public static final String STACK_POINTER = RunTime.STACK_POINTER; 
     public static final String TEMP_FRAME_POINTER = RunTime.PREV_FRAME_POINTER; 
     public static final int SIZE_DYNAMIC_LINK = 4; 
     public static final int SIZE_RETURN_ADDRESS = 4; 
     private static int count = 0; 
     
     
     public static void passInParameter(ASMCodeFragment code, int totalOffset, Type type, ASMCodeFragment expressionCode) {
          code.add(PushD, STACK_POINTER);
          code.add(LoadI);
          code.add(PushI, totalOffset);
          code.add(Add);
          code.append(expressionCode);
          code.add(opcodeForStore(type));
     }
     
     public static void moveStackPointerBy(ASMCodeFragment code, int size) {
          code.add(PushD, STACK_POINTER);

          code.add(PushD, STACK_POINTER);
          code.add(LoadI);
          code.add(PushI, size);
          code.add(Add);
          
          code.add(StoreI);
     }
     
     public static void createDynamicLink(ASMCodeFragment code) {
          code.add(PushD, STACK_POINTER); 
          code.add(LoadI); 
          code.add(PushI, -SIZE_DYNAMIC_LINK);
          code.add(Add);
          loadIFrom(code, FRAME_POINTER);
          code.add(StoreI); 
          

          moveStackPointerBy(code, -SIZE_DYNAMIC_LINK);
          
          setFPtoTempFP(code);
          
          
     }
     
     private static void setFPtoTempFP(ASMCodeFragment code) {
          code.add(PushD, FRAME_POINTER); 
          loadIFrom(code, TEMP_FRAME_POINTER);
          code.add(StoreI); 
     }
     
     public static void saveReturnAddress(ASMCodeFragment code) {
          //the stack: [... returnAddress]
          code.add(PushD, STACK_POINTER);
          code.add(LoadI);
          code.add(PushI, -SIZE_RETURN_ADDRESS);
          code.add(Add);
          code.add(Exchange);
          code.add(StoreI);
          moveStackPointerBy(code, -SIZE_RETURN_ADDRESS);
          
          updateTempFramePointer(code); 
     }
     
     private static void updateTempFramePointer(ASMCodeFragment code) {
          //make tempFP = SP
          code.add(PushD, TEMP_FRAME_POINTER); 
          loadIFrom(code, STACK_POINTER);
          code.add(StoreI);
     }
     public static void loadReturnAddress(ASMCodeFragment code) {
          code.add(PushD, STACK_POINTER);
          code.add(LoadI);
          code.add(LoadI); 
          
          moveStackPointerBy(code, SIZE_RETURN_ADDRESS);
     }
     public static void restorePointers(ASMCodeFragment code, int totalSize) {
          //restore frame pointer
          //todo: re-assign frame pointer
          code.add(PushD, FRAME_POINTER); 
          loadDynamicLink(code); 
          code.add(StoreI); 
          
          
          //restore stack pointer
          moveStackPointerBy(code, totalSize);
          
          //todo:reset tempFP to point to the top of the latest frame
          code.add(PushD, TEMP_FRAME_POINTER); 
          loadIFrom(code, FRAME_POINTER);
          code.add(StoreI); 
     }
     
     
     private static void loadDynamicLink(ASMCodeFragment code) {
          code.add(PushD, STACK_POINTER);
          code.add(LoadI);
          code.add(LoadI);    //stack: [... DL]
          
          
          moveStackPointerBy(code, SIZE_DYNAMIC_LINK);
     }
     public static void saveReturnValue(ASMCodeFragment code, Type returnType) {
          //stack: [...  returnAddress, returnValue]
          //store return value:
          code.add(PushD, STACK_POINTER);
          code.add(LoadI);
          code.add(PushI, -(returnType.getSize()));
          code.add(Add);
          code.add(Exchange); 
          code.add(opcodeForStore(returnType)); 
          
          moveStackPointerBy(code, -(returnType.getSize()));
     }
     
     public static void loadReturnValue(ASMCodeFragment code, Type returnType) {
          code.add(PushD, STACK_POINTER);
          code.add(LoadI);
          turnAddressIntoValue(code, returnType);
          
          moveStackPointerBy(code, returnType.getSize());
     }
     
//     public static void storeFPtoPrev(ASMCodeFragment code) {
//          code.add(PushD, PREV_FRAME_POINTER);
//          loadIFrom(code, FRAME_POINTER);
//          code.add(StoreI);
//     }
//     
//     public static void moveFPtoSP(ASMCodeFragment code) {
//          code.add(PushD, FRAME_POINTER);
//          loadIFrom(code, STACK_POINTER);
//          code.add(StoreI);
//     }
//     
//     public static void performEntranceHandshake(ASMCodeFragment code) {
//          storeFPtoPrev(code);
//          moveFPtoSP(code);
//     }

     
     
     
     /////////////////////////////////////////////////////////////////////////////
     private static void loadIFrom(ASMCodeFragment code, String label) {
          code.add(PushD, label); 
          code.add(LoadI); 
     }
     private static ASMOpcode opcodeForStore(Type type) {
          if(type == PrimitiveType.INTEGER) {
               return StoreI;
          }
          else if(type == ReferenceType.STRING) {
               return StoreI;
          }
          else if(type == PrimitiveType.FLOAT) {
               return StoreF;
          }
          else if(type == PrimitiveType.BOOLEAN) {
               return StoreC;
          }
          else if(type == PrimitiveType.CHARACTER) {
               return StoreC;
          }
          else if(type instanceof Array) {
               return StoreI;
          }
          assert false: "Type " + type + " unimplemented in opcodeForStore()";
          return null;
     }

     private static void turnAddressIntoValue(ASMCodeFragment code, Type type) {
          if(type == PrimitiveType.INTEGER) {
               code.add(LoadI);
          }
          else if(type == ReferenceType.STRING) {
               code.add(LoadI);
          }
          else if(type == PrimitiveType.FLOAT) {
               code.add(LoadF);
          }
          else if(type == PrimitiveType.BOOLEAN) {
               code.add(LoadC);
          }
          else if(type == PrimitiveType.CHARACTER) {
               code.add(LoadC);
          }
          else if(type instanceof Array) {
               code.add(LoadI);
          }
          else {
               assert false : "cannot load return value from frame stack";
          }
     }
}
