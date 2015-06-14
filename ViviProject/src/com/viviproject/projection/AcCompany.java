package com.viviproject.projection;

import com.viviproject.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AcCompany  extends Activity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private LinearLayout linButonSuMenhTamNhin, linButonLichSuPhatTrien, linButonChienLuocKinhDoanh;
	private ImageView imgSuMenhTamNhinBody, imgLichSuPhatTrieBody, imgChienLuocKinhDoanh; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_company_layout);
		initLayout();
	}

	/**
	 * initial layout
	 */
	private void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.COMPANY));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		linButonSuMenhTamNhin = (LinearLayout) findViewById(R.id.linButonSuMenhTamNhin);
		linButonSuMenhTamNhin.setOnClickListener(this);
		imgSuMenhTamNhinBody = (ImageView) findViewById(R.id.imgSuMenhTamNhinBody);
		
		linButonLichSuPhatTrien = (LinearLayout) findViewById(R.id.linButonLichSuPhatTrien);
		linButonLichSuPhatTrien.setOnClickListener(this);
		imgLichSuPhatTrieBody = (ImageView) findViewById(R.id.imgLichSuPhatTrieBody);
		
		linButonChienLuocKinhDoanh = (LinearLayout) findViewById(R.id.linButonChienLuocKinhDoanh);
		linButonChienLuocKinhDoanh.setOnClickListener(this);
		imgChienLuocKinhDoanh = (ImageView) findViewById(R.id.imgChienLuocKinhDoanh);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcCompany.this.finish();
			break;
			
		case R.id.linButonSuMenhTamNhin:
			if(imgSuMenhTamNhinBody.getVisibility() == View.VISIBLE){
				imgSuMenhTamNhinBody.setVisibility(View.GONE);
			} else {
				imgSuMenhTamNhinBody.setVisibility(View.VISIBLE);
			}
			break;

		case R.id.linButonLichSuPhatTrien:
			if(imgLichSuPhatTrieBody.getVisibility() == View.VISIBLE){
				imgLichSuPhatTrieBody.setVisibility(View.GONE);
			} else {
				imgLichSuPhatTrieBody.setVisibility(View.VISIBLE);
			}
			break;
			
		case R.id.linButonChienLuocKinhDoanh:
			if(imgChienLuocKinhDoanh.getVisibility() == View.VISIBLE){
				imgChienLuocKinhDoanh.setVisibility(View.GONE);
			} else {
				imgChienLuocKinhDoanh.setVisibility(View.VISIBLE);
			}
			break;
			
		default:
			break;
		}
		
	}
}
