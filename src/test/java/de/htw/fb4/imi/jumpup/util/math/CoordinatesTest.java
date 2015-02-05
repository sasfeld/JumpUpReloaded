/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util.math;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * <p>Test of {@link Coordinates} model.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.01.2015
 *
 */
@RunWith(Parameterized.class)
public class CoordinatesTest
{
    private static final float DELTA = 0.1f;
    
    protected float latitudeDegrees;
    protected double expectedLatitudeRadiance;
    protected float longitudeDegrees;
    protected double expectedLongitudeRadiance;
    
    protected Coordinates coordinates;
    
    public CoordinatesTest(float latitudeDegrees, float longitudeDegrees,
            double expectedLatitudeRadiance, double expectedLongitudeRadiance)
    {
        this.latitudeDegrees = latitudeDegrees;
        this.longitudeDegrees = longitudeDegrees;
        
        this.expectedLatitudeRadiance = expectedLatitudeRadiance;
        this.expectedLongitudeRadiance = expectedLongitudeRadiance;
        
        this.coordinates = new Coordinates(latitudeDegrees, longitudeDegrees);
    }

    @Test
    public void testGetLatitudeRadians()
    {
       assertEquals(this.expectedLatitudeRadiance, this.coordinates.getLatitudeRadians(), DELTA);
    }
    
    @Test
    public void testGetLongitudeRadians()
    {
       assertEquals(this.expectedLongitudeRadiance, this.coordinates.getLongitudeRadians(), DELTA);
    }
    
    @Parameters
    public static Iterable<Object[]> data() 
    {
        return Arrays.asList(
                new Object[][] {
                        // test dataset 1: Berlin
                        {55.4123f, 9.1234f, 0.9671270811d, 0.15923336898d},
                        // test dataset 2: Sydney
                        {33f, 151f, 0.57595865316d, 2.6354471705d},
                        // test dataset 3: New York
                        {40.43f, 74.9f, 0.70563661658d, 1.3072516097d},         
                }
                );
    }

}
