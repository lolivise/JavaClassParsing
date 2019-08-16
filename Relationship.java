import java.util.ArrayList;
import java.util.List;

/**
 * Detect the link among methods and fields
 **/
public class Relationship {
	
	/*Calculate variables*/
	private int 			nodeCount;
	private String			nodeLabel;  
    
	/*Return variables*/
    private List<String>    nodeList;
    private List<String>	nodeLabelList;
    private List<String>	linkList;
    
    public Relationship(){
    	nodeCount = 0;
    	nodeList = new ArrayList<>();
    	nodeLabelList = new ArrayList<>();
    	linkList = new ArrayList<>();
    }
    
    /**
     * Set node list for calculating
     * **/
    public void setNodeLabelList(String node){
    	if(!nodeList.contains(node)){
    		nodeList.add(node);
    		nodeCount++;
    		nodeLabel = nodeCount+"[label=\""+node+"\"]";
    		nodeLabelList.add(nodeLabel);
    	}
    }
    
    /**
     * Detect and Store the link for output
     * **/
    public void setEdge(String MethodSignature, List<String> edgeList){
    	for(int i=0; i<nodeList.size(); i++){
    		if(nodeList.get(i).toString().equals(MethodSignature)){
    			for(String f: edgeList){
    				for(int j=0; j<nodeList.size();j++)
    					if(nodeList.get(j).toString().equals(f)){
    						String edge = (i+1)+" -- "+(j+1);
    						String edgeReverse = (j+1)+" -- "+(i+1);
    						if(!linkList.contains(edgeReverse)&&!linkList.contains(edge))
    							linkList.add(edge);
    						break;
    					}
    			}
    			break;
    		}
    	}
    }
    
    
    /**
     * Return Value
     * **/
    public List<String> getnodeList(){
    	return nodeList;
    }
    
    public List<String> getnodeLabelList(){
    	return nodeLabelList;
    }
    
    public List<String> getLinkList(){
    	return linkList;
    }
}
