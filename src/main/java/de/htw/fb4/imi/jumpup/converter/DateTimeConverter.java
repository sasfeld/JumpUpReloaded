/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.converter;

import java.sql.Timestamp;
import java.text.ParseException;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.util.LocaleHelper;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2015
 *
 */
@Named( value = BeanNames.DATETIME_CONVERTER )
@RequestScoped
public class DateTimeConverter implements Converter
{

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        try {
            return new Timestamp(LocaleHelper.getInstance().parseDateFromString(value).getTime());
        } catch (ParseException e) {
            Application.log("DateTimeConverter: getAsObject: could not convert datetime " + value + " to a date object", LogType.ERROR, getClass());
            return null;
        }
    }

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        try {
            return LocaleHelper.getInstance().formatDateTime((Timestamp) value);
        } catch (ClassCastException e) {
            Application.log("DateTimeConverter: getAsObject: could not convert datetime " + value + " (type: " + value.getClass() + " object to a string", LogType.ERROR, getClass());
            return value.toString();
        }
    }

}
