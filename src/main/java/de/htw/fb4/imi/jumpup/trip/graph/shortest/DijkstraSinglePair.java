/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.graph.shortest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.htw.fb4.imi.jumpup.trip.graph.Edge;
import de.htw.fb4.imi.jumpup.trip.graph.Graph;
import de.htw.fb4.imi.jumpup.trip.graph.Path;
import de.htw.fb4.imi.jumpup.trip.graph.Vertex;
import de.htw.fb4.imi.jumpup.trip.util.VertexPriorityQueue;

/**
 * <p>This modification of the Dijkstra algorithm solves the Single Pair Shortest Path problem.</p>
 * 
 * <p>Dijkstra was designed to solve the Single Source Shortest Path problem. <br />
 * This modification marks the destination node as soon as it reached by the algorithm.<br />
 * Then, a {@link Path} instance will be built and returned as result.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 09.08.2015
 *
 */
public class DijkstraSinglePair implements Routable
{
    private Graph graph;
    private final HashMap<Vertex, Double> dist = new HashMap<Vertex, Double>();
    
    /**
     * Map of predecessors: key is the successor of value. 
     */
    private final HashMap<Vertex, Vertex> pred = new HashMap<>();
    private final VertexPriorityQueue priorityQueue;
    
    public DijkstraSinglePair()
    {
        this.priorityQueue = new VertexPriorityQueue(this.dist);
    }
    
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.graph.shortest.Routable#findShortestPath(de.htw.fb4.imi.jumpup.trip.graph.Graph, de.htw.fb4.imi.jumpup.trip.graph.Vertex, de.htw.fb4.imi.jumpup.trip.graph.Vertex)
     */
    @Override
    public Path findShortestPath(Graph graph, Vertex origin, Vertex destination) throws PathNotFoundException
    {
        this.prepare(graph);
        this.init(origin);
        
        this.executeDijkstra();
        
        return this.buildPathFromPredecessorMap(origin, destination);
    }   
   
    private void prepare(Graph graph)
    {
        this.dist.clear();
        this.pred.clear();
        
        this.graph = graph;
    }
    
    /**
     * Set every distance to int.max and sets the start vertex by setting
     * distance to 0
     * 
     * @throws IllegalArgumentException
     *             if the graph contains negative weights
     * 
     * @param start
     */
    private void init(Vertex start) throws IllegalArgumentException {       
        checkForNegativeWeights();

        for (Vertex v : graph.getVertices()) {
            dist.put(v, Double.MAX_VALUE);

        }
        
        // sets start Vertex by setting distance to 0
        dist.put(start, 0.0);
    }

    /**
     * Dijkstra doesn't allow negative edge weights so throw an exception if one was found.
     * @throws IllegalArgumentException
     *             if the graph contains negative weights
     */
    private void checkForNegativeWeights()
    {
        for (Edge e : graph.getEdges()) {
            if (e.getWeight() < 0) {
                throw new IllegalArgumentException(
                        "graph contains negative weights!");
            }
        }
    }
    
    private void executeDijkstra()
    {
        // determine minimum distance vertex and add it to the path.
        while (!this.priorityQueue.isEmpty()) {
            Vertex vertex = this.priorityQueue.extractMin();

            for (Edge e : graph.getIncidentEdgesOf(vertex)) {
                relax(vertex, e.getVertexB(), e.getWeight(), dist, pred);
            }
        }        
    }

    /**
     * Executes the relax algorithm. Check if the route to v is shorter using
     * the edge from u to v. If so, the new distance is set and the predecessor
     * is set new.
     * 
     * @param vertexA
     * @param vertexB
     * @param weight
     */
    private void relax(Vertex vertexA, Vertex vertexB, double weight,
            HashMap<Vertex, Double> distances,
            HashMap<Vertex, Vertex> predecessors) {
        // we first need to check if v and u are MAXDOUBLE, because x +
        // MAXDOUBLE < MAXDOUBLE !!!
        if (distances.get(vertexB) == Integer.MAX_VALUE
                && distances.get(vertexA) == Integer.MAX_VALUE) {
            // ignore
        } else if (distances.get(vertexB) > distances.get(vertexA) + weight) {
            Double newDist = distances.get(vertexA) + weight;
 
            distances.put(vertexB, newDist);
            predecessors.put(vertexB, vertexA);
        }
    }
    
    private Path buildPathFromPredecessorMap(Vertex origin, Vertex destination) throws PathNotFoundException
    {
        if (!this.pred.containsKey(destination)) {
            throw new PathNotFoundException(origin, destination);
        }
        
        Set<Vertex> verticesOnPath = new HashSet<Vertex>();
        Vertex currentVertex = destination;
        
        while (currentVertex != origin) {
            // go back from destination vertex until origin is reached
            verticesOnPath.add(currentVertex);
            
            currentVertex = this.pred.get(currentVertex);
        }        
        
        return this.newPathFromVertexSet(verticesOnPath);
    }

    private Path newPathFromVertexSet(Set<Vertex> verticesOnPath)
    {
        return new Path(this.graph, verticesOnPath);
    }

}
