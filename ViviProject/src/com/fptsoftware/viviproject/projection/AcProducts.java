package com.fptsoftware.viviproject.projection;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fptsoftware.adapter.AdapterFrgProducts;
import com.fptsoftware.library.TabPageIndicator;
import com.fptsoftware.viviproject.R;

public class AcProducts extends FragmentActivity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ViewPager productPager;
	private TabPageIndicator productIndicator;
	
	private ArrayList<Fragment> listFrgProducts;
	private AdapterFrgProducts adapterFrgProducts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_products);
		
		innitLaytout();
	}

	/**
	 * initial layout of screen
	 */
	private void innitLaytout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText("Sản phẩm");
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		productPager = (ViewPager) findViewById(R.id.product_type_pager);
		productIndicator = (TabPageIndicator) findViewById(R.id.product_type_title_indicator);
		
		listFrgProducts = new ArrayList<Fragment>();
		ArrayList<String> listUrl = new ArrayList<String>();
		listUrl.add("https://google.com.vn");
		listUrl.add("http://www.t-mobile.com/content/dam/tmo/en-g/hero/hp-tileA-LG-C40.jpg");
		listUrl.add("https://ithemes.com/wp-content/uploads/2012/07/mobile300.png");
		
		listFrgProducts.add(FrgProducts.newInstance(getApplicationContext(), " TPL ", listUrl));
		listFrgProducts.add(FrgProducts.newInstance(getApplicationContext(), "TPL Plus", listUrl));
		listFrgProducts.add(FrgProducts.newInstance(getApplicationContext(), "TPL Fast", listUrl));
		listFrgProducts.add(FrgProducts.newInstance(getApplicationContext(), "Maxxhair", listUrl));
		listFrgProducts.add(FrgProducts.newInstance(getApplicationContext(), "Niệu bảo", listUrl));
		adapterFrgProducts = new AdapterFrgProducts(getSupportFragmentManager(), listFrgProducts);
		productPager.setAdapter(adapterFrgProducts);
		productIndicator.setViewPager(productPager);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcProducts.this.finish();
			break;

		default:
			break;
		}
		
	}

}
