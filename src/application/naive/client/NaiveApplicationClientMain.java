package application.naive.client;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class NaiveApplicationClientMain {
	

	public static void main(String[] args)
    {
		//Program's AppController
		NaiveApplicationController appController=new NaiveApplicationController();
		
		
		//FIXME
		//FUTURE USE
		List<String[]> result;
		File resultFileMain = null;
		
        System.out.println("System starting...");
        System.out.println("------------------");
       
        //UC1
        String sAlias = "simple";		
		String sSeparator = ",";
		resultFileMain=appController.registerFile(sAlias, "./test/resources/input/simple.csv", sSeparator);

		
        //UC2
		String[] resultColumnNames;
		resultColumnNames=appController.getFileColumnNames(sAlias);
		System.out.println(Arrays.toString(resultColumnNames));
        
		
        
        
        //UC3
        Map<String, List<String>> atomicFilters;
        atomicFilters = new HashMap<String, List<String>>();
		List<String> countryFilter = new ArrayList<String>();
		countryFilter.add("AUS:Australia");
		atomicFilters.put("LOCATION:Country", countryFilter);
		
		List<String> providerFilter = new ArrayList<String>();
		providerFilter.add("HPTOT:All providers");
		atomicFilters.put("HP:Provider", providerFilter);
		
		result=appController.executeFilterAndShowJTable(sAlias, atomicFilters, "./simplefiltered.csv");
		
		
		//UC4
		appController.saveToResultTextFile("./anotherSimplefiltered.csv", result);
		
		//UC5
		appController.showSingleSeriesLineChart("simple", result, "TIME:Year", "MSR:Value", "./linechart.png");
		appController.showSingleSeriesBarChart("simple", result, "TIME:Year", "MSR:Value", "./barchart.png");
		
		System.out.println("------------------");
        System.out.println("System exiting...");
		
    }
}
