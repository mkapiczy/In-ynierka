package dron.mkapiczynski.pl.dronvision.domain;


import org.osmdroid.util.GeoPoint;

import java.util.List;
import java.util.Set;

/**
 * Created by Miix on 2016-01-05.
 */
public class Drone {
    private Long deviceId;
    private String deviceName;
    private GeoPoint currentPosition;
    private List<GeoPoint> searchedArea;
    private List<GeoPoint> lastSearchedArea;
    private int color;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public GeoPoint getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(GeoPoint currentPosition) {
        this.currentPosition = currentPosition;
    }

    public List<GeoPoint> getSearchedArea() {
        return searchedArea;
    }

    public void setSearchedArea(List<GeoPoint> searchedArea) {
        this.searchedArea = searchedArea;
    }

    public List<GeoPoint> getLastSearchedArea() {
        return lastSearchedArea;
    }

    public void setLastSearchedArea(List<GeoPoint> lastSearchedArea) {
        this.lastSearchedArea = lastSearchedArea;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
