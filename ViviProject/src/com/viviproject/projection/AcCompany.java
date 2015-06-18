package com.viviproject.projection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.viviproject.R;

public class AcCompany  extends Activity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private LinearLayout linButonSuMenhTamNhin, linButonLichSuPhatTrien, linButonChienLuocKinhDoanh;
	private LinearLayout linLichSuPhatTrienBody;
	private ImageView imgSuMenhTamNhinBody, imgLichSuPhatTrieBody, imgChienLuocKinhDoanh; 
	private ScrollView scrollViewCompany;
	
	private boolean checkScrollBottom = false;
	
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
		
		scrollViewCompany = (ScrollView) findViewById(R.id.scrollViewCompany);
		
		linButonSuMenhTamNhin = (LinearLayout) findViewById(R.id.linButonSuMenhTamNhin);
		linButonSuMenhTamNhin.setOnClickListener(this);
		imgSuMenhTamNhinBody = (ImageView) findViewById(R.id.imgSuMenhTamNhinBody);
		
		linButonLichSuPhatTrien = (LinearLayout) findViewById(R.id.linButonLichSuPhatTrien);
		linButonLichSuPhatTrien.setOnClickListener(this);
		linLichSuPhatTrienBody =(LinearLayout) findViewById(R.id.linLichSuPhatTrienBody);
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
				linButonSuMenhTamNhin.setBackgroundResource(R.drawable.bg_button_expand_blue);
			} else {
				imgSuMenhTamNhinBody.setVisibility(View.VISIBLE);
				linButonSuMenhTamNhin.setBackgroundResource(R.drawable.bg_button_expand_blue_press);
			}
			break;

		case R.id.linButonLichSuPhatTrien:
			if(imgLichSuPhatTrieBody.getVisibility() == View.VISIBLE){
				imgLichSuPhatTrieBody.setVisibility(View.GONE);
				linButonLichSuPhatTrien.setBackgroundResource(R.drawable.bg_button_expand_blue);
			} else {
				imgLichSuPhatTrieBody.setVisibility(View.VISIBLE);
				linButonLichSuPhatTrien.setBackgroundResource(R.drawable.bg_button_expand_blue_press);
				
				/*scrollViewCompany.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			        @Override
			        public void onGlobalLayout() {
			        	scrollViewCompany.post(new Runnable() {
			                public void run() {
			                	scrollViewCompany.scrollTo(0, linLichSuPhatTrienBody.getHeight()/10);
			                }
			            });
			        }
			    });*/
			}
			
			break;
			
		case R.id.linButonChienLuocKinhDoanh:
			checkScrollBottom = true;
			if(imgChienLuocKinhDoanh.getVisibility() == View.VISIBLE){
				imgChienLuocKinhDoanh.setVisibility(View.GONE);
				linButonChienLuocKinhDoanh.setBackgroundResource(R.drawable.bg_button_expand_blue);
			} else {
				imgChienLuocKinhDoanh.setVisibility(View.VISIBLE);
				linButonChienLuocKinhDoanh.setBackgroundResource(R.drawable.bg_button_expand_blue_press);
			}
			
			scrollViewCompany.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
		        @Override
		        public void onGlobalLayout() {
		        	scrollViewCompany.post(new Runnable() {
		                public void run() {
		                	if (checkScrollBottom) {
		                		scrollViewCompany.fullScroll(View.FOCUS_DOWN);
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
