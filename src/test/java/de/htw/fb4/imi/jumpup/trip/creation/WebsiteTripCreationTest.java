/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.creation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.htw.fb4.imi.jumpup.trip.entities.Trip;




/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 12.01.2015
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(WebsiteTripCreation.class)
public class WebsiteTripCreationTest
{
    @Test
    public void testAddTrip() throws Exception
    {
        Trip trip = givenAMockedTrip();
        WebsiteTripCreation creationEjb = givenAMockedWebsiteTripCreationEJB();
        
        whenAddTripIsCalled(creationEjb, trip);
        
        thenMakeSureThatCreationMailMethodsAreInvoked(creationEjb);
    }

    public void thenMakeSureThatCreationMailMethodsAreInvoked(WebsiteTripCreation creationEjb) throws Exception
    {
        PowerMockito.verifyPrivate(creationEjb).invoke("sendTripUpdatedMailToDriver");   
        PowerMockito.verifyPrivate(creationEjb).invoke("sendTripUpdatedMailToPassengers");   
    }

    public void whenAddTripIsCalled(WebsiteTripCreation creationEjb, Trip trip)
    {
       creationEjb.addTrip(trip);
    }

    public WebsiteTripCreation givenAMockedWebsiteTripCreationEJB() throws Exception
    {
        WebsiteTripCreation mockitoSpy = PowerMockito.spy(new WebsiteTripCreation());
        
        // Stub entity manager operations
        PowerMockito.when(mockitoSpy, "persistTrip").thenReturn(null);
        PowerMockito.when(mockitoSpy, "updateTrip").thenReturn(null);
        PowerMockito.when(mockitoSpy, "softDeleteTrip").thenReturn(null);        
        
        
        return mockitoSpy;
    }

    public Trip givenAMockedTrip()
    {
       return new Trip();
    }
  
}
