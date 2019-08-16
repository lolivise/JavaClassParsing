import java.util.ArrayList;
import java.util.List;

/**
 * Determine type of every parameter in method
 **/
public class ProcessParas {

	/* Calculate variables*/
    private String       	methodType;
    private String			temp;
    private boolean 	 	Array;
    
    /* Return variable*/
    private String       	parameters;
    private List<String> 	parameterList;

    /**
     * Generate a list of parameters Type
     **/
    public List<String> processParas (String MethodType) {
    	
        methodType = MethodType;
        parameterList = new ArrayList<>();
        Array = false;
        
        String entirePara;
        
        temp = "";
        if(methodType.charAt(0)=='('){
        	entirePara = methodType.substring(1, methodType.lastIndexOf(")"));
        }else{
        	entirePara = methodType;
        }
        
        while(!entirePara.isEmpty()) {

        	char CharPointer = entirePara.charAt(0);

        	if (CharPointer=='[') {
                entirePara = entirePara.substring(1);
                Array = true;
            }else if (CharPointer=='L') {
                temp = entirePara.substring(1, entirePara.indexOf(";") + 1);
                entirePara = entirePara.substring(entirePara.indexOf(";") + 1);
                ExtractPara();
            }else{
                temp = String.valueOf(CharPointer);
                entirePara = entirePara.substring(1);
                ExtractPara();
            }
        }
        
        parameters = parameterList.toString();
        parameters = parameters.substring(parameters.indexOf('[') + 1, parameters.lastIndexOf(']'));
        return parameterList;
    }
    

    
    private void ExtractPara(){
    	if (Array) {
            parameterList.add(ParaType() + "[]");
        } else {
            parameterList.add(ParaType());
        }
        Array = false;
    }


    /**
     *  Translate type to java form
     */
    private String ParaType () {
    	String paraType = "";

        switch (temp) {
            case "B":
                paraType = "byte";
                break;
            case "C":
                paraType = "char";
                break;
            case "D":
                paraType = "double";
                break;
            case "F":
                paraType = "float";
                break;
            case "I":
                paraType = "int";
                break;
            case "J":
                paraType = "long";
                break;
            case "S":
                paraType = "short";
                break;
            case "Z":
                paraType = "boolean";
                break;
            default:
                paraType = temp.substring(temp.lastIndexOf("/")+1, temp.indexOf(";"));
                break;
        }

        return paraType;
    }

    /**
     * Return Value
     */
    public String getParameters () {
        return parameters;
    }
    
}
