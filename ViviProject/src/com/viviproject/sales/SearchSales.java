package com.viviproject.sales;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemListCustomer;
import com.viviproject.customerline.CustomerDetails;
import com.viviproject.entities.EnArrayStores;
import com.viviproject.entities.EnStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.visit.SearchVisitAdapter;

public class SearchSales extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private ListView lvCustomer;
	private ImageView imgBackToTop, imgDelete;
	private RelativeLayout linFilter;
	private EditText edtFilter;
	private LinearLayout linLines;
	
	private Search search;
	private ProgressDialog progressDialog;
	private AppPreferences app;
	private EnArrayStores enStores;
	private EnStores items;
	private SearchVisitAdapter searchVistiAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_layout);
		app = new AppPreferences(this);
		enStores = new EnArrayStores();
		items = new EnStores();
		
		initLayout();
		
		search = new Search("", "");
		search.execute();
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
		linUpdate.setVisibility(View.GONE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		linRefresh.setVisibility(View.GONE);
		
		imgBackToTop = (ImageView) findViewById(R.id.imgBackToTop);
		imgBackToTop.setOnClickListener(this);
		imgBackToTop.setVisibility(View.GONE);
		
		linLines = (LinearLayout) findViewById(R.id.linLines);
		linLines.setVisibility(View.GONE);
		lvCustomer = (ListView) findViewById(R.id.lvCustomer);
		
		linFilter = (RelativeLayout) findViewById(R.id.linFilter);
		linFilter.setVisibility(View.VISIBLE);
		imgDelete = (ImageView) findViewById(R.id.imgDelete);
		imgDelete.setOnClickListener(this);
		edtFilter = (EditText) findViewById(R.id.edtFilter);
		edtFilter.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
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
			
		case R.id.linSearch:
			enStores = new EnArrayStores();
			searchVistiAdapter = new SearchVisitAdapter(this, enStores.getStores());
			searchVistiAdapter.setOnItemClickHandler(onItemClickHandler);					
			lvCustomer.setAdapter(searchVistiAdapter);
			search = new Search("", "");
			search.execute();
			break;
			
		case R.id.imgDelete:
			edtFilter.setText("");
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
            items = enStores.getStores().get(position);
            intent = new Intent(SearchSales.this, CustomerDetails.class);
            intent.putExtra(GlobalParams.STORES, items);
            startActivity(intent);
        }
    };
	
	class Search extends AsyncTask<Void, Void, String> {
		String data, page, per_page;

		protected Search(String page, String per_page) {
			this.page = page;
			this.per_page = per_page;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(SearchSales.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					search.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[4];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(SearchSales.this));
				netParameter[1] = new NetParameter("page", page);
				netParameter[2] = new NetParameter("per_page", per_page);
				netParameter[3] = new NetParameter("q", edtFilter.getEditableText().toString());
				try {
					data = HttpNetServices.Instance.search(netParameter);
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
					searchVistiAdapter = new SearchVisitAdapter(SearchSales.this, enStores.getStores());
					searchVistiAdapter.setOnItemClickHandler(onItemClickHandler);					
					lvCustomer.setAdapter(searchVistiAdapter);
					imgBackToTop.setVisibility(View.VISIBLE);
					app.hideKeyboard(SearchSales.this, edtFilter);
				}
			}
		}
	}
}
