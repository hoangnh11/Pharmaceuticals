package com.viviproject.overview;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnPlanSale;
import com.viviproject.entities.EnPlanSaleRowItem;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringUtils;

@SuppressLint("InflateParams")
public class ProfitReportActivity extends Activity implements OnClickListener{

	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private RadioGroup radioGroupDay;
	private RadioButton radioDay, radioCumulative;
	private TextView tvMonthDay, tvWorkingDay;
	private LinearLayout linPlanSaleToDay, linPlanSaleSummary;
	
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profit_report_layout);
		initLayout();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.PROFIT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.INVISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.INVISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		tvMonthDay = (TextView) findViewById(R.id.tvMonthDay);
		tvWorkingDay = (TextView) findViewById(R.id.tvWorkingDay);
		
		linPlanSaleToDay = (LinearLayout) findViewById(R.id.linPlanSaleToDay);
		linPlanSaleSummary = (LinearLayout) findViewById(R.id.linPlanSaleSummary);
		linPlanSaleSummary.setVisibility(View.GONE);
		
		radioDay = (RadioButton) findViewById(R.id.radioDay1);
		radioCumulative = (RadioButton) findViewById(R.id.radioDay2);
		radioGroupDay = (RadioGroup) findViewById(R.id.radioGroupDay);
		radioGroupDay.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radioDay1:
					if(radioDay.isChecked()){
						linPlanSaleToDay.setVisibility(View.VISIBLE);
						linPlanSaleSummary.setVisibility(View.GONE);
						Logger.error("radio day checked");
					} else {
						Logger.error("radio day unchecked");
					}
					break;
						
				case R.id.radioDay2:
					if(radioCumulative.isChecked()){
						linPlanSaleToDay.setVisibility(View.GONE);
						linPlanSaleSummary.setVisibility(View.VISIBLE);
						Logger.error("radio cumulative checked");
					} else {
						Logger.error("radio cumulative unchecked");
					}
					break;
					
				default:
					break;
				}
				//RadioButton radio = (RadioButton) findViewById(checkedId);				
			}
		});
		
		refreshData(false);
	}

	private void refreshData(boolean isGetNewest) {
		try {
			getPlanSaleFromServer();
		} catch (Exception e) {
			// exception when load data from server, display last data
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param enPlanSale
	 */
	private void updateDataScreen(EnPlanSale enPlanSale){
		linPlanSaleToDay.removeAllViews();
		linPlanSaleSummary.removeAllViews();
		
		tvMonthDay.setText(enPlanSale.getMonth_days());
		tvWorkingDay.setText(enPlanSale.getWorking_days());
		radioDay.setText(getResources().getString(R.string.COMMON_DAY) + " " + enPlanSale.getToday().getDate());
		radioCumulative.setText(getResources().getString(R.string.COMMON_CUMULATIVE) + " " + enPlanSale.getSumary().getDate());
		
		//to day data
		View todaySummary = createSummaryView(enPlanSale.getToday().getRow_sumary());
		linPlanSaleToDay.addView(todaySummary);
		if(null != enPlanSale && null != enPlanSale.getToday() 
				&& null != enPlanSale.getToday().getRow_items() && (enPlanSale.getToday().getRow_items().size() > 0)){
			for (int i = 0; i < enPlanSale.getToday().getRow_items().size(); i++) {
				View todayRow = createRowView(enPlanSale.getToday().getRow_items().get(i));
				linPlanSaleToDay.addView(todayRow);
			}
		}
		View todayCustomer = createCustommerView(enPlanSale.getToday().getRow_customer());
		linPlanSaleToDay.addView(todayCustomer);
		
		//to day data
		View summary = createSummaryView(enPlanSale.getSumary().getRow_sumary());
		linPlanSaleSummary.addView(summary);
		if(null != enPlanSale && null != enPlanSale.getToday() 
				&& null != enPlanSale.getSumary().getRow_items() && (enPlanSale.getSumary().getRow_items().size() > 0)){
			for (int i = 0; i < enPlanSale.getSumary().getRow_items().size(); i++) {
				View row = createRowView(enPlanSale.getSumary().getRow_items().get(i));
				linPlanSaleSummary.addView(row);
			}
		}
		View custommerView = createCustommerView(enPlanSale.getSumary().getRow_customer());
		linPlanSaleSummary.addView(custommerView);
	}
	
	/**
	 * createSummaryView
	 * @param enPlanSaleRowItem
	 * @return
	 */
	public View createSummaryView(EnPlanSaleRowItem enPlanSaleRowItem){
		View viewTodaySummary = getLayoutInflater().inflate(R.layout.plan_sale_summary, null);
		TextView tvSummaryName = (TextView) viewTodaySummary.findViewById(R.id.tvSummaryName);
		TextView tvSummaryPlan = (TextView) viewTodaySummary.findViewById(R.id.tvSummaryPlan);
		TextView tvSummaryDo = (TextView) viewTodaySummary.findViewById(R.id.tvSummaryDo);
		TextView tvSummaryLeft = (TextView) viewTodaySummary.findViewById(R.id.tvSummaryLeft);
		TextView tvSummaryComplete = (TextView) viewTodaySummary.findViewById(R.id.tvSummaryComplete);
		
		if(StringUtils.isNotBlank(enPlanSaleRowItem.getName())){
			tvSummaryName.setText(enPlanSaleRowItem.getName());
		}
		tvSummaryPlan.setText(String.valueOf(enPlanSaleRowItem.getPlan()));
		tvSummaryDo.setText(String.valueOf(enPlanSaleRowItem.getDoItem()));
		tvSummaryLeft.setText(String.valueOf(enPlanSaleRowItem.getLeft()));
		if(StringUtils.isNotBlank(enPlanSaleRowItem.getComplete())){
			tvSummaryComplete.setText(enPlanSaleRowItem.getComplete());
		}
		
		return viewTodaySummary;
	}
	
	/**
	 * createRowView
	 * @param enPlanSaleRowItem
	 * @return
	 */
	public View createRowView(EnPlanSaleRowItem enPlanSaleRowItem){
		View viewRowItem = getLayoutInflater().inflate(R.layout.plan_sale_summary, null);
		TextView tvSummaryName = (TextView) viewRowItem.findViewById(R.id.tvSummaryName);
		TextView tvSummaryPlan = (TextView) viewRowItem.findViewById(R.id.tvSummaryPlan);
		TextView tvSummaryDo = (TextView) viewRowItem.findViewById(R.id.tvSummaryDo);
		TextView tvSummaryLeft = (TextView) viewRowItem.findViewById(R.id.tvSummaryLeft);
		TextView tvSummaryComplete = (TextView) viewRowItem.findViewById(R.id.tvSummaryComplete);
		LinearLayout linRowItem = (LinearLayout) viewRowItem.findViewById(R.id.linRowItem);
		linRowItem.setBackgroundColor(getResources().getColor(R.color.WHITE));
		
		if(StringUtils.isNotBlank(enPlanSaleRowItem.getName())){
			tvSummaryName.setText(enPlanSaleRowItem.getName());
		}
		tvSummaryPlan.setText(String.valueOf(enPlanSaleRowItem.getPlan()));
		tvSummaryDo.setText(String.valueOf(enPlanSaleRowItem.getDoItem()));
		tvSummaryLeft.setText(String.valueOf(enPlanSaleRowItem.getLeft()));
		if(StringUtils.isNotBlank(enPlanSaleRowItem.getComplete())){
			tvSummaryComplete.setText(enPlanSaleRowItem.getComplete());
		}
		
		return viewRowItem;
	}
	
	/**
	 * createCustommerView
	 * @param enPlanSaleRowItem
	 * @return
	 */
	public View createCustommerView(EnPlanSaleRowItem enPlanSaleRowItem){
		View viewRowCustommer = getLayoutInflater().inflate(R.layout.plan_sale_summary, null);
		TextView tvSummaryName = (TextView) viewRowCustommer.findViewById(R.id.tvSummaryName);
		TextView tvSummaryPlan = (TextView) viewRowCustommer.findViewById(R.id.tvSummaryPlan);
		TextView tvSummaryDo = (TextView) viewRowCustommer.findViewById(R.id.tvSummaryDo);
		TextView tvSummaryLeft = (TextView) viewRowCustommer.findViewById(R.id.tvSummaryLeft);
		TextView tvSummaryComplete = (TextView) viewRowCustommer.findViewById(R.id.tvSummaryComplete);
		LinearLayout linRowItem = (LinearLayout) viewRowCustommer.findViewById(R.id.linRowItem);
		linRowItem.setBackgroundColor(getResources().getColor(R.color.PINK));
		
		if(StringUtils.isNotBlank(enPlanSaleRowItem.getName())){
			tvSummaryName.setText(enPlanSaleRowItem.getName());
		}
		tvSummaryPlan.setText(String.valueOf(enPlanSaleRowItem.getPlan()));
		tvSummaryDo.setText(String.valueOf(enPlanSaleRowItem.getDoItem()));
		tvSummaryLeft.setText(String.valueOf(enPlanSaleRowItem.getLeft()));
		if(StringUtils.isNotBlank(enPlanSaleRowItem.getComplete())){
			tvSummaryComplete.setText(enPlanSaleRowItem.getComplete());
		}
		
		return viewRowCustommer;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;

		case R.id.linRefresh:
			refreshData(true);
			break;
		default:
			break;
		}
	}

	/**
	 * get newest data from server
	 */
	private void getPlanSaleFromServer(){
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		Logger.error("access-token: " + token);
		
		if(null == dialog){
			dialog = new ProgressDialog(ProfitReportActivity.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					//cancel 
					planSaleCallBack.failure(null);
				}
			});
		}
		dialog.show();
		
		restAdapter.create(ViviApi.class).getPlanSale(token, planSaleCallBack);
	}
	
	Callback<EnPlanSale> planSaleCallBack = new Callback<EnPlanSale>() {
		
		@Override
		public void success(EnPlanSale enPlanSale, Response response) {
			if(null != ProfitReportActivity.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			if(null != enPlanSale){
				updateDataScreen(enPlanSale);
				try{
					DataStorage.getInstance().save_EnPlanSale(enPlanSale, ProfitReportActivity.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try{
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG_LOAD_DATA_LOCAL)
							, "Error", ProfitReportActivity.this);
					
					EnPlanSale enPlanSaleLocal = DataStorage.getInstance().read_EnPlanSale(ProfitReportActivity.this);
					if(null != enPlanSaleLocal){
						updateDataScreen(enPlanSaleLocal);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			retrofitError.printStackTrace();
			Logger.error("Get plan sale error:" + retrofitError.getMessage());
			
			if(null != ProfitReportActivity.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			if(null != retrofitError){
				if(retrofitError.isNetworkError()){
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG_LOAD_DATA_LOCAL)
							, "Error", ProfitReportActivity.this);
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", ProfitReportActivity.this);
				}
				
				try{
					EnPlanSale enPlanSale = DataStorage.getInstance().read_EnPlanSale(ProfitReportActivity.this);
					if(null != enPlanSale){
						updateDataScreen(enPlanSale);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
}
