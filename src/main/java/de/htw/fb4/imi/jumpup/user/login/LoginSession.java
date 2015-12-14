/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.entity.User;

/**
 * <p>Session-scoped model to get currently logged in user information.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2015
 *
 */
@Named(value = BeanNames.LOGIN_SESSION)
@SessionScoped
public class LoginSession implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -4060843072622346685L;
    
    protected LoginModel loginModel;
    
    public void setLoginModel(LoginModel loginModel)
    {
        this.loginModel = loginModel;
    }
    
    public LoginModel getLoginModel()
    {
        if (null == loginModel) {
            loginModel = new LoginModel();
        }
        
        return loginModel;
    }

    public User getCurrentUser()
    {
        return getLoginModel().getCurrentUser();
    }
}
