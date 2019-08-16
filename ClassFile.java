import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Read class file information and return important message
 **/
public class ClassFile {
	
	/* Partial structure variables*/
    private ConstantPool 	constantPool;
    private int 			accessFlag;

    private int 			thisClass;
    private int 			superClass;

    private int 			fieldsCount;
    private List<Field> 	fieldInfo;

    private int 			methodsCount;
    private List<Method> 	methodInfo;

    /*Return variables */
    private String 			thisClassName;
    private String 			superClassName;
    
    /* Object for obtaining text info */
    private CPEntry 		cpEntry;
    private ConstantClass 	constantClass;


    /**
     * Optionally read information from class file
     **/
    public ClassFile(String filename) throws ClassFileParserException, IOException {
    	
        fieldInfo = new ArrayList<>();
    	methodInfo = new ArrayList<>();

        DataInputStream dis = new DataInputStream(new FileInputStream(filename));

        /*Read DataInputStream*/
        dis.skip(8); 							// Skip  Magic, MinorVersion, MajorVersion
        constantPool = new ConstantPool(dis);  	// Construct constant pool
        accessFlag = dis.readUnsignedShort();
        thisClass = dis.readUnsignedShort();
        superClass = dis.readUnsignedShort();
        dis.skip(2); 							// Skip interface
        
        
        /* class name */
        cpEntry = constantPool.getEntry(thisClass);
        if (cpEntry instanceof ConstantClass) {
            constantClass = (ConstantClass) cpEntry;
            thisClassName = new String (constantClass.getName());
        }

        /* super class name */
        cpEntry = constantPool.getEntry(superClass);
        if (cpEntry instanceof ConstantClass) {
            constantClass = (ConstantClass) cpEntry;
            superClassName = new String(constantClass.getName());
        }
       

        /* Fields Info */
        fieldsCount = dis.readUnsignedShort();
        for (int i = 0; i < fieldsCount; i++) {
            fieldInfo.add(new Field(dis, constantPool)) ;
        }

        /* Methods Info */
        methodsCount = dis.readUnsignedShort();
        for (int i = 0; i < methodsCount; i++) {
            methodInfo.add(new Method(dis, constantPool)) ;
        }

        /* Skip Class Attributes Info */

    }



    /**
     * Return Value
     * **/
    public int getAccessFlags(){
    	return accessFlag;
    }
    
    public String getThisClassName() {
        return thisClassName;
    }
    public String getSuperClassName() {
        return superClassName;
    }
    
    public List<Field> getFieldInfo() {
        return fieldInfo;
    }

    public List<Method> getMethodInfo() {
        return methodInfo;
    }

}

