/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.test;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
public class IntegrationTest
{
    protected UnitTestConfigReader configReader;    
    protected MailConfigReader mailConfigReader;
    protected boolean enabled;
    
    public IntegrationTest()
    {
        this.configReader = new UnitTestConfigReader();
        
        this.enabled = Boolean.parseBoolean(this.configReader.fetchValue("jumpup.test.enable_integration"));
    }
}
