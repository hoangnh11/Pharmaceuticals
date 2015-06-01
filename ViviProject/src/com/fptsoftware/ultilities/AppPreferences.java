/**
 * Name: $RCSfile: AppPreferences.java,v $
 * Version: $Revision: 1.6 $
 * Date: $Date: 2015/01/06 11:23:49 $
 *
 * Copyright (C) 2015 FPT Software. All rights reserved.
 */
package com.fptsoftware.ultilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.fptsoftware.viviproject.HomeActivity;
import com.fptsoftware.viviproject.LoginActivity;
import com.fptsoftware.viviproject.R;

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
}