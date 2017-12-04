package com.lukasanda.columbus;


import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Lukas on 4.12.2017.
 */

public class LocationRequest {
    private LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient settingsClient;
    private GeofencingClient geofencingClient;
    private float mDistance = 0;
    private int mTrips = 0;
    private int mTime = 0;
    private int mPriority = com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    private boolean askForGPS = false;
    private long mExpirationTime = 0;
    private long mExpirationDruation = 0;
    private long mMaxTime = 0;
    private int mFastestInterval = 0;
    private com.google.android.gms.location.LocationRequest mLocationRequest;
    private Activity activity;

    protected LocationRequest(FusedLocationProviderClient client1, SettingsClient client2, GeofencingClient client3, LocationCallback locationCallback) {
        this.mFusedLocationClient = client1;
        this.settingsClient = client2;
        this.geofencingClient = client3;
        this.locationCallback = locationCallback;
    }


    public LocationRequest maxTime(long mMaxTime) {
        this.mMaxTime = mMaxTime;
        return this;
    }

    public LocationRequest fastestUpdate(int mFastestInterval) {
        this.mFastestInterval = mFastestInterval;
        return this;
    }

    public LocationRequest expirationTime(long mExpirationTime) {
        this.mExpirationTime = mExpirationTime;
        return this;
    }

    public LocationRequest expirationDuration(long mExpirationDruation) {
        this.mExpirationDruation = mExpirationDruation;
        return this;
    }

    public LocationRequest ask(Activity activity) {
        this.activity = activity;
        askForGPS = true;
        return this;
    }

    public LocationRequest time(int mTime) {
        this.mTime = mTime;
        return this;
    }

    public LocationRequest distance(float mDistance) {
        this.mDistance = mDistance;
        return this;
    }

    public LocationRequest trips(int mTrips) {
        this.mTrips = mTrips;
        return this;
    }

    public Location go() {
        if (locationCallback != null) {
            createLocationRequest();
            startLocationUpdates();
            return mFusedLocationClient.getLastLocation().getResult();
        } else {
            return mFusedLocationClient.getLastLocation().getResult();
        }
    }

    public void stop() {
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        if (locationCallback != null) mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void startLocationUpdates() {
        if (locationCallback != null) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, null);
        }

    }

    private void createLocationRequest() {
        mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setSmallestDisplacement(mDistance);
        mLocationRequest.setInterval(mTime);
        mLocationRequest.setFastestInterval(mFastestInterval);
        mLocationRequest.setPriority(mPriority);
        if (mTrips > 0) {
            mLocationRequest.setNumUpdates(mTrips);
        }
        if (mExpirationTime < 0) {
            mLocationRequest.setExpirationTime(mExpirationTime);
        }
        if (mExpirationDruation > 0) {
            mLocationRequest.setExpirationDuration(mExpirationDruation);
        }
        if (askForGPS) {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            builder.setAlwaysShow(askForGPS);
            settingsClient.checkLocationSettings(builder.build()).addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                @Override
                public void onComplete(Task<LocationSettingsResponse> task) {
                    try {
                        LocationSettingsResponse response = task.getResult(ApiException.class);
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                    } catch (ApiException exception) {
                        switch (exception.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                // Location settings are not satisfied. But could be fixed by showing the
                                // user a dialog.
                                try {
                                    // Cast to a resolvable exception.
                                    ResolvableApiException resolvable = (ResolvableApiException) exception;
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    resolvable.startResolutionForResult(
                                            activity,
                                            1000);
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                } catch (ClassCastException e) {
                                    // Ignore, should be an impossible error.
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                // Location settings are not satisfied. However, we have no way to fix the
                                // settings so we won't show the dialog.
                                break;
                        }
                    }
                }
            });
        }
    }

}
