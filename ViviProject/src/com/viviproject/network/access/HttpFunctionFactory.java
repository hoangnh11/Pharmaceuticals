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

/**
 * This class handle API and Environment in project
 * 
 * @author hoangnh11
 */
public final class HttpFunctionFactory {
	public static String viviHostURLshort = "http://api.tm.vivi.vn";

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
	
	public static HttpFunctionInfo getRegions(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getRegions");
		String params = funcLogParams(netParameters);
		Log.e("getRegions", "getRegions: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/regions?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo getStores(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getStores");
		String params = funcLogParams(netParameters);
		Log.e("getStores", "getStores: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/stores?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo getStoresLine(NetParameter[] netParameters, String day) {
		HttpFunctionInfo functionInfo = createGetMethod("getStoresLine");
		String params = funcLogParams(netParameters);
		Log.e("getStoresLine", "getStoresLine: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/stores/line/" + day + "?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo trackingLocation(NetParameter[] headers,NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("trackingLocation");
		functionInfo.setUrl(viviHostURLshort + "/v1/gps/create?access-token=" + headers[0].getValue());
		//functionInfo.setHeader(headers);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo createStores(NetParameter[] netParameters, String token) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("createStores");
		String params = funcLogParams(netParameters);
		Log.e("createStores", "createStores: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/stores/create?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo updateStores(NetParameter[] netParameters, String token, String id) {
		HttpFunctionInfo functionInfo = createPutBodyMethod("updateStores");
		String params = funcLogParams(netParameters);
		Log.e("updateStores", "updateStores: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/stores/" + id + "?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo getProducts(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getProducts");
		String params = funcLogParams(netParameters);
		Log.e("getProducts", "getProducts: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/products/forsale?" + params);
		return functionInfo;
	}

	public static HttpFunctionInfo reportImages(String token, NetParameter[] netParameters) throws Exception{
		HttpFunctionInfo functionInfo = createPostMethod("reportImages");
		functionInfo.setUrl(viviHostURLshort + "/v1/images/create?access-token=" + token);
		//functionInfo.setHeader(headers);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo prepareSale(NetParameter[] netParameters, String token) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("prepareSale");
		String params = funcLogParams(netParameters);
		Log.e("prepareSale", "prepareSale: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/prepare?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo createSale(NetParameter[] netParameters, String token) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("createSale");
		String params = funcLogParams(netParameters);
		Log.e("createSale", "createSale: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/create?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
}
