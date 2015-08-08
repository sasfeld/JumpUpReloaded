/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import de.htw.fb4.imi.jumpup.util.math.CoordinateUtil;

/**
 * <p>Test of {@link Graph}</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 08.08.2015
 *
 */
public class GraphTest
{

    @Test
    public void testGraph()
    {
        Graph g = new Graph();
        
        Vertex a = new Vertex(CoordinateUtil.newCoordinatesBy("55.12345,49.12345"));
        Vertex b = new Vertex(CoordinateUtil.newCoordinatesBy("53.12345,47.12345"));
        
        // given two vertices
        g.addVertex(a);
        g.addVertex(b);
        
        // then they should be set in the graph
        assertEquals(2, g.getNumberOfVertices());
        
        // given an edge between two existing vertices in the graph
        Edge edgeA = new Edge(a, b, 10);
        g.addEdge(edgeA);        
        
        // graph getIncidentEdges should return correct edge        
        assertTrue(g.getIncidentEdgesOf(a).contains(edgeA));
    }

}
