package com.viviproject.customerline;

import java.util.ArrayList;

import android.app.Activity;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.ListCustomerPendingAdapter;
import com.viviproject.core.ItemListCustomer;
import com.viviproject.entities.EnArrayStores;
import com.viviproject.entities.EnStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;

public class ListCustomerPending extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private ListView lvCustomer;
	private ImageView imgBackToTop, imgDelete;
	private RelativeLayout linFilter;
	private EditText edtFilter;
	
	private ListCustomerPendingAdapter listCustomerPendingAdapter;
	private EnStores items;
	private ProgressDialog progressDialog;
	private GetStoresWaitApprove getStoresWaitApprove;
	private int qtyPage, qtyPerPage;
	private EnArrayStores enStores;
	public static ArrayList<EnStores> arrEnStores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_customer_pending_layout);		
		items = new EnStores();
		enStores = new EnArrayStores();
		arrEnStores = new ArrayList<EnStores>();
		qtyPage = 1;
		qtyPerPage = 10;
		initLayout();
		
		getStoresWaitApprove = new GetStoresWaitApprove(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
		getStoresWaitApprove.execute();
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.LIST_CUSTOMER_PENDING));
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
		
		linFilter = (RelativeLayout) findViewById(R.id.linFilter);
		imgDelete = (ImageView) findViewById(R.id.imgDelete);
		imgDelete.setOnClickListener(this);
		edtFilter = (EditText) findViewById(R.id.edtFilter);
		edtFilter.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				listCustomerPendingAdapter.getFilter().filter(s);
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
			lvCustomer.setSelectionAfterHeaderView();
			break;
			
		case R.id.linRefresh:
			enStores = new EnArrayStores();	
			arrEnStores = new ArrayList<EnStores>();
			qtyPage = 1;
			qtyPerPage = 10;
			getStoresWaitApprove = new GetStoresWaitApprove(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
			getStoresWaitApprove.execute();
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
            items = arrEnStores.get(position);
            intent = new Intent(ListCustomerPending.this, CustomerDetails.class);
            intent.putExtra(GlobalParams.STORES, items);  
            startActivity(intent);
        }
    };
    
    class GetStoresWaitApprove extends AsyncTask<Void, Void, String> {
		String data, page, per_page;
		
		protected GetStoresWaitApprove(String page, String per_page) {
			this.page = page;
			this.per_page = per_page;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(ListCustomerPending.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getStoresWaitApprove.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[3];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(ListCustomerPending.this));
				netParameter[1] = new NetParameter("page", page);
				netParameter[2] = new NetParameter("per_page", per_page);
				try {
					data = HttpNetServices.Instance.getStoreWaitApprove(netParameter);					
					enStores = DataParser.getStores(data);
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
				try {
					if (result.equals(GlobalParams.TRUE) && enStores != null && enStores.getStores().size() > 0) {
						arrEnStores.addAll(enStores.getStores());
						listCustomerPendingAdapter = new ListCustomerPendingAdapter(ListCustomerPending.this, arrEnStores);
						listCustomerPendingAdapter.setOnItemClickHandler(onItemClickHandler);
						lvCustomer.setAdapter(listCustomerPendingAdapter);
						imgBackToTop.setVisibility(View.VISIBLE);
						lvCustomer.setOnScrollListener(new OnScrollListener() {
							
							@Override
							public void onScrollStateChanged(AbsListView view, int scrollState) {
								int threshold = 1;
								int count = lvCustomer.getCount();
								if (enStores != null && enStores.getStores().size() > 0) {
									if (scrollState == SCROLL_STATE_IDLE) {
										if (lvCustomer.getLastVisiblePosition() >= count - threshold) {
											// Execute LoadMoreDataTask AsyncTask
											qtyPage++;
											getStoresWaitApprove = new GetStoresWaitApprove(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
											getStoresWaitApprove.execute();
										}
									}
								}							
							}
							
							@Override
							public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}
	}
}
