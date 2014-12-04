/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
public class TemplateHandlerTest
{
    
    @Test
    public void testRegEx()
    {
        String pattern = TemplateHandler.PATTERN_EXPRESSION_LANGUAGE;
        
        Pattern p = Pattern.compile(pattern);
        
        Matcher m = p.matcher("irgendwas #{el} #{el2.someproperty}");
        
        int i = 0;
        while (m.find()) {            
            
            if (i == 0) {
                Assert.assertEquals("#{el}", m.group());
            } else if (i == 1) {
                Assert.assertEquals("#{el2.someproperty}", m.group());
            }
            
            i++;            
        }
    }

}
