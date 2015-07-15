/*
 * Name: $RCSfile: HttpFunctionFactory.java,v $
 * Version: $Revision: 1.333 $
 * Date: $Date: 2013/04/01 11:24:48 $
 * Version: $Revision: 1.333 $
 * Date: $Date: 2013/04/01 11:24:48 $
 *
 */

package com.viviproject.network.access;

import android.util.Log;

import com.viviproject.network.HttpConnection;
import com.viviproject.network.NetParameter;
import com.viviproject.ultilities.BuManagement;

/**
 * This class handle API and Environment in project
 * 
 * @author hoangnh11
 */
public final class HttpFunctionFactory {
	public static String viviHostURLshort = "http://api.tm.vivi.vn";

	private static String userAgent = BuManagement.getUserAgent();
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static HttpFunctionInfo createPutBodyMethod(String name) {
		HttpFunctionInfo func = new HttpFunctionInfo();
		func.setFunctionName(name);
		func.setMethodType(HttpConnection.HTTP_METHOD_PUT_BODY);
		return func;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static HttpFunctionInfo createDeleteMethod(String name) {
		HttpFunctionInfo func = new HttpFunctionInfo();
		func.setFunctionName(name);
		func.setMethodType(HttpConnection.HTTP_METHOD_DELETE);
		return func;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static HttpFunctionInfo createGetMethod(String name) {
		HttpFunctionInfo func = new HttpFunctionInfo();
		func.setFunctionName(name);
		func.setMethodType(HttpConnection.HTTP_METHOD_GET);
		return func;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static HttpFunctionInfo createPostMethod(String name) {
		HttpFunctionInfo func = new HttpFunctionInfo();
		func.setFunctionName(name);
		func.setMethodType(HttpConnection.HTTP_METHOD_POST);
		return func;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static HttpFunctionInfo createPostBodyMethod(String name) {
		HttpFunctionInfo func = new HttpFunctionInfo();
		func.setFunctionName(name);
		func.setMethodType(HttpConnection.HTTP_METHOD_POST_BODY);
		return func;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static HttpFunctionInfo createPutMethod(String name) {
		HttpFunctionInfo func = new HttpFunctionInfo();
		func.setFunctionName(name);
		func.setMethodType(HttpConnection.HTTP_METHOD_PUT);
		return func;
	}

	public static String funcLogParams(NetParameter[] netParameters) {
		String params = "";
		for (int i = 0; i < netParameters.length; i++) {
			if (i == netParameters.length - 1) {
				params = params + netParameters[i].getName() + "="
						+ netParameters[i].getValue().trim();
			} else {
				params = params + netParameters[i].getName() + "="
						+ netParameters[i].getValue().trim() + "&";
			}
		}
		return params;
	}
	
//	public static HttpFunctionInfo storeFailDeductMilesReport(NetParameter[] netParameters) {
//		HttpFunctionInfo functionInfo = createPostBodyMethod("storeFailDeductMilesReport");
//		String params = funcLogParams(netParameters);
//		Log.e("storeFailDeductMilesReport", "storeFailDeductMilesReport: " + params);
//		functionInfo.setUrl(vnaRedemptionHostURLshort + "Report/storeFailDeductMilesReport/v2");
//		functionInfo.setHeader(new NetParameter[] {HttpUtilities.getHeaderUserAgent(userAgent),
//				HttpUtilities.getHeaderUserId()});
//		functionInfo.setParams(netParameters);
//		return functionInfo;
//	}

	public static HttpFunctionInfo login(NetParameter[] netParameters)
	{
		HttpFunctionInfo functionInfo = createPostMethod("Login");
		String params = funcLogParams(netParameters);
		Log.e("Login", "Login: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/login");
		functionInfo.setParams(netParameters);		
		return functionInfo;
	}
	
	public static HttpFunctionInfo getUserInformation(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getUserInformation");
		String params = funcLogParams(netParameters);
		Log.e("getUserInformation", "getUserInformation: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/user/info?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo getStores(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getStores");
		String params = funcLogParams(netParameters);
		Log.e("getStores", "getStores: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/stores?" + params);
		return functionInfo;
	}
}
