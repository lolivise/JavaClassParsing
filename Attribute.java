import java.io.DataInputStream;
import java.io.IOException;

/**
 * Read Attribute information and extract code attribute only
 **/
public class Attribute {

    /* Structure variables */
    private int           attributeNameIndex;
    private int           attributeLength;

    /* Contain info*/
    private String        attributeName;
    
    /* Return variable*/
    private CodeAttribute codeAttribute;
    
    /* Object for obtaining text info */
    private CPEntry       cpEntry;
    private ConstantUtf8  constantUtf8;


    /**
     * Read Attribute information
     **/
    public Attribute(DataInputStream dis, ConstantPool cp) throws IOException, InvalidConstantPoolIndex {

        /* Read attribute name and length */
    	attributeNameIndex = dis.readUnsignedShort();
    	attributeLength = dis.readInt();
        
    	/* Read code Attribute only */
    	cpEntry = cp.getEntry(attributeNameIndex);
        if (cpEntry instanceof ConstantUtf8) {
            constantUtf8 = (ConstantUtf8) cpEntry;
            attributeName = new String(constantUtf8.getBytes());
        }
        if (attributeName.equals("Code")) {
            codeAttribute = new CodeAttribute(dis, cp);
        } else {
            dis.skip(attributeLength);
        }

    }
    
    /**
     * Return Value
     * **/
    public CodeAttribute getCodeAttribute() {
        return codeAttribute;
    }

}
