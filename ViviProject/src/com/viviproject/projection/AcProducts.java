package com.viviproject.projection;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.AdapterFrgProducts;
import com.viviproject.entities.EnNewsList;
import com.viviproject.entities.EnProduct;
import com.viviproject.entities.EnProductResponse;
import com.viviproject.library.TabPageIndicator;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringConverter;

public class AcProducts extends FragmentActivity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ViewPager productPager;
	private TabPageIndicator productIndicator;
	
	private ArrayList<Fragment> listFrgProducts;
	private AdapterFrgProducts adapterFrgProducts;
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	private int page = 1;
	private int perPage = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_products);
		
		innitLaytout();
	}

	/**
	 * initial layout of screen
	 */
	private void innitLaytout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.PRODUCT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		productPager = (ViewPager) findViewById(R.id.product_type_pager);
		productIndicator = (TabPageIndicator) findViewById(R.id.product_type_title_indicator);
		
		listFrgProducts = new ArrayList<Fragment>();
		
		
		adapterFrgProducts = new AdapterFrgProducts(getSupportFragmentManager(), listFrgProducts);
		productPager.setAdapter(adapterFrgProducts);
		productIndicator.setViewPager(productPager);
		
		refreshData();
	}

	private void refreshData() {
		getDataFromServer();
	}

	private void updateDataScreen(ArrayList<EnProduct> list){
		ArrayList<String> listUrl = new ArrayList<String>();
		
		//udpate tabindecator
		ArrayList<Fragment> frgProductList = new ArrayList<Fragment>();
		for (EnProduct enProduct : list) {
			FrgProducts frgProducts = FrgProducts.newInstance(AcProducts.this, enProduct, listUrl);
			frgProductList.add(frgProducts);
		}
		adapterFrgProducts.setListFragment(frgProductList);
		adapterFrgProducts.notifyDataSetChanged();
		productIndicator.notifyDataSetChanged();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcProducts.this.finish();
			break;

		default:
			break;
		}
		
	}

	/**
	 * get data from server
	 */
	private void getDataFromServer() {
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .setConverter(new StringConverter())
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		Logger.error("access-token: " + token);
		
		if(null == dialog){
			dialog = new ProgressDialog(AcProducts.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					//cancel threat 
					imageCatogoryCallback.failure(null);
				}
			});
		}
		dialog.show();
		
		restAdapter.create(ViviApi.class).getProductImageCategory(token, imageCatogoryCallback);
	}

	Callback<String> imageCatogoryCallback = new Callback<String>() {
		
		@Override
		public void success(String responseString, Response arg1) {
			Logger.error("GET Image Catogory response: " + responseString);
			if(null != AcProducts.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			try{
				EnProductResponse enProductResponse = DataParser.getEnProductResponse(responseString);
				if(null != enProductResponse){
					ArrayList<EnProduct> enProduct = enProductResponse.getCategories();
					updateDataScreen(enProduct);
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcProducts.this);
				}
			} catch (Exception e) {
				e.printStackTrace();
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcProducts.this);
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			retrofitError.printStackTrace();
			if(null != AcProducts.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			if(retrofitError.isNetworkError()){
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_INTERNET_CONNECTION)
						, "Error", AcProducts.this);
			} else {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcProducts.this);
			}
			
		}
	};
}
