package Biparted_Matching.Engine;

import java.util.*;

public class BipartedGraph
{
	//number of vertices on left(m) and right(n) 
	private int m, n;
	
	private Vertex[] leftSide;
	private Vertex[] rightSide;
	
	public BipartedGraph(int m, int n)
	{
		this.m = m;
		this.n = n;
		leftSide = new Vertex[m];
		rightSide = new Vertex[n];
		for (int i = 0; i < m; i++)
			leftSide[i] = new Vertex(i);
		for (int i = 0; i < n; i++)
			rightSide[i] = new Vertex(m + i);
	}
	
	
	public void makeRandomGraph(long seed, int thresh)
	{
		Random random = new Random(seed);
		for (int l = 0; l < m; l++)
		{
			for (int r = 0; r < n; r++)
			{
				if (random.nextInt(100) >= thresh)
				{
					Edge e = new Edge(leftSide[l], rightSide[r]);
					leftSide[l].addEdge(e);
					rightSide[r].addEdge(e);
				}
			}
		}
		System.out.println("left side:" + Arrays.toString(leftSide));
		//System.out.println("right side"+Arrays.toString(rightSide));
	}
	
	private List<Vertex> findPath()
	{
		List<Vertex> res = null;
		
		
		//for (int i = 0; i < 100; i++)
		//{
		for (Vertex v : leftSide)
		{
			if (v.isMatched())
				continue;
			System.out.println("searching from " + v);
			res = search(v, 0, new ArrayList<Vertex>(Collections.singleton(v)));
			if (res != null)
			{
				
				System.out.println("got path: " + res);
				break;
			}
		}
		//}
		return res;
	}
	
	/**
	 * attempts to find and augmenting path from the given root (recursive function)
	 *
	 * @param root    Vertex to start search from
	 * @param state   must be given as 0 in first call
	 * @param history must be given as singleton of root on first call
	 * @return augmenting path in reverse order
	 */
	private List<Vertex> search(Vertex root, int state, List<Vertex> history)// true for looking for matched
	{
		List<Vertex> children = root.getChildren();
		List<Vertex> path;
		List<Vertex> tmpHistory;
		Vertex o;
		Edge e;
		switch (state)
		{
			case 0:
				//noinspection Duplicates
				for (Vertex v : children)
				{
					if (v.isMatched())
						continue;
					tmpHistory = new ArrayList<>(history);
					tmpHistory.add(v);
					return tmpHistory;
				}
				for (Vertex v : children)
				{
					if (!v.isMatched())
						continue;
					tmpHistory = new ArrayList<>(history);
					tmpHistory.add(v);
					path = search(v, 1, tmpHistory);
					return path;
				}
				break;
			case 1:
				e = root.getMatchedEdge();
				if( e == null )
					return null;
				o = e.getOther(root);
				
				tmpHistory = new ArrayList<>(history);
				tmpHistory.add(o);
				
				return search(o,2,tmpHistory);
				
			case 2:
				
				//noinspection Duplicates
				for (Vertex v : children)
				{
					if (v.isMatched())
						continue;
					tmpHistory = new ArrayList<>(history);
					tmpHistory.add(v);
					return tmpHistory;
				}
				for (Vertex v : children)
				{
					if (!v.isMatched() || history.contains(v))
						continue;
					tmpHistory = new ArrayList<>(history);
					tmpHistory.add(v);
					return search(v,1,tmpHistory);
				}
				
				break;
		}
		return null;
	}
	
	private void invertAugmentingPath(List<Vertex> v)
	{
		if (v == null || v.size() % 2 != 0)
		{
			System.err.println("inverting failed");
			return;
		}
		int len = v.size();
		System.out.println("inverting " + pathToString(v));
		v.get(0).setMatched(true);
		v.get(len - 1).setMatched(true);
		for (int i = 0; i < len - 1; i++)
		{
			Edge e = v.get(i).getEdgeConnectedTo(v.get(i + 1));
			e.setMatched(!e.isMatched());
		}
		System.out.println("inverted " + pathToString(v) + "\n");
	}
	
	public int[] getMatched()
	{
		List<Vertex> path;
		while ((path = findPath()) != null)
		{
			invertAugmentingPath(path);
		}
		int[] res = new int[m];
		Edge e;
		for (int i = 0; i < m; i++)
		{
			e = leftSide[i].getMatchedEdge();
			if (e != null)
				res[i] = e.getOther(leftSide[i]).getId() - m;
			else
				res[i] = -1;
		}
		
		return res;
	}
	
	private String pathToString(List<Vertex> v)
	{
		String s = "<";
		for (Vertex vv : v)
		{
			s += " " + vv.getId() + ":" + vv.isMatched() + ",";
		}
		s += ">";
		return s;
	}
	
	public void resetGraph()
	{
		for (Vertex v : leftSide)
		{
			if (v.isMatched())
				v.setMatched(false);
			for (Edge e : v.getEdges())
				if (e.isMatched())
					e.setMatched(false);
		}
	}
	
	public void connectEdge(int l, int r)
	{
		if (l >= m || r >= n)
			throw new IllegalArgumentException();
		Edge e = new Edge(leftSide[l], rightSide[r]);
		leftSide[l].addEdge(e);
		leftSide[r].addEdge(e);
	}
	
	public void removeEdge(int l, int r)
	{
		if (l >= m || r >= n)
			throw new IllegalArgumentException();
		Edge e = leftSide[l].getEdgeConnectedTo(rightSide[r]);
		if (e == null)
			return;
		leftSide[l].removeEdge(e);
		rightSide[r].removeEdge(e);
		e.setA(null);
		e.setB(null);
	}
	
	public int getM()
	{
		return m;
	}
	
	public int getN()
	{
		return n;
	}
	
	public int[][] getGraph()
	{
		int res[][] = new int[m][];
		
		List<Vertex> ch;
		
		for (int i = 0; i < m; i++)
		{
			ch = leftSide[i].getChildren();
			res[i] = new int[ch.size()];
			for (int j = 0; j < res[i].length; j++)
			{
				res[i][j] = ch.get(j).getId() - m;
			}
			
		}
		return res;
	}
	//	public static void main(String[] args)
//	{
//		BipartedGraph bp = new BipartedGraph(3, 3);
//		//bp.makeRandomGraph(0, 60);
//		bp.test();
//		//System.out.println(bp.pathToString(bp.findPath()));
//		System.out.println("res: "+bp.getMatched());
//		System.out.println("\n\n");
//		System.out.println("left side:"+Arrays.toString(bp.leftSide));
//		System.out.println("right side"+Arrays.toString(bp.rightSide));
//		
//	
//public void test()
//{
//	Edge e;
//	e = new Edge(leftSide[0], rightSide[0]);
//	leftSide[0].addEdge(e);
//	rightSide[0].addEdge(e);
//	
//	e = new Edge(leftSide[1], rightSide[0]);
//	leftSide[1].addEdge(e);
//	rightSide[0].addEdge(e);
//	
//	e = new Edge(leftSide[1], rightSide[1]);
//	leftSide[1].addEdge(e);
//	rightSide[1].addEdge(e);
//	
//	e = new Edge(leftSide[1], rightSide[2]);
//	leftSide[1].addEdge(e);
//	rightSide[2].addEdge(e);
//	
//	e = new Edge(leftSide[2], rightSide[1]);
//	leftSide[2].addEdge(e);
//	rightSide[1].addEdge(e);
//}
}
