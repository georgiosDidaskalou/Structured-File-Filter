package file.manager;

public class StructuredFileManagerFactory {

	public StructuredFileManager createStructuredFileManager() {
		
		//The Factory creates a fileManager
		StructuredFileManager fileManager = new StructuredFileManager();
		
		return fileManager;
	}

}
