package com.viviproject.reports;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.viviproject.R;
import com.viviproject.entities.EnReportGroupItem;
import com.viviproject.entities.EnReportProductItem;
import com.viviproject.entities.EnReportProfitResponse;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringConverter;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class AcTotalSales extends FragmentActivity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private TextView tvTimeFrom,tvTimeTo;
	private ImageView imgIconCalendarFrom, imgIconCalendarTo;
	private Button btRevalueSearchOK;
	private LinearLayout linReportProfit;
	private TextView tvTotalSales;
	
 	private CaldroidListener listener;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private CaldroidFragment dialogCaldroidFragment;
	private Date strDateFrom;
	private Date strDateTo;
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_total_sales);
		getCurrentDate();
		
		initLayout();
	}

	/**
	 * initial layout of screen
	 */
	private void initLayout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.SUM_PROFIT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.INVISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.INVISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		linOptionRefresh.setOnClickListener(this);
		
		tvTimeFrom = (TextView) findViewById(R.id.tvTimeFrom);
		tvTimeFrom.setText("" + formatter.format(strDateFrom));
		
		tvTimeTo = (TextView) findViewById(R.id.tvTimeTo);
		tvTimeTo.setText("" + formatter.format(strDateTo));
		
		imgIconCalendarFrom = (ImageView) findViewById(R.id.imgIconCalendarFrom);
		imgIconCalendarFrom.setOnClickListener(this);
		
		imgIconCalendarTo = (ImageView) findViewById(R.id.imgIconCalendarTo);
		imgIconCalendarTo.setOnClickListener(this);
		
		btRevalueSearchOK = (Button) findViewById(R.id.btRevalueSearchOK);
		btRevalueSearchOK.setOnClickListener(this);
		
		linReportProfit = (LinearLayout) findViewById(R.id.linReportProfit);
		tvTotalSales = (TextView) findViewById(R.id.tvTotalSales);
		refreshData();
	}

	/**
	 * get current date and update to from field and to field
	 */
	public void getCurrentDate(){
		try {
			Calendar calendar = Calendar.getInstance();
			strDateTo = calendar.getTime();
			
			calendar.add(Calendar.MONTH, -1);
			strDateFrom = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * refresh data
	 */
	private void refreshData() {
		getDataFromServer();
	}

	protected void updateDataScreen(EnReportProfitResponse enResponse) {
		if(null == enResponse) return;
		
		tvTotalSales.setText(getResources().getString(R.string.SUM_PROFIT_LABLE) + " "+ enResponse.getTotal_sale());
		
		linReportProfit.removeAllViews();
		for (int i = 0; i < enResponse.getGroups().size(); i++) {
			View view = getRowViewData(enResponse.getGroups().get(i));
			linReportProfit.addView(view);
		}
		
	}
	
	private View getRowViewData(EnReportGroupItem enReportGroupItem){
		View view = getLayoutInflater().inflate(R.layout.item_report_profit_sale, null);
		LinearLayout linItemRow = (LinearLayout) view.findViewById(R.id.linItemRow);
		linItemRow.setBackgroundColor(getResources().getColor(R.color.OCEAN_BLUE));
		
		TextView tvProductName = (TextView) view.findViewById(R.id.tvItemProduct);
		TextView tvProductNumber = (TextView) view.findViewById(R.id.tvItemProductNumber);
		TextView tvProductProfit = (TextView) view.findViewById(R.id.tvItemProductProfit);
		LinearLayout linIemRowChile = (LinearLayout) view.findViewById(R.id.linIemRowChile);
		if(null != enReportGroupItem){
			tvProductName.setText(enReportGroupItem.getName());
			tvProductNumber.setText("" + enReportGroupItem.getQuantity());
			tvProductProfit.setText(enReportGroupItem.getSale());
			
			for (EnReportProductItem productItem : enReportGroupItem.getProducts()) {
				View rowChildView = getRowViewDataChild(productItem);
				linIemRowChile.addView(rowChildView);
			}
		}
		return view;
	}
	
	private View getRowViewDataChild(EnReportProductItem enReportProductItem){
		View view = getLayoutInflater().inflate(R.layout.item_reprot_profit_child, null);
		
		TextView tvProductName = (TextView) view.findViewById(R.id.tvItemProduct);
		TextView tvProductNumber = (TextView) view.findViewById(R.id.tvItemProductNumber);
		TextView tvProductProfit = (TextView) view.findViewById(R.id.tvItemProductProfit);
		
		if(null != enReportProductItem){
			tvProductName.setText(enReportProductItem.getName());
			tvProductNumber.setText("" + enReportProductItem.getQuantity());
			tvProductProfit.setText(enReportProductItem.getSale());
		}
		return view;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcTotalSales.this.finish();
			break;
		
		case R.id.btRevalueSearchOK:
			refreshData();
			break;
			
		case R.id.imgIconCalendarFrom:
			showCalender(R.id.imgIconCalendarFrom);
			break;
		
		case R.id.imgIconCalendarTo:
			showCalender(R.id.imgIconCalendarTo);
			break;
			
		case R.id.linRefresh:
			getCurrentDate();
			tvTimeFrom.setText("" + formatter.format(strDateFrom));
			tvTimeTo.setText("" + formatter.format(strDateTo));
			refreshData();
			break;
			
		default:
			break;
		}
		
	}
	
	@SuppressLint("SimpleDateFormat")
	private void showCalender(final int viewId) {
		listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				dialogCaldroidFragment.dismiss();
				
				if(!BuManagement.compareDateWithToDay(date)){
					BuManagement.alertErrorMessageString(getResources().getString(R.string.MSG_OVER_CURRENT_DATE)
							, "Error", AcTotalSales.this);
					return;
				}
				
				switch (viewId) {
				case R.id.imgIconCalendarTo:
					strDateTo = date;
					tvTimeTo.setText("" + formatter.format(strDateTo));
					break;
					
				case R.id.imgIconCalendarFrom:
					if(BuManagement.compareDateWithDay(date, strDateTo)){
						strDateFrom = date;
						tvTimeFrom.setText("" + formatter.format(strDateFrom));
					} else {
						BuManagement.alertErrorMessageString(getResources().getString(R.string.MSG_OVER_TO_DATE)
								, "Error", AcTotalSales.this);
					}
					break;

				default:
					break;
				}
				
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
	
	private void getDataFromServer() {
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .setConverter(new StringConverter())
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		
		if(null == dialog){
			dialog = new ProgressDialog(AcTotalSales.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					myCallback.failure(null);
				}
			});
		}
		dialog.show();
		
		String fromStr = formatter.format(strDateFrom);
		String toStr = formatter.format(strDateTo);
		
		restAdapter.create(ViviApi.class).getTotalSale(token, fromStr, toStr, myCallback);
		
	}
	
	Callback<String> myCallback = new Callback<String>() {
		
		@Override
		public void success(String strData, Response arg1) {
			Logger.error("Get Total Sale: #success: " + strData);
			if(null != AcTotalSales.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			try {
				EnReportProfitResponse reportProfitResponse = DataParser.getEnReportProfitResponse(strData);
				if(null != reportProfitResponse){
					if(null != reportProfitResponse.getGroups() && (reportProfitResponse.getGroups().size() > 0)){
						updateDataScreen(reportProfitResponse);
					} else {
						BuManagement.alertErrorMessageString(reportProfitResponse.getMessage()
								, "Error", AcTotalSales.this);
					}
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcTotalSales.this);
				}
			} catch (Exception e) {
				e.printStackTrace();
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcTotalSales.this);
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			if(null == retrofitError) return;
			
			retrofitError.printStackTrace();
			if(null != AcTotalSales.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			if(retrofitError.isNetworkError()){
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_INTERNET_CONNECTION)
						, "Error", AcTotalSales.this);
			} else {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcTotalSales.this);
			}
		}
	};
}
