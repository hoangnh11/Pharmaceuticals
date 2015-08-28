package com.viviproject.sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.VisitAdapter;
import com.viviproject.core.ItemListCustomer;
import com.viviproject.entities.EnArrayStores;
import com.viviproject.entities.EnStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.visit.PlaceOrderActivity;

public class Sales extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private ListView lvCustomer;
	private ImageView imgBackToTop, imgDelete;
	private RelativeLayout linFilter;
	private EditText edtFilter;	
	
	private Spinner spLine;
	private List<String> listWeek;
	private VisitAdapter listVisitAdapter;
	private ProgressDialog progressDialog;
	private GetStores getStores;
	private EnArrayStores enStores;
	private EnStores items;
	private GetStoresLine getStoresLine;
	private String selectDay;
	private Map<String, String> mapDay;
	private int qtyPage, qtyPerPage;
	public static ArrayList<EnStores> arrEnStores;
	private String tempFilter;
	private boolean checkFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_layout);
		enStores = new EnArrayStores();
		items = new EnStores();
		arrEnStores = new ArrayList<EnStores>();
		qtyPage = 1;
		qtyPerPage = 10;
		tempFilter = "";
		checkFilter = false;
		initLayout();
		
		mapDay = new HashMap<String, String>();		
		String[] week = getResources().getStringArray(R.array.week);
		listWeek = Arrays.asList(week);
		
		for (int j = 0; j < listWeek.size(); j++) {
			mapDay.put(listWeek.get(j), String.valueOf(j + 1));
		}
		
		ArrayAdapter<String> weekAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listWeek);
		spLine.setAdapter(weekAdapter);
		spLine.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {				
				arrEnStores.clear();
				selectDay = mapDay.get(listWeek.get(arg2));
				qtyPage = 1;
				qtyPerPage = 10;
				if (selectDay.equals("1")) {
					linSearch.setVisibility(View.VISIBLE);
					getStores = new GetStores(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
					getStores.execute();
				} else {
					linSearch.setVisibility(View.GONE);
					getStoresLine = new GetStoresLine(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
					getStoresLine.execute();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});		
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.SALE));
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
		imgBackToTop.setVisibility(View.GONE);		
		
		spLine = (Spinner) findViewById(R.id.spLine);		
		
		lvCustomer = (ListView) findViewById(R.id.lvCustomer);
		
		linFilter = (RelativeLayout) findViewById(R.id.linFilter);
		imgDelete = (ImageView) findViewById(R.id.imgDelete);
		imgDelete.setOnClickListener(this);
		edtFilter = (EditText) findViewById(R.id.edtFilter);
		edtFilter.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				listVisitAdapter.getFilter().filter(s);
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
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.linSearch:
			intent = new Intent(this, SearchSales.class);
			startActivity(intent);
			break;
			
		case R.id.linUpdate:
			if (linFilter.getVisibility() == View.VISIBLE) {
				linFilter.setVisibility(View.GONE);
				edtFilter.setText("");
				checkFilter = false;
			} else {
				linFilter.setVisibility(View.VISIBLE);
				checkFilter = true;
			}
			break;
			
		case R.id.imgDelete:
			edtFilter.setText("");
			break;
			
		case R.id.imgBackToTop:
			lvCustomer.setSelectionAfterHeaderView();
			break;
			
		case R.id.linRefresh:		
			arrEnStores = new ArrayList<EnStores>();
			tempFilter = "";
			qtyPage = 1;
			qtyPerPage = 10;			
			if (selectDay.equals("1")) {
				getStores = new GetStores(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
				getStores.execute();
			} else {
				getStoresLine = new GetStoresLine(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
				getStoresLine.execute();
			}
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
            intent = new Intent(Sales.this, PlaceOrderActivity.class);            
            intent.putExtra(GlobalParams.STORES, items);
            startActivity(intent);
        }
    };
    
    /**
     * Get Stores list
     * @author hoangnh11
     *
     */
    class GetStores extends AsyncTask<Void, Void, String> {
    	String data, page, per_page;

		protected GetStores(String page, String per_page) {
			this.page = page;
			this.per_page = per_page;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Sales.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getStores.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[3];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(Sales.this));
				netParameter[1] = new NetParameter("page", page);
				netParameter[2] = new NetParameter("per_page", per_page);
				try {
					data = HttpNetServices.Instance.getStores(netParameter);					
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
				if (result.equals(GlobalParams.TRUE) && enStores != null && enStores.getStores().size() > 0) {
					arrEnStores.addAll(enStores.getStores());
					listVisitAdapter = new VisitAdapter(Sales.this, arrEnStores);
					listVisitAdapter.setOnItemClickHandler(onItemClickHandler);
					
					if (checkFilter) {
						listVisitAdapter.getFilter().filter(tempFilter);
						edtFilter.setText(tempFilter);
						linFilter.setVisibility(View.VISIBLE);
						lvCustomer.setVisibility(View.VISIBLE);						
					}
					
					lvCustomer.setAdapter(listVisitAdapter);
					imgBackToTop.setVisibility(View.VISIBLE);
					lvCustomer.setOnScrollListener(new OnScrollListener() {
						
						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							int threshold = 1;
							int count = lvCustomer.getCount();
							if (enStores != null && enStores.getStores().size() > 0) {
								if (scrollState == SCROLL_STATE_IDLE) {
									if (lvCustomer.getLastVisiblePosition() >= count - threshold) {
										if (checkFilter) {
											tempFilter = edtFilter.getEditableText().toString();
											edtFilter.setText("");
											linFilter.setVisibility(View.GONE);
											lvCustomer.setVisibility(View.GONE);
											imgBackToTop.setVisibility(View.GONE);
										}
										// Execute LoadMoreDataTask AsyncTask
										qtyPage++;
										
										getStores = new GetStores(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
										getStores.execute();										
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
						listVisitAdapter.getFilter().filter(tempFilter);
						edtFilter.setText(tempFilter);
						linFilter.setVisibility(View.VISIBLE);
						lvCustomer.setVisibility(View.VISIBLE);
						imgBackToTop.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	}
    
    /**
     * Get Stores list follow line
     * @author hoangnh11
     *
     */
    class GetStoresLine extends AsyncTask<Void, Void, String> {
    	String data, page, per_page;

		protected GetStoresLine(String page, String per_page) {
			this.page = page;
			this.per_page = per_page;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Sales.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getStoresLine.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[4];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(Sales.this));
				netParameter[1] = new NetParameter("page", page);
				netParameter[2] = new NetParameter("per_page", per_page);
				netParameter[3] = new NetParameter("line", selectDay);
				try {
					data = HttpNetServices.Instance.getStoresLine(netParameter, selectDay);					
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
				if (result.equals(GlobalParams.TRUE) && enStores != null && enStores.getStores() != null 
						&& enStores.getStores().size() > 0) {
					arrEnStores.addAll(enStores.getStores());
					listVisitAdapter = new VisitAdapter(Sales.this, arrEnStores);
					listVisitAdapter.setOnItemClickHandler(onItemClickHandler);
					
					if (checkFilter) {
						listVisitAdapter.getFilter().filter(tempFilter);
						edtFilter.setText(tempFilter);
						linFilter.setVisibility(View.VISIBLE);
						lvCustomer.setVisibility(View.VISIBLE);						
					}
					
					lvCustomer.setAdapter(listVisitAdapter);
					imgBackToTop.setVisibility(View.VISIBLE);
					lvCustomer.setOnScrollListener(new OnScrollListener() {
						
						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							int threshold = 1;
							int count = lvCustomer.getCount();
							if (enStores != null && enStores.getStores().size() > 0) {
								if (scrollState == SCROLL_STATE_IDLE) {
									if (lvCustomer.getLastVisiblePosition() >= count - threshold) {
										if (checkFilter) {
											tempFilter = edtFilter.getEditableText().toString();
											edtFilter.setText("");
											linFilter.setVisibility(View.GONE);
											lvCustomer.setVisibility(View.GONE);
											imgBackToTop.setVisibility(View.GONE);
										}
										// Execute LoadMoreDataTask AsyncTask
										qtyPage++;
										getStoresLine = new GetStoresLine(String.valueOf(qtyPage), String.valueOf(qtyPerPage));
										getStoresLine.execute();										
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
						listVisitAdapter.getFilter().filter(tempFilter);
						edtFilter.setText(tempFilter);
						linFilter.setVisibility(View.VISIBLE);
						lvCustomer.setVisibility(View.VISIBLE);
						imgBackToTop.setVisibility(View.VISIBLE);
					} else {
						listVisitAdapter = new VisitAdapter(Sales.this, arrEnStores);
						listVisitAdapter.setOnItemClickHandler(onItemClickHandler);
						lvCustomer.setAdapter(listVisitAdapter);
						imgBackToTop.setVisibility(View.GONE);
					}					
				}
			}
		}
	}
}
