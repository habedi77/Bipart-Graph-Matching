package Biparted_Matching.Engine;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex>
{
	private int id;
	private boolean matched;
	private List<Edge> edges;
	
	
	public Vertex(int id)
	{
		this.id = id;
		matched = false;
		edges = new ArrayList<>();
	}
	
	public void addEdge(Edge e)
	{
		if (e.connectedTo(this))
			edges.add(e);
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public boolean isMatched()
	{
		return matched;
	}
	
	public void setMatched(boolean matched)
	{
		this.matched = matched;
	}
	
	@Override
	public int compareTo(Vertex o)
	{
		return Integer.compare(id, o.id);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Vertex)
			return ((Vertex) obj).id == id;
		return false;
	}
	
	@Override
	public String toString()
	{
		return "{" + id + ", " + matched + ", " + edges + "}";
	}
	
	public List<Edge> getEdges()
	{
		return edges;
	}
	
	public List<Vertex> getChildren()
	{
		List<Vertex> res = new ArrayList<>(edges.size());
		for (Edge e : edges)
		{
			res.add(e.getOther(this));
		}
		return res;
	}
	
	public Edge getEdgeConnectedTo(Vertex o)
	{
		
		for(Edge e:edges)
		{
			if(e.connectedTo(o))
				return e;
		}
		return null;
	}
	
	public void removeEdge(Edge e)
	{
		edges.remove(e);
	}
	
	public Edge getMatchedEdge()
	{
		if(!matched)
			return null;
		for (Edge e : edges) 
		{
		    if(e.isMatched())
		    	return e;
		}
		return null;
	}
}
