import java.util.HashSet;
import java.util.Set;
import java.util.*;

/**
 * 
 */

/**
 * @author Mamadou
 *
 */


public /* abstract */ class Graph /* implements GraphInterface */ {
	protected Set<Integer> nodes;
	protected Set<Edge> edges;
	
	protected Boolean directed;
	protected Boolean weighted;
	
	protected Integer maxflow;
	
	public Graph()
	{
		this(0);
	}
	
	public Graph(Integer n)
	{
		if( n < 0 )
		{
			throw new IllegalArgumentException("number of nodes `n` can not be negative");
		}
		
		this.nodes = new HashSet<Integer>();
		for( int i = 0; i < n; ++i )
		{
			this.nodes.add((i + 1));
		}
		
		this.edges = new HashSet<Edge>();
		
		this.directed = this.weighted = false;
		
		this.maxflow = 0;
	}
	
	public Graph(Graph g)
	{
		this.directed = g.directed;
		this.weighted = g.weighted;
		this.nodes = g.nodes();
		this.edges = g.edges();
		this.maxflow = g.maxflow;
	}
	
	public Set<Integer> nodes()
	{
		Set<Integer> copy = new HashSet<Integer>();
		Iterator<Integer> iter = this.nodes.iterator();
		
		while( iter.hasNext() )
		{
			Integer i = Integer.valueOf(iter.next());
			copy.add(i);
		}
		
		return copy;
	}

	public Set<Edge> edges()
	{
		Set<Edge> copy = new HashSet<Edge>();
		for ( Edge e : edges )
		{
			copy.add(new Edge(e.source(), e.destination(), e.flow(), e.capacity()));
		}
		return copy;
	}

	public Graph addNode(Integer node)
	{
		nodes.add(node);
		return this;
	}

	public Graph removeNode(Integer node)
	{
		nodes.remove(node);
		return this;
	}

	public Graph addEdge(Edge e)
	{
		edges.add(e);
		return this;
	}

	public Graph addEdge(Integer nodeU, Integer nodeV)
	{
		edges.add(new Edge(nodeU, nodeV));
		return this;
	}

	public Graph addEdge(Integer nodeU, Integer nodeV, Integer capacity)
	{
		edges.add(new Edge(nodeU, nodeV, capacity));
		return this;
	}

	public Graph addEdge(Integer nodeU, Integer nodeV, Integer flow, Integer capacity)
	{
		edges.add(new Edge(nodeU, nodeV, flow, capacity));
		return this;
	}
	
	public Graph removeEdge(Edge e)
	{
		edges.remove(e);
		return this;
	}
	
	public Graph removeEdge(Integer nodeU, Integer nodeV)
	{
		for ( Edge e : edges )
			if ( e.source() == nodeU && e.destination() == nodeV )
			{
				edges.remove(e);
			}
		return this;
	}

	public Iterator<Integer> nodeIterator()
	{
		return nodes.iterator();
	}
		
	public Iterator<Edge> edgeIterator()
	{
		return edges.iterator();
	}
	
	public Edge getEdge(Integer nodeU, Integer nodeV)
	{
		for ( Edge e : edges )
			if ( e.source() == nodeU && e.destination() == nodeV )
				return e;
		return null;
	}

	public Boolean isDirected()
	{
		return this.directed;
	}
	
	public Boolean isWeighted()
	{
		return this.weighted;
	}
	
	public Integer maxflow()
	{
		return this.maxflow;
	}

}
