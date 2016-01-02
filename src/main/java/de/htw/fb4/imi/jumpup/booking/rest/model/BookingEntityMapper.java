/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking.rest.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import de.htw.fb4.imi.jumpup.booking.entity.Booking;
import de.htw.fb4.imi.jumpup.rest.IEntityMapper;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.trip.TripDAO;
import de.htw.fb4.imi.jumpup.validation.ValidationException;

/**
 * <p>Entity mapper between {@link Booking} and {@link BookingWebServiceModel}</p>.
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 02.01.2016
 *
 */
@Named(value = BeanNames.BOOKING_ENTITY_MAPPER)
@RequestScoped
public class BookingEntityMapper implements IEntityMapper<BookingWebServiceModel, Booking>
{
    @Inject
    private TripDAO tripDAO;
    
    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.rest.IEntityMapper#mapEntity(de.htw.fb4.imi.jumpup.entity.AbstractEntity)
     */
    public BookingWebServiceModel mapEntity(Booking entity)
    {
        BookingWebServiceModel booking = new BookingWebServiceModel();
        booking.setStartLatitude(entity.getStartLatitude());
        booking.setStartLongitude(entity.getStartLongitude());
        booking.setEndLatitude(entity.getEndLatitude());
        booking.setEndLongitude(entity.getEndLongitude());
        booking.setConfirmationDateTime(entity.getConfirmationDateTime());
        booking.setCancellationDateTime(entity.getCancellationDateTime());
        booking.setActorOnLastChange(entity.getActorOnLastChange());
        booking.setTripId(entity.getTripIdentity());
        
        return booking;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.rest.IEntityMapper#mapWebServiceModel(de.htw.fb4.imi.jumpup.rest.response.model.AbstractRestModel)
     */
    public Booking mapWebServiceModel(BookingWebServiceModel webServiceModel) throws ValidationException
    {
        Booking booking = new Booking();
        booking.setStartLatitude(webServiceModel.getStartLatitude());
        booking.setStartLongitude(webServiceModel.getStartLongitude());
        booking.setEndLatitude(webServiceModel.getEndLatitude());
        booking.setEndLongitude(webServiceModel.getEndLongitude());
        booking.setConfirmationDateTime(webServiceModel.getConfirmationDateTime());
        booking.setCancellationDateTime(webServiceModel.getCancellationDateTime());
        booking.setActorOnLastChange(webServiceModel.getActorOnLastChange());
        
        this.validateTrip(webServiceModel.getTripId());
        booking.setTripIdentity(webServiceModel.getTripId());
        
        return booking;
    }

    private void validateTrip(Long tripId) throws ValidationException
    {
        try { 
            this.tripDAO.getTripByID(tripId);        
        } catch (EJBException e) {
            throw new ValidationException("tripId", "The given trip couldn't be found.");
        }
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.rest.IEntityMapper#mapEntities(java.util.Collection)
     */
    public Collection<BookingWebServiceModel> mapEntities(Collection<Booking> entityTypes)
    {
        Collection<BookingWebServiceModel> bookings = new ArrayList<>();
        
        for (Booking bookingEntity : entityTypes) {
            bookings.add(this.mapEntity(bookingEntity));
        }
        
        return bookings;
    }
    
    
}
