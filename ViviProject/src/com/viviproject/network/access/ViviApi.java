package com.viviproject.network.access;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import com.viviproject.entities.EnCoverReport;
import com.viviproject.entities.EnPlanSale;
import com.viviproject.entities.UserInformation;

public interface ViviApi {
	@GET("/v1/user/info")
	void getUserInfo(@Query("access-token") String token, 
	          Callback<UserInformation> callback);
	
	@GET("/v1/reports/plan_sale")
	void getPlanSale(@Query("access-token") String token, 
	          Callback<EnPlanSale> callback);
	
	@GET("/v1/reports/plan_quantity")
	void getCoverReport(@Query("access-token") String token, 
	          Callback<EnCoverReport> callback);
}
