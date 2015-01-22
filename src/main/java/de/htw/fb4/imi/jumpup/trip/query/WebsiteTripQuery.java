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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.commons.lang.exception.ExceptionUtils;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.query.filter.TripFilter;
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
    
    @EJB(name = BeanNames.TRIP_SEARCH_FILTER_CHAIN)
    protected TripFilter tripSearchFilterChain;
    
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
    public List<Trip> searchForTrips(TripSearchCriteria tripSearchModel)
    {
        List<Trip> matchedTrips = this.prepareCriteriaSearch(tripSearchModel);
        
        List<Trip> filteredTrips = this.triggerFilteringChain(tripSearchModel, matchedTrips);
        
        return filteredTrips;
    }

    private List<Trip> triggerFilteringChain(TripSearchCriteria tripSearchCriteria, List<Trip> matchedTrips)
    {
        if (null == matchedTrips) {
            return new ArrayList<Trip>();
        }
        
        this.tripSearchFilterChain.setCriteria(tripSearchCriteria);
        return this.tripSearchFilterChain.applyFilter(matchedTrips);        
    }

    private List<Trip> prepareCriteriaSearch(TripSearchCriteria tripSearchModel)
    {
        EntityManager em = this.getFreshEntityManager();
        
        String startPoint = tripSearchModel.getStartPoint();        
        String endPoint = tripSearchModel.getEndPoint();        
        Float startLat = tripSearchModel.getLatStartPoint();        
        Float startLong = tripSearchModel.getLongStartPoint();       
        Float endLat = tripSearchModel.getLatEndPoint();   
        Float endLong = tripSearchModel.getLongEndPoint();       
        Timestamp dateFromTimeStamp = this.convertToTimestamp(tripSearchModel.getDateFrom());
        Timestamp dateToTimeStamp = this.convertToTimestamp(tripSearchModel.getDateTo());
        Float priceFrom = tripSearchModel.getPriceFrom();
        Float priceTo = tripSearchModel.getPriceTo();
        Integer maxDistance = tripSearchModel.getMaxDistance();
        
        try {
            return em.createNamedQuery(Trip.NAME_CRITERIA_QUERY, Trip.class)
                .setParameter("dateFrom", dateFromTimeStamp)
                .setParameter("dateTo", dateToTimeStamp)
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .getResultList();
        } catch (Exception e) {
            this.messages.add("Error while searching for trips by the given criteria");
            Application.log("prepareCriteriaSearch(): exception " + e.getMessage() + "\nstack:" 
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());               
        }
        
        return null;
    }

    private Timestamp convertToTimestamp(Date date)
    {
        if (null == date) {
            return null;
        }
        
        return new Timestamp(date.getTime());
    }

}
