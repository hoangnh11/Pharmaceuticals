package com.viviproject.service;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.SharedPreferenceManager;

public class TrackingLocationService extends Service {
	private static double latitude = 0.0;
	private static double longitude = 0.0;
	
	private final static long trackingTimeout = 180000;
	
	public TrackingLocationService()
	{
		super();		
	}
	
	/**
	 * onStart - Service method for 1.6 and below
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		 Logger.error("TrackingLocationService: onStart");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    Logger.error("TrackingLocationService: Service started");
	   
	    Logger.error("========== get location: START===========");
		// create class object
		GPSTracker gps = new GPSTracker(getApplicationContext());
		// check if GPS enabled     
		latitude = gps.getLatitude();
		longitude = gps.getLongitude();
        Logger.error("latitude: " + latitude + " -longitude: " + longitude);
        BuManagement.saveLatitude(TrackingLocationService.this, String.valueOf(latitude));
        BuManagement.saveLongitude(TrackingLocationService.this, String.valueOf(longitude));      
		Logger.error("========== get location: END===========");
		
	    TrackingLocationAtask m = new TrackingLocationAtask();
	    m.execute();
	    
	    return START_NOT_STICKY;
	 }
	
	@Override
	public void onDestroy() {
		Logger.error("TrackingLocationService: Service destroyed");
		super.onDestroy();
	}
	
	private void trackingLocationToServer() {
		String tocken = BuManagement.getToken(getApplicationContext());
		Logger.error("access-token:" + tocken);
		
		Calendar calendar = Calendar.getInstance();
		String date = calendar.getTime().toString();
		
		NetParameter[] headers = new NetParameter[1];
		headers[0] = new NetParameter("access-token", tocken );
		
		NetParameter[] params = new NetParameter[3];
		params[0] = new NetParameter("lat", String.valueOf(latitude));
		params[1] = new NetParameter("long", String.valueOf(longitude));
		params[2] = new NetParameter("title", date);
		
		try {
			for (int i = 0; i < params.length; i++) {
				Logger.error("" + params[i].getName() + "*" + params[i].getValue());
			}
			String data = HttpNetServices.Instance.trackingLocation(headers, params);
			Logger.error("" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	class TrackingLocationAtask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Logger.error("TrackingLocationAtask");
			trackingLocationToServer();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			TrackingLocationService.this.onDestroy();
		}
	}
}
