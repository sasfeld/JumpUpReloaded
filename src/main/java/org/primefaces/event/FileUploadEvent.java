/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package org.primefaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.primefaces.model.UploadedFile;

public class FileUploadEvent extends FacesEvent
{
    /**
     * 
     */
    private static final long serialVersionUID = -7940232370197310150L;
    private UploadedFile file;

    public FileUploadEvent(UIComponent component, UploadedFile file)
    {
        super(component);
        this.file = file;
    }

    public boolean isAppropriateListener(FacesListener listener)
    {
        return false;
    }

    public void processListener(FacesListener listener)
    {
        throw new UnsupportedOperationException();
    }

    public UploadedFile getFile()
    {
        return this.file;
    }
}
