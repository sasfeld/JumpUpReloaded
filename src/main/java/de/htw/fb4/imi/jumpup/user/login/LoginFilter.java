/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
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

import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p>The LoginFilter checks incoming requests for a LogInController that has an authentificated {@link User}.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
public class LoginFilter implements Filter
{
    /**
     * Relative path to the login page.
     */
    private static final String LOGIN_PAGE = "";
    
    @Inject
    protected LoginSession loginSession;

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
        // check isLoggedIn flag. it should be set in the session
        if (null == loginSession || !loginSession.getLoginModel().getIsLoggedIn()) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            
            ((HttpServletResponse) response).sendRedirect(contextPath + "/" + LOGIN_PAGE);
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
