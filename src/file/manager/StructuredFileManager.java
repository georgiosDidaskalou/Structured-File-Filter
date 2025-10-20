package file.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import filtering.FilteringEngine;
import metadata.NaiveFileMetadataManager;

public class StructuredFileManager implements StructuredFileManagerInterface {

	

	//private ArrayList<StructuredFile> allStructuredFiles;
	private ArrayList<NaiveFileMetadataManager> allNaiveFileMetadataManagers;
	
	//one filtering engine to use it for filtering every file of the list 
	//when needed
	private FilteringEngine filteringEngine;
	
	public StructuredFileManager() {
		this.allNaiveFileMetadataManagers = new ArrayList<NaiveFileMetadataManager>();
		this.filteringEngine = new FilteringEngine();
	}
	
	
	
	@Override
	public File registerFile(String pAlias, String pPath, String pSeparator) throws IOException, NullPointerException {
		
		File registerFile = new File(pPath);
		
		//create a new NaiveFileMetadataManager
		//StructuredFile sFile = new StructuredFile(registerFile,pAlias,pPath,pSeparator);
		NaiveFileMetadataManager sNaiveFileMetadataManager= new NaiveFileMetadataManager(pAlias,registerFile,pSeparator);
		
		//add the new NaiveFileMetadataManager File to the List of NaiveFileMetadataManagers
		allNaiveFileMetadataManagers.add(sNaiveFileMetadataManager);
		
		return registerFile;
	}

	@Override
	public String[] getFileColumnNames(String pAlias) {
		
		for(NaiveFileMetadataManager sNaiveFileMetadataManager: allNaiveFileMetadataManagers) {
			if(sNaiveFileMetadataManager.getAlias().equals(pAlias)) {
				return sNaiveFileMetadataManager.getColumnNames();
			}
		}
		
		//KnownIssues-2021/12/11
		//zero sized array of strings for the StructuredFileManagerTest 
		String[] zeroString = new String[0];
		return zeroString;
	}

	@Override
	public List<String[]> filterStructuredFile(String pAlias, Map<String, List<String>> pAtomicFilters) {
		
		
		
		//find from the list the file for filtering
		NaiveFileMetadataManager fNaiveFileMetadataManager = null;
		
		for(NaiveFileMetadataManager sNaiveFileMetadataManager: allNaiveFileMetadataManagers) {
			if(sNaiveFileMetadataManager.getAlias().equals(pAlias)) {
				fNaiveFileMetadataManager = sNaiveFileMetadataManager;
			}
		}
		
		//setup filteringEngine for the specific file
		filteringEngine.setupFilteringEngine(pAtomicFilters, fNaiveFileMetadataManager);
		
		
		//return the results of filtering
		return filteringEngine.workWithFile();
		
		
	}

	@Override
	public int printResultsToPrintStream(List<String[]> recordList, PrintStream pOut) {
		
		for (int i = 0; i < recordList.size(); i++) {
            
            pOut.println(Arrays.toString(recordList.get(i)));
        }
		
		

		return recordList.size();
	}

}
