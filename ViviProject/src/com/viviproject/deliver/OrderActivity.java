package com.viviproject.deliver;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.viviproject.adapter.OrderListAdapter;
import com.viviproject.core.ItemOrderList;
import com.viviproject.entities.EnOrder;
import com.viviproject.entities.ResponseDelivery;
import com.viviproject.entities.ResponseOrders;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;

public class OrderActivity extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private ImageView imgBackToTop, imgSearchTop;	
	private ListView lvOrder;
	private EditText edtSearch;
	private Button btnOk, btnCancel;
	
	private OrderListAdapter orderListAdapter;	
	private ProgressDialog progressDialog;
	private GetSales getSales;
	private ResponseOrders responseOrders;
	private EnOrder items;
	private Delivery delivery;
	private Dialog dialog;
	private ResponseDelivery responseDelivery;
	private AppPreferences app;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list_layout);
		app = new AppPreferences(this);
		responseOrders = new ResponseOrders();
		items = new EnOrder();
		dialog = new Dialog(this);
		responseDelivery = new ResponseDelivery();
		
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

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_deliver);
		btnOk = (Button) dialog.findViewById(R.id.btnOk);
		btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				delivery = new Delivery();
	            delivery.execute();
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				orderListAdapter = new OrderListAdapter(OrderActivity.this, responseOrders);
				orderListAdapter.setOnItemClickHandler(onItemClickHandler);
				orderListAdapter.setOnCheckboxItemClickHandler(onCheckboxClickHandler);
				app.keepPositionListView(lvOrder, orderListAdapter);
				dialog.dismiss();
			}
		});
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
            items = responseOrders.getOrders().get(position);
            intent = new Intent(OrderActivity.this, ChangeOrderActivity.class);
            intent.putExtra(GlobalParams.CHANGE_ORDER, items);
            startActivity(intent);
        }
    };
    
    OnClickListener onCheckboxClickHandler = new OnClickListener() 
	{
    	@Override
        public void onClick(View v)
        {
    		items = new EnOrder();
        	int position = ((ItemOrderList) v).get_position();
            items = responseOrders.getOrders().get(position);        
            dialog.show();
        }
    };
    
    /**
     * Delivery follow line
     * @author hoangnh11
     *
     */
    class Delivery extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(OrderActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.PROCESSING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					delivery.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				try {
					data = HttpNetServices.Instance.delivery(BuManagement.getToken(OrderActivity.this), items.getId());					
					responseDelivery = DataParser.getResponseDelivery(data);					
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
				if (result.equals(GlobalParams.TRUE) && responseDelivery != null 
						&& responseDelivery.getStatus().equalsIgnoreCase("success")) {					
					getSales = new GetSales();
					getSales.execute();
				} else {
					app.alertErrorMessageString(responseDelivery.getMessage(),
							getResources().getString(R.string.COMMON_MESSAGE), OrderActivity.this);
				}
			}
		}
	}
    
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
				}
			}
		}
	}
}
