/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.query.filter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.query.TripSearchCriteria;

/**
 * <p>Test of {@link NearbyTripsFilter} to find passenger's trips that are near the driver's route.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.01.2015
 *
 */
@RunWith(Parameterized.class)
public class NearbyTripsFilterTest
{    
    private List<Trip> givenOriginalTripList;
    private TripSearchCriteria givenTripSearchCriteria;
    private List<Trip> expectedTripList;
    protected static NearbyTripsFilter TRIP_FILTER;
    

    public NearbyTripsFilterTest(List<Trip> givenOriginalTripList,  TripSearchCriteria givenTripSearchCriteria, List<Trip> expectedTripList)
    {
        this.givenOriginalTripList = givenOriginalTripList;
        this.givenTripSearchCriteria = givenTripSearchCriteria;
        this.expectedTripList = expectedTripList;
    }
    
    @BeforeClass
    public static void initializeTests()
    {
        TRIP_FILTER = new NearbyTripsFilter();
    }
    
    @Test
    public void testFilterNearby()
    {
        TRIP_FILTER.setCriteria(this.givenTripSearchCriteria);
        List<Trip> resultingTrips = TRIP_FILTER.applyFilter(this.givenOriginalTripList);
        
        assertArrayEquals(expectedTripList.toArray(new Trip[expectedTripList.size()]),
                resultingTrips.toArray(new Trip[resultingTrips.size()]) );
    }
    
    @Parameters
    public static Iterable<Object[]> data() 
    {
        return Arrays.asList(
                new Object[][] {
                        {getOriginalTripList1(), getTripSearchCriteria1(), getExpectedTripList1()}     
                });
    }

    private static List<Trip> getExpectedTripList1()
    {
        List<Trip> tripList = new ArrayList<Trip>();
        
        tripList.add(FilterTestHelper.getTripFromBerlinToLeipzig());
        
        return tripList;
    }

    private static TripSearchCriteria getTripSearchCriteria1()
    {
        // max distance to trips = 10km
        return FilterTestHelper.getTripSearchFromBerlinToLeipzig(10);
    }

    private static List<Trip>  getOriginalTripList1()
    {
        List<Trip> tripList = new ArrayList<Trip>();
        
        tripList.add(FilterTestHelper.getTripFromBerlinToLeipzig());
        tripList.add(FilterTestHelper.getTripFromLeipzigToErfurt());
        tripList.add(FilterTestHelper.getTripFromWeimarToTrier());
        
        return tripList;
    }

}
