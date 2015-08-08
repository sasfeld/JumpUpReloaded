/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.util;

import java.util.ArrayList;
import java.util.HashMap;

import de.htw.fb4.imi.jumpup.trip.graph.Vertex;

/**
 * <p>Implementation of a priority queue for the shortest path problem.</p>
 * 
 * Basically, a vertex priority queue takes a hash map of Vertex -> weight mapping and will return the {@link Vertex} with the lowest weight yet.
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 08.08.2015
 *
 */
public class VertexPriorityQueue
{
    /**
     * Reference to the minW hash map which will be changed by the prim
     * algorithm.
     */
    private final HashMap< Vertex, Integer > minW;
    /**
     * Contains the already extracted vertices. Those will be ignored in future
     * extracts.
     */
    private final ArrayList< Vertex > removedVertices;

    /**
     * Initialize a new priority queue.
     * 
     * @param minW
     *            the {@link HashMap} which contains the weights.
     */
    public VertexPriorityQueue( HashMap< Vertex, Integer > minW ) {
        this.minW = minW;

        this.removedVertices = new ArrayList< Vertex >();
    }

    /**
     * Extract the minimum {@link Vertex} (it's removed in the queue).
     * 
     * @return the {@link Vertex} with a minimum weight.
     */
    public Vertex extractMin() {
        Vertex ret = null;
        int i = 0;
        for( Vertex v : this.minW.keySet() ) {
            if( !this.removedVertices.contains( v ) ) { // v wasn't extracted
                                                        // yet
                if( i == 0 ) {
                    ret = v;
                } else {
                    if( this.minW.get( v ) < this.minW.get( ret ) ) {
                        ret = v;
                    }
                }
                i++;
            }
        }
        this.removedVertices.add( ret );
        return ret;
    }

    /**
     * Check if a priority queue is empty.
     * 
     * @return
     */
    public boolean isEmpty() {
        if( this.removedVertices.size() == this.minW.size() ) {
            return true;
        }
        return false;
    }

    /**
     * Check if the priority queue contains a {@link Vertex}.
     * 
     * @param v
     * @return
     */
    public boolean contains( Vertex v ) {
        return !this.removedVertices.contains( v );
    }
}
