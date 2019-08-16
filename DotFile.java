import java.io.*;
import java.util.List;

/**
 * Output graph file of each classes and CBO
 **/
public class DotFile {
	
	/**
	 * Output graph of each classes
	 * **/
    public void dotfile(String info, String ClassName,int methodstartpoint, List<String> Node, List<String> Edges) throws IOException {
    	    	
    	 String FileName = ClassName+".dot";
         PrintWriter os = new PrintWriter(FileName);
         
         os.println("graph G");
         os.println("{");
         os.println("    overlap=scalexy;");
         os.println("    labelloc=\"t\";");
         os.println("    label="+info+";");
         os.println("    ");
         os.println();
         os.println("    node[shape=ellipse,color=blue]");
         for(int i=0; i<Node.size(); i++){
        	 if(i==methodstartpoint){
        		 os.println();
        		 os.println("    node[shape=rectangle, color=black]");
        		 os.print("        ");
        		 os.println((i+1)+"[label=\""+Node.get(i).toString()+"\"]");
        	 }else{
        		 os.print("        ");
        		 os.println((i+1)+"[label=\""+Node.get(i).toString()+"\"]");
        	 }
         }
         os.println();
         for(String edge:Edges){
        	 os.println("    "+edge+";");
         }
         os.print("}");
         os.close();      
   }
    
    
    /**
     * Output graph of CBO
     * **/
    public void dotfile(List<String> CBONode,List<String> CBOrelateLabel, int LCOM4Total,int FileCount,double CBO) throws FileNotFoundException{
    	
 		String ForDot = "\"CBO = "+LCOM4Total+"/"+FileCount+" = "+CBO;
	   	
    	String FileName = "CBO.dot";
        PrintWriter os = new PrintWriter(FileName);
        
        os.println("graph G");
        os.println("{");
        os.println("    overlap=scalexy;");
        os.println("    labelloc=\"t\";");
        os.println("    label="+ForDot+";");
        os.println();
        os.println();
        os.println("    node[shape=rectangle]");
        for(int i=0; i<CBONode.size(); i++){
       		 os.println("    "+(i+1)+" [label=\""+CBONode.get(i).toString()+"\"]");
        }
        os.println();
        for(String edge:CBOrelateLabel){
       	 os.println("    "+edge+";");
        }
        os.print("}");
        os.close();

    }
}

