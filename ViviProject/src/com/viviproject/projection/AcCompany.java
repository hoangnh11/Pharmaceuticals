package com.viviproject.projection;

import java.util.ArrayList;

import com.viviproject.R;
import com.viviproject.adapter.AdapterCompanyNews;
import com.viviproject.entities.EnNews;
import com.viviproject.entities.EnNewsList;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringConverter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AcCompany  extends Activity implements OnClickListener, OnItemClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ListView lvNews;
	
	private ArrayList<EnNews> listNews = new ArrayList<EnNews>();
	private AdapterCompanyNews adapterCompanyNews;
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	private int page = 1;
	private int perPage = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_company_layout);
		initLayout();
	}

	/**
	 * initial layout
	 */
	private void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.COMPANY));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.INVISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.INVISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setOnClickListener(this);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		lvNews = (ListView) findViewById(R.id.lvNews);
		lvNews.setOnItemClickListener(this);
		adapterCompanyNews = new AdapterCompanyNews(AcCompany.this, listNews);
		lvNews.setAdapter(adapterCompanyNews);
		
		refreshData();
	}
	
	private void refreshData() {
		getListCompanyNewsFromServer();
	}

	/**
	 * update data in screen
	 * @param listNews
	 */
	private void updateScreenData(EnNewsList listNewsLocal) {
		if(page == 1){
			// update new data
			for (int i = 0; i < listNews.size(); i++) {
				listNews.remove(i);
			}
			adapterCompanyNews.setListNew(listNewsLocal.getNews());
			adapterCompanyNews.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcCompany.this.finish();
			break;
			
		case R.id.linRefresh:
			refreshData();
			break;
			
		default:
			break;
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		EnNews enNews = adapterCompanyNews.getItem(position);
		if(null != enNews){
			Intent intent = new Intent(AcCompany.this, AcCompanyNewsDetails.class);
			intent.putExtra("com.vivi.EnNews", enNews);
			startActivity(intent);
		}
		
	}
	
	/**
	 * getListCompanyNewsFromServer
	 */
	private void getListCompanyNewsFromServer(){
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .setConverter(new StringConverter())
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		Logger.error("access-token: " + token);
		
		if(null == dialog){
			dialog = new ProgressDialog(AcCompany.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					//cancel threat 
					companyNewsCallBack.failure(null);
				}
			});
		}
		dialog.show();
		
		restAdapter.create(ViviApi.class).getPresentationsNews(token, page, perPage, companyNewsCallBack);
	}
	
	Callback<String> companyNewsCallBack = new Callback<String>() {
		
		@Override
		public void success(String responseString, Response response) {
			Logger.error("GET Company new response: " + responseString);
			if(null != AcCompany.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			try{
				EnNewsList listNews = DataParser.getEnNewsList(responseString);
				if(null != listNews){
					updateScreenData(listNews);
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcCompany.this);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			retrofitError.printStackTrace();
			if(null != AcCompany.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			if(retrofitError.isNetworkError()){
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_INTERNET_CONNECTION)
						, "Error", AcCompany.this);
			} else {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcCompany.this);
			}
		}
	};
	
}
