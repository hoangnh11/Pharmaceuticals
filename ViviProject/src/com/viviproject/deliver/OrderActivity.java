package com.viviproject.deliver;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.OrderListAdapter;
import com.viviproject.core.ItemOrderList;
import com.viviproject.entities.EnCustomer;
import com.viviproject.ultilities.Logger;

public class OrderActivity extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private ImageView imgBackToTop, imgSearchTop;	
	private ListView lvOrder;
	private EditText edtSearch;
	private OrderListAdapter orderListAdapter;
	private ArrayList<EnCustomer> listOrder;
	private EnCustomer enOrder;
	private EnCustomer items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list_layout);
		listOrder = new ArrayList<EnCustomer>();
		items = new EnCustomer();
		enOrder = new EnCustomer();
		initLayout();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.ORDER_LIST));
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
		
		imgSearchTop = (ImageView) findViewById(R.id.imgSearchTop);
		imgSearchTop.setOnClickListener(this);
		
		edtSearch = (EditText) findViewById(R.id.edtSearch);
		edtSearch.clearComposingText();
			
		lvOrder = (ListView) findViewById(R.id.lvOrder);
		
		for (int i = 0; i < 10; i++) {
			enOrder = new EnCustomer();
			enOrder.setId(i + 1);
			listOrder.add(enOrder);
		}
		
		orderListAdapter = new OrderListAdapter(this, listOrder);
		orderListAdapter.setOnItemClickHandler(onItemClickHandler);
		orderListAdapter.setOnCheckboxItemClickHandler(onCheckboxClickHandler);
		lvOrder.setAdapter(orderListAdapter);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.imgBackToTop:
			lvOrder.setSelectionAfterHeaderView();
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
        	int position = ((ItemOrderList) v).get_position();
            items = listOrder.get(position);
            intent = new Intent(OrderActivity.this, ChangeOrderActivity.class);
            startActivity(intent);
        }
    };
    
    OnClickListener onCheckboxClickHandler = new OnClickListener() 
	{
    	@Override
        public void onClick(View v)
        {
        	int position = ((ItemOrderList) v).get_position();
            items = listOrder.get(position);
            Logger.error(":             "+position);
        }
    };
}
