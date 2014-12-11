package de.htw.fb4.imi.jumpup.user.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.entities.UserDetails;

/**
 * Controller class for {@link de.htw.fb4.imi.jumpup.user.entities.UserDetails}.
 * 
 * @author Marco Seidler
 * 
 */

@SuppressWarnings("cdi-ambiguous-name")
@Named(value = BeanNames.REGISTRATION_CONTROLLER)
@SessionScoped
public class UserDetailsController extends AbstractFacesController implements
        Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 39062452564614352L;

    /**
     * {@link UserDetails} for current session {@link User}
     */
    private UserDetails userDetails = new UserDetails();

    public UserDetails getUserDetails()
    {
        return userDetails;
    }

}
