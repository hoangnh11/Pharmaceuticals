package com.viviproject.customerline;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.ListCustomerAdapter;
import com.viviproject.core.ItemListCustomer;
import com.viviproject.entities.EnCustomer;

public class ListCustomer extends Activity implements OnClickListener{

	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private ListView lvCustomer;
	private ImageView imgBackToTop;
	
	private ListCustomerAdapter listCustomerAdapter;
	private ArrayList<EnCustomer> listCustomer;
	private EnCustomer enCustomer;
	private EnCustomer items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_customer);
		listCustomer = new ArrayList<EnCustomer>();
		enCustomer = new EnCustomer();
		items = new EnCustomer();
		initLayout();
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.LIST_CUSTOMER));
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
		
		lvCustomer = (ListView) findViewById(R.id.lvCustomer);		
		
		for (int i = 0; i < 10; i++) {
			enCustomer = new EnCustomer();
			enCustomer.setId(i + 1);
			listCustomer.add(enCustomer);
		}
		
		listCustomerAdapter = new ListCustomerAdapter(this, listCustomer);
		listCustomerAdapter.setOnItemClickHandler(onItemClickHandler);
		lvCustomer.setAdapter(listCustomerAdapter);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
	
		case R.id.linUpdate:
			
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
            intent = new Intent(ListCustomer.this, CustomerDetails.class);
            startActivity(intent);
        }
    };
}
