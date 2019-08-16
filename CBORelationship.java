import java.util.ArrayList;
import java.util.List;

/**
 * Calculate Link between classes 
 **/
public class CBORelationship {
	
	/*Calculate variables*/
    private List<List<String>>	linkList;
    
	/* Return variables*/
    private List<String>	nodeLabelList;
    private	List<String> 	CBOrelateLabel; 


	
	public CBORelationship(){
		nodeLabelList = new ArrayList<>();
    	CBOrelateLabel = new ArrayList<>();
		linkList = new ArrayList<List<String>>();
	}
	
	/**
	 *  Construct a list of class name 
	 **/
	public void setNode(List<String> fileList){
		for(String s:fileList){
    		List<String> Temp = new ArrayList<>();
    		if(s.contains("\\")){
    			s = s.substring(s.lastIndexOf("\\")+1, s.lastIndexOf("."));
    		}else{
            	s = s.substring(0, s.lastIndexOf("."));
    		}
    		Temp.add(s);
    		nodeLabelList.add(s);
    		linkList.add(Temp);

    	}
	}
	
	/**
	 *  Construct the list of classes which is invoke in other class 
	 **/
    public void cboEdge(String ClassName,String s){    	
    	String fieldDescrip = s;
    	if(fieldDescrip.charAt(0) == 'L'){
    		fieldDescrip = fieldDescrip.substring(1);
    	}else if(fieldDescrip.charAt(0) == '['){
    		fieldDescrip = fieldDescrip.substring(2);
    	}
    	if(fieldDescrip.length()>0)
    	if(fieldDescrip.charAt(fieldDescrip.length()-1)==';')
    	fieldDescrip = fieldDescrip.substring(0, fieldDescrip.lastIndexOf(';'));
    	for(int i = 0; i<linkList.size();i++){
    		if(!linkList.get(i).contains(fieldDescrip)&&linkList.get(i).get(0).toString().equals(ClassName)){
    			linkList.get(i).add(fieldDescrip);

    		}
    	}
    	    	
    }
    
    /**
     *  Construct List of link for graph file 
     **/
    public List<String> detectCBOLink(){
    	List<String> CBOrelateLabel = new ArrayList<>();
    	for(int i =0; i<linkList.size();i++){
    		for(int j = 1; j<linkList.get(i).size();j++){
    			for(int k = 0; k<linkList.size();k++){
    				if(linkList.get(i).get(j).toString().equals(linkList.get(k).get(0).toString())){
    					String relate = (i+1)+" -- " +(k+1);
    					String inverse = (k+1)+" -- "+(1+i);
    					if(!CBOrelateLabel.contains(relate)&&!CBOrelateLabel.contains(inverse)){
    						CBOrelateLabel.add(relate);
    					}
    				}
    			}
    		}
    	}
    	return CBOrelateLabel;
    }
    
    /**
     * Return Value
     * **/
    public List<String> getFileNameList(){
    	return nodeLabelList;
    }
    
    public List<String> getCBOrelateLabel(){
    	return CBOrelateLabel;
    }
}

