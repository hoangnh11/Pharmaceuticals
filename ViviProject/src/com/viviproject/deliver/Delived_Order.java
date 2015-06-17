package com.viviproject.deliver;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.DelivedOrderAdapter;
import com.viviproject.core.ItemDelivedOrder;
import com.viviproject.entities.EnCustomer;

public class Delived_Order extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private ImageView imgBackToTop, imgSearchTop;	
	private ListView lvOrder;
	private EditText edtSearch, edtContent;
	private Dialog dialog;
	private Button btnOk, btnCancel;
	
	private DelivedOrderAdapter delivedOrderAdapter;
	private ArrayList<EnCustomer> listDelivedOrder;
	private EnCustomer enDelivedOrder;
	private EnCustomer items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delived_order);
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_cancel_order_layout);
		listDelivedOrder = new ArrayList<EnCustomer>();
		items = new EnCustomer();
		enDelivedOrder = new EnCustomer();
		initLayout();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.DELIVED_ORDER));
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
		
		edtContent = (EditText) dialog.findViewById(R.id.edtContent);
		btnOk = (Button) dialog.findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
		btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
			
		lvOrder = (ListView) findViewById(R.id.lvOrder);
		
		for (int i = 0; i < 10; i++) {
			enDelivedOrder = new EnCustomer();
			enDelivedOrder.setId(i + 1);
			listDelivedOrder.add(enDelivedOrder);
		}
		
		delivedOrderAdapter = new DelivedOrderAdapter(this, listDelivedOrder);
		delivedOrderAdapter.setOnItemClickHandler(onItemClickHandler);		
		lvOrder.setAdapter(delivedOrderAdapter);
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
			
		case R.id.btnOk:
			dialog.dismiss();
			break;
			
		case R.id.btnCancel:
			dialog.dismiss();
			break;
			
		default:
			break;
		}
	}
	
	OnClickListener onItemClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemDelivedOrder) v).get_position();
            items = listDelivedOrder.get(position);
            dialog.show();
        }
    };   
}
