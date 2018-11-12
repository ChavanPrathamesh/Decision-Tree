package decisiontree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - PrathameshChavan 
 * This is the main class of the project
 * It reads the command line arguments, processes them and gives a call to create tree
 * Printing of heuristics is handled in this class
 */

public class Driver {
	
	//This is the main class
	public static void main(String[] args)
	{
		if(args.length!=5)
		{
			System.out.println("Incorrect number of arguments specified");
			System.exit(0);
		}
		
		String training = args[0];
		String validation = args[1];
		String test = args[2];
		String toprint = args[3];
		String toprune = args[4];
		
		//Validation for toprint
		if(toprint.equalsIgnoreCase("yes"))
		{
			System.out.println("You have selected to print the generated trees.");
		}
		else if(toprint.equalsIgnoreCase("no"))
		{
			System.out.println("You have selected not to print the generated trees.");
		}
		else
		{
			System.out.println("Incorrect argument for toprint. Pass one of {yes,no}");
			System.exit(0);
		}
		
		//Validation for toprune
		if(toprune.equalsIgnoreCase("yes"))
		{
			System.out.println("You have selected to prune the generated trees.\n");
		}
		else if(toprune.equalsIgnoreCase("no"))
		{
			System.out.println("You have selected not to prune the generated trees.\n");
		}
		else
		{
			System.out.println("Incorrect argument for toprune. Pass one of {yes,no}\n");
			System.exit(0);
		}
		
		//FileProcessor class is used for reading data from files
		FileProcessor fp = new FileProcessor();
		
		System.out.println("*******************************");
		System.out.println("********* HEURISTIC 1 *********");
		System.out.println("*******************************");
		System.out.println();
		
		//Creating the decision tree for heuristic one
		DecisionTree dtree = new DecisionTree(fp.getData(training),fp.getFeatureList(),true);
		dtree.createTree();
		
		//Print the created tree
		if(toprint.equalsIgnoreCase("yes"))
		{
			dtree.createPrintList(dtree.getRootNode(),0);
			System.out.println("Tree before pruning");
			dtree.print();
			dtree.clearPrintList();
			System.out.println();
		}
		
		/**
		 * Below commented code can be used for testing purpose. To print data data set created
		 */
		/*
		 LinkedHashMap<String, LinkedHashMap<Integer,Integer>> trainingData = fp.getData(Training);
		 for(int i=0;i<trainingData.size();i++)
		{
			
			System.out.println("Feature "+featuresList.get(i)+" : "+trainingData.get(featuresList.get(i)));
		}*/
		
		//Calculate the initial accuracy
		double oldaccuracy = dtree.calculateAccuracy(fp.getData(test),fp.getFeatureList());
		System.out.println("Accuracy before pruning : " +oldaccuracy);
		System.out.println("\n");
		
		//Perform pruning and print new tree and accuracy
		if(toprune.equalsIgnoreCase("yes"))
		{
			dtree.prune(fp.getData(validation));
		
			double newaccuracy = dtree.calculateAccuracy(fp.getData(test),fp.getFeatureList());
			
			if(toprint.equalsIgnoreCase("yes"))
			{
				dtree.createPrintList(dtree.getRootNode(),0);
				System.out.println("Tree after pruning");
				dtree.print();
				dtree.clearPrintList();
				System.out.println();
			}
			
			System.out.println("Accuracy after pruning : " +newaccuracy);
			System.out.println("\n");
		}
		
		
		System.out.println("*******************************");
		System.out.println("********* HEURISTIC 2 *********");
		System.out.println("*******************************");
		System.out.println();
		
		//Creating the decision tree for heuristic two
		DecisionTree dttwo = new DecisionTree(fp.getData(training),fp.getFeatureList(),false);
		dttwo.createTree();
		
		
		//Print the created tree
		if(toprint.equalsIgnoreCase("yes"))
		{
			dttwo.createPrintList(dttwo.getRootNode(),0);
			System.out.println("Tree before pruning");
			dttwo.print();
			dttwo.clearPrintList();
			System.out.println();
		}
		
		//Calculate the initial accuracy
		double oldaccuracytwo = dttwo.calculateAccuracy(fp.getData(test),fp.getFeatureList());
		System.out.println("Accuracy before pruning : " +oldaccuracytwo);
		System.out.println("\n");
		
		//Perform pruning and print new tree and accuracy
		if(toprune.equalsIgnoreCase("yes"))
		{
			dttwo.prune(fp.getData(validation));
		
			double newaccuracy = dttwo.calculateAccuracy(fp.getData(test),fp.getFeatureList());
			
			if(toprint.equalsIgnoreCase("yes"))
			{
				dttwo.createPrintList(dttwo.getRootNode(),0);
				System.out.println("Tree after pruning");
				dttwo.print();
				dttwo.clearPrintList();
				System.out.println();
			}
			System.out.println("Accuracy after pruning : " +newaccuracy);
			System.out.println("\n");
		}
	}
}
