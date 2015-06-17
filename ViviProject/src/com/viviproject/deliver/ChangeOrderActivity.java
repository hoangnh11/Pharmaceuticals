package com.viviproject.deliver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;

public class ChangeOrderActivity extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private TextView tvCreateOrder;	
	private LinearLayout linSubCreateOrder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_order);	
		initLayout();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.ORDER_CHANGE));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
	
		tvCreateOrder = (TextView) findViewById(R.id.tvCreateOrder);
		tvCreateOrder.setOnClickListener(this);	
		linSubCreateOrder = (LinearLayout) findViewById(R.id.linSubCreateOrder);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
	
		case R.id.tvCreateOrder:
			if (linSubCreateOrder.getVisibility() == View.GONE) {
				tvCreateOrder.setBackgroundResource(R.color.BG_GRAY9E);
				linSubCreateOrder.setVisibility(View.VISIBLE);
			} else {
				linSubCreateOrder.setVisibility(View.GONE);
				tvCreateOrder.setBackgroundResource(R.color.BLUE);
			}
			break;	
			
		default:
			break;
		}
	}

}
