package de.htw.fb4.imi.jumpup.user.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.navigation.NavigationOutcomes;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.entities.UserDetails;
import de.htw.fb4.imi.jumpup.user.registration.UserDetailsMethod;
import de.htw.fb4.imi.jumpup.util.Gender;
import de.htw.fb4.imi.jumpup.util.Languages;

/**
 * Controller class for {@link de.htw.fb4.imi.jumpup.user.entities.UserDetails}.
 * 
 * @author Marco Seidler
 * 
 */

@Named(value = BeanNames.USER_DETAILS_CONTROLLER)
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

    @Inject
    protected UserDetailsMethod userDetailsMethod;

    protected Languages languages = Languages.GERMAN;

    protected Gender genders = Gender.MAN;

    public UserDetails getUserDetails()
    {
        return userDetails;
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
        Application.log("In getGenders() " + genders.toString(), LogType.DEBUG,
                getClass());
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

    /**
     * Method tries to add {@link UserDetails} and displays errors if something
     * fails.
     * 
     * @return the view for continuing
     *         {@link NavigationOutcomes.TO_USER_PROFILE}
     */
    public String editProfile()
    {
        try {
            Application.log("UserDetailsContoller: try to edit Profile",
                    LogType.DEBUG, getClass());
            userDetailsMethod.sendUserDetails(getUserDetails());
            if (userDetailsMethod.hasError()) {
                for (String error : userDetailsMethod.getErrors()) {
                    this.addDisplayErrorMessage(error);
                }
            }

        } catch (Exception e) {
            Application.log("UserDetailsController: " + e.getMessage(),
                    LogType.ERROR, getClass());
            this.addDisplayErrorMessage("Could not change your details.");
        }

        return NavigationOutcomes.TO_USER_PROFILE;
    }
}
