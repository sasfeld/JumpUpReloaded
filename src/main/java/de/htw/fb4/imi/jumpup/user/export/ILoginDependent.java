/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.export;

import de.htw.fb4.imi.jumpup.user.login.LoginModel;
import de.htw.fb4.imi.jumpup.user.login.LoginSession;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 06.12.2015
 *
 *@deprecated make proper use of @Inject to inject session-scoped {@link LoginSession}
 */
public interface ILoginDependent
{

    LoginModel getLoginModel();
    
    void setLoginModel(LoginModel loginModel);
}
