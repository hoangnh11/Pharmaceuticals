/**
 * Name: $RCSfile: DataParser.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: May 31, 2013 2:40:18 PM $
 *
 * Copyright (c) 2013 FPT Software, Inc. All rights reserved.
 */
package com.viviproject.ultilities;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.viviproject.entities.ResponseLogin;
import com.viviproject.entities.UserInformation;

/**
 * @author HieuND9
 * 
 */
public final class DataParser {

	private static Gson mGson;

	/**
	 * Gson member initialization
	 */
	private static void initGson() {
		if (null == mGson) {
			mGson = new Gson();
		}
	}

	public static ResponseLogin[] getLogin(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseLogin[]>() {}.getType();

			ResponseLogin[] details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static UserInformation getUserInformation(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<UserInformation>() {}.getType();

			UserInformation details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
}
