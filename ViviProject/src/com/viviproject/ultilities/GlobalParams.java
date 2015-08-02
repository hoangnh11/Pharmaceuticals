package com.viviproject.ultilities;

import android.os.Environment;




public final class GlobalParams {
	
	public static final String KEY_PASSWORD_USER = "password user";
	public static final String VERSION_APP_PRF = "VERSION_APP_PRF";
	public static final String KEY_DATE_TIME_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String KEY_DATE_TIME_FORMAT_EEEE_MMMM_DD_YYYY = "EEEE, MMMM dd, yyyy";
	public static final String KEY_DATE_TIME_FORMAT_YYYY_MM_DD_T_HH_MM = "yyyy-MM-dd'T'hh:mm";
	public static final String SETTING_MESSAGE_APP_VERSION = "App Version: ";
	public static final String COMMA_SPACE_SEPARATOR = ", ";
	public static final String SETTING_MESSAGE_DEVICE_MODEL = "Device Model: ";
	public static final String SETTING_MESSAGE_SYSTEM_NAME = "OS Name: ";
	public static final String SETTING_MESSAGE_SYSTEM_VERSION = "OS Version: ";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final int ACTIVITY_FOR_RESULT_ACFUNCHOME = 1;
	public static final int SET_RESULT_TO_ACFUNCHOME = 2;
	public static final int LOGIN_TO_ENROLL_REQUEST_CODE = 101;
	public static final int AC_NOMINEE_LIST_ACTIVITY = 201;
    public static final String NEW_LINE = "\n";
    public static final String MINUSWITHSPACE = " - ";    
    public static final int MESSAGE_SUCCEED = 1;
    public static final int MESSAGE_FAIL = 2;
    public static final String THREE_DOT_ELLIPSES = " ...";
    public static final char SPACE_CHARACTER = ' ';
    public static final char DOT_CHARACTER = '.';
    public static final String THREE_SHARPS_CHARACTER = "###"; 
    public static final String BLANK_CHARACTER = "";
    
    public static final String EXTRA_PICTURE_REPORT_TYPE = "com.viviproject.PICTURE_REPORT_TYPE";
    public static final String EXTRA_PICTURE_DATA = "com.viviproject.EXTRA_PICTURE_DATA";
    public static final String EXTRA_IMAGE_PATH = "com.viviproject.EXTRA_IMAGE_PATH";
    public static final String EXTRA_IMAGE_ORIENTATION = "EXTRA_IMAGE_ORIENTATION";
    public static final int GALERRY_GET_IMAGE = 4;
    public static final String SPLASH_STRING = "/";    
    public static final int THUMBNAIL_SIZE = 160;
    public static final String TEMP_SCALE_IMG_FOR_SENDING_NAME = "tmpScaledForSending.jpg";
    public static final String APP_NAME = "Pharmacy";
    public static final String STORES = "STORES";
    public static final String CHANGE_ORDER = "CHANGE_ORDER";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String STORES_ID = "store id";
    public static final String FOLDER_DATA = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ SPLASH_STRING
			+ APP_NAME;
	public static final String FOLDER_TEMP = FOLDER_DATA
			+ SPLASH_STRING
			+ "Pharmacy_MMS";
	public static final String SUFFIX_FILE_NAME_JPEG = ".jpg";
	public static final String IMAGE_BACKGROUND_FOLDER_NAME = "Pharmacy_image_background_files";
	public static final String TEMP_COMMON_FOLDER_NAME = "Pharmacy_temp_files";
	public static final String TOKEN_SHARE_PREFERENT_KEY = "TOKEN";
	
	public static final int CAPTURE_TYPE_CLOSE = 0;
	public static final int CAPTURE_TYPE_POSTER = 1;
	public static final int CAPTURE_TYPE_RIVAL = 2;
	public static final int CAPTURE_TYPE_TRADE_MARKETING = 3;
	public static final int CAPTURE_TYPE_ORTHER = 4;
	
	// key to check language that app using
	public static final String USING_VI_RESOUCE = "STRING-VI";
	public static final String USING_EN_RESOUCE = "STRING-EN";
	public static final String USING_KO_RESOUCE = "STRING-KO";
	public static final String USING_JA_RESOUCE = "STRING-JA";
	
}
