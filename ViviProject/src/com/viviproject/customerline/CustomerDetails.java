package com.viviproject.customerline;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.SalerAdapter;
import com.viviproject.entities.EnCustomer;
import com.viviproject.ultilities.AppPreferences;

public class CustomerDetails extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;	
	private LinearLayout linEdit, linOwnerPharmacy, linSubOwnerPharmacy;
	private LinearLayout linProfitFive, linSubProfitFive;
	private LinearLayout linSaler;
	private ListView lvSaler;
	
	private SalerAdapter salerAdapter;
	private ArrayList<EnCustomer> listSaler;
	private EnCustomer enCustomer;
	private AppPreferences app;	
	private boolean checkScrollBottom = false;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_details);
		listSaler = new ArrayList<EnCustomer>();
		enCustomer = new EnCustomer();
		app = new AppPreferences(this);
		initLayout();
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.CUSTOMER_INFORMATION));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
	
		linEdit = (LinearLayout) findViewById(R.id.linEdit);
		linEdit.setOnClickListener(this);
		
		linOwnerPharmacy = (LinearLayout) findViewById(R.id.linOwnerPharmacy);
		linOwnerPharmacy.setOnClickListener(this);		
		linSubOwnerPharmacy = (LinearLayout) findViewById(R.id.linSubOwnerPharmacy);
		
		linProfitFive = (LinearLayout) findViewById(R.id.linProfitFive);
		linProfitFive.setOnClickListener(this);
		linSubProfitFive = (LinearLayout) findViewById(R.id.linSubProfitFive);
		
		scrollView = (ScrollView) findViewById(R.id.scrollview);
		
		linSaler = (LinearLayout) findViewById(R.id.linSaler);
		linSaler.setOnClickListener(this);
		lvSaler = (ListView) findViewById(R.id.lvSaler);
		
		for (int i = 0; i < 2; i++) {
			enCustomer = new EnCustomer();
			enCustomer.setId(i + 1);
			listSaler.add(enCustomer);
		}
		
		salerAdapter = new SalerAdapter(this, listSaler);	
		lvSaler.setAdapter(salerAdapter);
		app.setListViewHeight(lvSaler, salerAdapter);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;	
			
		case R.id.linEdit:
			intent = new Intent(this, EditCustomer.class);
			startActivity(intent);
			break;
			
		case R.id.linOwnerPharmacy:
			if (linSubOwnerPharmacy.getVisibility() == View.GONE) {
				linOwnerPharmacy.setBackgroundResource(R.color.BG_GRAY9E);
				linSubOwnerPharmacy.setVisibility(View.VISIBLE);
			} else {
				linSubOwnerPharmacy.setVisibility(View.GONE);
				linOwnerPharmacy.setBackgroundResource(R.color.BLUE);
			}
			break;	
			
		case R.id.linSaler:
			if (lvSaler.getVisibility() == View.GONE) {
				linSaler.setBackgroundResource(R.color.BG_GRAY9E);
				lvSaler.setVisibility(View.VISIBLE);
			} else {
				linSaler.setBackgroundResource(R.color.BLUE);
				lvSaler.setVisibility(View.GONE);
			}
			break;
			
		case R.id.linProfitFive:
			checkScrollBottom = true;
			if (linSubProfitFive.getVisibility() == View.GONE) {
				linProfitFive.setBackgroundResource(R.color.BG_GRAY9E);
				linSubProfitFive.setVisibility(View.VISIBLE);
			} else {
				linProfitFive.setBackgroundResource(R.color.BLUE);
				linSubProfitFive.setVisibility(View.GONE);
			}
			
			scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
		        @Override
		        public void onGlobalLayout() {
		        	scrollView.post(new Runnable() {
		                public void run() {
		                	if (checkScrollBottom) {
		                		scrollView.fullScroll(View.FOCUS_DOWN);
		                		checkScrollBottom = false;
							}
		                }
		            });
		        }
		    });
			break;		
			
		default:
			break;
		}
	}
}
