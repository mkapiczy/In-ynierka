package dron.mkapiczynski.pl.dronvision.domain;

import java.util.Comparator;

/**
 * Created by Miix on 2016-01-08.
 */

/**
 * Klasa reprezentująca położenie geograficzne drona wykorzystywana do komunikacji z serwerem
 */
public class MyGeoPoint {
    private double latitude;
    private double longitude;
    private double altitude;

    public MyGeoPoint() {

    }

    public MyGeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MyGeoPoint(double latitude, double longitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

}
