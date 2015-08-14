package com.viviproject.reports;

import java.text.DecimalFormat;
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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.viviproject.R;
import com.viviproject.entities.EnItemDataChart;
import com.viviproject.entities.EnReportChartResponse;
import com.viviproject.entities.EnReportDay;
import com.viviproject.entities.EnReportMonth;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.MyValueFormatter;
import com.viviproject.ultilities.StringConverter;

@SuppressLint("SimpleDateFormat")
public class AcSalesChart extends FragmentActivity implements OnClickListener, OnChartValueSelectedListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private BarChart mChartProductByDay;
	private BarChart mChartProductByMonth;
	private Spinner spMonth, spYear;
	private ImageView imgIconCalendar;
	private TextView tvTime;
	private Button btOK;
	
	private CaldroidListener listener;
	private SimpleDateFormat formatterDay = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formatterMonth = new SimpleDateFormat("MM/yyyy");
	private CaldroidFragment dialogCaldroidFragment;
	
	private List<String> listMonth, listYear;
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	private String strDay;
	private String strMonth;
	private boolean flagCalenDerDialodgIsShowing = false;
	private EnReportChartResponse chartResponse;
	private int positionDataToSave = 0;
	
	//key for oriontation change layout 
	private String SELECTED_MONTH = "SELECTED_MONTH";
	private String SELECTED_DAY = "SELECTED_DAY";
	private String KEY_EN_REPORT_CHART_RESPONSE = "KEY_EN_REPORT_CHART_RESPONSE";
	private String KEY_POSITION_DATA_TO_SAVE = "KEY_POSITION_DATA_TO_SAVE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_sales_chart);
		
		if(null != savedInstanceState){
			initLayout();
			initOrientationLayout(savedInstanceState);
		} else {
			getCurrentDate();
			initLayout();
			initData();
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
		tvHeader.setText(getResources().getString(R.string.PROFIT_GRAPHIC));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.INVISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.INVISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		linOptionRefresh.setOnClickListener(this);
		
		imgIconCalendar = (ImageView) findViewById(R.id.imgIconCalendar);
		imgIconCalendar.setOnClickListener(this);
		
		tvTime = (TextView) findViewById(R.id.tvTime);
		
		btOK =(Button) findViewById(R.id.btOK);
		btOK.setOnClickListener(this);
		
		spMonth = (Spinner) findViewById(R.id.spMonth);
		spYear = (Spinner) findViewById(R.id.spYear);
		mChartProductByDay = (BarChart) findViewById(R.id.chartProduct);
		setUpChartByDay();
		mChartProductByMonth = (BarChart) findViewById(R.id.chartProductByMonth);
		setUpChartByMonth();
	}
	
	/**
	 * initial layout of screen
	 */
	private void initData() {
		tvTime.setText(String.format(getResources().getString(R.string.DAY_TIME), strDay));
		
		String[] month = getResources().getStringArray(R.array.month);
		listMonth = Arrays.asList(month);
		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listMonth);
		spMonth.setAdapter(monthAdapter);
		
		String[] year = getResources().getStringArray(R.array.year);
		listYear = Arrays.asList(year);
		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listYear);
		spYear.setAdapter(yearAdapter);
		
		try {
			Calendar calendar = Calendar.getInstance();
			int positionMonth = calendar.get(Calendar.MONTH);
			spMonth.setSelection(positionMonth);
			
			int currentYear = calendar.get(Calendar.YEAR);	
			int startYear = Integer.parseInt(listYear.get(0));
			Logger.error("currentYear:" + currentYear + "*startYear:" + startYear);
			int positionYear = currentYear - startYear;
			spYear.setSelection(positionYear);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		refreshData();
	}
	
	public void initOrientationLayout(Bundle savedInstanceState){
		if(savedInstanceState.containsKey("SELECTED_DAY")){
			strDay = savedInstanceState.getString("SELECTED_DAY");
			tvTime.setText(String.format(getResources().getString(R.string.DAY_TIME), strDay));
		}
		
		if(savedInstanceState.containsKey(SELECTED_MONTH)){
			strMonth = savedInstanceState.getString(SELECTED_MONTH);
		}
		
		if(savedInstanceState.containsKey(KEY_POSITION_DATA_TO_SAVE)){
			positionDataToSave = savedInstanceState.getInt(KEY_POSITION_DATA_TO_SAVE);
		}
		
		String[] month = getResources().getStringArray(R.array.month);
		listMonth = Arrays.asList(month);
		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listMonth);
		spMonth.setAdapter(monthAdapter);
		
		String[] year = getResources().getStringArray(R.array.year);
		listYear = Arrays.asList(year);
		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listYear);
		spYear.setAdapter(yearAdapter);
		
		try {
			String[] dateSplit = strDay.split("/");
			int monthIntent = Integer.parseInt(dateSplit[1]);
			int yearIntent = Integer.parseInt(dateSplit[2]);
			
			spMonth.setSelection(monthIntent);
			
			int startYear = Integer.parseInt(listYear.get(0));
			int positionYear = yearIntent - startYear;
			spYear.setSelection(positionYear);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(savedInstanceState.containsKey(KEY_EN_REPORT_CHART_RESPONSE)){
			chartResponse = (EnReportChartResponse) savedInstanceState.getSerializable(KEY_EN_REPORT_CHART_RESPONSE);
			if(null != chartResponse){
				updateDataScreen(chartResponse);
			}
		}
		
	}
	
	/**
	 * get current date and update to from field and to field
	 */
	public void getCurrentDate(){
		try {
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			
			strDay = formatterDay.format(date);
			strMonth = formatterMonth.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onResume() {
		if(null != dialogCaldroidFragment) {
			try {
				if(flagCalenDerDialodgIsShowing){
					dialogCaldroidFragment.show(getSupportFragmentManager(),
							"CalenderAndroid");
				} else {
					dialogCaldroidFragment.dismiss();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onResume();
	}
	
	@Override
    protected void onSaveInstanceState(Bundle saveState) {
		saveState.putString(SELECTED_DAY,strDay);
		saveState.putString(SELECTED_MONTH, strMonth);
		saveState.putInt(KEY_POSITION_DATA_TO_SAVE, positionDataToSave);
		
		if(null != chartResponse){
			saveState.putSerializable(KEY_EN_REPORT_CHART_RESPONSE, chartResponse);
		}
		
		super.onSaveInstanceState(saveState);
	}
	
	public void refreshData(){
		getDataFromServer();
	}
	
	/**
	 * updateDataScreen
	 * @param chartResponse
	 */
	protected void updateDataScreen(EnReportChartResponse chartResponse) {
		if(null == chartResponse) return;
		
		// update data on day
		EnReportDay enReportDay = chartResponse.getDay();
		if(null != enReportDay){
			ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
			ArrayList<String> labels = new ArrayList<String>();
			
			for (int i = 0; i < enReportDay.getChart_data().size(); i++) {
				EnItemDataChart enItemDataChart = enReportDay.getChart_data().get(i);
				entries.add(new BarEntry(enItemDataChart.getValue_percent(), i));
				labels.add(enItemDataChart.getName());
			}
			
			BarDataSet dataset = new BarDataSet(entries, getResources().getString(R.string.PROFIT));
			dataset.setColors(ColorTemplate.COLORFUL_COLORS);
			dataset.setValueTextSize(10f);
			dataset.setValueFormatter(new ValueFormatter() {
				
				@Override
				public String getFormattedValue(float value) {
					DecimalFormat mFormat = new DecimalFormat("###,###,###,##0");
					return mFormat.format(value);
				}
			});
			BarData data = new BarData(labels, dataset);
			mChartProductByDay.setData(data);
			mChartProductByDay.invalidate();
		}
		
		// update data on month
		EnReportMonth enReportMonth = chartResponse.getMonth();
		if(null != enReportMonth){
			ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
			ArrayList<String> labels = new ArrayList<String>();
			
			for (int i = 0; i < enReportMonth.getChart_data().size(); i++) {
				EnItemDataChart enItemDataChart = enReportMonth.getChart_data().get(i);
				entries.add(new BarEntry(enItemDataChart.getValue_percent(), i));
				labels.add(enItemDataChart.getName());
			}
			
			BarDataSet dataset = new BarDataSet(entries, getResources().getString(R.string.PROFIT));
			dataset.setColors(ColorTemplate.COLORFUL_COLORS);
			dataset.setValueTextSize(10f);
			dataset.setValueFormatter(new ValueFormatter() {
				
				@Override
				public String getFormattedValue(float value) {
					DecimalFormat mFormat = new DecimalFormat("###,###,###,##0");
					return mFormat.format(value);
				}
			});
			BarData data = new BarData(labels, dataset);
			mChartProductByMonth.setData(data);
			mChartProductByMonth.invalidate();
		}
	}
	
	/**
	 * setUpChartByDay
	 */
	private void setUpChartByDay(){
		//mChart.setBackgroundColor(Color.WHITE);
		mChartProductByDay.setDrawBarShadow(false);
        mChartProductByDay.setDrawValueAboveBar(true);
        mChartProductByDay.setDescription("");
        mChartProductByDay.setDrawBorders(false);
        mChartProductByDay.setDoubleTapToZoomEnabled(false);
        mChartProductByDay.setScaleEnabled(false);
        
        LimitLine limitLine = new LimitLine(100f, getResources().getString(R.string.PASS));
        limitLine.setLineWidth(0.5f);
        limitLine.setLineColor(Color.BLACK);
        limitLine.enableDashedLine(10, 10, 0);
        limitLine.setLabelPosition(LimitLabelPosition.POS_RIGHT);
        limitLine.setTextSize(10f);
        
        YAxis leftAxis = mChartProductByDay.getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setDrawGridLines(false);
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(limitLine);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinValue(110);
        leftAxis.setSpaceTop(10);
        ValueFormatter valueFormatter = new MyValueFormatter();
        leftAxis.setValueFormatter(valueFormatter);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        
        YAxis rightAxis = mChartProductByDay.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        
        XAxis xAxis  = mChartProductByDay.getXAxis();
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        
        //setData(true, 12, 50);
	}
	
	/**
	 * setUpChartByDay
	 */
	private void setUpChartByMonth(){
		//mChartProductByMonth.setBackgroundColor(Color.WHITE);
		mChartProductByMonth.setDrawBarShadow(false);
		mChartProductByMonth.setDrawValueAboveBar(true);
		mChartProductByMonth.setDescription("");
		mChartProductByMonth.setDrawBorders(false);
		mChartProductByMonth.setDoubleTapToZoomEnabled(false);
		mChartProductByMonth.setScaleEnabled(false);
		
        LimitLine limitLine = new LimitLine(80f, getResources().getString(R.string.PASS));
        limitLine.setLineWidth(0.5f);
        limitLine.setLineColor(Color.BLACK);
        limitLine.enableDashedLine(10, 10, 0);
        limitLine.setLabelPosition(LimitLabelPosition.POS_RIGHT);
        limitLine.setTextSize(10f);
        
        YAxis leftAxis = mChartProductByMonth.getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setDrawGridLines(false);
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(limitLine);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinValue(110);
        leftAxis.setSpaceTop(10);
        ValueFormatter valueFormatter = new MyValueFormatter();
        leftAxis.setValueFormatter(valueFormatter);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        
        YAxis rightAxis = mChartProductByMonth.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        
        XAxis xAxis  = mChartProductByMonth.getXAxis();
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        //setData(false, 12, 50);
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcSalesChart.this.finish();
			break;
			
		case R.id.imgIconCalendar:
			try{
				String[] dateSplit = strDay.split("/");
				int month = Integer.parseInt(dateSplit[1]);
				int year = Integer.parseInt(dateSplit[2]);
				showCalender(month, year);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case R.id.btOK:
			String textMonth = (String) spMonth.getSelectedItem();
			String textYear = (String) spYear.getSelectedItem();
			strMonth = textMonth + "/" + textYear;
			getDataFromServer();
			break;
			
		case R.id.linRefresh:
			try {
				getCurrentDate();
				tvTime.setText(String.format(getResources().getString(R.string.DAY_TIME), strDay));
				
				Calendar calendar = Calendar.getInstance();
				int positionMonth = calendar.get(Calendar.MONTH);
				spMonth.setSelection(positionMonth);
				
				int currentYear = calendar.get(Calendar.YEAR);	
				int startYear = Integer.parseInt(listYear.get(0));
				Logger.error("currentYear:" + currentYear + "*startYear:" + startYear);
				int positionYear = currentYear - startYear;
				spYear.setSelection(positionYear);
				
				positionDataToSave = 0;
				getDataFromServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		default:
			break;
		}
		
	}

	/**
	 * valid date
	 * @param mDate date to check valid
	 * @return return true if date before to day and false otherwise
	 */
	public boolean compareDateWithToDay(Date mDate){
		boolean isBefore = false;
		try {
			Calendar calendar = Calendar.getInstance();
			if(mDate.compareTo(calendar.getTime()) > 0){
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isBefore;
	}
	
	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		
	}

	@Override
	public void onNothingSelected() {
		
	}
	
	@SuppressLint("SimpleDateFormat")
	private void showCalender(int month, int year) {
		listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				dialogCaldroidFragment.dismiss();
				flagCalenDerDialodgIsShowing = false;
				
				if(compareDateWithToDay(date)){
					strDay = formatterDay.format(date);
					tvTime.setText(String.format(getResources().getString(R.string.DAY_TIME), strDay));
					getDataFromServer();
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.MSG_OVER_CURRENT_DATE)
							, "Error", AcSalesChart.this);
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
				Logger.error("Calender dialog #onNothingSelected");
				flagCalenDerDialodgIsShowing = false;
			}

		};
		
		dialogCaldroidFragment = CaldroidFragment.newInstance("", month, year);
		dialogCaldroidFragment.setCaldroidListener(listener);
		
		dialogCaldroidFragment.show(getSupportFragmentManager(),
				"CalenderAndroid");
		flagCalenDerDialodgIsShowing = true;
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
			dialog = new ProgressDialog(AcSalesChart.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					//cancel get data
				}
			});
			
			dialog.show();
		} else {
			if(!dialog.isShowing()){
				dialog.show();
			}
		}
		
		
		restAdapter.create(ViviApi.class).getReportChartSale(token, strDay, strMonth, callback);
	}
	
	Callback<String> callback = new Callback<String>() {
		
		@Override
		public void success(String strData, Response arg1) {
			Logger.error("Get Total Sale: #success: " + strData);
			if(null != AcSalesChart.this && null != dialog){
				if(dialog.isShowing()){ 
					dialog.dismiss();
				}
			}
			
			try {
				chartResponse = DataParser.getEnReportChartResponse(strData);
				
				if(null != chartResponse){
					updateDataScreen(chartResponse);
					if(positionDataToSave == 0){
						DataStorage.getInstance().save_EnReportChartResponse(chartResponse, AcSalesChart.this);
					}
					positionDataToSave++;
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcSalesChart.this);
				}
			} catch (Exception e) {
				e.printStackTrace();
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcSalesChart.this);
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			if(null == retrofitError) return;
			
			retrofitError.printStackTrace();
			if(null != AcSalesChart.this && null != dialog){
				if(dialog.isShowing()){ 
					dialog.dismiss();
				}
			}
			if(retrofitError.isNetworkError()){
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG_LOAD_DATA_LOCAL)
						, "Error", AcSalesChart.this);
				try{
					chartResponse = DataStorage.getInstance().read_EnReportChartResponse(getApplicationContext());
					updateDataScreen(chartResponse);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcSalesChart.this);
			}
			
		}
	};
	
}
