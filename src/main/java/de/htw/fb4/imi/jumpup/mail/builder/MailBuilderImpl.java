/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.mail.builder;

import java.io.File;

import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import de.htw.fb4.imi.jumpup.mail.MailModel;
import de.htw.fb4.imi.jumpup.util.FaceletRenderer;
import de.htw.fb4.imi.jumpup.util.FacesFacade;
import de.htw.fb4.imi.jumpup.util.TemplateHandler;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
@Stateful
public class MailBuilderImpl implements MailBuilder
{
    @Inject
    protected FacesFacade facesFacade;
    
    protected FaceletRenderer faceletRenderer;
    
    protected TemplateHandler templateHandler;
    
    protected MailModel mailModel;
    
    /**
     * Get template renderer.
     * @return
     */
    public TemplateHandler getTemplateHandler()
    {
        if (null == this.templateHandler) {
            this.templateHandler = new TemplateHandler(FacesContext.getCurrentInstance());                        
        }
        
        return this.templateHandler;
    }
    
    /**
     * Get facelet renderer.
     * @return
     */
    public FaceletRenderer getFaceletRenderer()
    {
        if (null == this.faceletRenderer) {
            this.faceletRenderer = new FaceletRenderer(FacesContext.getCurrentInstance());
        }
        
        return this.faceletRenderer;
    }
    
    protected MailModel getMailModel()
    {
        if (null == this.mailModel) {
            this.mailModel = new MailModel();
        }
        
        return this.mailModel;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.mail.builder.MailBuilder#addPlainTextContent(java.io.File)
     */
    @Override
    public MailBuilder addPlainTextContent(File templateFile)
    {
        TemplateHandler handler = this.prepareTemplateHandler(templateFile);
        
        String plainContent = handler.evaluateTemplate();
        
        // set plain content in mail model
        this.getMailModel().setContentText(plainContent);
        
        return this;
    }

    private TemplateHandler prepareTemplateHandler(File templateFile)
    {
        TemplateHandler handler = this.getTemplateHandler();
        handler.setTemplate(templateFile);
        return handler;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.mail.builder.MailBuilder#addHtmlContent(java.io.File)
     */
    @Override
    public MailBuilder addHtmlContent(final String faceletPath)
    {
        FaceletRenderer renderer = this.getFaceletRenderer();
        
        String htmlContent = renderer.renderView(faceletPath);
        
        // set html content in mail model
        this.getMailModel().setContentHtml(htmlContent);
        
        return this;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.mail.builder.MailBuilder#getBuildedMailModel()
     */
    public MailModel getBuildedMailModel()
    {
        return this.getMailModel();
    }

}
