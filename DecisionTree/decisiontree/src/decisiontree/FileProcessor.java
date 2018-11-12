package decisiontree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author PrathameshChavan
 * This class generates the data set and feature list in 
 * java representable format
 *
 */

public class FileProcessor {
	
	private List<String> featuresList;		//Contains list of features
	
	public FileProcessor()
	{
		featuresList = new ArrayList<String>();
	}
	
	/**
	 * @param String file path of the input file which contains the data set
	 * The first line in the file is the feature list 
	 */
	public LinkedHashMap<String, LinkedHashMap<Integer, Integer>> getData(String filepath)
	{
		featuresList.clear();
		LinkedHashMap<String, LinkedHashMap<Integer, Integer>> dataset = new LinkedHashMap<String, LinkedHashMap<Integer, Integer>>();
		String featuresarray[]=null;
		String line = null;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			try
			{
				if((line=br.readLine())!=null)
				{
					featuresarray = line.split(",");
					for(int i=0;i<featuresarray.length;i++)
					{
						featuresList.add(featuresarray[i]);
						dataset.put(featuresarray[i], new LinkedHashMap<Integer, Integer>());
					}
				}
				
				int insertindex = 0;
				while((line=br.readLine())!=null)
				{
					String dataarray[] = line.split(",");
					for(int i=0;i<dataarray.length;i++)
					{
						dataset.get(featuresarray[i]).put(insertindex, Integer.parseInt(dataarray[i]));
					}
					insertindex++;
				}
				
			}
			catch(IOException e)
			{
				System.out.println("Incorrect arguments present in file");
			}	
		}
		 catch(FileNotFoundException e)
		 {
			 System.out.println("Incorrect file path or file does not exist so exiting");
			 System.exit(0);
		 }
		return dataset;
	}
	
	/**
	 * @return the featureList 
	 */
	public List<String> getFeatureList()
	{
		return featuresList;
	}
	
}
