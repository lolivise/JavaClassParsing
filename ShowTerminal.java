import java.util.List;

/**
 * Output result on terminal
 **/
public class ShowTerminal {
	
	public final void print(List<String> PrintList, int LCOM4Total, int FileCount, double CBO){
    	System.out.println("LCOM4:");
    	for(String s:PrintList){
    		System.out.println("    "+s);
    	}    	
    	System.out.println("CBO: "+LCOM4Total+"/"+FileCount+" = "+CBO);
    }

}
