/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.test.user.entities;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p>Test of {@link UserTest} entity.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.11.2014
 *
 */
public class UserTest
{
    protected User userEntity;
    
    /*
     * test setup
     */
    @Before
    public void setUp()
    {
        this.userEntity = UserFactory.newUser();
    }
    
    /*
     * tests
     */
    @Test
    public void testPasswordHashGeneration()
    {
        String password = this.givenSomePassword();
        whenPwIsSetOnUser(password);
        thenAssertHashedPassword(password);
    }   
  
    @Test
    public void testUserHashGeneration()
    {
        this.givenOneUser();        
        User anotherUser = this.andGivenAnotherUser();
        
        thenAssertDifferentHashes(anotherUser);
    }
    
    /*
     * splitted test methods. 
     */
    
    private void thenAssertDifferentHashes(User anotherUser)
    {
        assertNotEquals(this.userEntity, anotherUser);        
        assertNotEquals(this.userEntity.hashCode(), anotherUser.hashCode());
    }

    private User andGivenAnotherUser()
    {
        User anotherUser = UserFactory.newUser();
        anotherUser.setEmail("someone@sometld.tld");
        anotherUser.setIsConfirmed(false);
        anotherUser.setPrename("Karl");
        anotherUser.setLastname("Keks");
        anotherUser.setUsername("cookiesanddonuts");
        
        anotherUser.getResidence().setCountry("Cookieland");
        anotherUser.getResidence().setTown("Cookietown");
        anotherUser.getResidence().setLocale(Locale.CHINESE.getLanguage());
        
        return anotherUser;
    }

    private void givenOneUser()
    {
        this.userEntity.setEmail("user@sometld.tld");
        this.userEntity.setIsConfirmed(false);
        this.userEntity.setPrename("Kevin");
        this.userEntity.setLastname("Nervbalg");
        this.userEntity.setUsername("kevinnervt");
        
        this.userEntity.getResidence().setCountry("Schland");
        this.userEntity.getResidence().setTown("Nervstadt");
        this.userEntity.getResidence().setLocale(Locale.GERMAN.getLanguage());
        
    }

    private String givenSomePassword()
    {
        return "some-password";
    }
    
    private void whenPwIsSetOnUser(final String password)
    {
       this.userEntity.setPassword(password);        
    }
    
    private void thenAssertHashedPassword(final String originalPassword)
    {
       assertNotEquals(originalPassword, this.userEntity.getPasswordHash());
       assertTrue(this.userEntity.getPasswordHash().length > originalPassword.length());       
    }


}
