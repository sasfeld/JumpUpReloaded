/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.rest.model;

import java.util.ArrayList;
import java.util.Collection;

import de.htw.fb4.imi.jumpup.rest.IEntityMapper;
import de.htw.fb4.imi.jumpup.user.entity.User;
import de.htw.fb4.imi.jumpup.user.entity.UserDetails;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
public class UserEntityMapper implements IEntityMapper<UserWebServiceModel, User>
{

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.rest.IEntityMapper#mapEntity(de.htw.fb4.imi.jumpup.entities.AbstractEntity)
     */
    public UserWebServiceModel mapEntity(User entity)
    {
        UserWebServiceModel webServiceModel = new UserWebServiceModel();
        
        webServiceModel.setUsername(entity.getUsername());
        webServiceModel.seteMail(entity.geteMail());
        webServiceModel.setPrename(entity.getPrename());
        webServiceModel.setLastname(entity.getLastname());
        webServiceModel.setTown(entity.getTown());
        webServiceModel.setCountry(entity.getCountry());
        webServiceModel.setLocale(entity.getLocale());
        webServiceModel.setIsConfirmed(entity.getIsConfirmed());
        
        UserDetails userDetails = entity.getUserDetails();
        
        if (null != userDetails && userDetails.isFilled()) {
            webServiceModel.setDateOfBirth(userDetails.getDateOfBirth());
            webServiceModel.setPlaceOfBirth(userDetails.getPlaceOfBirth());
            webServiceModel.setGender(userDetails.getGender());
            webServiceModel.setMobileNumber(userDetails.getMobileNumber());
            webServiceModel.setSkype(userDetails.getSkype());
        }
        
        return webServiceModel;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.rest.IEntityMapper#mapWebServiceModel(de.htw.fb4.imi.jumpup.rest.AbstractRestModel)
     */
    public User mapWebServiceModel(UserWebServiceModel webServiceModel)
    {
        User entity = new User();
        
        entity.setUsername(webServiceModel.getUsername());
        entity.seteMail(webServiceModel.geteMail());
        entity.setPrename(webServiceModel.getPrename());
        entity.setLastname(webServiceModel.getLastname());
        entity.setTown(webServiceModel.getTown());
        entity.setCountry(webServiceModel.getCountry());
        entity.setLocale(webServiceModel.getLocale());
        entity.setIsConfirmed(webServiceModel.getIsConfirmed());
        
        UserDetails userDetails = entity.getUserDetails();
        
        if (null == userDetails) {
            userDetails = new UserDetails();
            entity.setUserDetails(userDetails);
        }
        
        userDetails.setDateOfBirth(webServiceModel.getDateOfBirth());
        userDetails.setPlaceOfBirth(webServiceModel.getPlaceOfBirth());
        userDetails.setGender(webServiceModel.getGender());
        userDetails.setMobileNumber(webServiceModel.getMobileNumber());
        userDetails.setSkype(webServiceModel.getSkype());
        
        return entity;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.rest.IEntityMapper#mapEntities(java.util.Collection)
     */
    public Collection<UserWebServiceModel> mapEntities(
            Collection<User> entityTypes)
    {
        Collection<UserWebServiceModel> userWebServiceCollection = new ArrayList<UserWebServiceModel>();
        
        for (User user : entityTypes) {
            userWebServiceCollection.add(this.mapEntity(user));
        }
        
        return userWebServiceCollection;
    }
}
