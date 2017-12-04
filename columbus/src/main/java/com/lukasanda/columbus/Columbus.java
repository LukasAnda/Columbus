package com.lukasanda.columbus;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsClient;

/**
 * Created by Lukas Anda on 2.12.2017.
 */

public class Columbus {
    static volatile Columbus singleton;
    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SettingsClient settingsClient;
    private GeofencingClient geofencingClient;

    private Columbus(Context context, FusedLocationProviderClient fusedLocationProviderClient, SettingsClient settingsClient, GeofencingClient geofencingClient) {
        this.context = context;
        this.fusedLocationProviderClient = fusedLocationProviderClient;
        this.settingsClient = settingsClient;
        this.geofencingClient = geofencingClient;
    }

    public static Columbus with() {
        if (singleton == null) {
            synchronized (Columbus.class) {
                if (singleton == null) {
                    if (ColumbusProvider.context == null) {
                        throw new IllegalStateException("context == null");
                    }
                    singleton = new Builder(ColumbusProvider.context).build();
                }
            }
        }
        return singleton;
    }


    public LocationRequest load(LocationCallback locationListener) {
        return new LocationRequest(singleton.fusedLocationProviderClient, singleton.settingsClient,singleton.geofencingClient,locationListener);
    }

    public LocationRequest load() {
        return new LocationRequest(singleton.fusedLocationProviderClient, singleton.settingsClient,singleton.geofencingClient,null);
    }


    private static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        Columbus build() {
            return new Columbus(context, LocationServices.getFusedLocationProviderClient(context),LocationServices.getSettingsClient(context), LocationServices.getGeofencingClient(context));
        }
    }
}
