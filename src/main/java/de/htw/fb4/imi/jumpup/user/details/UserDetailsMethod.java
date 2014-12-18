package de.htw.fb4.imi.jumpup.user.details;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.user.entities.UserDetails;
import de.htw.fb4.imi.jumpup.util.ErrorPrintable;

/**
 * 
 * <p>
 * Interface for filling the {@link UserDetails} can tigger
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
     * Methode checks the given {@link UserDetails} and can tigger
     * {@link ErrorPrintable}.
     * 
     * @param userDetails
     *            - the {@link UserDetails}
     */
    void sendUserDetails(UserDetails userDetails);

}
