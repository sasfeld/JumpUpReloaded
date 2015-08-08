/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>A graph consists of {@link Vertex} and {@link Edge} instances.</p>
 * 
 * You can add vertices and edges.
 * 
 * <ul>
 * <li>If you add an edge, always add the attached vertices to the graph first.</li>
 * <li>The edges will be stored in an adjacency list that each vertex has.</li>
 * <li>To get the neighbour / incident edges of a vertex, use getIncidentEdgesOf().</li>
 * </ul>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 08.08.2015
 *
 */
public class Graph
{
    protected final List<Vertex> vertices;
    protected final Map< Double, LinkedList<Edge>> adjacencyList;
    
    public Graph() {
        this.vertices = new ArrayList<Vertex>();
        this.adjacencyList = new HashMap<Double, LinkedList<Edge>>();
    }
    
    /**
     * Add a vertex, handle entry in adjacency list.
     * @param vertex
     */
    public void addVertex( Vertex vertex ) 
    {
        if (null == vertex) {
            throw new NullPointerException("Vertex must not be null.");
        }
        
        if (contains(vertex)) {
            throw new IllegalArgumentException("Vertex with ID " + vertex.getId() + " is already in list." );
        }
        
        this.vertices.add(vertex);        
        // add new record for vertex to adjacency list
        this.adjacencyList.put(vertex.getId(), this.newAdjacencyListEntry());
    }

    public boolean contains(Vertex vertex)
    {
        return this.adjacencyList.containsKey(vertex.getId());
    }

    private LinkedList<Edge> newAdjacencyListEntry()
    {
        return new LinkedList<Edge>();
    }
    
    public void addEdge( Edge edge ) 
    {
        if (null == edge) {
            throw new NullPointerException("edge must not be null.");
        }
        
        if (!this.bothVerticesExist(edge)) {
            throw new IllegalArgumentException("The graph doesn't contain the given edge's vertices.");
        }
        
        this.adjacencyList.get(edge.getVertexA().getId()).add(edge);
    }
    
    private boolean bothVerticesExist(Edge e) 
    {
        return this.contains(e.getVertexA())
                && this.contains(e.getVertexB());
    }
    
    public List<Edge> getEdges()
    {
        List<Edge> allEdges = new ArrayList<Edge>();
        
        for (LinkedList<Edge> edgeList : this.adjacencyList.values()) {
            for (Edge edge : edgeList) {
                allEdges.add(edge);
            }
        }
        
        return allEdges;
    }
    
    public Collection<Vertex> getVertices()
    {
       return this.vertices;
    }
    
    public int getNumberOfVertices()
    {
        return this.adjacencyList.keySet().size();
    }
    
    /**
     * Get all incident edges of the given {@link Vertex}.
     * 
     * Using this method, you can get the neighbours and the weight.
     * @param vertex
     * @return
     */
    public Collection<Edge> getIncidentEdgesOf( Vertex vertex )
    {
        if (!this.contains(vertex)) {
            throw new IllegalArgumentException("Vertex with ID " + vertex.getId() + " is not contained in graph." );
        }
        
        return adjacencyList.get(vertex.getId());
    }
}
