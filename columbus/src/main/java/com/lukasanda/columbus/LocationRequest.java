package com.lukasanda.columbus;


import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Lukas on 30.11.2016.
 */

public class LocationRequest {
    private LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient settingsClient;
    private float mDistance = 0;
    private int mTrips = 0;
    private int mTime = 0;
    private int mPriority = com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    private boolean askForGPS;
    private long mExpirationTime = 0;
    private long mExpirationDruation = 0;
    private long mMaxTime = 0;
    private int mFastestInterval = 0;
    private com.google.android.gms.location.LocationRequest mLocationRequest;
    private Activity activity;

    LocationRequest(Context context, LocationCallback locationCallback) {
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationCallback = locationCallback;
        this.mTime = 10000;
        this.mPriority = com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        this.askForGPS = false;
        this.mFastestInterval = mTime / 2;
        this.settingsClient = LocationServices.getSettingsClient(context);
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

    public LocationRequest askForPermission(Activity activity){
        this.activity = activity;
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
        mLocationRequest.setInterval(0);
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
