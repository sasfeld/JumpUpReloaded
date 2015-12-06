/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.export;

import de.htw.fb4.imi.jumpup.user.login.LoginModel;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 06.12.2015
 *
 */
public interface ILoginDependent
{

    LoginModel getLoginModel();
    
    void setLoginModel(LoginModel loginModel);
}
