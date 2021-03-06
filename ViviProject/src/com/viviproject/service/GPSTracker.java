package com.viviproject.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.viviproject.ultilities.Logger;

public class GPSTracker extends Service implements LocationListener{
	private final Context mContext;
	
	// flag for GPS status
    boolean isGPSEnabled = false;
    
    // flag for network status
    boolean isNetworkEnabled = false;
 
    boolean canGetLocation = false;
    String provider;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 5; // 5 minute
    
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	// Declaring a Location Manager
    protected LocationManager locationManager;
 
    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }
    
	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				Logger.error("no network provider is enabled");
				Logger.error("isGPSEnabled:" + isGPSEnabled + "*isNetworkEnabled:" + isNetworkEnabled + "*canGetLocation:" + canGetLocation);
				try{
					Criteria criteria = new Criteria();
				    provider = locationManager.getBestProvider(criteria, false);
				    Location location = locationManager.getLastKnownLocation(provider);
				    if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						Logger.error("Location using Criteria: latitude:" + latitude + "-longitude:" + longitude);
					} else {
						Logger.error("Location NULL");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				this.canGetLocation = true;
				Logger.error("isGPSEnabled:" + isGPSEnabled + "*isNetworkEnabled:" + isNetworkEnabled + "*canGetLocation:" + canGetLocation);
				// First get location from Network Provider
				if (isNetworkEnabled) {
					Logger.error("Traking location using network:" + isNetworkEnabled);
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (locationManager != null) {
						Logger.error("locationManager not null");
						location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
							Logger.error("Location using net work: latitude:" + latitude + "-longitude:" + longitude);
						}
					}
				}
				
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					Logger.error("Traking location using gps:" + isGPSEnabled);
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								Logger.error("Location using GPS: latitude:" + latitude + "-longitude:" + longitude);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.error("get GPS location have an exception:" + e.getMessage());
		}

		return location;
	}
    
	/**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
         
        // return longitude
        return longitude;
    }
    
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
    
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }       
    }
    
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		Logger.error("GPSTracker: #onProviderEnabled:" + provider );
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		Logger.error("GPSTracker: #onProviderDisabled:" + provider );
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
