/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.commons.lang.exception.ExceptionUtils;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.translate.Translatable;
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
    
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.query.TripQueryMethod#getOfferedTrips(de.htw.fb4.imi.jumpup.user.entities.User)
     */
    @Override
    public List<Trip> getOfferedTrips(final User user)
    {
        this.reset();
        EntityManager em = this.prepareEntityManager(user);        
       
        try {
            return em.createNamedQuery(Trip.NAME_QUERY_BY_USER, Trip.class)
                .setParameter("driver", user)
                .getResultList();
        } catch (Exception e) {
            this.messages.add("We could not load your trips. Please contact our customer care team.");
            Application.log("getOfferedTrips(): exception " + e.getMessage() 
                    + "\nStack trace: " + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
        }
        
        return null;
    }

    private void reset()
    {
        this.messages = new ArrayList<String>();
    }

    private EntityManager prepareEntityManager(User user)
    {
        // prepare for transaction
        EntityManager em = this.getFreshEntityManager();
        
        em.merge(user);    
        
        return em;
    }
   

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.query.TripQueryMethod#searchForTrips(de.htw.fb4.imi.jumpup.trip.query.TripSearchCriteria)
     */
    public TripQueryResults searchForTrips(TripSearchCriteria tripSearchModel)
    {
        List<Trip> matchedTrips = this.prepareCriteriaSearch(tripSearchModel);
        
        Application.log("got trips from HQL: " + matchedTrips, LogType.DEBUG, getClass());
        
        List<Trip> filteredTrips = this.triggerFilteringChain(tripSearchModel, matchedTrips);
        
        Application.log("filtered trips: " + filteredTrips, LogType.DEBUG, getClass());
        
        return this.toQueryResultList(filteredTrips);
    }

    private TripQueryResults toQueryResultList(
            List<Trip> filteredTrips)
    {
        List<SingleTripQueryResult> list = new ArrayList<SingleTripQueryResult>();
        
        for (Trip filteredTrip : filteredTrips) {
            list.add(this.toSingleTripQueryResult(filteredTrip));
        }        
        
        return queryResultFactory.newTripQueryResults(list);
    }

    private SingleTripQueryResult toSingleTripQueryResult(Trip filteredTrip)
    {
        return queryResultFactory.newSingleTripQueryResult(filteredTrip);
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
     * Search {@link Trip} instances by basic criteria.
     * @param tripSearchModel
     * @return
     */
    private List<Trip> prepareCriteriaSearch(TripSearchCriteria tripSearchModel)
    {
        EntityManager em = this.getFreshEntityManager();
              
        Timestamp dateFromTimeStamp = this.convertToTimestamp(tripSearchModel.getDateFrom());
        Timestamp dateToTimeStamp = this.convertToTimestamp(tripSearchModel.getDateTo());
        Float priceFrom = tripSearchModel.getPriceFrom();
        Float priceTo = tripSearchModel.getPriceTo();
        
        try {
            // search within date and price range, exclude trips offered by currently logged-in user
            
            return em.createNamedQuery(Trip.NAME_QUERY_ALL, Trip.class) .getResultList();
//            return em.createNamedQuery(Trip.NAME_CRITERIA_QUERY, Trip.class)
//                .setParameter("dateFrom", null)
//                .setParameter("dateTo", null)
//                .setParameter("priceFrom", null)
//                .setParameter("priceTo", null)
//                .setParameter("passenger", null)
//                .getResultList();
        } catch (Exception e) {
            this.messages.add("Error while searching for trips by the given criteria");
            Application.log("prepareCriteriaSearch(): exception " + e.getMessage() + "\nstack:" 
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());               
        }
        
        return null;
    }

    /**
     * Get currently logged-in user.
     * @return
     */
    private User getCurrentlyLoggedInUser()
    {
        return loginController.getLoginModel().getCurrentUser();
    }

    /**
     * Convert from date to {@link Timestamp}.
     * @param date
     * @return
     */
    private Timestamp convertToTimestamp(Date date)
    {
        if (null == date) {
            return null;
        }
        
        return new Timestamp(date.getTime());
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
