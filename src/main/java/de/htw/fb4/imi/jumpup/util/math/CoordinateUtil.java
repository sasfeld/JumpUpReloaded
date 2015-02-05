/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util.math;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>General helper for working with coordinates, LatLng etc.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.01.2015
 *
 */
public class CoordinateUtil
{
    /**
     * Radius of earth's equator.
     */
    private static final double EQUATOR_RADIUS = 6378.137d;

    /**
     * Calculate the distance in kilometers between two given points.
     * 
     * @param point1
     * @param point2
     * @return distance in kilometers
     */
    public static double calculateDistanceBetween(Coordinates point1, Coordinates point2)
    {
        double point1LatRad = point1.getLatitudeRadians();
        double point1LongRad = point1.getLongitudeRadians();
        
        double point2LatRad = point2.getLatitudeRadians();
        double point2LongRad = point2.getLongitudeRadians();
        
        return EQUATOR_RADIUS * Math.acos(Math.sin(point1LatRad) * Math.sin(point2LatRad) 
                + Math.cos(point1LatRad) * Math.cos(point2LatRad) * Math.cos(point2LongRad - point1LongRad)
                );
                
    }
    
    /**
     * Get new coordinates model by a string of comma-separated latitude and longitude.<br />
     * Example: 55.5123,52.3131 which will be parsed into a {@link Coordinates} instance with latitude = 55.5123 and longitude = 52.3131
     * @param latLng
     * @return
     */
    public static Coordinates newCoordinatesBy(String latLng)
    {
        String[] rawCoordinates = latLng.split(",");
        
        if (2 != rawCoordinates.length) {
            throw new IllegalArgumentException("The value for latLng must be a string of comma-separated LatLng values like '55.5123,52.3131'");
        }
        
        float latitude;
        try {
            latitude = Float.parseFloat(rawCoordinates[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The value for latLng must be a string of comma-separated LatLng values like '55.5123,52.3131'");
        }
        
        float longitude;
        try {
            longitude = Float.parseFloat(rawCoordinates[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The value for latLng must be a string of comma-separated LatLng values like '55.5123,52.3131'");
        }
        
        Coordinates coordinates = new Coordinates(latitude, longitude);
        
        return coordinates;
    }
    
    /**
     * Get a set of {@link Coordinates} for the raw coordinates comma-separated string as saved in the database.<br />
     * Example: 55.5123,52.3131;55.5123,52.3131 will return two {@link Coordinates} instances, the first one with latitude = 55.5123 and longitude = 52.3131
     * 
     * @param latLngValues
     * @return
     */
    public static Set<Coordinates> parseCoordinateSetBy(String latLngValues)
    {
        String[] rawCoordinates = latLngValues.split(";");
        
        if (0 == rawCoordinates.length) {
            throw new IllegalArgumentException("The value for latLng must be a string of comma-separated LatLng values like '55.5123,52.3131;55.5123,52.3131'");
        }
        
        Set<Coordinates> coordinatesSet = new HashSet<Coordinates>();
        for (String rawLatLng : rawCoordinates) {
            Coordinates coordinates = newCoordinatesBy(rawLatLng);
            coordinatesSet.add(coordinates);
        }
        
        return coordinatesSet;
    }

}
