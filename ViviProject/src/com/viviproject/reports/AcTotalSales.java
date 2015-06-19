package com.viviproject.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.viviproject.R;

public class AcTotalSales extends FragmentActivity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ImageView imgCalenDa;
	
	private CaldroidListener listener;
	private SimpleDateFormat formatter;
	private CaldroidFragment dialogCaldroidFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_total_sales);
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
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		imgCalenDa = (ImageView) findViewById(R.id.imgIconCalendar);
		imgCalenDa.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcTotalSales.this.finish();
			break;
			
		case R.id.imgIconCalendar:
			showCalender();
			break;
			
		default:
			break;
		}
		
	}
	
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
