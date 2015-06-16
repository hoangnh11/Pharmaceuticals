package com.viviproject.visit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;

public class VisitDetailsActivity extends Activity implements OnClickListener{

	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private LinearLayout linCheckWarehouse, linSubChekcWarehouse;
	private TextView tvBuy, tvGivegimic, tvCloseDoor, tvFeedback;
	private LinearLayout linBuyHistory, linSubBuyHistory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visit_details_layout);
		initLayout();
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.CREATE_ORDER));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		tvBuy = (TextView) findViewById(R.id.tvBuy);
		tvBuy.setOnClickListener(this);
		tvGivegimic = (TextView) findViewById(R.id.tvGivegimic);
		tvGivegimic.setOnClickListener(this);
		tvCloseDoor = (TextView) findViewById(R.id.tvCloseDoor);
		tvCloseDoor.setOnClickListener(this);
		tvFeedback = (TextView) findViewById(R.id.tvFeedback);
		tvFeedback.setOnClickListener(this);
		
		linCheckWarehouse = (LinearLayout) findViewById(R.id.linCheckWarehouse);
		linCheckWarehouse.setOnClickListener(this);
		linSubChekcWarehouse = (LinearLayout) findViewById(R.id.linSubChekcWarehouse);
		
		linBuyHistory = (LinearLayout) findViewById(R.id.linBuyHistory);
		linBuyHistory.setOnClickListener(this);
		linSubBuyHistory = (LinearLayout) findViewById(R.id.linSubBuyHistory);
		
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
	
		case R.id.linBuyHistory:
			if (linSubBuyHistory.getVisibility() == View.GONE) {
				linBuyHistory.setBackgroundResource(R.color.BG_GRAY9E);
				linSubBuyHistory.setVisibility(View.VISIBLE);
			} else {
				linBuyHistory.setBackgroundResource(R.color.BLUE);
				linSubBuyHistory.setVisibility(View.GONE);
			}
			break;
			
		case R.id.linCheckWarehouse:
			if (linSubChekcWarehouse.getVisibility() == View.GONE) {
				linCheckWarehouse.setBackgroundResource(R.color.BG_GRAY9E);
				linSubChekcWarehouse.setVisibility(View.VISIBLE);
			} else {
				linCheckWarehouse.setBackgroundResource(R.color.BLUE);
				linSubChekcWarehouse.setVisibility(View.GONE);
			}
			break;	
			
		case R.id.tvBuy:
			intent = new Intent(this, PlaceOrderActivity.class);
			startActivity(intent);
			break;
			
		case R.id.tvGivegimic:
			intent = new Intent(this, GiveGimic.class);
			startActivity(intent);
			break;
			
		case R.id.tvFeedback:
			intent = new Intent(this, FeedbackActivity.class);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}

}
