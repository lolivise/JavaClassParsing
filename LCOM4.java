import java.util.ArrayList;
import java.util.List;

/**
 * Calculate LCOM4 of each class
 * **/
public class LCOM4 {
	
	/* Calculate variables*/
    private List<Integer>			removeList;
    private List<ArrayList<String>>	nodeGroups;
    
    /*Return variable*/
    private int						returnLCOM4;

    
	public LCOM4(){
		removeList = new ArrayList<>();
		nodeGroups = new ArrayList<ArrayList<String>>();
		returnLCOM4 = 0;
	}
	
	/*Set Node for calculating*/
	public void setLCOM4(List<String> NodeList){
		int x = 0;
		for(String n:NodeList){
			nodeGroups.add(new ArrayList<String>());
			nodeGroups.get(x).add(n);
			x++;
		}

	}
	
	/*Calculate the value of LCOM4*/
	public void Calculatelcom4(String MethodSigniture, List<String> edgeList){		
		for(int i=0; i<nodeGroups.size();i++){
			if(nodeGroups.get(i).contains(MethodSigniture)&& !removeList.contains(i)){
				for(String edge:edgeList){
					if(!nodeGroups.get(i).contains(edge)){
						for(int j=0;j<nodeGroups.size();j++){
							if( i!=j &&nodeGroups.get(j).contains(edge)&&!removeList.contains(j)){
								for(String s:nodeGroups.get(j)){
									if(!nodeGroups.get(i).contains(s)){
										nodeGroups.get(i).add(s);
									}
								}
								removeList.add(j);
							}
						}
					}
				}
			}
		}
		returnLCOM4 = nodeGroups.size() - removeList.size();
	}
		
	/**
	 * Return Value
	 * **/
	public int getLCOM4(){
		return returnLCOM4;
	}
}
