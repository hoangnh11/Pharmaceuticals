package com.viviproject.deliver;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
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
	
	private ImageView imgBackToTop, imgSearchTop, imgDelete;	
	private ListView lvOrder;
	private EditText edtSearch;
	private Button btnOk, btnCancel;
	private RelativeLayout linFilter;
	private EditText edtFilter;
	
	private OrderListAdapter orderListAdapter;	
	private ProgressDialog progressDialog;
	private GetSales getSales;
	private ResponseOrders responseOrders;
	public static ArrayList<EnOrder> arrEnOrders;
	private EnOrder items;
	private Delivery delivery;
	private Dialog dialog;
	private ResponseDelivery responseDelivery;
	private AppPreferences app;
	private int qtyPage, qtyPerPage;
	private String tempFilter;
	private boolean checkFilter;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list_layout);
		app = new AppPreferences(this);
		responseOrders = new ResponseOrders();
		arrEnOrders = new ArrayList<EnOrder>();
		items = new EnOrder();
		dialog = new Dialog(this);
		responseDelivery = new ResponseDelivery();
		qtyPage = 1;
		qtyPerPage = 10;
		tempFilter = "";
		checkFilter = false;
		initLayout();
		
		getSales = new GetSales(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
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
		
		linFilter = (RelativeLayout) findViewById(R.id.linFilter);
		imgDelete = (ImageView) findViewById(R.id.imgDelete);
		imgDelete.setOnClickListener(this);
		edtFilter = (EditText) findViewById(R.id.edtFilter);
		edtFilter.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {		
				orderListAdapter.getFilter().filter(s);
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
				orderListAdapter = new OrderListAdapter(OrderActivity.this, arrEnOrders);
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
			
		case R.id.linUpdate:
			if (arrEnOrders != null && arrEnOrders.size() > 0) {
				if (linFilter.getVisibility() == View.VISIBLE) {
					linFilter.setVisibility(View.GONE);
					edtFilter.setText("");
					checkFilter = false;
				} else {
					linFilter.setVisibility(View.VISIBLE);
					checkFilter = true;
				}
			}			
			break;
			
		case R.id.imgDelete:
			edtFilter.setText("");
			break;
			
		case R.id.imgBackToTop:
			lvOrder.setSelectionAfterHeaderView();
			break;	
			
		case R.id.linRefresh:
			responseOrders = new ResponseOrders();	
			arrEnOrders = new ArrayList<EnOrder>();
			tempFilter = "";
			qtyPage = 1;
			qtyPerPage = 10;
			getSales = new GetSales(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
			getSales.execute();
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
            items = arrEnOrders.get(position);
            intent = new Intent(OrderActivity.this, ChangeOrderActivity.class);
            intent.putExtra(GlobalParams.CHANGE_ORDER, items);
            intent.putExtra(GlobalParams.PRODUCTS, responseOrders.getProducts());
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
            items = arrEnOrders.get(position);
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
					responseOrders = new ResponseOrders();	
					arrEnOrders = new ArrayList<EnOrder>();
					qtyPage = 1;
					qtyPerPage = 10;
					getSales = new GetSales(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
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
    	String data, page, per_page;
    	
    	protected GetSales(String page, String per_page) {
			this.page = page;
			this.per_page = per_page;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(OrderActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(true);
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
				NetParameter[] netParameter = new NetParameter[3];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(OrderActivity.this));
				netParameter[1] = new NetParameter("page", page);
				netParameter[2] = new NetParameter("per_page", per_page);
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
					arrEnOrders.addAll(responseOrders.getOrders());
					orderListAdapter = new OrderListAdapter(OrderActivity.this, arrEnOrders);
					orderListAdapter.setOnItemClickHandler(onItemClickHandler);
					orderListAdapter.setOnCheckboxItemClickHandler(onCheckboxClickHandler);
					
					if (checkFilter) {
						orderListAdapter.getFilter().filter(tempFilter);
						edtFilter.setText(tempFilter);
						linFilter.setVisibility(View.VISIBLE);
						lvOrder.setVisibility(View.VISIBLE);						
					}
					
					lvOrder.setAdapter(orderListAdapter);
					lvOrder.setOnScrollListener(new OnScrollListener() {
						
						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							int threshold = 1;
							int count = lvOrder.getCount();
							if (responseOrders != null && responseOrders.getOrders().size() > 0) {
								if (scrollState == SCROLL_STATE_IDLE) {
									if (lvOrder.getLastVisiblePosition() >= count - threshold) {
										if (checkFilter) {
											tempFilter = edtFilter.getEditableText().toString();
											edtFilter.setText("");
											linFilter.setVisibility(View.GONE);
											lvOrder.setVisibility(View.GONE);
											imgBackToTop.setVisibility(View.GONE);
										}
										// Execute LoadMoreDataTask AsyncTask
										qtyPage++;
										getSales = new GetSales(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
										getSales.execute();
									}
								}
							}							
						}
						
						@Override
						public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
						}
					});
				} else {
					if (checkFilter) {
						orderListAdapter.getFilter().filter(tempFilter);
						edtFilter.setText(tempFilter);
						linFilter.setVisibility(View.VISIBLE);
						lvOrder.setVisibility(View.VISIBLE);
						imgBackToTop.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	}
}
