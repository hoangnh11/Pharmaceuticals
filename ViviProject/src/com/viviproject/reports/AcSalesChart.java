package com.viviproject.reports;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
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
import com.viviproject.R;

public class AcSalesChart extends Activity implements OnClickListener, OnChartValueSelectedListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private BarChart mChart;
	
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
	
		mChart = (BarChart) findViewById(R.id.chartProduct);
		//mChart.setBackgroundColor(Color.WHITE);
		mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        mChart.setDrawBorders(false);
        
        LimitLine limitLine = new LimitLine(100f, "Đạt");
        limitLine.setLineWidth(0.5f);
        limitLine.setLineColor(Color.BLACK);
        limitLine.setLabelPosition(LimitLabelPosition.POS_RIGHT);
        limitLine.setTextSize(10f);
        
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setDrawGridLines(false);
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(limitLine);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMaxValue(120);
        leftAxis.setSpaceTop(10);
        
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        
        XAxis xAxis  = mChart.getXAxis();
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        
        setData(12, 50);
        
	}

	private void setData(int count, float range) {
		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
		entries.add(new BarEntry(40f, 0));
		entries.add(new BarEntry(100f, 1));
		entries.add(new BarEntry(60f, 2));
		entries.add(new BarEntry(50f, 3));
		entries.add(new BarEntry(80f, 4));
		
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("TPL"); 
		labels.add("TPL Plus"); 
		labels.add("TPL Fast"); 
		labels.add("MXH"); 
		labels.add("Niệu bảo");
		
		BarDataSet dataset = new BarDataSet(entries, "Doanh số");
		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
		BarData data = new BarData(labels, dataset);
		
        mChart.setData(data);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcSalesChart.this.finish();
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
}
