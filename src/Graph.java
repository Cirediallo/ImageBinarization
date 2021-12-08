import java.util.HashSet;
import java.util.Set;
import java.util.*;

public class Graph {
	protected Set<Integer> nodes;
	protected ArrayList<Set<Edge>> edges;	
	
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
			this.nodes.add((i));
		}
		
		this.edges = new ArrayList<Set<Edge>>(nodes.size());
		for ( Integer i : nodes )
		{
			edges.add(new HashSet<Edge>());
		}		
	}
	
	public Graph(Graph g)
	{
		this.nodes = g.nodes();
		this.edges = g.edges();
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

	public ArrayList<Set<Edge>> edges()
	{
		ArrayList<Set<Edge>> copyList = new ArrayList<Set<Edge>>(edges.size());
		for ( Set<Edge> s : edges )
		{
			Set<Edge> copySet = new HashSet<Edge>(s.size());
			for ( Edge e : s )
				copySet.add(new Edge(e.destination(), e.flow(), e.capacity()));
			copyList.add(copySet);
		}
		return copyList;
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

	public Graph addEdge(Integer nodeU, Integer nodeV)
	{
		edges.get(nodeU).add(new Edge(nodeV));
		return this;
	}

	public Graph addEdge(Integer nodeU, Integer nodeV, Integer capacity)
	{
		edges.get(nodeU).add(new Edge(nodeV, capacity));
		return this;
	}

	public Graph addEdge(Integer nodeU, Integer nodeV, Integer flow, Integer capacity)
	{
		edges.get(nodeU).add(new Edge(nodeV, flow, capacity));
		return this;
	}
	
	public Graph removeEdge(Integer nodeU, Integer nodeV)
	{
		Set<Edge> toRemove = new HashSet<Edge>(1);
		for ( Edge e : edges.get(nodeU) )
			if ( e.destination().intValue() == nodeV.intValue() )
			{
				toRemove.add(e);
			}
		edges.get(nodeU).removeAll(toRemove);
		return this;
	}

	public Iterator<Integer> nodeIterator()
	{
		return nodes.iterator();
	}
	
	public Edge getEdge(Integer nodeU, Integer nodeV)
	{
		for ( Edge e : edges.get(nodeU) )
			if ( e.destination().intValue() == nodeV.intValue() )
				return e;
		return null;
	}
}
