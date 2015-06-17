package com.viviproject.deliver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;

public class OrderImportActivity extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private TextView tvConfirm;
	private LinearLayout linSubConfirm;
	private EditText edtContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_import_layout);
		initLayout();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.PRODUCT_IMPORT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);	
	
		tvConfirm = (TextView) findViewById(R.id.tvConfirm);
		tvConfirm.setOnClickListener(this);
		linSubConfirm = (LinearLayout) findViewById(R.id.linSubConfirm);
		
		edtContent = (EditText) findViewById(R.id.edtContent);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.tvConfirm:
			if (linSubConfirm.getVisibility() == View.GONE) {
				tvConfirm.setBackgroundResource(R.color.BLUE);
				linSubConfirm.setVisibility(View.VISIBLE);
			} else {
				tvConfirm.setBackgroundResource(R.color.BG_GRAY9E);
				linSubConfirm.setVisibility(View.GONE);
			}
			break;
			
		default:
			break;
		}
	}
}
