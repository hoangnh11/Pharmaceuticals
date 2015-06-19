package com.viviproject.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.viviproject.R;
import com.viviproject.adapter.AdapterProfitFollowCustomer;
import com.viviproject.entities.EnCustomer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AcProfitFollowCustomer extends FragmentActivity implements OnClickListener, OnItemClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ListView lvProfitFollowCustomer;
	private ImageView imgBackToTop;
	private Spinner spProductProfitView, spSortType;
	private ImageView imgIconCalendar;
	
	private CaldroidListener listener;
	private SimpleDateFormat formatter;
	private CaldroidFragment dialogCaldroidFragment;
	
	private ArrayList<EnCustomer> listCustomer = new ArrayList<EnCustomer>();
	private List<String> listProductProfit = new ArrayList<String>();
	private List<String> listSortType = new ArrayList<String>();
	private AdapterProfitFollowCustomer adapterProfitFollowCustomer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_profit_follow_customer);
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
		String[] product_profit_view = getResources().getStringArray(R.array.product_profit_view);
		listProductProfit = Arrays.asList(product_profit_view);
		ArrayAdapter<String> productProfitAdaper = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listProductProfit);
		spProductProfitView.setAdapter(productProfitAdaper);
		
		lvProfitFollowCustomer = (ListView) findViewById(R.id.lvProfitFllowCustomer);
		for (int i = 0; i < 15; i++) {
			EnCustomer enCustomer = new EnCustomer();
			listCustomer.add(enCustomer);
		}
		
		adapterProfitFollowCustomer = new AdapterProfitFollowCustomer(getApplicationContext(), listCustomer);
		lvProfitFollowCustomer.setAdapter(adapterProfitFollowCustomer);
		lvProfitFollowCustomer.setOnItemClickListener(this);
		
		imgBackToTop = (ImageView) findViewById(R.id.imgBackToTop);
		imgBackToTop.setOnClickListener(this);
		
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
