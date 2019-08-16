import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *Detect and store methods and fields information in a method 
 **/
public class CodeAttribute{

    /* Structure Variables exclude MaxStack and MaxLocals */
    private int 				codeLength;
    private byte[]              code;
    private int                 exceptionTableLength;
    private int                 attributesCount;
    
    /* detection */
    private String[]            operCode;
    private boolean             isField;
    private boolean 			isMethod;
    
    /* Contain info */
    private int                 codeIndex;
    private String              usedField;
    private String				usedFieldType;
    private String              usedMethod;
    private String              usedMethodName;
    private String              usedMethodType;

    /* Return variable */
    private List<String>        usedFieldList;
    private List<String>        usedFieldTypeList;
    private List<String>        parasTypeList;
    private List<String>        usedMethodList;

    /* Object for obtaining text info */    
    private CPEntry             cpEntry;
    private ConstantRef         constantRef;
    private ConstantFieldRef  	constantFieldRef;
    private ProcessParas 		processParas;




    public CodeAttribute(DataInputStream dis, ConstantPool cp) throws IOException, InvalidConstantPoolIndex {

        usedFieldList = new ArrayList<>();
        usedFieldTypeList = new ArrayList<>();
        usedMethodList = new ArrayList<>();
        parasTypeList = new ArrayList<>();
        processParas = new ProcessParas();
        
        isField = false;
        isMethod = false;
        
        /*Skip MaxStack and MaxLocals*/
        dis.skip(4);
        
        /*Read and translate the code*/
        codeLength = dis.readInt();
        code = new byte[codeLength];
        operCode = new String[codeLength];
        for (int i = 0; i < codeLength; i++) {
            code[i] = dis.readByte();
            operCode[i] = String.format("0x%02x", code[i]);
        }

        /*Skip unused information*/
        exceptionTableLength = dis.readUnsignedShort();
        for (int i = 0; i < exceptionTableLength; i++) {
        	dis.skip(8);
        }
        attributesCount = dis.readUnsignedShort();
        for (int i = 0; i < attributesCount; i++) {
            dis.skipBytes(2);
            dis.skipBytes(dis.readInt());
        }
        
        getMethodorField(cp);
    }
    
    
    /**
     * obtain the method or field
     * **/
    private void getMethodorField (ConstantPool cp) throws InvalidConstantPoolIndex {
        for (int i = 0; i < codeLength-2; i++) {
        	isMethodorField(operCode[i]);
        	if(isMethod||isField){
        		codeIndex= Integer.parseInt(String.format("%02x", code[i+2]), 16);
        		if (codeIndex>0) {
        			cpEntry = cp.getEntry(codeIndex);
        			if(isMethod&&cpEntry instanceof ConstantRef){
            			constantRef = (ConstantRef) cpEntry;
        				usedMethodName = constantRef.getName();
        				usedMethodType = constantRef.getType();
                    
        				parasTypeList = processParas.processParas(usedMethodType);
        				usedMethod = usedMethodName+"("+ processParas.getParameters()+")";
        				usedMethodList.add(usedMethod);
        			}else if(isField&&cpEntry instanceof ConstantFieldRef){
        				constantFieldRef = (ConstantFieldRef) cpEntry;
        				usedField = constantFieldRef.getName();
        				usedFieldType = constantFieldRef.getType();
        				usedFieldList.add(usedField);
        				usedFieldTypeList.add(usedFieldType);
        			}
        		}             
            }
        }
    }

    /**Determine it is method or field**/
    private void isMethodorField (String opcodeValue) {
        switch (opcodeValue) {
            case "0xb2":
            case "0xb3":
            case "0xb4":
            case "0xb5":
            	isMethod = false;
            	isField = true;
                break;
            case "0xb6":
            case "0xb7":
            case "0xb8":
            case "0xb9":
            	isMethod = true;
            	isField = false;
                break;
            default:
            	isField = false;
            	isMethod = false;
            	break;
          }
        }

    /**
     * Return Value
     **/
    public List<String> getUsedMethod(){
        return usedMethodList;
    }
    public List<String> getParameters(){
    	return parasTypeList;
    }
    
    public List<String> getUsedFields(){
        return usedFieldList;
    }   
    public List<String> getUsedFieldsType(){
        return usedFieldTypeList;
    }  
}