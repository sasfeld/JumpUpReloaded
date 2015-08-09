/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.graph.shortest;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.trip.graph.Graph;
import de.htw.fb4.imi.jumpup.trip.graph.Path;
import de.htw.fb4.imi.jumpup.trip.graph.Vertex;

/**
 * <p>Interface for shortest path algorithms.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 09.08.2015
 *
 */
@Local
public interface Routable
{
    /**
     * Find the shortest path for the given origin and destination.
     * 
     * Implementations should solve the Single Pair Shortest Path Problem.
     * 
     * @param graph
     * @param origin
     * @param destination
     * @return
     * @throws PathNotFoundException if no path from origin to destination was found.
     */
    Path findShortestPath(Graph graph, Vertex origin, Vertex destination) throws PathNotFoundException;
}
