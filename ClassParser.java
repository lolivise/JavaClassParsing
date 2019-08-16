import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Read commend Line and pass to FileReader
 **/
public class ClassParser {
	
	/*print String if no input argument*/
    private static final String USAGE    = "Please enter .class file";
    
    /*construct object of FileReader*/
    private static FileReader	fileReader;
    
    /*Contain arguments*/
    private static List<String> files;         
    
    /**
     * detect arguments and pass to FileReader
     **/
    public static void main(String[] args) throws FileNotFoundException {

        /* detect arguments */
        if (args.length < 1) {
        	
            System.out.println(USAGE);
           
        }else{
        	
        	/* Store arguments */
            files = new ArrayList<>();
            for (int i=0; i<args.length; i++){
                files.add(args[i]);
            }
            
            /* Pass argument list */
            fileReader = new FileReader();
            fileReader.ReadFile(files);
        }


        
             
    }
}