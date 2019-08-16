import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Read ClassFile information and calculate LCOM4 and cboValue 
 **/
public class ProcessCentre {

	/*Global variables*/
    private Relationship	relationship;
    private CBORelationship cboRelationship;
    private DotFile			dotfile;
    private ShowTerminal	showTerminal;
                  
    private String			className;
    private String			extendsClass;

    private List<String>	fileList;
    private List<String>	checkList;
    private List<String>	edgeList;
    private List<String>	printList;

    private int 			lcom4Value;
    private int				lcom4Total;

    public ProcessCentre() {
        className = "";
        lcom4Value = 0;
        lcom4Total = 0;
        checkList = new ArrayList<>();
        printList = new ArrayList<>();
        fileList = new ArrayList<>();
        dotfile = new DotFile();
        cboRelationship = new CBORelationship();
        showTerminal = new ShowTerminal();
        
    }
    
    /**
     * Read file path and obtain ClassFile information
     * **/
    public void preprocess(List<String> filePathList) {
    	fileList = filePathList;
    	List<ClassFile> classFileList = new ArrayList<>();
        for (Object o : filePathList) {

            try {

            	ClassFile classFile = new ClassFile(o.toString());
                classFileList.add(classFile);
                
            } catch (ClassFileParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        output(classFileList);
    }

    /**
     * Run whole information in every ClassFile
     */
    private void output(List<ClassFile> cfList) {
    	
        cboRelationship.setNode(fileList);
        String methodwithParas = "";
        int fileCount = fileList.size();
        	
        /* Read every Classfile */
        for (ClassFile cf : cfList) {
            LCOM4 lcom4 = new LCOM4();
            relationship = new Relationship();
        	className = cf.getThisClassName();
            
        	setNodes(cf);
            checkList = relationship.getnodeList();
            lcom4.setLCOM4(checkList);
            
            
            /* Read very Methods in a class*/
            for (Method m : cf.getMethodInfo()) {
            	methodwithParas = m.getMethodwithParas();
                edgeList = new ArrayList<>();
                if (m.getAttributes().size() != 0) {
                	for (Attribute a : m.getAttributes()) {
                		if (a.getCodeAttribute() != null) {
                			
                			for (Object o : a.getCodeAttribute().getUsedFieldsType()) {
                				cboRelationship.cboEdge(className,o.toString());
                			}
                			
               				for (Object o : a.getCodeAttribute().getUsedFields()) {
               					if(checkList.contains(o)){
               						edgeList.add(o.toString());
               					}
               				}

               				for (Object o : a.getCodeAttribute().getUsedMethod()) {
               					if(checkList.contains(o)){
               						edgeList.add(o.toString());
               					}
               				}
               			}
               		}
               	}
               	relationship.setEdge(methodwithParas, edgeList);
               	lcom4.Calculatelcom4(methodwithParas, edgeList);
           }
            
            
           edgeList = relationship.getLinkList();
           className = cf.getThisClassName();
           lcom4Value = lcom4.getLCOM4();
           lcom4Total += lcom4Value;
        	
           detectSuperclass(cf);
           outputClassDot(cf);
        }
        
        fileCount = fileList.size();
        double cboValue = Double.valueOf(lcom4Total)/Double.valueOf(fileCount);
        
        
        /*Print Terminal and Dot of cboValue*/      
        outputCBODot(fileCount, cboValue);
		showTerminal.print(printList,lcom4Total,fileCount,cboValue);
    }
    
    
    /**
     * Set Node for calculate LCOM4 and cboValue
     **/
    private void setNodes(ClassFile cf){

    	for(Field f : cf.getFieldInfo()){
    		relationship.setNodeLabelList(f.getFieldName());
        	cboRelationship.cboEdge(className,f.getFieldType());
    	}

                
        for (Method m : cf.getMethodInfo()){
        	String methodName = m.getMethodName();
        	if(!methodName.equals("<init>")&&!methodName.equals("<clinit>")){
        		methodName = m.getMethodwithParas();
           		relationship.setNodeLabelList(methodName);
           	}
        	List<String> paras = m.getParameters();
        	for(String s: paras){
        		cboRelationship.cboEdge(className,s);
        	}
        }
    }  
    
    
    /**
     * Obtain super class name for cboValue and terminal output
     * **/
    private void detectSuperclass(ClassFile cf){
    	if (!cf.getSuperClassName().equals("java/lang/Object")) {
        	String superClass=cf.getSuperClassName();
        	if(superClass.contains("/")){
        		superClass = superClass.substring(superClass.lastIndexOf('/')+1, superClass.length());
        	}
        	extendsClass = " extends "+superClass;
        	cboRelationship.cboEdge(className,superClass);

        } else {
        	extendsClass = "";
        }
    }
    
    
    /**
     * Pass info to output graph file of each class
     * **/
    private void outputClassDot(ClassFile cf){
    	int methodStartCount = cf.getFieldInfo().size();
    	ClassFlag classFlag = new ClassFlag();
        String cboInfo = classFlag.getClassFlag(cf.getAccessFlags());
    	String forDot = "\""+cboInfo+" class "+className+" \\n LCOM4 = "+ lcom4Value+"\"";
    	String subInfo = " class "+className+extendsClass+" : "+lcom4Value;
    	cboInfo = cboInfo.concat(subInfo);
        printList.add(cboInfo);
        
    	try {
			dotfile.dotfile(forDot,className,methodStartCount, checkList, edgeList);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Pass info to output graph file of overall cboValue
     * **/
    private void outputCBODot(int fileCount, double cboValue){
    	List<String> cboRelateLabel = new ArrayList<>();
		List<String> cboNode = new ArrayList<>();
		cboNode = cboRelationship.getFileNameList();
		cboRelateLabel = cboRelationship.detectCBOLink();
		try {
			dotfile.dotfile(cboNode,cboRelateLabel,lcom4Total, fileCount, cboValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}