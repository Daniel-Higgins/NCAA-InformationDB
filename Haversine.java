/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbb;

/**
 *
 * @author danielhiggins
 */
public class Haversine {
     private static final int EARTH_RADIUS = 6371; 
    //radius in km

    public static double distance(double startLat, double startLong, double endLat, double endLong) {

        double distanceLat  = Math.toRadians((endLat - startLat));
        double distanceLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(distanceLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(distanceLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
