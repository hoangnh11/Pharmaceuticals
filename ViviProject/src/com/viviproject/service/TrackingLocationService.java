package com.viviproject.service;

import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.Logger;

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
		Logger.error("getToken:" + tocken);
		
		String body = "lat=0&long=0&title=vivi";
		NetParameter[] headers = new NetParameter[1];
		headers[0] = new NetParameter("access-token", tocken );
		
		NetParameter[] params = new NetParameter[3];
		params[0] = new NetParameter("lat", "0.0");
		params[1] = new NetParameter("long", "0.0");
		params[2] = new NetParameter("title", "vivi");
		
		try {
			JSONObject json = new JSONObject();
			json.put("lat", 0.5);
			json.put("long", 0.5);
			json.put("title", "vivi");
			body = json.toString();
			Logger.error("body:" + body);
			String data = HttpNetServices.Instance.trackingLocation(headers, params, body);
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
			Logger.error("========== get location: START===========");
			// create class object
			GPSTracker gps = new GPSTracker(getApplicationContext());
			// check if GPS enabled     
            if(gps.canGetLocation()){
                 
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Logger.error("latitude: " + latitude + " -longitude: " + longitude);
            }
            
			Logger.error("========== get location: END===========");
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
