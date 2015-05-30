/**
 * Name: $RCSfile: DataParser.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: May 31, 2013 2:40:18 PM $
 *
 * Copyright (c) 2013 FPT Software, Inc. All rights reserved.
 */
package com.fptsoftware.ultilities;

import com.google.gson.Gson;

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

//	public static EnMyProfile getProfile(String jsonData)
//			throws JsonSyntaxException {
//		if (StringUtils.isBlank(jsonData)) {
//			return null;
//		}
//		try {
//			initGson();
//			Type collectionType = new TypeToken<EnMyProfile>() {
//			}.getType();
//
//			EnMyProfile details = gson.fromJson(jsonData, collectionType);
//			return details;
//		} catch (Exception e) {
//			Logger.error(e);
//			return null;
//		}
//	}
}
