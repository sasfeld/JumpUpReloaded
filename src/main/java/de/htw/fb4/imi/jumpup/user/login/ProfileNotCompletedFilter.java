/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.login;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.htw.fb4.imi.jumpup.navigation.NavigationBean;
import de.htw.fb4.imi.jumpup.user.controllers.Login;

/**
 * <p>Filter to check whether the user filled out the mandatory profile fields.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 01.02.2015
 *
 */
public class ProfileNotCompletedFilter implements Filter
{
    
    @Inject
    protected Login loginController;
    
    @Inject
    protected NavigationBean navigationBean;
    
    @Inject
    protected LoginMethod loginBean;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {      
        String editProfilePath = navigationBean.toEditProfile(false);
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String requestUrl = ((HttpServletRequest) request).getRequestURI();
        
        // check if user is new or has an incomplete profile
        if (!requestUrl.contains(editProfilePath)
                && null != loginController 
                && loginController.getLoginModel().getIsLoggedIn()
                && null != loginController.getLoginModel().getCurrentUser() 
                && loginBean.isNew(loginController.getLoginModel())) {
            
            ((HttpServletResponse) response).sendRedirect(contextPath + "/" + editProfilePath);
        } else {
            // continue filter chain
            chain.doFilter(request, response);
        }        
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy()
    {
        
    }

}
