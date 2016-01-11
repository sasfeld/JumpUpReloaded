/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p>
 * Helper for localization works.
 * </p>
 * 
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 21.01.2015
 * 
 */
@Named(value=BeanNames.LOCALE_HELPER)
public class LocaleHelper
{

    private static LocaleHelper instance;

    /**
     * Format the given date and return only the date, regardless to the time. <br />
     * For returning time, see the other methods of this bean.
     * 
     * @param inputDate
     * @return
     */
    public String formatDate(final Date inputDate)
    {
        String formatted = DateFormat.getDateInstance(getDatePrecision())
                .format(inputDate);

        Application.log("formatDate(): will return formatted " + formatted,
                LogType.DEBUG, getClass());

        return formatted;
    }

    protected int getDatePrecision()
    {
        return DateFormat.MEDIUM;
    }

    public Date parseDateFromString(String dateString) throws ParseException
    {

        SimpleDateFormat sdf = new SimpleDateFormat(getDateTimeFormat());

        Date date = sdf.parse(dateString);

        return date;
    }

    /**
     * Get the date time format to be used in frontend components and for conversion.
     * @return
     */
    public String getDateTimeFormat()
    {
        return "dd.MM.yyyy H:mm";
    }

    /**
     * Format the given date and return only the time, regardless to the date. <br />
     * For returning date, see the other methods of this bean.
     * 
     * @param inputDate
     * @return
     */
    public String formatTime(final Date inputDate)
    {
        String formatted = DateFormat.getTimeInstance(getTimePrecision())
                .format(inputDate);

        Application.log("formatTime(): will return formatted " + formatted,
                LogType.DEBUG, getClass());

        return formatted;
    }

    protected int getTimePrecision()
    {
        return DateFormat.SHORT;
    }

    /**
     * Format the given date and return both date and time. <br />
     * For returning date and time separated, see the other methods of this
     * bean.
     * 
     * @param inputDate
     * @return
     */
    public String formatDateTime(final Date inputDate)
    {
        if (null == inputDate) {
            return "---";
        }
        
        return DateFormat.getDateTimeInstance(getDatePrecision(),
                getTimePrecision()).format(inputDate);
    }
    
    public static LocaleHelper getInstance()
    {
        if (null == instance) {
            instance = new LocaleHelper();
        }
        
        return instance;
    }

}
