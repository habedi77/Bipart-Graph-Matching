package Biparted_Matching.Engine;

public class Edge
{
	private Vertex a,b;
	private boolean matched;
	
	public Edge(Vertex a, Vertex b)
	{
		this.a = a;
		this.b = b;
		matched =false;
	}
	
	@Override
	public String toString()
	{
		return "{"+a.getId()+", "+b.getId()+", "+matched+"}";
	}
	
	public boolean connectedTo(Vertex v)
	{
		return v.equals(a) || v.equals(b);
	}
	
	public Vertex getA()
	{
		return a;
	}
	
	public void setA(Vertex a)
	{
		this.a = a;
	}
	
	public Vertex getB()
	{
		return b;
	}
	
	public void setB(Vertex b)
	{
		this.b = b;
	}
	
	public boolean isMatched()
	{
		return matched;
	}
	
	public void setMatched(boolean matched)
	{
		this.matched = matched;
	}
	
	public Vertex getOther(Vertex o)
	{
		if( a.equals(o) )
			return b;
		else if( b.equals(o) )
			return a;
		return null;
	}
}
