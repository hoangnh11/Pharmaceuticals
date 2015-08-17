/**
 * Name: $RCSfile: AppPreferences.java,v $
 * Version: $Revision: 1.6 $
 * Date: $Date: 2015/01/06 11:23:49 $
 *
 * Copyright (C) 2015 FPT Software. All rights reserved.
 */
package com.viviproject.ultilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.viviproject.HomeActivity;
import com.viviproject.LoginActivity;
import com.viviproject.R;

/**
 * @author HoangNH11
 * Handling Preferences
 */
public class AppPreferences {
	public static final String KEY_PREFS_SITE = "SITE";
	
	private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName();
	private static SharedPreferences _sharedPrefs;
	private Editor _prefsEditor;	

	@SuppressWarnings("static-access")
	public AppPreferences(Context context) {
		this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
		this._prefsEditor = _sharedPrefs.edit();
	}
	
	public void savePreference(String key, String value){
		_prefsEditor.putString(key, value);
		_prefsEditor.commit();
	}
	
	public String getPreferencesString(String key, String valueExpect){
		return _sharedPrefs.getString(key, valueExpect);
	}
	
	public static String getSite() {
		return _sharedPrefs.getString(KEY_PREFS_SITE, GlobalParams.BLANK_CHARACTER);
	}

	public void saveSite(String site) {
		_prefsEditor.putString(KEY_PREFS_SITE, site);
		_prefsEditor.commit();
	}
	
	public String getVersionName(Context context){
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {			
			e.printStackTrace();
		}
		return "";
	}
	
	public String getAndroidVersion() {
	    return Build.VERSION.RELEASE;
	}
	
	public String getFullPlatform(Context context){
		try {
			return "Android" + Build.VERSION.RELEASE 					
					+ "+Appv"
					+ context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
					+ "+" + android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
		} catch (NameNotFoundException e) {			
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * go to home screen and finish current activity
	 * @param activity
	 * @return
	 */
	public boolean goHome(Activity activity)
	{
		boolean bRet = false;
		if (null == activity)
		{
			return bRet;
		}
		activity.finish();
		Intent intent = new Intent(activity, HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
		bRet = true;
		return bRet;
	}
	
	/**
	 * go to home screen and finish current activity
	 * @param activity
	 * @return
	 */
	public boolean goLogin(Activity activity)
	{
		boolean bRet = false;
		if (null == activity)
		{
			return bRet;
		}
		activity.finish();
		Intent intent = new Intent(activity, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
		bRet = true;
		return bRet;
	}
	
	/**
	 * calculate height for list view
	 */
	public void setListViewHeight(ListView lv, Adapter adapter){
		int totalHeight = 0;
		for (int h = 0; h < adapter.getCount(); h++) {
			View listItem = adapter.getView(h, null, lv);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = lv.getLayoutParams();
		params.height = totalHeight	+ (lv.getDividerHeight() * (adapter.getCount() - 1));
		lv.setLayoutParams(params);
		lv.requestLayout();
	}
	
	/**
	 * Keep position list view when refresh
	 * @param lv
	 * @param adapter
	 */
	public void keepPositionListView(ListView lv, ListAdapter adapter){
		// save index and top position
		int index = lv.getFirstVisiblePosition();
		View v = lv.getChildAt(0);
		int top = (v == null) ? 0 : v.getTop();

		// notify data set changed or re-assign adapter here
		lv.setAdapter(adapter);
		
		// restore the position of list view
		lv.setSelectionFromTop(index, top);
	}
	
	/**
	 * @return
	 */
	public Bundle getBundle(Activity activity) {
		Bundle bundle = activity.getIntent().getExtras();

		if (bundle == null) {
			bundle = new Bundle();
		}

		return bundle;
	}
	
	/**
	 * Alert error Message
	 * 
	 * @param errorCode
	 * @param title
	 */
	public void alertErrorMessageInt(int errorCode, String title, Activity activity) {
		Dialog dialog = alertErrorMessage(title, activity.getString(errorCode),	activity);
		dialog.show();
	}

	public void alertErrorMessageString(String errorCode, String title, Activity activity) {
		Dialog dialog = alertErrorMessage(title, errorCode, activity);
		dialog.show();
	}

	public static Dialog alertErrorMessage(String title, String message, final Activity active) {
		return new AlertDialog.Builder(active)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(R.string.COMMON_OK,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,	int which) {
								dialog.dismiss();
							}
						}).create();
	}
	
	/**
	 * Get IMEI
	 * @param activity
	 * @return
	 */
	public String getIMEI(Activity activity){
		TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	/**
	 * @return yyyy-MM-dd HH:mm:ss format date as string
	 */
	public String getCurrentTimeStamp() {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTimeStamp = dateFormat.format(new Date());
			return currentTimeStamp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get ratio of window screen
	 */
    public static float getAspectRatioOfWindow(Activity activity){
    	Point size = new Point();
    	WindowManager w = activity.getWindowManager();
    	w.getDefaultDisplay().getSize(size);
    	float result = (float) size.x/size.y;
    	return result;
    }    
    
    public void hideKeyboard(Activity activity, EditText edittext) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(edittext.getWindowToken(), 0);       
    }
    
    public void showKeyboard(Activity activity, EditText edittext) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(edittext, 0);       
    }    
}