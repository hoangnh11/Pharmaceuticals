package com.viviproject.customerline;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;

public class EditCustomer extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_customer_layout);	
		initLayout();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.INFORMATION_EDIT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;

		default:
			break;
		}
	}

}
