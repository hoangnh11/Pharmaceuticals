package com.viviproject.customerline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viviproject.R;

public class MapActivity extends Activity implements OnClickListener, LocationListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private GoogleMap googleMap;
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		initLayout();
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.MAP));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		try {
            // Loading map
            initilizeMap(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            } else {
            	googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);            	
            	googleMap.setMyLocationEnabled(true);
            	googleMap.getUiSettings().setCompassEnabled(true);
            	googleMap.getUiSettings().setRotateGesturesEnabled(true);
            	CameraPosition cameraPosition = new CameraPosition.Builder().target
            			(new LatLng(21.0243791, 105.8445168)).zoom(15).build();
            	googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            	LatLng latLng = new LatLng(21.0243791, 105.8445168);
             	// create marker
            	MarkerOptions marker = new MarkerOptions().position(latLng).title("Nhà thuốc Huy Hoàng")
            			.snippet("Số 2 Ngõ 67 Lê Thanh Nghị - Hai Bà Trưng - Hà Nội");            	 
            	// Changing marker icon
            	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_red_point));            	 
            	// adding marker
            	googleMap.addMarker(marker);
			}
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
	
    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
     	// create marker
    	MarkerOptions marker = new MarkerOptions().position
    			(new LatLng(location.getLatitude(), location.getLongitude())).title("Nhà thuốc Tuyết Lan")
    			.snippet("Số 1 Ngõ 9 Đào Tấn - Ba Đình - Hà Nội");            	 
    	// Changing marker icon
    	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_point));            	 
    	// adding marker
    	googleMap.addMarker(marker);
    	
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        googleMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }
    
    
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;		
			
		default:
			break;
		}
	}
}
