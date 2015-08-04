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
	
	public static HttpFunctionInfo getProductsSimple(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getProductsSimple");
		String params = funcLogParams(netParameters);
		Log.e("getProductsSimple", "getProductsSimple: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/products/simple?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo sendReportInventory(NetParameter[] netParameters, String token) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("sendReportInventory");
		String params = funcLogParams(netParameters);
		Log.e("sendReportInventory", "sendReportInventory: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/reports/inventory?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo feedback(NetParameter[] netParameters, String token) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("feedback");
		String params = funcLogParams(netParameters);
		Log.e("feedback", "feedback: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/reports/feedback?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo getGimics(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getGimics");
		String params = funcLogParams(netParameters);
		Log.e("getGimics", "getGimics: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/gimics/prepare?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo createGimic(NetParameter[] netParameters, String token) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("createGimic");
		String params = funcLogParams(netParameters);
		Log.e("createGimic", "createGimic: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/gimics/create?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo getSales(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getSales");
		String params = funcLogParams(netParameters);
		Log.e("getSales", "getSales: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/list_not_delivery?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo delivery(String token, String id) {
		HttpFunctionInfo functionInfo = createPutMethod("delivery");
		Log.e("delivery", "delivery: " + id);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/delivery/" + id + "?access-token=" + token);
		return functionInfo;
	}
	
	public static HttpFunctionInfo getSalesOrder(NetParameter[] netParameters, String id) {
		HttpFunctionInfo functionInfo = createGetMethod("getSalesOrder");
		String params = funcLogParams(netParameters);
		Log.e("getSalesOrder", "getSalesOrder: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/" + id + "?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo createSale(NetParameter[] netParameters, String token, String id) {
		HttpFunctionInfo functionInfo = createPutBodyMethod("createSale");
		String params = funcLogParams(netParameters);
		Log.e("createSale", "createSale: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/" + id + "?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo getListDelivery(NetParameter[] netParameters) {
		HttpFunctionInfo functionInfo = createGetMethod("getListDelivery");
		String params = funcLogParams(netParameters);
		Log.e("getListDelivery", "getListDelivery: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/list_delivery?" + params);
		return functionInfo;
	}
	
	public static HttpFunctionInfo orderCancel(NetParameter[] netParameters, String token) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("orderCancel");
		String params = funcLogParams(netParameters);
		Log.e("orderCancel", "orderCancel: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/cancel?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
	
	public static HttpFunctionInfo refund(NetParameter[] netParameters, String token) {
		HttpFunctionInfo functionInfo = createPostBodyMethod("refund");
		String params = funcLogParams(netParameters);
		Log.e("refund", "refund: " + params);
		functionInfo.setUrl(viviHostURLshort + "/v1/sales/refund?access-token=" + token);
		functionInfo.setParams(netParameters);
		return functionInfo;
	}
}
