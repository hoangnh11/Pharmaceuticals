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
import com.viviproject.entities.EnCoverReport;
import com.viviproject.entities.EnPlanSaleRowItem;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringUtils;

@SuppressLint("InflateParams")
public class CoverProductReport extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private RadioGroup radioGroupDay;
	private RadioButton radioDay,radioCumulative;
	private TextView tvMonthDays, tvWorkingDays;
	private LinearLayout linPlanCoverDataToday, linPlanCoverDataSummary;
	
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cover_report_layout);
		initLayout();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.COVER_PRODUCT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.INVISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.INVISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		linPlanCoverDataToday = (LinearLayout) findViewById(R.id.linPlanCoverDataToday);
		linPlanCoverDataSummary = (LinearLayout) findViewById(R.id.linPlanCoverDataSummary);
		linPlanCoverDataSummary.setVisibility(View.GONE);
		
		tvMonthDays = (TextView) findViewById(R.id.tvMonthDays);
		tvWorkingDays = (TextView) findViewById(R.id.tvWorkingDays);
		
		radioDay = (RadioButton) findViewById(R.id.radioDay);
		radioCumulative = (RadioButton) findViewById(R.id.radioCumulative);
		
		radioGroupDay = (RadioGroup) findViewById(R.id.radioGroupDay);
		radioGroupDay.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//radioDay = (RadioButton) findViewById(checkedId);		
				switch (checkedId) {
				case R.id.radioDay:
					if(radioDay.isChecked()){
						linPlanCoverDataToday.setVisibility(View.VISIBLE);
						linPlanCoverDataSummary.setVisibility(View.GONE);
						Logger.error("radio day checked");
					} else {
						Logger.error("radio day unchecked");
					}
					break;
						
				case R.id.radioCumulative:
					if(radioCumulative.isChecked()){
						linPlanCoverDataToday.setVisibility(View.GONE);
						linPlanCoverDataSummary.setVisibility(View.VISIBLE);
						Logger.error("radio cumulative checked");
					} else {
						Logger.error("radio cumulative unchecked");
					}
					break;
					
				default:
					break;
				}
			}
		});
		
		refreshData();
	}

	private void refreshData() {
		try {
			getCoverReportFromServer();
		} catch (Exception e) {
			// exception when load data from server, display last data
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param enPlanSale
	 */
	private void updateDataScreen(EnCoverReport enCoverReport){
		linPlanCoverDataToday.removeAllViews();
		linPlanCoverDataSummary.removeAllViews();
		
		tvMonthDays.setText(enCoverReport.getMonth_days());
		tvWorkingDays.setText(enCoverReport.getWorking_days());
		radioDay.setText(getResources().getString(R.string.COMMON_DAY) + " " + enCoverReport.getToday().getDate());
		radioCumulative.setText(getResources().getString(R.string.COMMON_CUMULATIVE) + " " + enCoverReport.getSumary().getDate());
		
		//to day data
		if(null != enCoverReport && null != enCoverReport.getToday() 
				&& null != enCoverReport.getToday().getRow_items() && (enCoverReport.getToday().getRow_items().size() > 0)){
			for (int i = 0; i < enCoverReport.getToday().getRow_items().size(); i++) {
				View todayRow = createRowView(enCoverReport.getToday().getRow_items().get(i));
				linPlanCoverDataToday.addView(todayRow);
			}
		}
		
		//cumulative data
		if(null != enCoverReport && null != enCoverReport.getToday() 
				&& null != enCoverReport.getSumary().getRow_items() && (enCoverReport.getSumary().getRow_items().size() > 0)){
			for (int i = 0; i < enCoverReport.getSumary().getRow_items().size(); i++) {
				View row = createRowView(enCoverReport.getSumary().getRow_items().get(i));
				linPlanCoverDataSummary.addView(row);
			}
		}
	}
	
	/**
	 * createRowView
	 * @param enPlanSaleRowItem
	 * @return
	 */
	public View createRowView(EnPlanSaleRowItem enPlanSaleRowItem){
		View viewRowItem = getLayoutInflater().inflate(R.layout.cover_report_row_item, null);
		TextView tvContent = (TextView) viewRowItem.findViewById(R.id.tvContent);
		TextView tvPlan = (TextView) viewRowItem.findViewById(R.id.tvPlan);
		TextView tvDo = (TextView) viewRowItem.findViewById(R.id.tvDo);
		TextView tvComplete = (TextView) viewRowItem.findViewById(R.id.tvComplete);
		
		LinearLayout linCoverReportRow = (LinearLayout) viewRowItem.findViewById(R.id.linCoverReportRow);
		linCoverReportRow.setBackgroundColor(getResources().getColor(R.color.WHITE));
		
		if(StringUtils.isNotBlank(enPlanSaleRowItem.getName())){
			tvContent.setText(enPlanSaleRowItem.getName());
		}
		tvPlan.setText(String.valueOf(enPlanSaleRowItem.getPlan()));
		tvDo.setText(String.valueOf(enPlanSaleRowItem.getDoItem()));
		
		if(StringUtils.isNotBlank(enPlanSaleRowItem.getComplete())){
			tvComplete.setText(enPlanSaleRowItem.getComplete());
		}
		
		return viewRowItem;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;

		case R.id.linRefresh:
			refreshData();
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * get newest data from server
	 */
	private void getCoverReportFromServer(){
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		Logger.error("access-token: " + token);
		
		if(null == dialog){
			dialog = new ProgressDialog(CoverProductReport.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					//cancel 
					coverReportCallBack.failure(null);
				}
			});
		}
		dialog.show();
		
		restAdapter.create(ViviApi.class).getCoverReport(token, coverReportCallBack);
	}
	
	Callback<EnCoverReport> coverReportCallBack = new Callback<EnCoverReport>() {
		
		@Override
		public void success(EnCoverReport enPlanSale, Response response) {
			if(null != CoverProductReport.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			if(null != enPlanSale){
				updateDataScreen(enPlanSale);
				try{
					DataStorage.getInstance().save_EnCoverReport(enPlanSale, CoverProductReport.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try{
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG_LOAD_DATA_LOCAL)
							, "Error", CoverProductReport.this);
					
					EnCoverReport enCoverReport = DataStorage.getInstance().read_EnCoverReport(CoverProductReport.this);
					updateDataScreen(enCoverReport);
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
			
			if(null != CoverProductReport.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			if(null != retrofitError){
				if(retrofitError.isNetworkError()){
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG_LOAD_DATA_LOCAL)
							, "Error", CoverProductReport.this);
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", CoverProductReport.this);
				}
				
				try{
					EnCoverReport enCoverReport = DataStorage.getInstance().read_EnCoverReport(CoverProductReport.this);
					updateDataScreen(enCoverReport);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
}
