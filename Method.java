import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *Read Method Information 
 **/
public class Method {

    /* Structure variables exclude Access Flag*/
    private int             nameIndex;
    private int             descriptorIndex;
    private int             attributesCount;
    private List<Attribute> attributesInfo;

    /* Return variable */
    private String          methodName;
    private String          MethodType;
    private String			methodWithParas;
    private List<String>    ParasType;
    
    /* Object for obtaining text info */
    private CPEntry         cpEntry;
    private ConstantUtf8    constantUtf8;
    private ProcessParas 	processParas;



    public Method(DataInputStream dis, ConstantPool cp) throws IOException, InvalidConstantPoolIndex {

        processParas = new ProcessParas();
        attributesInfo = new ArrayList<>();

        /* Read Data Input Stream  */
        dis.skip(2); //Skip Access Flag
        nameIndex = dis.readUnsignedShort();
        descriptorIndex = dis.readUnsignedShort();
        attributesCount = dis.readUnsignedShort();

        /* method name */
        cpEntry = cp.getEntry(nameIndex);
        if (cpEntry instanceof ConstantUtf8) {
            constantUtf8 = (ConstantUtf8) cpEntry;
            methodName = new String(constantUtf8.getBytes());
        }

        /* method type and extract parameters type */
        cpEntry = cp.getEntry(descriptorIndex);
        if (cpEntry instanceof ConstantUtf8) {
            constantUtf8 = (ConstantUtf8) cpEntry;
            MethodType = constantUtf8.getBytes();
            ParasType = processParas.processParas(MethodType);
        }

        /* attributesInfo of method */
        for (int i = 0; i < attributesCount; i++) {
            attributesInfo.add(new Attribute(dis, cp));
        }
        
        /*combine method with parameters*/
        methodWithParas = methodName+"("+ processParas.getParameters()+")";

    }


    /**
     * Return Value
     * **/
    
    public String getMethodName() {
        return methodName;
    }

    public List<String> getParameters() {
        return ParasType;
    }

    public List<Attribute> getAttributes() {
        return attributesInfo;
    }
    public String getMethodwithParas(){
    	return methodWithParas;
    }
}
