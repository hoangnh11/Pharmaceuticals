package com.viviproject.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
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
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.viviproject.R;

public class AcSalesChart extends FragmentActivity implements OnClickListener, OnChartValueSelectedListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private BarChart mChartProductByDay;
	private BarChart mChartProductByMonth;
	private Spinner spMonth, spYear;
	private ImageView imgIconCalendar;
	
	private CaldroidListener listener;
	private SimpleDateFormat formatter;
	private CaldroidFragment dialogCaldroidFragment;
	
	private List<String> listMonth, listYear;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_sales_chart);
		
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
		tvHeader.setText(getResources().getString(R.string.PROFIT_GRAPHIC));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		imgIconCalendar = (ImageView) findViewById(R.id.imgIconCalendar);
		imgIconCalendar.setOnClickListener(this);
		
		spMonth = (Spinner) findViewById(R.id.spMonth);
		String[] month = getResources().getStringArray(R.array.month);
		listMonth = Arrays.asList(month);
		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listMonth);
		spMonth.setAdapter(monthAdapter);
		
		spYear = (Spinner) findViewById(R.id.spYear);
		String[] year = getResources().getStringArray(R.array.year);
		listYear = Arrays.asList(year);
		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listYear);
		spYear.setAdapter(yearAdapter);
		
		mChartProductByDay = (BarChart) findViewById(R.id.chartProduct);
		setUpChartByDay();
        
		mChartProductByMonth = (BarChart) findViewById(R.id.chartProductByMonth);
		setUpChartByMonth();
		
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
        
        LimitLine limitLine = new LimitLine(100f, "Đạt");
        limitLine.setLineWidth(0.5f);
        limitLine.setLineColor(Color.BLACK);
        limitLine.setLabelPosition(LimitLabelPosition.POS_RIGHT);
        limitLine.setTextSize(10f);
        
        YAxis leftAxis = mChartProductByDay.getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setDrawGridLines(false);
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(limitLine);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMaxValue(120);
        leftAxis.setSpaceTop(10);
        
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        
        YAxis rightAxis = mChartProductByDay.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        
        XAxis xAxis  = mChartProductByDay.getXAxis();
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        
        setData(true, 12, 50);
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
        
        LimitLine limitLine = new LimitLine(80f, "Đạt");
        limitLine.setLineWidth(0.5f);
        limitLine.setLineColor(Color.BLACK);
        limitLine.setLabelPosition(LimitLabelPosition.POS_RIGHT);
        limitLine.setTextSize(10f);
        
        YAxis leftAxis = mChartProductByMonth.getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setDrawGridLines(false);
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(limitLine);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMaxValue(120);
        leftAxis.setSpaceTop(10);
        
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        
        YAxis rightAxis = mChartProductByMonth.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        
        XAxis xAxis  = mChartProductByMonth.getXAxis();
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        
        setData(false, 12, 50);
	}
	
	private void setData(boolean byDay, int count, float range) {
		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
		entries.add(new BarEntry(40f, 0));
		entries.add(new BarEntry(100f, 1));
		entries.add(new BarEntry(60f, 2));
		entries.add(new BarEntry(50f, 3));
		entries.add(new BarEntry(80f, 4));
		
		ArrayList<String> labels = new ArrayList<String>();
		labels.add(getResources().getString(R.string.TPL)); 
		labels.add(getResources().getString(R.string.TPL_PLUS1)); 
		labels.add(getResources().getString(R.string.TPL_FAST1)); 
		labels.add(getResources().getString(R.string.MXH)); 
		labels.add(getResources().getString(R.string.NIEU_BAO));
		
		BarDataSet dataset = new BarDataSet(entries, getResources().getString(R.string.PROFIT));
		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
		BarData data = new BarData(labels, dataset);
		
		if(byDay){
			mChartProductByDay.setData(data);
		} else {
			mChartProductByMonth.setData(data);
		}
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcSalesChart.this.finish();
			break;
			
		case R.id.imgIconCalendar:
			showCalender();
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		
	}

	@Override
	public void onNothingSelected() {
		
	}
	
	@SuppressLint("SimpleDateFormat")
	private void showCalender() {
		formatter = new SimpleDateFormat("yyyy-MM-dd");
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
}
