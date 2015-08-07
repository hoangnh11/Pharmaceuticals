/**
 * Name: $RCSfile: DataParser.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: May 31, 2013 2:40:18 PM $
 *
 * Copyright (c) 2013 FPT Software, Inc. All rights reserved.
 */
package com.viviproject.ultilities;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.viviproject.entities.EnArrayStores;
import com.viviproject.entities.EnCompanyNewsDetails;
import com.viviproject.entities.EnCompanyNewsDetailsRespone;
import com.viviproject.entities.EnDiscountProgram;
import com.viviproject.entities.EnElement;
import com.viviproject.entities.EnFeedback;
import com.viviproject.entities.EnGimicManager;
import com.viviproject.entities.EnImageUrlResponse;
import com.viviproject.entities.EnNews;
import com.viviproject.entities.EnNewsList;
import com.viviproject.entities.EnProductResponse;
import com.viviproject.entities.EnRegions;
import com.viviproject.entities.EnReportImageResponse;
import com.viviproject.entities.EnReportProfitResponse;
import com.viviproject.entities.EnVideosResponse;
import com.viviproject.entities.Products;
import com.viviproject.entities.ResponseCreateGimics;
import com.viviproject.entities.ResponseCreateSales;
import com.viviproject.entities.ResponseCreateStores;
import com.viviproject.entities.ResponseDelivery;
import com.viviproject.entities.ResponseLogin;
import com.viviproject.entities.ResponseOrders;
import com.viviproject.entities.ResponsePrepare;
import com.viviproject.entities.ResponseReport;
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

	public static String convertObjectToString(Object jsonData)	throws JsonSyntaxException {
		if (jsonData == null) {
			return null;
		}

		String json;
		initGson();
		json = mGson.toJson(jsonData);
		return json;
	}
	
	public static ResponseLogin getLogin(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseLogin>() {}.getType();

			ResponseLogin details = mGson.fromJson(jsonData, collectionType);
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
	
	public static ArrayList<EnRegions> getRegions(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ArrayList<EnRegions>>() {}.getType();

			ArrayList<EnRegions> details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnReportImageResponse getEnReportImageResponse(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnReportImageResponse>() {}.getType();

			EnReportImageResponse details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnArrayStores getStores(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnArrayStores>() {}.getType();

			EnArrayStores details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static Products getProducts(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<Products>() {}.getType();

			Products details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnElement getElements(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnElement>() {}.getType();

			EnElement details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ResponseOrders getResponseOrders(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseOrders>() {}.getType();

			ResponseOrders details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ResponseDelivery getResponseDelivery(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseDelivery>() {}.getType();

			ResponseDelivery details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ResponseCreateStores createStores(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseCreateStores>() {}.getType();

			ResponseCreateStores details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ResponseReport responseReport(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseReport>() {}.getType();

			ResponseReport details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnFeedback responseFeedback(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnFeedback>() {}.getType();

			EnFeedback details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ResponseCreateGimics responseCreateGimics(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseCreateGimics>() {}.getType();

			ResponseCreateGimics details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ResponseCreateStores updateStores(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseCreateStores>() {}.getType();

			ResponseCreateStores details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnDiscountProgram getEnDiscountProgram(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnDiscountProgram>() {}.getType();

			EnDiscountProgram details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ArrayList<EnNews> getListEnNews(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ArrayList<EnNews>>() {}.getType();

			ArrayList<EnNews> details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnNewsList getEnNewsList(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnNewsList>() {}.getType();

			EnNewsList details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnCompanyNewsDetailsRespone getNewsDetailResponse(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnCompanyNewsDetailsRespone>() {}.getType();

			EnCompanyNewsDetailsRespone details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnVideosResponse getEnVideosResponse(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnVideosResponse>() {}.getType();

			EnVideosResponse details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnProductResponse getEnProductResponse(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnProductResponse>() {}.getType();

			EnProductResponse details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnImageUrlResponse getEnImageUrlResponse(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnImageUrlResponse>() {}.getType();

			EnImageUrlResponse details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnReportProfitResponse getEnReportProfitResponse(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnReportProfitResponse>() {}.getType();

			EnReportProfitResponse details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static EnGimicManager getEnGimicManager(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<EnGimicManager>() {}.getType();

			EnGimicManager details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ResponsePrepare prepareSale(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponsePrepare>() {}.getType();

			ResponsePrepare details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public static ResponseCreateSales createSale(String jsonData) throws JsonSyntaxException {
		if (StringUtils.isBlank(jsonData)) {
			return null;
		}
		try {
			initGson();
			Type collectionType = new TypeToken<ResponseCreateSales>() {}.getType();

			ResponseCreateSales details = mGson.fromJson(jsonData, collectionType);
			return details;
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
}
