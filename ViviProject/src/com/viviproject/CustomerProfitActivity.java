package com.viviproject;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.adapter.CustomerAdapter;
import com.viviproject.entities.EnCustomer;

public class CustomerProfitActivity extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private ListView lvCustomer;
	private ImageView imgBackToTop; 
	
	private CustomerAdapter customerAdapter;
	private ArrayList<EnCustomer> listCustomer;
	private EnCustomer enCustomer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_layout);
		listCustomer = new ArrayList<EnCustomer>();				
		enCustomer = new EnCustomer();
		initLayout();
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.CUSTOMER_NOT_PROFIT_TITLE));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		imgBackToTop = (ImageView) findViewById(R.id.imgBackToTop);
		imgBackToTop.setOnClickListener(this);
		
		lvCustomer = (ListView) findViewById(R.id.lvCustomer);
		
		
		for (int i = 0; i < 10; i++) {
			enCustomer = new EnCustomer();
			enCustomer.setId(i + 1);
			listCustomer.add(enCustomer);
		}
		
		customerAdapter = new CustomerAdapter(this, listCustomer);
		lvCustomer.setAdapter(customerAdapter);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
	
		case R.id.imgBackToTop:
			lvCustomer.setSelectionAfterHeaderView();
			break;
			
		default:
			break;
		}
	}
}
