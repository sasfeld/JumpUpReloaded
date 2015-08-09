/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.restservice.model;

import java.util.Set;

import de.htw.fb4.imi.jumpup.trip.graph.Vertex;

/**
 * <p>Record returned via AJAX on overlapping partial trip query results.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 09.08.2015
 *
 */
public class OverlappingPartialTripQueryResult extends TripQueryResults
{
    public class Intersections {
        protected Set<Vertex> intersectionVertices;

        /**
         * @return the intersectionVertices
         */
        public Set<Vertex> getIntersectionVertices()
        {
            return intersectionVertices;
        }

        /**
         * @param intersectionVertices the intersectionVertices to set
         */
        public void setIntersectionVertices(Set<Vertex> intersectionVertices)
        {
            this.intersectionVertices = intersectionVertices;
        }       
        
    }
    
    protected Intersections intersections;
    protected Type type = Type.MULTIPLE_PARTIAL_TRIP_RESULT;

    /**
     * @return the intersections
     */
    public Intersections getIntersections()
    {
        return intersections;
    }

    /**
     * @param intersections the intersections to set
     */
    public void setIntersections(Intersections intersections)
    {
        this.intersections = intersections;
    }    
}
