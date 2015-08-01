package com.viviproject.deliver;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.viviproject.entities.EnCustomer;
import com.viviproject.entities.ResponseOrders;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;

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
	
	private ProgressDialog progressDialog;
	private GetSales getSales;
	private ResponseOrders responseOrders;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list_layout);
		listOrder = new ArrayList<EnCustomer>();
		items = new EnCustomer();
		enOrder = new EnCustomer();
		responseOrders = new ResponseOrders();
		initLayout();
		
		getSales = new GetSales();
		getSales.execute();
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
//        	int position = ((ItemOrderList) v).get_position();
//            items = listOrder.get(position);
//            intent = new Intent(OrderActivity.this, ChangeOrderActivity.class);
//            startActivity(intent);
        }
    };
    
    OnClickListener onCheckboxClickHandler = new OnClickListener() 
	{
    	@Override
        public void onClick(View v)
        {
//        	int position = ((ItemOrderList) v).get_position();
//            items = listOrder.get(position);
//            Logger.error(":             "+position);
        }
    };
    
    /**
     * Get Sales list follow line
     * @author hoangnh11
     *
     */
    class GetSales extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(OrderActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getSales.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[1];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(OrderActivity.this));				
				try {
					data = HttpNetServices.Instance.getSales(netParameter);					
					responseOrders = DataParser.getResponseOrders(data);				
					return GlobalParams.TRUE;
				} catch (Exception e) {
					return GlobalParams.FALSE;
				}
			} else {
				return GlobalParams.FALSE;
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if (!isCancelled()) {
				if (result.equals(GlobalParams.TRUE) && responseOrders != null && responseOrders.getOrders() != null
						&& responseOrders.getOrders().size() > 0
						&& responseOrders.getStatus().equalsIgnoreCase("success")) {
					
					orderListAdapter = new OrderListAdapter(OrderActivity.this, responseOrders);
					orderListAdapter.setOnItemClickHandler(onItemClickHandler);
					orderListAdapter.setOnCheckboxItemClickHandler(onCheckboxClickHandler);
					lvOrder.setAdapter(orderListAdapter);
//					gimicAdapter = new GimicAdapter(GiveGimic.this, elements);
//					gimicAdapter.setOnMinusClickHandler(onMinusClickHandler);
//					gimicAdapter.setOnPlusClickHandler(onPlusClickHandler);
//					lvGimic.setAdapter(gimicAdapter);
//					app.setListViewHeight(lvGimic, gimicAdapter);
				}
			}
		}
	}
}
