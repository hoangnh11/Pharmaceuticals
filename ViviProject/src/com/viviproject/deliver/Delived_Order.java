package com.viviproject.deliver;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.DelivedOrderAdapter;
import com.viviproject.core.ItemDelivedOrder;
import com.viviproject.entities.EnOrder;
import com.viviproject.entities.ResponseCreateSales;
import com.viviproject.entities.ResponseOrders;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

public class Delived_Order extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private ImageView imgBackToTop, imgSearchTop, imgDelete;	
	private ListView lvOrder;
	private EditText edtSearch, edtContent;
	private Dialog dialog;
	private Button btnOk, btnCancel;
	private RelativeLayout linFilter;
	private EditText edtFilter;
	
	private DelivedOrderAdapter delivedOrderAdapter;
	private AppPreferences app;
	private ProgressDialog progressDialog;
	private GetSalesDelivery getSalesDelivery;
	private ResponseOrders responseOrders;
	private EnOrder items;
	private CancelOrder cancelOrder;
	private ResponseCreateSales responseCancel;
	public static ArrayList<EnOrder> arrEnOrders;
	private int qtyPage, qtyPerPage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delived_order);
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_cancel_order_layout);
		app = new AppPreferences(this);	
		responseOrders = new ResponseOrders();
		items = new EnOrder();
		arrEnOrders = new ArrayList<EnOrder>();
		responseCancel = new ResponseCreateSales();
		qtyPage = 1;
		qtyPerPage = 10;
		initLayout();
		
		getSalesDelivery = new GetSalesDelivery(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
		getSalesDelivery.execute();
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
		
		linFilter = (RelativeLayout) findViewById(R.id.linFilter);
		imgDelete = (ImageView) findViewById(R.id.imgDelete);
		imgDelete.setOnClickListener(this);
		edtFilter = (EditText) findViewById(R.id.edtFilter);
		edtFilter.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				delivedOrderAdapter.getFilter().filter(s);
				if (s.length() > 0) {
					imgDelete.setVisibility(View.VISIBLE);
				} else {
					imgDelete.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.linUpdate:
			if (linFilter.getVisibility() == View.VISIBLE) {
				linFilter.setVisibility(View.GONE);
				edtFilter.setText("");
			} else {
				linFilter.setVisibility(View.VISIBLE);
			}
			break;
			
		case R.id.imgDelete:
			edtFilter.setText("");
			break;
			
		case R.id.imgBackToTop:
			lvOrder.setSelectionAfterHeaderView();
			break;
			
		case R.id.btnOk:
			dialog.dismiss();
			cancelOrder = new CancelOrder();
			cancelOrder.execute();
			edtContent.setText("");
			break;
			
		case R.id.btnCancel:
			dialog.dismiss();
			edtContent.setText("");
			break;
			
		case R.id.linRefresh:
			arrEnOrders = new ArrayList<EnOrder>();
			responseOrders = new ResponseOrders();		
			responseCancel = new ResponseCreateSales();
			qtyPage = 1;
			qtyPerPage = 10;
			getSalesDelivery = new GetSalesDelivery(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
			getSalesDelivery.execute();
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
            items = arrEnOrders.get(position);
            dialog.show();
        }
    };
    
    /**
     * Get Sales Delivery list follow line
     * @author hoangnh11
     *
     */
    class GetSalesDelivery extends AsyncTask<Void, Void, String> {
		String data, page, per_page;
		
		protected GetSalesDelivery(String page, String per_page) {
			this.page = page;
			this.per_page = per_page;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Delived_Order.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getSalesDelivery.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[3];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(Delived_Order.this));
				netParameter[1] = new NetParameter("page", page);
				netParameter[2] = new NetParameter("per_page", per_page);
				try {
					data = HttpNetServices.Instance.getListDelivery(netParameter);			
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
					arrEnOrders.addAll(responseOrders.getOrders());
					delivedOrderAdapter = new DelivedOrderAdapter(Delived_Order.this, arrEnOrders);
					delivedOrderAdapter.setOnItemClickHandler(onItemClickHandler);		
					lvOrder.setAdapter(delivedOrderAdapter);
					lvOrder.setOnScrollListener(new OnScrollListener() {
						
						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							int threshold = 1;
							int count = lvOrder.getCount();
							if (responseOrders != null && responseOrders.getOrders().size() > 0) {
								if (scrollState == SCROLL_STATE_IDLE) {
									if (lvOrder.getLastVisiblePosition() >= count - threshold) {
										// Execute LoadMoreDataTask AsyncTask
										qtyPage++;
										getSalesDelivery = new GetSalesDelivery(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
										getSalesDelivery.execute();
									}
								}
							}							
						}
						
						@Override
						public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
						}
					});
				} else {
					app.alertErrorMessageString(getResources().getString(R.string.COMMON_DATA_NULL),
							getResources().getString(R.string.COMMON_MESSAGE), Delived_Order.this);
				}
			}
		}
	}
    
    /**
     * 
     * @author hoangnh11
     *
     */
    class CancelOrder extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Delived_Order.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancelOrder.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[2];				
				netParameter[0] = new NetParameter("order_id", items.getId());
				netParameter[1] = new NetParameter("note", edtContent.getEditableText().toString());
				
				try {
					data = HttpNetServices.Instance.orderCancel(netParameter, BuManagement.getToken(Delived_Order.this));				
					responseCancel = DataParser.createSale(data);
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
				if (result.equals(GlobalParams.TRUE) && responseCancel != null
						&& String.valueOf(responseCancel.getStatus()).equalsIgnoreCase("success")) {
					arrEnOrders = new ArrayList<EnOrder>();
					responseOrders = new ResponseOrders();		
					responseCancel = new ResponseCreateSales();
					qtyPage = 1;
					qtyPerPage = 10;
					getSalesDelivery = new GetSalesDelivery(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
					getSalesDelivery.execute();
				} else {
					try {
						app.alertErrorMessageString(responseCancel.getMessage(),
								getString(R.string.COMMON_MESSAGE), Delived_Order.this);
					} catch (Exception e) {
						Logger.error("responseCreateStores: " + e);
					}
				}
			}
		}
	}
}
