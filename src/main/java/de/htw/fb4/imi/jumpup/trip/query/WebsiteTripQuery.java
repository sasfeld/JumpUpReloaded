/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.query;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.translate.Translatable;
import de.htw.fb4.imi.jumpup.trip.TripDAO;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.query.filter.TripSearchFilterChain;
import de.htw.fb4.imi.jumpup.trip.restservice.QueryResultFactory;
import de.htw.fb4.imi.jumpup.trip.restservice.model.SingleTripQueryResult;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripQueryResults;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripSearchCriteria;
import de.htw.fb4.imi.jumpup.user.controllers.Login;
import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 19.01.2015
 *
 */
@Stateless(name = BeanNames.WEBSITE_TRIP_QUERY)
public class WebsiteTripQuery implements TripQueryMethod
{
    @PersistenceUnit(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManagerFactory entityManagerFactory;
    
    @Inject
    protected TripSearchFilterChain tripSearchFilterChain;
    
    @Inject
    protected Login loginController;    
    
    @Inject
    protected Translatable translator;
    
    @Inject
    protected QueryResultFactory queryResultFactory;
    
    @Inject
    protected TripDAO tripDao;
    
    protected List<String> messages;

    /**
     * Create fresh entity manager.
     * @return
     */
    protected EntityManager getFreshEntityManager()
    {
        EntityManager em = this.entityManagerFactory.createEntityManager();

        return em;
    }    
    
    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#hasError()
     */
    public boolean hasError()
    {
        return !this.messages.isEmpty();
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getErrors()
     */
    public String[] getErrors()
    {
        return this.messages.toArray(new String[this.messages.size()]);
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getSingleErrorString()
     */
    public String getSingleErrorString()
    {
        // TODO Auto-generated method stub
        return null;
    }
   

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.query.TripQueryMethod#searchForTrips(de.htw.fb4.imi.jumpup.trip.query.TripSearchCriteria)
     */
    public TripQueryResults searchForTrips(TripSearchCriteria tripSearchModel)
    {
        // exclude passenger's own trip first
        tripSearchModel.setPassenger(this.getCurrentlyLoggedInUser());
        List<Trip> matchedTrips = this.tripDao.getByCriteria(tripSearchModel);
        
        Application.log("got trips from HQL: " + matchedTrips, LogType.DEBUG, getClass());
        
        List<Trip> filteredTrips = this.triggerFilteringChain(tripSearchModel, matchedTrips);
        
        Application.log("filtered trips: " + filteredTrips, LogType.DEBUG, getClass());
        
        return this.toQueryResultList(filteredTrips, tripSearchModel);
    }

    private TripQueryResults toQueryResultList(
            List<Trip> filteredTrips, TripSearchCriteria tripSearchModel)
    {
        List<SingleTripQueryResult> list = new ArrayList<SingleTripQueryResult>();
        
        for (Trip filteredTrip : filteredTrips) {
            list.add(this.toSingleTripQueryResult(filteredTrip, tripSearchModel));
        }        
        
        return queryResultFactory.newTripQueryResults(list);
    }

    private SingleTripQueryResult toSingleTripQueryResult(Trip filteredTrip, TripSearchCriteria tripSearchModel)
    {
        return queryResultFactory.newSingleTripQueryResult(filteredTrip, tripSearchModel);
    }

    private List<Trip> triggerFilteringChain(TripSearchCriteria tripSearchCriteria, List<Trip> matchedTrips)
    {
        if (null == matchedTrips) {
            return new ArrayList<Trip>();
        }
        
        this.tripSearchFilterChain.setCriteria(tripSearchCriteria);
        return this.tripSearchFilterChain.applyFilter(matchedTrips);        
    }

    /**
     * Get currently logged-in user.
     * @return
     */
    private User getCurrentlyLoggedInUser()
    {
        return loginController.getLoginModel().getCurrentUser();
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.query.TripQueryMethod#getNoTripsResult()
     */
    public TripQueryNoResults getNoTripsResult()
    {
        return queryResultFactory.newNoTripsResult(this.translator.translate("Sorry, we didn't find any trips that matched your criteria. Please try again and be less restrictive."));
    }
}
