package com.viviproject.projection;

import java.util.ArrayList;

import com.viviproject.R;
import com.viviproject.adapter.AdapterFrgProducts;
import com.viviproject.adapter.AdapterFrgPromotions;
import com.viviproject.library.TabPageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AcPromotions extends FragmentActivity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ViewPager promotionsPager;
	private TabPageIndicator promotionsIndicator;
	
	private ArrayList<Fragment> listFrgPromotion;
	private AdapterFrgPromotions adapterFrgPromotion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_promotions);
		
		initLayout();
	}
	
	/**
	 * initial layout of screen
	 */
	private void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.DISCOUNT_PROGRAM));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		promotionsPager = (ViewPager) findViewById(R.id.promotions_pager);
		promotionsIndicator = (TabPageIndicator) findViewById(R.id.promotions_title_indicator);
		
		listFrgPromotion = new ArrayList<Fragment>();
		listFrgPromotion.add(FrgPromotions.newInstance(getApplicationContext(), "Tích điểm"));
		listFrgPromotion.add(FrgPromotions.newInstance(getApplicationContext(), "Chiết khấu"));
		listFrgPromotion.add(FrgPromotions.newInstance(getApplicationContext(), "KM khác"));
		listFrgPromotion.add(FrgPromotions.newInstance(getApplicationContext(), "KM theo SP"));
		adapterFrgPromotion = new AdapterFrgPromotions(getSupportFragmentManager(), listFrgPromotion);
		promotionsPager.setAdapter(adapterFrgPromotion);
		promotionsIndicator.setViewPager(promotionsPager);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcPromotions.this.finish();
			break;

		default:
			break;
		}
	}
}
