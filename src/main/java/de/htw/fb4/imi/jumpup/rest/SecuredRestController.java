/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.user.login.LoginMethod;
import de.htw.fb4.imi.jumpup.user.login.LoginModel;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
public abstract class SecuredRestController extends AbstractRestController
{
    /**
     * Pattern for Authorization HTTP Header field: Example -> Authorization: Basic asdasdrawe==
     */
    private static final String REGEX_BASIC_HEADER = "Basic ([A-Za-z0-9+/=]+=)";
 
    protected LoginModel loginModel = new LoginModel();
    
    @Inject
    protected LoginMethod loginMethod;
    
    protected List<String> loginErrorMessages = new ArrayList<String>();
    
    public LoginModel getLoginModel()
    {
        return loginModel;
    }
    
    private boolean isAuthenticated(HttpHeaders headers)
    {
        if (null != getLoginModel() && null != getLoginModel().getCurrentUser()) {
            return true;
        }
        
        String authenticationHeaderField = getAuthorizationHeader(headers);
        parseAuthorizationHeader(authenticationHeaderField);
        
        // trigger login
        if (this.tryToLoginUser()) {
            return true;
        }
        
        return false;
    }   
    
    private String getAuthorizationHeader(HttpHeaders headers)
    {
        List<String> authorizationHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        
        if (null == authorizationHeaders || 0 == authorizationHeaders.size()) {
            throw new IllegalArgumentException("No " + HttpHeaders.AUTHORIZATION + " header given within a secured action.");
        } 
        
        if (authorizationHeaders.size() > 1) {
            throw new IllegalArgumentException("More than one " + HttpHeaders.AUTHORIZATION + " header given within a secured action.");
        }
        
        return authorizationHeaders.get(0);
    }

    private void parseAuthorizationHeader(String authenticationHeaderField)
    {
        Pattern pBasicHeader = Pattern.compile(REGEX_BASIC_HEADER);   
        Matcher mBasicHeader = pBasicHeader.matcher(authenticationHeaderField);
        
        if (!mBasicHeader.matches()) {
            throw new IllegalArgumentException("Invalid HTTP Basic header given for " 
                    + HttpHeaders.AUTHORIZATION + " header. It must match the pattern " + REGEX_BASIC_HEADER);
        }
        
        mBasicHeader.reset();
        
        if (mBasicHeader.find()) {
            String base64EncodedData = mBasicHeader.group(1);            
            String decodedData = new String(DatatypeConverter.parseBase64Binary(base64EncodedData));
            
            String[] splitDecodedData = decodedData.split(":");
            
            if (splitDecodedData.length != 2) {
                throw new IllegalArgumentException("Invalid HTTP Basic header given for " 
                        + HttpHeaders.AUTHORIZATION + " header. It's base64 encoded value must be a pair of username:pwd");
            }
            
            // fill login model directly
            getLoginModel().setUsernameOrMail(splitDecodedData[0]);
            getLoginModel().setPassword(splitDecodedData[1]);                        
        }
    }    
    
    private boolean tryToLoginUser()
    {
        loginErrorMessages.clear();
        
        loginMethod.logIn(getLoginModel());
        
        if (!loginMethod.hasError()) {
            return true;
        }
        
        Application.log("tryToLoginUser(): login failed: " + loginMethod.getSingleErrorString(), LogType.ERROR, getClass());
        return false;
    }

    @GET
    public Response get(@Context HttpHeaders headers) {
        Response response = super.get(headers);
        
        if (null == response && !this.isAuthenticated(headers)) {
            return this.sendUnauthorizedResponse();
        }
        
        return response;
    }       
   
    @GET
    public Response get(@Context HttpHeaders headers, Long... ids) {
        Response response = super.get(headers);
        
        if (null == response && !this.isAuthenticated(headers)) {
            return this.sendUnauthorizedResponse();
        }
        
        return response;
    }

    @POST
    public Response post(@Context HttpHeaders headers){
        Response response = super.get(headers);
        
        if (null == response && !this.isAuthenticated(headers)) {
            return this.sendUnauthorizedResponse();
        }
        
        return response;
    }   
    
    @PUT
    public Response put(@Context HttpHeaders headers, Long entityId){
        Response response = super.get(headers);
        
        if (null == response && !this.isAuthenticated(headers)) {
            return this.sendUnauthorizedResponse();
        }
        
        return response;
    }   
    
    @DELETE
    public Response delete(@Context HttpHeaders headers, Long entityId){
        Response response = super.get(headers);
        
        if (null == response && !this.isAuthenticated(headers)) {
            return this.sendUnauthorizedResponse();
        }
        
        return response;
    }   
    
    @OPTIONS
    public Response options(@Context HttpHeaders headers){
        Response response = super.get(headers);
        
        if (null == response && !this.isAuthenticated(headers)) {
            return this.sendUnauthorizedResponse();
        }
        
        return response;
    }   
    
    protected Response sendUnauthorizedResponse()
    {
        return Response.status(Status.UNAUTHORIZED).build();
    }
    
    protected Response sendForbiddenResponse()
    {
        return Response.status(Status.FORBIDDEN).build();
    }
}
