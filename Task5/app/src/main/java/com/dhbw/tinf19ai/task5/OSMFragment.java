package com.dhbw.tinf19ai.task5;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OSMFragment extends Fragment {

    private static final String TAG = "OSMFragment";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private static final GeoPoint MANNHEIM_GEO_POINT = new GeoPoint(49.48406198, 8.47564897);

    private MapView mapView;
    private IMapController mapController;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GeoPoint selectedLocation;

    public OSMFragment() {
        // Required empty public constructor
    }

    public static OSMFragment newInstance() {
        OSMFragment fragment = new OSMFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().setUserAgentValue(getActivity().getPackageName());
        requestPermissionsIfNecessary(new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_osm, container, false);
        this.mapView = (MapView) view.findViewById(R.id.map_view);
        this.mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        this.mapController = this.mapView.getController();
        this.mapController.setZoom(15.0);

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        setMarkerAndCenter(MANNHEIM_GEO_POINT);

        if (getArguments() != null) {
            String location = getArguments().getString("result");
            searchAndCenterAddress(location);
        }
        getLatKnownLocation();
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    public void searchAndCenterAddress(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final GeocoderNominatim geocoderNominatim = new GeocoderNominatim("default-user-agent");
                    List<Address> addresses = geocoderNominatim.getFromLocationName(input, 10);
                    Address address = addresses.get(0);
                    final GeoPoint geoPoint = new GeoPoint(address.getLatitude(), address.getLongitude());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMarkerAndCenter(geoPoint);
                            selectedLocation = geoPoint;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void setMarkerAndCenter(GeoPoint geoPoint) {
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(geoPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(startMarker);

        this.mapController.setCenter(geoPoint);
    }

    @SuppressLint("MissingPermission")
    private void getLatKnownLocation() {
        this.fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // setMarkerAndCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
            }
        });
    }

}