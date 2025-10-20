package filtering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import metadata.MetadataManagerInterface;
import metadata.NaiveFileMetadataManager;

public class FilteringEngine implements FilteringEngineInterface{
	
	Map<String, List<String>> atomicFilters;
	NaiveFileMetadataManager metadataManager;

	public FilteringEngine(Map<String, List<String>> atomicFilters, NaiveFileMetadataManager metadataManager) {
		this.atomicFilters=atomicFilters;
		this.metadataManager=metadataManager;
	}

	public FilteringEngine() {
		
	}

	@Override
	public int setupFilteringEngine(Map<String, List<String>> pAtomicFilters,
			MetadataManagerInterface pMetadataManager) {

		this.atomicFilters=pAtomicFilters;
		this.metadataManager=(NaiveFileMetadataManager) pMetadataManager;
		
		return 1;
	}

	@Override
	public List<String[]> workWithFile() {		
		
		
		//initial data
		ArrayList<String[]> data= this.metadataManager.getData();
		
		//filtered data
		ArrayList<String[]> fdata = new ArrayList<String[]>();
		
		//Column names positions
		Map<String, Integer> fieldPositions =this.metadataManager.getFieldPositions();
		
		//check every row 
		String[] rowData=null;
		
		//which column to check to every row based on the given filter
		int rowDataColumnPosition=0;
		
		//add or not the row to the filtered data
		boolean rowDataStatus=false;
		
		//check every row
		for (int c = 0; c < data.size(); c++) { 		      
	          
			//System.out.println(data.get(c));
			
			//get the row 
			rowData=data.get(c);
			
			
			// Iterating HashMap through for loop
	        for (Map.Entry< String, List<String> > set : this.atomicFilters.entrySet()) {
	 
	            // Printing all elements of a Map
	            //System.out.println(set.getKey() + " = "  + set.getValue());
	            
	        	//
	        	rowDataColumnPosition=fieldPositions.get( set.getKey() );
	        	//if the list of strings of the filter contains the value
	        	//in the specific column then 
	        	//set true (possible) the row to be added and continue to 
	        	//the next filter 
	        	if( set.getValue().contains( rowData[rowDataColumnPosition] )  )
	        	{
	        		rowDataStatus=true;
	        	}
	        	else
	        	{	rowDataStatus=false;
	        		break;}
	        }
			
	        //if all filters found true, add row
	        if (rowDataStatus==true) {
	        	fdata.add(rowData);
	        }
	        
			//clear rowStatus
	        rowDataStatus=false;
	      }   
		
		
		return fdata;
		
	
	}

}
