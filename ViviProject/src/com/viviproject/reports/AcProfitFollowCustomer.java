package com.viviproject.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.viviproject.R;
import com.viviproject.adapter.AdapterProfitFollowCustomer;
import com.viviproject.adapter.AdapterSpProducts;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.EnStoreReport;
import com.viviproject.entities.EnStoreReportItem;
import com.viviproject.entities.Products;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringConverter;

public class AcProfitFollowCustomer extends FragmentActivity implements OnClickListener, OnItemClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ListView lvProfitFollowCustomer;
	private ImageView imgBackToTop;
	private Spinner spProductProfitView, spSortType;
	private ImageView imgIconCalendar;
	
	private CaldroidListener listener;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");;
	private CaldroidFragment dialogCaldroidFragment;
	
	private ArrayList<EnStoreReportItem> listCustomer = new ArrayList<EnStoreReportItem>();
	private List<String> listSortType = new ArrayList<String>();
	private ArrayList<EnProducts> lisProduct = new ArrayList<EnProducts>();
	private AdapterSpProducts adapterSpProducts;
	private AdapterProfitFollowCustomer adapterProfitFollowCustomer;
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	private Date dateFrom, dateTo;
	private int page = 0, perPage = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_profit_follow_customer);
		setDateDataToCurrent();
		initLayout();
		refreshLayout();
	}

	private void setDateDataToCurrent() {
		try {
			Calendar calendar = Calendar.getInstance();
			dateTo = calendar.getTime();
			
			calendar.add(Calendar.MONTH, -1);
			dateFrom = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * initial layout of screen
	 */
	private void initLayout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.PROFIT_FOLLOW_CUSTOMER));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		imgIconCalendar = (ImageView) findViewById(R.id.imgIconCalendar);
		imgIconCalendar.setOnClickListener(this);
		
		spSortType = (Spinner) findViewById(R.id.spSortType);
		String[] sort_type = getResources().getStringArray(R.array.sort_type);
		listSortType = Arrays.asList(sort_type);
		ArrayAdapter<String> sortTypeAdaper = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listSortType);
		spSortType.setAdapter(sortTypeAdaper);
		
		spProductProfitView = (Spinner) findViewById(R.id.spProducts);
		adapterSpProducts = new AdapterSpProducts(AcProfitFollowCustomer.this, lisProduct);
		spProductProfitView.setAdapter(adapterSpProducts);
		
		lvProfitFollowCustomer = (ListView) findViewById(R.id.lvProfitFllowCustomer);
		adapterProfitFollowCustomer = new AdapterProfitFollowCustomer(getApplicationContext(), listCustomer);
		lvProfitFollowCustomer.setAdapter(adapterProfitFollowCustomer);
		lvProfitFollowCustomer.setOnItemClickListener(this);
		
		imgBackToTop = (ImageView) findViewById(R.id.imgBackToTop);
		imgBackToTop.setOnClickListener(this);
		
	}
	
	private void refreshLayout(){
		getDataFromServer();
	}
	
	protected void updateProductType(Products enProducts){
		if(null == enProducts) return;
		
		ArrayList<EnProducts> lisProduct = enProducts.getProducts(); 
		adapterSpProducts.setListProduct(lisProduct);
		adapterSpProducts.notifyDataSetChanged();
		
	}
	
	protected void updateStoreReportScreen(ArrayList<EnStoreReportItem> stores) {
		if(null == stores) return;
		
		adapterProfitFollowCustomer.setListProfitCustomer(stores);
		adapterProfitFollowCustomer.notifyDataSetChanged();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcProfitFollowCustomer.this.finish();
			break;
			
		case R.id.imgBackToTop:
			lvProfitFollowCustomer.setSelectionAfterHeaderView();
			break;
			
		case R.id.imgIconCalendar:
			showCalender();
			break;
			
		default:
			break;
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(AcProfitFollowCustomer.this, AcSalesOnCustomer.class);
		startActivity(i);
	}
	
	@SuppressLint("SimpleDateFormat")
	private void showCalender() {
		listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				dialogCaldroidFragment.dismiss();
				
			}

			@Override
			public void onChangeMonth(int month, int year) {
			}

			@Override
			public void onLongClickDate(Date date, View view) {
			}

			@Override
			public void onCaldroidViewCreated() {
				if (dialogCaldroidFragment.getLeftArrowButton() != null) {
				}
			}

			@Override
			public void onNothingSelected() {
			}

		};
		
		dialogCaldroidFragment = new CaldroidFragment();
		dialogCaldroidFragment.setCaldroidListener(listener);
		dialogCaldroidFragment.show(getSupportFragmentManager(),
				"CalenderAndroid");
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
		
		if(null == dialog){
			dialog = new ProgressDialog(AcProfitFollowCustomer.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					callbackProducts.failure(null);
				}
			});
			
			dialog.show();
		} else {
			if(!dialog.isShowing()){
				dialog.show();
			}
		}
		
		restAdapter.create(ViviApi.class).getProducts(token, callbackProducts);
	}
	
	Callback<String> callbackProducts = new Callback<String>() {
		
		@Override
		public void success(String strData, Response response) {
			Logger.error("Get Products: #success: " + strData);
			try {
				Products enProducts = DataParser.getProducts(strData);
				if(null != enProducts){
					updateProductType(enProducts);
					//get list product data
					getListReportProductFromServer("0", "name");
				} else {
					if(null != AcProfitFollowCustomer.this && null != dialog){
						if(dialog.isShowing()) dialog.dismiss();
					}
					
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcProfitFollowCustomer.this);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(null != AcProfitFollowCustomer.this && null != dialog){
					if(dialog.isShowing()) dialog.dismiss();
				}
				
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcProfitFollowCustomer.this);
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			if(null == retrofitError) return;
			
			retrofitError.printStackTrace();
			if(null != AcProfitFollowCustomer.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			if(retrofitError.isNetworkError()){
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_INTERNET_CONNECTION)
						, "Error", AcProfitFollowCustomer.this);
			} else {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcProfitFollowCustomer .this);
			}
		}
	};
	
	public void getListReportProductFromServer(String productId, String sortBy){
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .setConverter(new StringConverter())
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		
		if(null == dialog){
			dialog = new ProgressDialog(AcProfitFollowCustomer.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					callbackProducts.failure(null);
				}
			});
			
			dialog.show();
		} else {
			if(!dialog.isShowing()){
				dialog.show();
			}
		}
		
		String sdtDateFrom = "04/07/2015";
		String sdtDateTo = "04/08/2015";
		try {
			sdtDateFrom = formatter.format(dateFrom);
			sdtDateTo = formatter.format(dateTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		restAdapter.create(ViviApi.class).getRealueByStore(token, sdtDateFrom,
				sdtDateTo, productId, sortBy, page, perPage,
				callbackRevalueByStore);
	}
	
	Callback<String> callbackRevalueByStore = new Callback<String>() {
		
		@Override
		public void success(String strData, Response arg1) {
			Logger.error("Get list revelute success:" + strData);
			if(null != AcProfitFollowCustomer.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			try {
				EnStoreReport enStoreReport = DataParser.getEnStoreReport(strData);
				if(null != enStoreReport){
					if(null != enStoreReport.getStores()){
						updateStoreReportScreen(enStoreReport.getStores());
					}
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcProfitFollowCustomer .this);
				}
			} catch (Exception e) {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcProfitFollowCustomer .this);
			}
			
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			if(null == retrofitError) return;
			
			retrofitError.printStackTrace();
			if(null != AcProfitFollowCustomer.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			if(retrofitError.isNetworkError()){
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_INTERNET_CONNECTION)
						, "Error", AcProfitFollowCustomer.this);
			} else {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcProfitFollowCustomer .this);
			}
		}
	};
}
