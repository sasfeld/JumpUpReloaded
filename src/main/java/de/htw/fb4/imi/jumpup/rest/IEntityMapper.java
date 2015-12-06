/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest;

import java.util.Collection;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;

/**
 * <p>An entity mapper maps an {@link AbstractEntity} to a plain old java object that will be read or sent via web service actions.</p>
 * 
 * <p>The entity mapper is meant to be an adapter between the entity / persistence layer and the entity representation that is used in web servicess.</p>
 * 
 * <p>It might be that the entity scheme changes, but the REST model should stay stable for particular web service versions.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 02.12.2015
 *
 */
@Local
public interface IEntityMapper<WebServiceType, EntityType>
{

    /**
     * Map the given entity, make sure to return the plain old web service object that will be used to marshal and unmarshal web service requests / responses.
     * @param entity
     * @return
     * @throws IllegalArgumentException if given entity is not concrete expected one
     */
    WebServiceType mapEntity(EntityType entity);
    
    /**
     * Map the given web service model that was sent within a request body, for example.
     * 
     * @param webServiceModel
     * @return
     */
    EntityType mapWebServiceModel(WebServiceType webServiceModel);
    
    /**
     * Map the given collection of entities to a collection of web service models.
     * @param entityTypes
     * @return
     */
    Collection<WebServiceType> mapEntities(Collection<EntityType> entityTypes);
}
