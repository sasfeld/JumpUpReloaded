package de.htw.fb4.imi.jumpup.user.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.UserDAO;
import de.htw.fb4.imi.jumpup.user.details.UserDetailsMethod;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.entities.UserDetails;
import de.htw.fb4.imi.jumpup.util.Gender;
import de.htw.fb4.imi.jumpup.util.Languages;

/**
 * Controller class for {@link de.htw.fb4.imi.jumpup.user.entities.UserDetails}.
 * 
 * @author Marco Seidler
 * 
 */

@Named(value = BeanNames.PROFILE_VIEW_CONTROLLER)
@SessionScoped
public class ProfileViewController extends AbstractFacesController implements
        Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 39062452564614352L;

    @Inject
    protected UserDAO userDAO;
    @Inject
    protected UserDetailsMethod userDetailsMethod;

    protected UserDetails userDetails;

    protected Languages languages = Languages.GERMAN;
    protected Gender genders = Gender.MAN;
    protected String userID;

    public UserDetails getUserDetails()
    {

        if (userID != null & userDetails == null) {
            try {
                User currentUser = userDAO.loadById(new Long(userID));
                Application.log("UserDetailsContoller: current user "
                        + currentUser, LogType.DEBUG, getClass());
                userDetails = currentUser.getUserDetails();

            } catch (Exception e) {
                Application.log("Exception here: " + e.getLocalizedMessage(),
                        LogType.CRITICAL, getClass());
                throw e;
            }
        }
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails)
    {
        this.userDetails = userDetails;
    }

    /**
     * @return the languages
     */
    public Languages getLanguages()
    {
        return languages;
    }

    /**
     * @param languages
     *            the languages to set
     */
    public void setLanguages(Languages languages)
    {
        this.languages = languages;
    }

    /**
     * @return the genders
     */
    public Gender getGenders()
    {
        return genders;
    }

    /**
     * @param genders
     *            the genders to set
     */
    public void setGenders(Gender genders)
    {
        this.genders = genders;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

}
