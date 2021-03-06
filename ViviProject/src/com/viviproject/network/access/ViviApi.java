package com.viviproject.network.access;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
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
	
	@GET("/v1/presentations/discount_program")
	void getDiscountProgram(@Query("access-token") String token, 
	          Callback<String> callback);
	
	@GET("/v1/presentations/product_image_category")
	void getProductImageCategory(@Query("access-token") String token, 
	          Callback<String> callback);
	
	@GET("/v1/presentations/product_image_list")
	void getProductImageList(@Query("access-token") String token, @Query("cat_id") String cat_id, @Query("page") int page,
	          @Query("per_page") int per_page, Callback<String> callback);
	
	@GET("/v1/presentations/news")
	void getPresentationsNews(@Query("access-token") String token, @Query("page") int page,
	          @Query("per_page") int per_page, Callback<String> callback);
	
	@GET("/v1/presentations/news/{id_bai_viet}")
	void getPresentationsNewsDetail(@Path("id_bai_viet") String id, @Query("access-token") String token,
			Callback<String> callback);
	
	@GET("/v1/presentations/videos")
	void getVideos(@Query("access-token") String token, @Query("page") int page,
	          @Query("per_page") int per_page, Callback<String> callback);
	
	@GET("/v1/gimics/report")
	void getGimicManager(@Query("access-token") String token, @Query("from") String from, @Query("to") String to,
			@Query("page") int page, @Query("per_page") int per_page, Callback<String> callback);
	
	@GET("/v1/reports/total_sale")
	void getTotalSale(@Query("access-token") String token, @Query("from") String from,
	          @Query("to") String to, Callback<String> callback);

	@GET("/v1/reports/chart_sale")
	void getReportChartSale(@Query("access-token") String token, @Query("day") String day,
	          @Query("month") String month, Callback<String> callback);
	
	@GET("/v1/products/simple")
	void getProducts(@Query("access-token") String token, Callback<String> callback);
	
	@GET("/v1/reports/revenue_by_store")
	void getRealueByStore(@Query("access-token") String token, @Query("from") String from, @Query("to") String to,
	          @Query("product_id") String productId, @Query("sort_by") String sortBy, 
	          @Query("page") int page, @Query("per_page") int perPage, Callback<String> callback);
}
