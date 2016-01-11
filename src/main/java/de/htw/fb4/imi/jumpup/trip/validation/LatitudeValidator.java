/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.validation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p>
 * Validator for decimal latitudes (Breitengrad).
 * </p>
 * 
 * <p>
 * We handle those as documented by Google: <br />
 * https://developers.google.com/maps/documentation/javascript/reference?hl=de#
 * LatLng
 * </p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 18.01.2015
 *
 */
@Named(value=BeanNames.LATITUDE_VALIDATOR)
@RequestScoped
public class LatitudeValidator extends AbstractTripValidator
{
    private static final String PATTERN_LATITUDE = "^[0-9.]+$";

    /**
     * Minimum range for latitudes.
     * 
     * @see https
     *      ://developers.google.com/maps/documentation/javascript/reference
     *      ?hl=de#LatLng
     */
    public static final Float MIN_VALUE = -90f;
    /**
     * Maximum range for latitudes.
     * 
     * @see https
     *      ://developers.google.com/maps/documentation/javascript/reference
     *      ?hl=de#LatLng
     */
    public static final Float MAX_VALUE = 90f;

    @Override
    /*
     * (non-Javadoc)
     * 
     * @see
     * de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final Double latitude = getDouble(value);

        if (null == latitude || !this.checkRange(latitude)) {
            return false;
        }

        return true;
    }

    protected Double getDouble(final Object value)
    {
        final Double latitude;

        if (value instanceof String) {
            latitude = Double.parseDouble((String) value);
        } else if (value instanceof Double || value instanceof Double ) {
            latitude = (Double) value;
        } else {
            Application.log(
                    "getDouble(): invalid value for latitude: " + value + " which is typeof " + value.getClass(),
                    LogType.ERROR, getClass());
            errorMessages.add("Invalid value given for latitude.");
            return null;
        }

        return latitude;
    }

    private boolean checkRange(Double latitude)
    {
        if (latitude > this.getMaxValue() || latitude < this.getMinValue()) {
            return false;
        }

        return true;
    }

    /**
     * @see https
     *      ://developers.google.com/maps/documentation/javascript/reference
     *      ?hl=de#LatLng
     * @return
     */
    public Float getMinValue()
    {
        return MIN_VALUE;
    }

    /**
     * @see https
     *      ://developers.google.com/maps/documentation/javascript/reference
     *      ?hl=de#LatLng
     * @return
     */
    public Float getMaxValue()
    {
        return MAX_VALUE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMinLength()
     */
    @Override
    public int getMinLength()
    {
        return 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    @Override
    public int getMaxLength()
    {
        return 3;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#
     * getFrontendValidationPattern()
     */
    @Override
    public String getFrontendValidationPattern()
    {
        return PATTERN_LATITUDE;
    }

    @Override
    /*
     * (non-Javadoc)
     * 
     * @see
     * de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage
     * ()
     */
    protected String getDefaultFailureMessage()
    {
        return "An invalid latitude was sent. Did you activate Javascript?";
    }
}
