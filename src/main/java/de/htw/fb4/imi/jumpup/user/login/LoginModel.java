/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.login;

import de.htw.fb4.imi.jumpup.user.controllers.Login;
import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p>Simple Login data object.</p>
 * 
 * <p>To get the login model for the current user, use the managed bean {@link Login}.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
public class LoginModel
{
    protected String usernameOrMail;
    
    protected String password;
    
    protected User currentUser;
    
    protected boolean isLoggedIn = false;

    /**
     * @return the usernameOrMail
     */
    public String getUsernameOrMail()
    {
        return usernameOrMail;
    }

    /**
     * @param usernameOrMail the usernameOrMail to set
     */
    public void setUsernameOrMail(final String usernameOrMail)
    {
        this.usernameOrMail = usernameOrMail;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(final String password)
    {
        this.password = password;
    }

    /**
     * @return the currentUser
     */
    public User getCurrentUser()
    {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(final User currentUser)
    {
        this.currentUser = currentUser;
    }

    /**
     * @return the isLoggedIn
     */
    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }

    /**
     * @param isLoggedIn the isLoggedIn to set
     */
    public void setLoggedIn(final boolean isLoggedIn)
    {
        this.isLoggedIn = isLoggedIn;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((currentUser == null) ? 0 : currentUser.hashCode());
        result = prime * result + (isLoggedIn ? 1231 : 1237);
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
        result = prime * result
                + ((usernameOrMail == null) ? 0 : usernameOrMail.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginModel other = (LoginModel) obj;
        if (currentUser == null) {
            if (other.currentUser != null)
                return false;
        } else if (!currentUser.equals(other.currentUser))
            return false;
        if (isLoggedIn != other.isLoggedIn)
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (usernameOrMail == null) {
            if (other.usernameOrMail != null)
                return false;
        } else if (!usernameOrMail.equals(other.usernameOrMail))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("LoginModel [usernameOrMail=");
        builder.append(usernameOrMail);
        builder.append(", password=");
        builder.append(password);
        builder.append(", currentUser=");
        builder.append(currentUser);
        builder.append(", isLoggedIn=");
        builder.append(isLoggedIn);
        builder.append("]");
        return builder.toString();
    }    

}
