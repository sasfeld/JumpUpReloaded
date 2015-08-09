/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.graph;

/**
 * <p>An edge connects two {@link Vertex} instances.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 08.08.2015
 *
 */
public class Edge
{
    protected final Vertex vertexA;
    protected final Vertex vertexB;
    protected final double weight;
    
    public Edge(Vertex vertexA, Vertex vertexB, double weight) 
    {
        if (null == vertexA || null == vertexB) {
            throw new NullPointerException("Null values are not allowed for edge vertices!");
        }
         
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.weight = weight;
    }
    
    public Vertex getVertexA()    
    {
        return this.vertexA;
    }
    
    public Vertex getVertexB()
    {
        return this.vertexB;
    }
    
    public double getWeight() 
    {
        return this.weight;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vertexA == null) ? 0 : vertexA.hashCode());
        result = prime * result + ((vertexB == null) ? 0 : vertexB.hashCode());
        long temp;
        temp = Double.doubleToLongBits(weight);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edge other = (Edge) obj;
        if (vertexA == null) {
            if (other.vertexA != null)
                return false;
        } else if (!vertexA.equals(other.vertexA))
            return false;
        if (vertexB == null) {
            if (other.vertexB != null)
                return false;
        } else if (!vertexB.equals(other.vertexB))
            return false;
        if (Double.doubleToLongBits(weight) != Double
                .doubleToLongBits(other.weight))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Edge [vertexA=");
        builder.append(vertexA);
        builder.append(", vertexB=");
        builder.append(vertexB);
        builder.append(", weight=");
        builder.append(weight);
        builder.append("]");
        return builder.toString();
    }   
    
}
