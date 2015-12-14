package de.htw.fb4.imi.jumpup.user.details;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.user.entity.UserDetails;
import de.htw.fb4.imi.jumpup.util.ErrorPrintable;

/**
 * 
 * <p>
 * Interface for filling the {@link UserDetails} can trigger
 * {@link ErrorPrintable}.
 * </p>
 * 
 * @author <a href="mailto:m_seidler@hotmail.de">Marco Seidler</a>
 * @since 14.12.2014
 * 
 */
@Local
public interface UserDetailsMethod extends ErrorPrintable
{

    /**
     * Method checks the given {@link UserDetails} and can trigger
     * {@link ErrorPrintable}.
     * 
     * @param userDetails
     *            - the {@link UserDetails}
     */
    void sendUserDetails(UserDetails userDetails);

    /**
     * Take the user's avatar and store it in the {@link UserDetails} instance. 
     * @param userDetails
     */
    void uploadAvatar(UserDetails userDetails);

}
