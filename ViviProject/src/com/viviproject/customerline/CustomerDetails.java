package com.viviproject.customerline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;

public class CustomerDetails extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;	
	private LinearLayout linEdit, linOwnerPharmacy, linSubOwnerPharmacy;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_details);
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
		
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;	
			
		case R.id.linEdit:
			
			break;
			
		case R.id.linOwnerPharmacy:
			if (linSubOwnerPharmacy.getVisibility() == View.GONE) {
				linSubOwnerPharmacy.setVisibility(View.VISIBLE);
			} else {
				linSubOwnerPharmacy.setVisibility(View.GONE);
			}
			break;	
			
		default:
			break;
		}
	}
}
