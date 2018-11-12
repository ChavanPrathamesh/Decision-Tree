package decisiontree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author PrathameshChavan
 * This class has the core logic for building decision tree
 * for pruning it for calculating accuracies and for printing it
 **/

public class DecisionTree {
	
	private LinkedHashMap<String,LinkedHashMap<Integer,Integer>>dataSet;
	private List<String> featureList;
	private Node rootNode=null;
	private String LABEL;
	private List<String> printList = new ArrayList<String>();
	private boolean HEURISTIC=false;
	
	public DecisionTree(LinkedHashMap<String,LinkedHashMap<Integer,Integer>> data, List<String> features,boolean heuristic)
	{
		dataSet = data;
		featureList = features;
		HEURISTIC=heuristic;
	}
	
	/**
	 * This method initiates the tree building
	 */
	public void createTree()
	{
		LABEL = featureList.get(featureList.size()-1);
		Node root = new Node(dataSet,featureList);
		rootNode = createNode(root);
	}
	
	/**
	 * This function recursively creates new nodes for the tree
	 * The stoppage condition is when entropy = 0 
	 **/
	private Node createNode(Node node)
	{
		node.setEntropy(determineEntropy(node.getCurrentSet().get(LABEL).values()));
		
		if(node.getEntropy()==0)
		{
			if(checkIfAllZeros(node.getCurrentSet().get(LABEL).values()))
			{
				node.setLeafNodeValue(0);
				node.setDecisionAttribute("0");
			}
			else
			{
				node.setLeafNodeValue(1);
				node.setDecisionAttribute("1");
			}
			node.setLeafNode();;
			node.setZeroChild(null);
			node.setOneChild(null);
			return node;
		}
		
		//Selects the best attribute to split
		String splitAttribute = selectSplitAttribute(node);
		node.setDecisionAttribute(splitAttribute);
		
		//Create zeroset and oneset
		createSets(node,splitAttribute);
		
		//If there are no more nodes to split set the node as leaf node
		//select the best possible value and return
		if(node.getFeatureList().size()-1==0)
		{
			node.setLeafNode();
			node.setZeroChild(null);
			node.setOneChild(null);
			node.setLeafNodeValue(getMostCommonValue(node.getCurrentSet().get(LABEL).values()));
			node.setDecisionAttribute(Integer.toString(node.getLeafNodeValue()));
			return node;
		}
		
		//Recursive call for zero child
		if(node.getZeroSet().isEmpty())
		{
			node.setZeroChild(null);
		}
		else
		{
			Node zeroNode = createNode(new Node(node.getZeroSet(),node.getFeatureList()));
			node.setZeroChild(zeroNode);
		}
		
		//Recursive call for one child
		if(node.getOneSet().isEmpty())
		{
			node.setOneChild(null);
		}
		else
		{
			Node oneNode = createNode(new Node(node.getOneSet(),node.getFeatureList()));
			node.setOneChild(oneNode);
		}	
		return node;
	}
	
	
	/**
	 * This is a generic function to print particular column
	 * Used for testing purpose 
	 */
	public void print(Collection<Integer> values)
	{
		for(int i:values)
		{
			System.out.println(i);
		}
	}
	
	/**
	 * Returns the rootnode of the tree 
	 */
	public Node getRootNode()
	{
		return rootNode;
	}
	
	/**
	 * Check if the collection has all zeros 
	 */
	public boolean checkIfAllZeros(Collection<Integer> values)
	{
		for(int i:values)
		{
			if(i!=0)
			{
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Check if the collection has all ones 
	 */
	public boolean checkIfAllOnes(Collection<Integer> values)
	{
		for(int i:values)
		{
			if(i!=1)
			{
				return false;
			}
		}
		return true;
	}
	
	/*
	 * When no more attributes are available for splitting this function is called
	 * It returns the most probable value
	 */
	private int getMostCommonValue(Collection<Integer> values)
	{
		int zerocount = 0;
		int onecount = 0;
		
		for(int i:values)
		{
			if(i==0)
			{
				zerocount++;
			}
			if(i==1)
			{
				onecount++;
			}
		}
		
		if(zerocount>onecount)
		{
			return 0;
		}
		else
		{
			return 1;
		}	
	}
	
	/**
	 * @return Entropy 
	 */
	private double determineEntropy(Collection<Integer> values)
	{
		double zerocount = 0;
		double onecount = 0;
		double totalcount = 0;
		for(int i:values)
		{
			if(i==0)
			{
				zerocount++;
			}
			else
			{
				onecount++;
			}
		}
		totalcount = zerocount+onecount;
		if(zerocount==totalcount||onecount==totalcount)
		{
			return 0;
		}
		
		double probzero = zerocount/totalcount;
		double probone = onecount/totalcount;
		if(HEURISTIC)
		{
			double entropy = ((-1)*(probzero)*(Math.log10(probzero)/Math.log10(2)))+ 
				        ((-1)*(probone)*(Math.log10(probone)/Math.log10(2)));
			return entropy;
		}
		else
		{
			double entropy = probzero*probone;
			return entropy;
		}
	}
	
	/**
	 * Creates the zero and one sets of the parent
	 * @param Node
	 * @param The string to split the attributes 
	 */
	
	private void createSets(Node node,String splitString)
	{
		LinkedHashMap<Integer,Integer> splitFeature = node.getCurrentSet().get(splitString);
		Set<Integer> keys = splitFeature.keySet();
		
		//Clone values to new sets
		node.setZeroSet(clone(node.getCurrentSet(),node.getFeatureList()));
		node.setOneSet(clone(node.getCurrentSet(),node.getFeatureList()));
		
		//Maintains a list of values to be removed
		ArrayList<Integer> removezero = new ArrayList<Integer>();
		ArrayList<Integer> removeone = new ArrayList<Integer>();
		
        for(int k:keys)
        {
        	if(node.getCurrentSet().get(splitString).get(k)==1)
        	{
        		removezero.add(k);
        	}
        	else
        	{
        		removeone.add(k);
        	}
        }
        	 
        for(int j:removezero)
        {
        	for(int i=0;i<node.getFeatureList().size();i++)
	 			{
	 				node.getZeroSet().get(node.getFeatureList().get(i)).remove(j);
	 			}
        }
        
        for(int j:removeone)
        {
        	for(int i=0;i<node.getFeatureList().size();i++)
	 			{
	 				node.getOneSet().get(node.getFeatureList().get(i)).remove(j);
	 			}
        }
        
        /*	
         * Removing the split attribute from all sets as it is not to be passed for
         * new node creation
         */
        node.getZeroSet().remove(splitString);
        node.getOneSet().remove(splitString);
        node.getFeatureList().remove(splitString);
	}
	
	/**
	 * Cloning the map
	 * @return new clone of parent 
	 */
	private LinkedHashMap<String,LinkedHashMap<Integer,Integer>> clone 
	(LinkedHashMap<String,LinkedHashMap<Integer,Integer>> parentSet, List<String> featureList)
	{
		LinkedHashMap<String,LinkedHashMap<Integer,Integer>> newClone = new LinkedHashMap<String,LinkedHashMap<Integer,Integer>>();		
		
		for(Entry<String, LinkedHashMap<Integer, Integer>> entry : parentSet.entrySet())
		{
			newClone.put(entry.getKey(), new LinkedHashMap<Integer,Integer>(entry.getValue()));
		}
		return newClone;
	}
	
	/**
	 * @return Best attribute to split 
	 */
	private String selectSplitAttribute(Node splitNode)
	{
		String bestattribute = null;
		double IGValue = -1;
		
		for(String str:splitNode.getFeatureList())
		{
			if(str.equals(LABEL))
			{
				continue;
			}
			
			LinkedHashMap<String,LinkedHashMap<Integer,Integer>> zeroset = getZeroSet(splitNode,str);
			LinkedHashMap<String,LinkedHashMap<Integer,Integer>> oneset = getOneSet(splitNode,str);
			
			double tempgain = getGain(splitNode.getEntropy(),
									  splitNode.getCurrentSet().get(str).values(),
									  zeroset,oneset);
			if(tempgain>IGValue)
			{
				IGValue = tempgain;
				bestattribute = str;
			}
		}
		return bestattribute;
	}
	
	
	/**
	 * Calculating the Information gain for two heuristics 
	 */
	public double getGain(double rootentropy,Collection<Integer> values,
			LinkedHashMap<String,LinkedHashMap<Integer,Integer>> zeroset,
			LinkedHashMap<String,LinkedHashMap<Integer,Integer>> oneset)
	{
		double zerocount = 0;
		double onecount = 0;
		double totalcount = 0;
		for(int i:values)
		{
			if(i==0)
			{
				zerocount++;
			}
			else
			{
				onecount++;
			}
		}
		
		totalcount = zerocount+onecount;
		
		double summation= 
				(-1)*(zerocount/totalcount)*determineEntropy(zeroset.get(LABEL).values())
				+(-1)*(onecount/totalcount)*determineEntropy(oneset.get(LABEL).values());
		
		double informationgain = rootentropy + (summation);
		
		return informationgain;
	}
	
	/**
	 * @return Set containing only zero values 
	 */
	public LinkedHashMap<String,LinkedHashMap<Integer,Integer>> getZeroSet(Node node,String splitString)
	{
		LinkedHashMap<Integer,Integer> splitFeature = node.getCurrentSet().get(splitString);
		Set<Integer> keys = splitFeature.keySet();
		
		LinkedHashMap<String,LinkedHashMap<Integer,Integer>> zeromap = clone(node.getCurrentSet(),node.getFeatureList());
		
		ArrayList<Integer> temparray = new ArrayList<Integer>();
		
		 for(int k:keys)
	        {
	        	if(node.getCurrentSet().get(splitString).get(k)==1)
	        	{
	        		temparray.add(k);
	        	}
	        }
		 
		 for(int j:temparray)
		 {
			 for(int i=0;i<node.getFeatureList().size();i++)
		 		{
		 			zeromap.get(node.getFeatureList().get(i)).remove(j);
		 		}
		 }	
		 return zeromap;
	}
	
	/**
	 * @return Set containing only zero values 
	 */
	
	public LinkedHashMap<String,LinkedHashMap<Integer,Integer>> getOneSet(Node node,String splitString)
	{
		LinkedHashMap<Integer,Integer> splitFeature = node.getCurrentSet().get(splitString);
		Set<Integer> keys = splitFeature.keySet();
		LinkedHashMap<String,LinkedHashMap<Integer,Integer>> onemap = clone(node.getCurrentSet(),node.getFeatureList());
		ArrayList<Integer> temparray = new ArrayList<Integer>(); 
		
		for(int k:keys)
	        {
	        	if(node.getCurrentSet().get(splitString).get(k)==0)
	        	{
	        		temparray.add(k);
	        	}
	        }
		
		for(int j:temparray)
		{
			for(int i=0;i<node.getFeatureList().size();i++)
    		{
    			onemap.get(node.getFeatureList().get(i)).remove(j);
    		}
		}
		 return onemap;
	}
	
	/**
	 * As we are using the same class for two heuristics
	 * This method has to be called to clear printing list before 
	 * printing values for second heuristic 
	 */
	public void clearPrintList()
	{
		printList.clear();
	}
	
	/**
	 * Creating a list of String which holds the tree structure 
	 */
	public void createPrintList(Node node,int value)
	{
		String strleft="";
		String strright="";
		for(int i=0;i<value;i++)
		{
			strleft = strleft + "| ";
			strright = strright + "| ";
		}
		strleft = strleft + node.getDecisionAttribute()+" = ";
		strright = strright + node.getDecisionAttribute()+" = ";
		if(node.isLeafNode())
		{
			strleft = strleft + Integer.toString(node.getLeafNodeValue());
			printList.add(strleft);
			return;
		}
		if(node.getZeroChild()!=null)
		{
			if(node.getZeroChild().isLeafNode())
			{
				strleft = strleft + "0 : "+node.getZeroChild().getLeafNodeValue();
				printList.add(strleft);
			}
			else if(node.getZeroChild().isVirtual())
			{
				strleft = strleft + "0 : "+node.getZeroChild().getBestPossibleValue();
				printList.add(strleft);
			}
			else
			{
				strleft = strleft +"0 :";
				printList.add(strleft);
				createPrintList(node.getZeroChild(),value+1);
			}
		}
		
		if(node.getOneChild()!=null)
		{
			if(node.getOneChild().isLeafNode())
			{
				strright = strright + "1 : "+node.getOneChild().getLeafNodeValue();
				printList.add(strright);
			}
			else if(node.getOneChild().isVirtual())
			{
				strright = strright + "1 : "+node.getOneChild().getBestPossibleValue();
				printList.add(strright);
			}
			else
			{
				strright = strright +"1 :";
				printList.add(strright);
				createPrintList(node.getOneChild(),value+1);
			}
		}
	}
	
	/**
	 * Prints the created list 
	 */
	public void print()
	{
		for(String str:printList)
		{
			System.out.println(str);
		}
	}
	
	/**
	 * Calculate the accuracy of the tree
	 * @param Validation set or test set
	 * @return accuracy 
	 */
	public double calculateAccuracy(LinkedHashMap<String, LinkedHashMap<Integer, Integer>> testset,List<String> featureList)
	{
		LinkedHashMap<Integer,Integer> splitFeature = testset.get(LABEL);
		Set<Integer> keys = splitFeature.keySet();
		double totalclassified = 0;
		double classifiedcorrectly = 0;
		
		for(int k:keys)
        {
			LinkedHashMap<String,Integer> tempmap = new LinkedHashMap<String,Integer>();
			for(int i=0;i<featureList.size()-1;i++)
			{
				tempmap.put(featureList.get(i),testset.get(featureList.get(i)).get(k));
			}
			int value = classify(rootNode,tempmap);
			if(value==testset.get(LABEL).get(k))
			{
				classifiedcorrectly++;
			}
			totalclassified++;
			tempmap.clear();
        }
		return (classifiedcorrectly/totalclassified)*100;
	}
	
	/**
	 * Called to classify a particular tuple
	 * @return 0 or 1 
	 */
	private int classify(Node node,LinkedHashMap<String,Integer> classificationmap)
	{
		if(node.isLeafNode())
		{
			return node.getLeafNodeValue();
		}
		if(node.isVirtual())
		{
			return node.getBestPossibleValue();
		}
		if(classificationmap.get(node.getDecisionAttribute()).equals(0))
		{
			if(node.getZeroChild()!=null)
			{
				if(node.getZeroChild().isLeafNode())
				{
					return node.getZeroChild().getLeafNodeValue();
				}
				else if(node.getZeroChild().isVirtual())
				{
					return node.getZeroChild().getBestPossibleValue();
				}
				else
				{
					return classify(node.getZeroChild(),classificationmap);
				}
			}
			else
			{
				return -1;
			}
		}
		else
		{
			if(node.getOneChild()!=null)
			{
				if(node.getOneChild().isLeafNode())
				{
					return node.getOneChild().getLeafNodeValue();
				}
				else if(node.getOneChild().isVirtual())
				{
					return node.getOneChild().getBestPossibleValue();
				}
				else
				{
					return classify(node.getOneChild(),classificationmap);
				}
			}
			else
			{
				return -1;
			}
		}
	}
	
	/**
	 * Prunes the tree 
	 */
	public void prune(LinkedHashMap<String, LinkedHashMap<Integer, Integer>> validationset)
	{
		pruneTree(rootNode,validationset);
	}
	
	/**
	 * Recursive prune call
	 */
	public boolean pruneTree(Node node,LinkedHashMap<String, LinkedHashMap<Integer, Integer>> validationset)
	{
		boolean zerochild = false;
		boolean onechild = false;
		
		if(node.getZeroChild()==null)
		{
			zerochild = true;
		}
		else if(node.getZeroChild().isVirtual()||node.getZeroChild().isLeafNode())
		{
			zerochild = true;
		}
		else
		{
			zerochild = pruneTree(node.getZeroChild(),validationset);
		}
		
		if(node.getOneChild()==null)
		{
			onechild = true;
		}
		else if(node.getOneChild().isVirtual()||node.getOneChild().isLeafNode())
		{
			onechild = true;
		}
		else
		{
			onechild = pruneTree(node.getOneChild(),validationset);
		}
		
		if(zerochild&&onechild)
		{
			double originalaccuracy = 0;
			double newaccuracy = 0;
			originalaccuracy = calculateAccuracy(validationset,featureList);
			node.setVirtuat(true);
			node.setBestPossibleValue(getMostCommonValue(node.getCurrentSet().get(LABEL).values()));
			newaccuracy = calculateAccuracy(validationset,featureList);
			if(newaccuracy>=originalaccuracy)
			{
				node = null;
				return true;
			}
			else
			{
				node.setVirtuat(false);
				return false;
			}
			
		}
		return false;
	}
	
}
