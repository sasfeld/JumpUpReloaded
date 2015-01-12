/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.controllers;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.util.FacesFacade;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public class AbstractFacesController
{
    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManagerFactory entityManagerFactory;
    
    @Inject
    protected FacesFacade facesFacade;
    
    protected EntityManager getFreshEntityManager()
    {
        return this.entityManagerFactory.createEntityManager();
    }

    protected void addDisplayInfoMessage(final String infoMessage)
    {
        facesFacade.addInfoMessage(infoMessage);
    }
    
    protected void addDisplayErrorMessage(final String errorMessage)
    {
        facesFacade.addErrorMessage(errorMessage);
    }
}
