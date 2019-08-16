import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Read Field information 
 **/
public class Field {

    /* Structure variables exclude Access Flag*/
    private int          		nameIndex;
    private int			 		descriptorIndex;
    private int          		attributesCount;
    private List<Attribute>    	attributesInfo;

    /* Return variable */
    private String       		fieldName;
    private String		 		fieldType;
    
    /* Object for obtaining text info */
    private CPEntry      		cpEntry;
    private ConstantUtf8 		constantUtf8;
    

    public Field(DataInputStream dis, ConstantPool cp) throws IOException, InvalidConstantPoolIndex {

    	attributesInfo = new ArrayList<>();
    	
    	/*Read Data Input Stream */
    	dis.skip(2); 								// Skip AssessFlag
        nameIndex = dis.readUnsignedShort();
        descriptorIndex = dis.readUnsignedShort();
        attributesCount = dis.readUnsignedShort();
        
    	/* Field name */
        cpEntry = cp.getEntry(nameIndex);
        if (cpEntry instanceof ConstantUtf8) {
            constantUtf8 = (ConstantUtf8) cpEntry;
            fieldName = new String(constantUtf8.getBytes());
        }
        
        /* Field Type */
        cpEntry = cp.getEntry(descriptorIndex);
        if (cpEntry instanceof ConstantUtf8) {
            constantUtf8 = (ConstantUtf8) cpEntry;
            fieldType = new String(constantUtf8.getBytes());
        }

        /* Attribute Info */
        for (int i = 0; i < attributesCount; i++) {
            attributesInfo.add(new Attribute(dis, cp));
        }

    }
    
    
    /**
     * Return Value
     * **/
    public String getFieldName(){
    	return fieldName;
    }
    
    public String getFieldType(){
    	return fieldType;
    }

}
