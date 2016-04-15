package dron.mkapiczynski.pl.dronvision.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dron.mkapiczynski.pl.dronvision.domain.DBDrone;
import dron.mkapiczynski.pl.dronvision.domain.Drone;
import dron.mkapiczynski.pl.dronvision.domain.DroneHoleInSearchedArea;

/**
 * Created by Miix on 2016-01-14.
 */
public class MapUtils {

    public static void setMapViewDefaultSettings(MapView mapView, Activity activity) {
        SessionManager sessionManager = new SessionManager(activity.getApplicationContext());
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.setMinZoomLevel(14);
        mapView.setMaxZoomLevel(20);
        MapController mapController = (MapController) mapView.getController();
        mapController.setZoom(16);
        GeoPoint lastMapCenter = sessionManager.getLastMapCenter();
        if (lastMapCenter != null) {
            mapController.setCenter(lastMapCenter);
        } else {
            mapController.setCenter(new GeoPoint(52.24695, 21.105083));
        }
        addScaleBarOverlayToMapView(mapView.getOverlays(), activity);
        /*CustomMapListener customMapListener = new CustomMapListener(VisualizeActivity.this, getApplicationContext(), 16);
        mapView.setMapListener(customMapListener);*/
        /*DisplayMetrics metrics = getResources().getDisplayMetrics();
        double scale = 1 / (metrics.densityDpi * 39.37 * 1.1943);*/
    }

    public static List<Overlay> updateMapOverlays(Set<Drone> drones, List<DBDrone> trackedDrones, List<DBDrone> visualizedDroned, MapView mapView, Activity activity) {
        List<Overlay> mapOverlays = new ArrayList<>();
        addScaleBarOverlayToMapView(mapOverlays, activity);

        Iterator<Drone> dronesIterator = drones.iterator();
        while (dronesIterator.hasNext()) {
            Drone currentIteratedDrone = dronesIterator.next();
            if (droneIsVisualized(currentIteratedDrone, visualizedDroned)) {
                updateDroneVisualizedOverlays(currentIteratedDrone, mapOverlays, mapView, activity);
            } else if (droneIsShownOnMap(currentIteratedDrone, trackedDrones)) {
                updateDroneShownOverlays(currentIteratedDrone, mapOverlays, mapView, activity);
            }

        }

        return mapOverlays;
    }

    public static void addScaleBarOverlayToMapView(List<Overlay> mapOverlays, Activity activity) {
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(activity.getApplicationContext());
        mapOverlays.add(myScaleBarOverlay);
    }

    private static boolean droneIsVisualized(Drone drone, List<DBDrone> visualizedDrones) {
        for (int i = 0; i < visualizedDrones.size(); i++) {
            if (visualizedDrones.get(i).getDroneId().compareTo(drone.getDroneId()) == 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean droneIsShownOnMap(Drone drone, List<DBDrone> trackedDrones) {
        for (int i = 0; i < trackedDrones.size(); i++) {
            if (trackedDrones.get(i).getDroneId().compareTo(drone.getDroneId()) == 0) {
                return true;
            }
        }
        return false;
    }

    private static void updateDroneVisualizedOverlays(Drone droneToUpdate, List<Overlay> mapOverlays, MapView mapView, Activity activity) {
        updateDroneSearchedAreaOnMap(droneToUpdate, mapOverlays, activity);
        updateDroneLastSearchedAreaOnMap(droneToUpdate, mapOverlays, activity);
        updateDroneLastPositionMarkerOnMap(droneToUpdate, mapOverlays, mapView, activity);
    }

    private static void updateDroneShownOverlays(Drone droneToUpdate, List<Overlay> mapOverlays, MapView mapView, Activity activity) {
        updateDroneLastPositionMarkerOnMap(droneToUpdate, mapOverlays, mapView, activity);
    }

    private static void updateDroneLastPositionMarkerOnMap(Drone droneToUpdate, List<Overlay> mapOverlays, MapView mapView, Activity activity) {
        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(droneToUpdate.getCurrentPosition()));
        StringBuffer droneHintStringBuffer = new StringBuffer();
        droneHintStringBuffer.append("Id: ").append(droneToUpdate.getDroneId().toString()).append(" Nazwa: ").append(droneToUpdate.getDroneName());
        marker.setTitle(droneHintStringBuffer.toString());
        Drawable droneIcon = DroneUtils.getDroneMarkerIcon(droneToUpdate, activity);
        marker.setIcon(droneIcon);
        mapOverlays.add(marker);
    }

    private static void updateDroneLastSearchedAreaOnMap(Drone droneToUpdate, List<Overlay> mapOverlays, Activity activity) {
        Polygon lastSearchedArea = new Polygon(activity.getApplicationContext());
        lastSearchedArea.setPoints(droneToUpdate.getLastSearchedArea());
        lastSearchedArea.setFillColor(0X3C5EAAF6);
        lastSearchedArea.setStrokeColor(0X3C5EAAF6);
        lastSearchedArea.setStrokeWidth(3);
        List<DroneHoleInSearchedArea> lastholesPoints = droneToUpdate.getLastHoles();
        addHolesToMapOverlays(lastholesPoints, mapOverlays, activity, true);
        mapOverlays.add(lastSearchedArea);
    }

    private static void updateDroneSearchedAreaOnMap(Drone droneToUpdate, List<Overlay> mapOverlays, Activity activity) {
        if (droneToUpdate.getSearchedArea().size() > 1) {
            Polygon searchedArea = new Polygon(activity.getApplicationContext());
            searchedArea.setPoints(droneToUpdate.getSearchedArea());
            searchedArea.setFillColor(0x32121212);
            searchedArea.setStrokeColor(0x12121212);
            searchedArea.setStrokeWidth(3);
            /*List<List<GeoPoint>> holes = new ArrayList<>();
            for(int i=0; i<droneToUpdate.getLastSearchedAreaHoles().size();i++){
                if(i%2!=0) {
                   List<GeoPoint> hole = new ArrayList<>();
                    hole.add(droneToUpdate.getLastSearchedAreaHoles().get(i));
                    hole.add(droneToUpdate.getLastSearchedAreaHoles().get(i-1));
                    holes.add(hole);
                }
            }
            searchedArea.setLastSearchedAreaHoles(holes);*/
            //Toast.makeText(activity.getApplicationContext(), holes.size(), Toast.LENGTH_SHORT).show();
            /*List<List<GeoPoint>> holes = new ArrayList<>();
            List<GeoPoint> singleHole = new ArrayList<>();
            singleHole.addAll(droneToUpdate.getLastSearchedArea());
            holes.add(singleHole);
            searchedArea.setLastSearchedAreaHoles(holes);*/
            List<DroneHoleInSearchedArea> holesPoints = droneToUpdate.getHoles();
            addHolesToMapOverlays(holesPoints, mapOverlays, activity, false);
            mapOverlays.add(searchedArea);
        }
    }

    public static void addHolesToMapOverlays(List<DroneHoleInSearchedArea> holesPoints, List<Overlay> mapOverlays, Activity activity, boolean last){
        for(int i=0; i<holesPoints.size();i++){
            if(i%2!=0) {
                org.osmdroid.bonuspack.overlays.Polyline line = new Polyline(activity);
                line.setPoints(holesPoints.get(i).getHoleLocations());
                line.setColor(Color.DKGRAY);
                line.setWidth(10);
                mapOverlays.add(line);
            }
        }
    }
}
