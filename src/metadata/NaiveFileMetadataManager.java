package metadata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import application.jtable.management.JTableViewer;

public class NaiveFileMetadataManager implements MetadataManagerInterface{
	
	private String pAlias; 
	private File pFile;
	private String pSeparator;
	
	private String[] columnNames;
	private ArrayList<String[]> data;
	
	




	public NaiveFileMetadataManager(String pAlias, File pFile, String pSeparator) {
		this.pAlias = pAlias;
		this.pFile = pFile;
		this.pSeparator = pSeparator;
		
		
		readCSVfile(pFile,pSeparator);
		
		//////////////////////////////////////////////////////////
		//show data of the registered file
		JTableViewer jTableViewer;

		jTableViewer = new JTableViewer(data, columnNames);
		jTableViewer.createAndShowJTable();
		
		
		
	}
	
	



	public void readCSVfile(File dataFile,String pSeparator) {
    	
    	ArrayList<String[]> Rs = new ArrayList<String[]>();
    	
        String[] OneRow;
    	
        try {
            try (BufferedReader brd = new BufferedReader(new FileReader(dataFile))) {
            	//get the column names
            	if(brd.ready()){
            		String cn = brd.readLine();
				    //OneRow = st.split(",|\\s|;");
				    //this.columnNames = cn.split(",");
				    this.columnNames = cn.split(pSeparator);
				    System.out.println(Arrays.toString(this.columnNames));
            	} 
            	else return;
            	
            	//continue with the data
				while (brd.ready()) {
				    String st = brd.readLine();
				    //OneRow = st.split(",|\\s|;");
				    //OneRow = st.split(",");
				    OneRow = st.split(pSeparator);
				    Rs.add(OneRow);
				    System.out.println(Arrays.toString(OneRow));
				} // end of while
			}
        } // end of try
        catch (Exception e) {
            String errmsg = e.getMessage();
            System.out.println("File not found:" + errmsg);
        } // end of Catch
        
        this.data=Rs;
        
    }// end of ReadFile method
	
	 public String getAlias() {
			return pAlias;
		}

    public String[] getColumnNames() {
		return columnNames;
	}





	@Override
	public Map<String, Integer> getFieldPositions() {
		
		Map<String, Integer> columnNamesMapping= new HashMap<String, Integer>();
		
		for (int i = 0; i < this.columnNames.length; i++) {
			columnNamesMapping.put(this.columnNames[i], i);
		}
		
		return columnNamesMapping;
	}





	@Override
	public File getDataFile() {		
		return this.pFile;
	}





	@Override
	public String getSeparator() {		
		return this.pSeparator;
	}
	
	public ArrayList<String[]> getData() {
		return data;
	}

}
