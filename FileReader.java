import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Obtain the path of the files
 * **/
public class FileReader {

	/* Global variables*/
    private File 			file;
	private List<String> 	filePathList = new ArrayList<>();
	
	/*Object for passing */
	private ProcessCentre 	processCentre;
	
	public FileReader(){
		processCentre = new ProcessCentre();
	}


	/*Read every class file*/
    public void ReadFile (List<String> files) {

        for (String filess: files) {
            file = new File(filess);
            if (!file.isDirectory()) {
                if (file.getName().contains(".class")) {
                    filePathList.add(file.getPath());
                }
            } else {
                ReadFolder(file);
            }
        }
        processCentre.preprocess(filePathList);
    }



    /*Read every class file in directory*/
    private void ReadFolder (File folder) {

        for (File files : folder.listFiles()) {
            if (files.isDirectory()) {
                ReadFolder(files);
            } else {
                if (files.getName().contains(".class")) {
                	filePathList.add(files.getPath());
                }
            }
        }
    }
}
