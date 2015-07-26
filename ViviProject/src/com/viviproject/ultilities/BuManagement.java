/*
 * Name: $RCSfile: BuManagement.java,v $
 * Version: $Revision: 1.20 $
 * Date: $Date: 2013/03/19 02:48:48 $
 *
 * Copyright (C) 2013 FPT Software, Inc. All rights reserved.
 */

package com.viviproject.ultilities;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.maps.GeoPoint;
import com.viviproject.R;

/**
 * Business management: include common methods
 * 
 * @author hunglv1
 */
public final class BuManagement {
	public final static BuManagement Instance = new BuManagement();
	private static String appVersionName;

	private BuManagement() {}

	/**
	 * decryption UserName and Password for login function
	 * 
	 * @param data
	 * @return
	 */
	public static String decrypt(String data) {
		int map;
		String orig = "";
		String from[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "0", };
		String to[] = { "b", "1", "G", "e", "4", "h", "d", "8", "9", "a", "A",
				"B", "D", "C", "c", "7", "F", "3", "E", "n", "0", "f", "2",
				"6", "g", "H", "J", "i", "I", "j", "5", "k", "K", "M", "L",
				"l", "m", "N", "o", "O", "u", "P", "Q", "X", "R", "r", "x",
				"S", "t", "U", "T", "p", "v", "q", "Y", "w", "V", "W", "s",
				"y", "Z", "z" };
		/* Decrypt Logic */
		for (int i = 0; i < data.length(); i++) {

			map = -1;

			for (int j = 0; j < 62; j++) {

				if (to[j].equals(data.substring(i, i + 1))) {

					map = j;
					orig = orig.trim() + from[j];

				}

			}
			if (map == -1) {
				orig = orig.trim() + data.substring(i, i + 1);

			}
		}
		return orig;
	}

	/**
	 * encryption UserName and Password for login function
	 * 
	 * @param data
	 * @return
	 */
	public static String encrypt(String data) {
		String orig = "";
		String from[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "0", };
		String to[] = { "b", "1", "G", "e", "4", "h", "d", "8", "9", "a", "A",
				"B", "D", "C", "c", "7", "F", "3", "E", "n", "0", "f", "2",
				"6", "g", "H", "J", "i", "I", "j", "5", "k", "K", "M", "L",
				"l", "m", "N", "o", "O", "u", "P", "Q", "X", "R", "r", "x",
				"S", "t", "U", "T", "p", "v", "q", "Y", "w", "V", "W", "s",
				"y", "Z", "z" };
		int i, map;

		for (i = 0; i < data.length(); i++) {
			map = -1;

			for (int j = 0; j < 62; j++) {

				if (from[j].equals(data.substring(i, i + 1))) {

					map = j;
					orig = orig.trim() + to[j];

				}

			}
			if (map == -1) {
				orig = orig.trim() + data.substring(i, i + 1);

			}
		}
		return orig;
	}

	/**
	 * get password
	 * 
	 * @param activity
	 * @return
	 */
	public static String getPassword(Activity activity) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		return sharedPreferenceManager.getString(GlobalParams.KEY_PASSWORD_USER, GlobalParams.BLANK_CHARACTER);
	}

	/**
	 * save password
	 * 
	 * @param activity
	 * @param password
	 * @return
	 */
	public static boolean savePassword(Activity activity, String password) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		sharedPreferenceManager.saveString(GlobalParams.KEY_PASSWORD_USER, password);
		return true;
	}

	public static String getToken(Activity activity) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		return sharedPreferenceManager.getString(GlobalParams.TOKEN_SHARE_PREFERENT_KEY, GlobalParams.BLANK_CHARACTER);
	}
	
	public static String getToken(Context activity) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		return sharedPreferenceManager.getString(GlobalParams.TOKEN_SHARE_PREFERENT_KEY, GlobalParams.BLANK_CHARACTER);
	}
	
	public static boolean saveToken(Activity activity, String token) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		sharedPreferenceManager.saveString(GlobalParams.TOKEN_SHARE_PREFERENT_KEY, token);
		return true;
	}
	
	public static String getLatitude(Activity activity) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		return sharedPreferenceManager.getString(GlobalParams.LATITUDE, GlobalParams.BLANK_CHARACTER);
	}
	
	public static boolean saveLatitude(Context activity, String lat) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		sharedPreferenceManager.saveString(GlobalParams.LATITUDE, lat);
		return true;
	}	
	
	public static String getLongitude(Activity activity) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		return sharedPreferenceManager.getString(GlobalParams.LONGITUDE, GlobalParams.BLANK_CHARACTER);
	}
	
	public static boolean saveLongitude(Context activity, String _long) {
		SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(activity);
		sharedPreferenceManager.saveString(GlobalParams.LONGITUDE, _long);
		return true;
	}
	
	/**
	 * expand view animation
	 * 
	 * @param v
	 */
	public static void expand(final View v) {
		v.measure(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		final int measuredHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? android.view.ViewGroup.LayoutParams.WRAP_CONTENT
						: (int) (measuredHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp per milliseconds
		a.setDuration((int) (measuredHeight / v.getContext().getResources()
				.getDisplayMetrics().density));
		v.startAnimation(a);
	}

	/**
	 * collapse view animation
	 * 
	 * @param v
	 */
	public static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight
							- (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp per milliseconds
		a.setDuration((int) (initialHeight / v.getContext().getResources()
				.getDisplayMetrics().density));
		v.startAnimation(a);
	}

	/**
	 * increase days in calendar
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	/**
	 * compareDate
	 * 
	 * @param date1
	 * @param date2
	 * @return 1 if date1 after date2, 0 if equae, -1 if before
	 */
	@SuppressWarnings("deprecation")
	public static int compareDate(Date date1, Date date2) {
		if (date1.getYear() == date2.getYear()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate()) {
			return 0;
		} else if (date1.getYear() < date1.getYear()
				|| (date1.getYear() == date2.getYear() && date1.getMonth() < date2
						.getMonth())
				|| (date1.getYear() == date2.getYear()
						&& date1.getMonth() == date2.getMonth() && date1
						.getDate() < date2.getDate())) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * Returns a string that describes the number of days between dateOne and
	 * dateTwo.
	 * 
	 */

	public static int getDateDiff(Date dateOne, Date dateTwo) {
		long timeOne = dateOne.getTime();
		long timeTwo = dateTwo.getTime();
		long oneDay = 1000 * 60 * 60 * 24;
		int delta = (int) ((timeTwo - timeOne) / oneDay);

		if (delta > 0) {
			return delta;
		} else {
			delta *= -1;
			return delta;
		}
	}

	/**
	 * updateConnectedFlags
	 */
	public static boolean updateConnectedFlags(final Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * getListViewSize
	 * 
	 * @param myListView
	 */
	public static void getListViewSize(ListView myListView) {
		ListAdapter myListAdapter = myListView.getAdapter();
		if (myListAdapter == null) {
			// do nothing return null
			return;
		}
		// set listAdapter in loop for getting final size
		int totalHeight = 0;
		for (int size = 0; size < myListAdapter.getCount(); size++) {
			View listItem = myListAdapter.getView(size, null, myListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		// setting listview item in adapter
		ViewGroup.LayoutParams params = myListView.getLayoutParams();
		params.height = totalHeight
				+ (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
		myListView.setLayoutParams(params);
	}

	/**
	 * Get location from GPS
	 * 
	 * @return
	 */
	public static GeoPoint getLocation(Context context) {
		try {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			boolean isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			String provider = locationManager.getBestProvider(criteria, true);
			Location curLocation;
			if (isNetworkEnabled) {
				// In order to make sure the device is getting the location,
				// request updates.
				// this requests updates every hour or if the user moves 1
				// kilometer
				curLocation = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				GeoPoint curGeoPoint = new GeoPoint(
						(int) (curLocation.getLatitude() * 1e6),
						(int) (curLocation.getLongitude() * 1e6));
				return curGeoPoint;
			} else {
				curLocation = locationManager.getLastKnownLocation(provider);
				GeoPoint curGeoPoint = new GeoPoint(
						(int) (curLocation.getLatitude() * 1e6),
						(int) (curLocation.getLongitude() * 1e6));
				return curGeoPoint;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			Log.e("BookFlight", "Get countrycode null");
		}
		return null;
	}

	public static String getStringResourceByKey(Context mContext, String key) {
		try {
			int i = mContext.getResources().getIdentifier(key, "string",
					mContext.getPackageName());
			String msg = mContext.getString(i);
			return msg;
		} catch (Exception e) {
			String msg = mContext.getResources().getString(
					R.string.COMMON_ERROR);
			return msg;
		}
	}

	/**
	 * getUserAgent
	 * 
	 * @return
	 */
	public static String getUserAgent() {
		try {
			String ua = System.getProperty("http.agent");
			return ua;
		} catch (Exception e) {
			e.printStackTrace();
			return "Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K)";
		}
	}

	/**
	 * @return the userAgentString
	 */
	public static String getUserAgentString() {
		StringBuilder userAgentString = new StringBuilder();
		userAgentString.append(GlobalParams.SETTING_MESSAGE_APP_VERSION
				+ appVersionName + GlobalParams.COMMA_SPACE_SEPARATOR
				+ GlobalParams.SETTING_MESSAGE_DEVICE_MODEL
				+ android.os.Build.MODEL + GlobalParams.COMMA_SPACE_SEPARATOR
				+ GlobalParams.SETTING_MESSAGE_SYSTEM_NAME + "Android OS"
				+ GlobalParams.COMMA_SPACE_SEPARATOR
				+ GlobalParams.SETTING_MESSAGE_SYSTEM_VERSION
				+ android.os.Build.VERSION.RELEASE);
		return userAgentString.toString();
	}

	/**
	 * @return the appVersionName
	 */
	public static String getAppVersionName() {
		return appVersionName;
	}

	/**
	 * @param appVersionName
	 *            the appVersionName to set
	 */
	public static void setAppVersionName(String appVersionName) {
		BuManagement.appVersionName = appVersionName;
	}

	public static void appendLog(String text) {
		File logFile = new File("sdcard/log.file");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));
			buf.append(text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	/**
	 * function to convert a View to image image will be save to DCIM folder of
	 * device
	 * 
	 * @param ctx
	 * @param v
	 * @param appName
	 * @return
	 */
	@SuppressLint({ "SimpleDateFormat", "NewApi" })
	public final static boolean shootView(final Context ctx, final View v,
			final String appName) {
		// Get the bitmap from the view
		v.setDrawingCacheEnabled(true);

		boolean isOK = false;

		final Bitmap bmp = v.getDrawingCache();

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd@HHmmss");
		final Calendar cal = Calendar.getInstance();

		// Set file properties
		final String fileJPG = appName + "_" + sdf.format(cal.getTime());

		/*
		 * Create a path where we will place our picture in the user's public
		 * pictures directory. Note that you should be careful about what you
		 * place here, since the user often manages these files. For pictures
		 * and other media owned by the application, consider
		 * Context.getExternalMediaDir().
		 */
		final File path = Environment.getExternalStoragePublicDirectory(
		// Environment.DIRECTORY_PICTURES
				Environment.DIRECTORY_DCIM);

		// Make sure the Pictures directory exists.
		if (!path.exists()) {
			path.mkdirs();
		}

		final File file = new File(path, fileJPG + ".jpg");

		try {
			final FileOutputStream fos = new FileOutputStream(file);

			final BufferedOutputStream bos = new BufferedOutputStream(fos, 8192);

			bmp.compress(CompressFormat.JPEG, 45, bos);

			bos.flush();
			bos.close();

			isOK = true;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return isOK;
	}

	/**
	 * get bitmap of view
	 * 
	 * @param v
	 * @return
	 */
	public static Bitmap loadBitmapFromView(View v) {
		Bitmap b = Bitmap.createBitmap(540, 960, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
		v.draw(c);
		return b;
	}

	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */
	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	public static int getWithDementionDevice(Activity context) {
		try {
			Display display = context.getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			return size.x;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		return imageEncoded;
	}

	public static Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
    
	public static String endCodeImageFromFile(String photoPath) {
		try {
			InputStream is = new FileInputStream(photoPath);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int bytesRead;
			while ((bytesRead = is.read(b)) != -1) {
			   bos.write(b, 0, bytesRead);
			}
			byte[] bytes = bos.toByteArray();
			return Base64.encodeToString(bytes, Base64.DEFAULT);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
	/**
	 * Compares two version strings.
	 * 
	 * Use this instead of String.compareTo() for a non-lexicographical
	 * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
	 * 
	 * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
	 * 
	 * @param str1
	 *            a string of ordinal numbers separated by decimal points.
	 * @param str2
	 *            a string of ordinal numbers separated by decimal points.
	 * @return The result is a negative integer if str1 is _numerically_ less
	 *         than str2. The result is a positive integer if str1 is
	 *         _numerically_ greater than str2. The result is zero if the
	 *         strings are _numerically_ equal.
	 */
	public static Integer versionCompare(String str1, String str2) {
		String[] vals1 = str1.split("\\.");
		String[] vals2 = str2.split("\\.");
		int i = 0;
		// set index to first non-equal ordinal or length of shortest version
		// string
		while (i < vals1.length && i < vals2.length
				&& vals1[i].equals(vals2[i])) {
			i++;
		}
		// compare first non-equal ordinal number
		if (i < vals1.length && i < vals2.length) {
			int diff = Integer.valueOf(vals1[i]).compareTo(
					Integer.valueOf(vals2[i]));
			return Integer.signum(diff);
		}
		// the strings are equal or one string is a substring of the other
		// e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
		else {
			return Integer.signum(vals1.length - vals2.length);
		}
	}
}
