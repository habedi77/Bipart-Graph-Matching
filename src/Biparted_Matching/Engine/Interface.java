package Biparted_Matching.Engine;

import java.util.Arrays;
import java.util.List;

public class Interface
{
	BipartedGraph bipartedGraph;
	public void init(int m,int n)
	{
		bipartedGraph = new BipartedGraph(m, n);
	}
	
	/**
	 * makes a random graph based on seed and threshold
	 * @param seed seed for the random generator
	 * @param threshold 0-100 where 0=fully connected and 100 is with no edges
	 */
	public void random(long seed,int threshold)
	{
		bipartedGraph.makeRandomGraph(seed, threshold);
	}
	
	public void connectEdge(int l,int r)
	{
		bipartedGraph.connectEdge(l,r);
	}
	
	public void removeEdge(int l, int r)
	{
		bipartedGraph.removeEdge(l,r);
	}
	
	/**
	 * 
	 * @return int[i]
	 * where i-th vertex in the left side is connected to the int[i]-th vertex on the right side
	 * (-1 if it is not connected to any vertex)
	 */
	public int[] getMaximumMatchingGraph()
	{
		return bipartedGraph.getMatched();
	}
	
	/**
	 * 
	 * @return where i-th vertex in the left side is connected to Array of vertexes int[i] on the right side
	 * (empty array if it is not connected to any vertex)
	 */
	public int[][] getGraph()
	{
		return bipartedGraph.getGraph();
	}
	
	public void reset()
	{
		bipartedGraph.resetGraph();
	}
	
}
