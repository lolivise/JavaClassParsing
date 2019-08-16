import java.util.ArrayList;
import java.util.List;

/**
 * Process the Access Flag of a class
 **/
public class ClassFlag {
	
	/*Calculate variable*/
	private int		Operater;
	
	/*Determine public*/
	private int 	PublicIndex;
	private int 	CheckPublic;
	
	/*Determine final*/
	private int		FinalIndex;
	private int 	CheckFinal;
	
	/*Determine abstract*/
	private int		AbstractIndex;
	private int		CheckAbstract;

	/*contain class flag*/
	private List<String> ClassType;
	
	
    public ClassFlag(){
    	this.ClassType = new ArrayList<>();
    	PublicIndex = Integer.parseInt("0001", 16);
    	FinalIndex = Integer.parseInt("0010", 16);
    	AbstractIndex = Integer.parseInt("0400", 16);   	
    }
    
    /**
     * Recognize the flag and store in the list
     **/
    public String getClassFlag(int AccessFlag){
    	this.ClassType = new ArrayList<>();
    	Operater = AccessFlag;
        CheckPublic = Operater & PublicIndex;
        CheckFinal = Operater & FinalIndex;
        CheckAbstract = Operater & AbstractIndex;
        
        if(CheckPublic != 0)
        	ClassType.add("public");
        if(CheckFinal != 0)
        	ClassType.add("final");
        if(CheckAbstract != 0)
        	ClassType.add("Abstract");
        
        String classflags = ClassType.toString();
        classflags = classflags.substring(classflags.indexOf('[')+1, classflags.indexOf(']'));
        classflags = classflags.replace(',', ' ');
        return classflags;

    }

}
