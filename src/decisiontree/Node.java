package decisiontree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Node {

	private String decisionAttribute;
	private Node zeroChild;
	private Node oneChild;
	private boolean isLeaf;
	private int value;
	private double entropy;
	private boolean isVirtualNode;
	private int bestpossiblevalue;
	private LinkedHashMap<String,LinkedHashMap<Integer,Integer>> currentSet;
	private LinkedHashMap<String,LinkedHashMap<Integer,Integer>> zeroSet;
	private LinkedHashMap<String,LinkedHashMap<Integer,Integer>>oneSet;
	private List<String> featuresList = new ArrayList<String>();
	
	public Node(LinkedHashMap<String,LinkedHashMap<Integer,Integer>> data, List<String> features)
	{
		currentSet = data;
		for(String str:features)
		{
			featuresList.add(str);
		}
		isLeaf = false;
		isVirtualNode=false;
	}
	
	/**
	 * Getters and setters for making a node virtual 
	 */
	public void setVirtuat(boolean vvalue)
	{
		isVirtualNode = vvalue;
	}
	public boolean isVirtual()
	{
		return isVirtualNode;
	}
	
	/**
	 * Getters and setters for best possible value of a node 
	 */
	public void setBestPossibleValue(int bestvalue)
	{
		bestpossiblevalue = bestvalue;
	}
	public int getBestPossibleValue()
	{
		return bestpossiblevalue;
	}
	
	
	/**
	 * Getters and setters for decision attribute of node
	 */
	public void setDecisionAttribute(String Iattribute)
	{
		decisionAttribute = Iattribute;
	}
	public String getDecisionAttribute()
	{
		return  decisionAttribute;
	}
	
	/**
	 * Getters and setters for entropy
	 */
	public void setEntropy(double Ientrpoy)
	{
		entropy = Ientrpoy;
	}
	public double getEntropy()
	{
		return entropy;
	}
	
	/**
	 * Getters and setters for leaf node
	 */
	public void setLeafNode()
	{
		isLeaf = true;
	}
	public boolean isLeafNode()
	{
		return isLeaf;
	}
	
	
	/**
	 * Getters and setters for leaf node value
	 */
	public void setLeafNodeValue(int ivalue)
	{
		value = ivalue;
	}
	public int getLeafNodeValue()
	{
		return value;
	}
	
	/**
	 * Getters and setters for zero/lest child
	 */
	public void setZeroChild(Node zChildnode)
	{
		zeroChild = zChildnode;
	}
	public Node getZeroChild()
	{
		return zeroChild;
	}
	/**
	 * Getters and setters for one/right child 
	 */
	public void setOneChild(Node oChildNode)
	{
		oneChild = oChildNode;
	}
	public Node getOneChild()
	{
		return oneChild;
	}
	
	/**
	 * Getters and setters for zero set
	 */
	public LinkedHashMap<String,LinkedHashMap<Integer,Integer>> getZeroSet()
	{
		return zeroSet;
	}
	public void setZeroSet(LinkedHashMap<String,LinkedHashMap<Integer,Integer>> set)
	{
		zeroSet = set;
	}
	
	
	/**
	 * Getters for feature list
	 */
	public List<String> getFeatureList()
	{
		return featuresList;
	}
	
	/**
	 * Getters for current set
	 */
	public LinkedHashMap<String,LinkedHashMap<Integer,Integer>> getCurrentSet()
	{
		return currentSet;
	}
	
	
	/**
	 * Getters and setters for one set
	 */
	public LinkedHashMap<String,LinkedHashMap<Integer,Integer>> getOneSet()
	{
		return oneSet;
	}
	public void setOneSet(LinkedHashMap<String,LinkedHashMap<Integer,Integer>> set)
	{
		oneSet = set;
	}
}
