/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import de.htw.fb4.imi.jumpup.ApplicationError;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2015
 *
 */
public class WebAppFilter implements Filter
{
    public static String CONTEXT_PATH;
    public static String SCHEME;
    public static String SERVER_NAME;
    public static int SERVER_PORT;
    
    private static URL url;

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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        if (null == CONTEXT_PATH) {
            CONTEXT_PATH = ((HttpServletRequest) request).getContextPath();
            SCHEME = ((HttpServletRequest) request).getScheme();
            SERVER_NAME = ((HttpServletRequest) request).getServerName();
            SERVER_PORT = ((HttpServletRequest) request).getServerPort();
        }
        
        chain.doFilter(request, response);
    }
    
    public static URL getWebAppUrl()
    {
        if (null == url) {
            buildUrl();
        }
        
        return url;
    }

    protected static void buildUrl()
    {
        try {
            url = new URL(SCHEME,
                    SERVER_NAME,
                    SERVER_PORT,
                    CONTEXT_PATH);
        } catch (MalformedURLException e) {
            throw new ApplicationError("Could not build URL to webapp.",
                    WebAppFilter.class);
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
