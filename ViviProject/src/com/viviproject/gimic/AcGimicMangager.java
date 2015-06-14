package com.viviproject.gimic;

import java.util.ArrayList;

import com.viviproject.R;
import com.viviproject.adapter.AdapterGimicListCutomer;
import com.viviproject.adapter.AdapterGimicStatistic;
import com.viviproject.entities.EnGimicCustomerList;
import com.viviproject.entities.EnGimicStatistic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AcGimicMangager extends Activity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ListView lvGmicStatistic, lvGimicListCustomer;
	
	private ArrayList<EnGimicStatistic> listGimicStatistic = new ArrayList<EnGimicStatistic>();
	private ArrayList<EnGimicCustomerList> listGimicCustomerLists = new ArrayList<EnGimicCustomerList>();
	private AdapterGimicStatistic adapterGimicStatistic;
	private AdapterGimicListCutomer adapterGimicListCutomer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_gimic_manager);
		initLayout();
	}

	private void initLayout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.MANAGE_GIMIC));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		initSampleData();
		adapterGimicStatistic = new AdapterGimicStatistic(getApplicationContext(), listGimicStatistic);
		lvGmicStatistic = (ListView) findViewById(R.id.lvGimicStatistic);
		lvGmicStatistic.setAdapter(adapterGimicStatistic);
		
		adapterGimicListCutomer = new AdapterGimicListCutomer(getApplicationContext(), listGimicCustomerLists);
		lvGimicListCustomer = (ListView) findViewById(R.id.lvGimicCustomerList);
		lvGimicListCustomer.setAdapter(adapterGimicListCutomer);
	}

	private void initSampleData() {
		//statistic
		listGimicStatistic.add(new EnGimicStatistic("Bút", 150, 80, 70));
		listGimicStatistic.add(new EnGimicStatistic("Tạp chí", 30, 10, 20));
		listGimicStatistic.add(new EnGimicStatistic("Đồng hồ", 30, 30, 0));
		listGimicStatistic.add(new EnGimicStatistic("Bình nước", 30, 0, 30));
		
		listGimicCustomerLists.add(new EnGimicCustomerList("Nhà thuốc Quỳnh Lan", "số 132 Ngọc Khánh, Ba Đình", 5, 2, 0, 1));
		listGimicCustomerLists.add(new EnGimicCustomerList("Nhà thuốc Quỳnh Lan", "số 132 Ngọc Khánh, Ba Đình", 5, 2, 0, 1));
		listGimicCustomerLists.add(new EnGimicCustomerList("Nhà thuốc Quỳnh Lan", "số 132 Ngọc Khánh, Ba Đình", 5, 2, 0, 1));
		listGimicCustomerLists.add(new EnGimicCustomerList("Nhà thuốc Quỳnh Lan", "số 132 Ngọc Khánh, Ba Đình", 5, 2, 0, 1));
		listGimicCustomerLists.add(new EnGimicCustomerList("Nhà thuốc Quỳnh Lan", "số 132 Ngọc Khánh, Ba Đình", 5, 2, 0, 1));
		listGimicCustomerLists.add(new EnGimicCustomerList("Nhà thuốc Quỳnh Lan", "số 132 Ngọc Khánh, Ba Đình", 5, 2, 0, 1));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcGimicMangager.this.finish();
			break;

		default:
			break;
		}
		
	}
}
