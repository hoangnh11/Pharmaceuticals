package com.viviproject.visit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemListCustomer;
import com.viviproject.customerline.CustomerDetails;
import com.viviproject.entities.EnCustomer;
import com.viviproject.adapter.VisitAdapter;;

public class VisitAcitvity extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private ListView lvCustomer;
	private ImageView imgBackToTop;
	
	private Spinner spLine;
	private List<String> listWeek;
	private VisitAdapter listCustomerPendingAdapter;
	private ArrayList<EnCustomer> listCustomer;
	private EnCustomer enCustomer;
	private EnCustomer items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visit_layout);
		listCustomer = new ArrayList<EnCustomer>();
		enCustomer = new EnCustomer();
		items = new EnCustomer();
		initLayout();
		
		String[] week = getResources().getStringArray(R.array.week);
		listWeek = Arrays.asList(week);
		ArrayAdapter<String> weekAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listWeek);
		spLine.setAdapter(weekAdapter);
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.VISIT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		imgBackToTop = (ImageView) findViewById(R.id.imgBackToTop);
		imgBackToTop.setOnClickListener(this);
		
		spLine = (Spinner) findViewById(R.id.spLine);		
		
		lvCustomer = (ListView) findViewById(R.id.lvCustomer);		
		
		for (int i = 0; i < 10; i++) {
			enCustomer = new EnCustomer();
			enCustomer.setId(i + 1);
			listCustomer.add(enCustomer);
		}
		
		listCustomerPendingAdapter = new VisitAdapter(this, listCustomer);
		listCustomerPendingAdapter.setOnItemClickHandler(onItemClickHandler);
		lvCustomer.setAdapter(listCustomerPendingAdapter);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
	
		case R.id.imgBackToTop:
			lvCustomer.setSelectionAfterHeaderView();
			break;
			
		default:
			break;
		}
	}
	
	OnClickListener onItemClickHandler = new OnClickListener() 
	{
		Intent intent;
		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListCustomer) v).get_position();
            items = listCustomer.get(position);
            intent = new Intent(VisitAcitvity.this, VisitDetailsActivity.class);
            startActivity(intent);
        }
    };
}
