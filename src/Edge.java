/**
 * 
 */

/**
 * @author Mamadou
 *
 */
public class Edge {
	private Integer destination;
	private Integer flow, capacity;
	
	public Edge(Integer nodeV)
	{
		this.destination = nodeV;
	}
	
	public Edge(Integer nodeV, Integer capacity)
	{
		this(nodeV, 0, capacity);
	}
	
	public Edge(Integer nodeV, Integer flow, Integer capacity)
	{
		this(nodeV);
		if( flow > capacity )
		{
			throw new IllegalArgumentException("Flow can not exceed capacity");
		}
		this.flow = flow;
		this.capacity = capacity;
	}
	
	public Edge(Edge e)
	{
		this.destination = e.destination;
		this.flow = e.flow;
		this.capacity = e.capacity;
	}

	public Integer destination()
	{
		return this.destination;
	}
	
	public Integer capacity()
	{
		return this.capacity;
	}
	
	public void capacity(Integer w)
	{
		this.capacity = w;
	}

	public Integer flow()
	{
		return this.flow;
	}
	
	public void flow(Integer f)
	{
		if( f > this.capacity() )
		{
			throw new IllegalArgumentException("Flow can not exceed capacity: " + f + " > " + this.capacity);
		}
		
		this.flow = f;
	}
	
	/*
	 * (source, destination, flow, capacity)
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(edge to " + destination);
		if( this.flow != null )
		{
			sb.append(", flow: " + this.flow);
		}
		if( this.capacity != null )
		{
			sb.append(", capacity: " + this.capacity);
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	/*
	 * useful so as to not add the same edge twice even if the edge has different weights
	 */
	@Override
	public int hashCode()
	{
		int result = 0;
		
		result = this.destination.hashCode();
		
		return result;
	}
}
