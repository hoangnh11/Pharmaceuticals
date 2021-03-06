package com.viviproject.customerline;

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
import com.viviproject.entities.EnArrayStores;
import com.viviproject.entities.EnStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;

public class SearchListCustomer extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linRefresh;
	private TextView tvHeader;
	private ListView lvCustomer;
	private ImageView imgBackToTop, imgDel, imgSearchOnline;
	private EditText edtSearchOnline;
	private RelativeLayout relSearch;
	
	private Search search;
	private ProgressDialog progressDialog;
	private AppPreferences app;
	private EnArrayStores enStores;
	private EnStores items;
	private SearchListCustomerAdapter searchListCustomerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_customer);
		app = new AppPreferences(this);
		enStores = new EnArrayStores();
		items = new EnStores();
		
		initLayout();

	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.LIST_CUSTOMER));
		tvHeader.setVisibility(View.VISIBLE);	
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setVisibility(View.GONE);
		
		imgBackToTop = (ImageView) findViewById(R.id.imgBackToTop);
		imgBackToTop.setOnClickListener(this);
		imgBackToTop.setVisibility(View.GONE);
		
		lvCustomer = (ListView) findViewById(R.id.lvCustomer);
		
		relSearch = (RelativeLayout) findViewById(R.id.relSearch);
		relSearch.setVisibility(View.VISIBLE);
		imgDel = (ImageView) findViewById(R.id.imgDel);
		imgDel.setOnClickListener(this);
		imgSearchOnline = (ImageView) findViewById(R.id.imgSearchOnline);
		imgSearchOnline.setOnClickListener(this);
		edtSearchOnline = (EditText) findViewById(R.id.edtSearchOnline);
		edtSearchOnline.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
				if (s.length() > 0) {
					imgDel.setVisibility(View.VISIBLE);
				} else {
					imgDel.setVisibility(View.GONE);
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
			
		case R.id.imgSearchOnline:
			enStores = new EnArrayStores();
			searchListCustomerAdapter = new SearchListCustomerAdapter(SearchListCustomer.this, enStores.getStores());
			searchListCustomerAdapter.setOnItemClickHandler(onItemClickHandler);					
			lvCustomer.setAdapter(searchListCustomerAdapter);
			search = new Search("", "");
			search.execute();
			break;
			
		case R.id.imgDel:
			edtSearchOnline.setText("");
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
            intent = new Intent(SearchListCustomer.this, CustomerDetails.class);
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
			progressDialog = new ProgressDialog(SearchListCustomer.this);
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
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(SearchListCustomer.this));
				netParameter[1] = new NetParameter("page", page);
				netParameter[2] = new NetParameter("per_page", per_page);
				netParameter[3] = new NetParameter("q", edtSearchOnline.getEditableText().toString());
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
					searchListCustomerAdapter = new SearchListCustomerAdapter(SearchListCustomer.this, enStores.getStores());
					searchListCustomerAdapter.setOnItemClickHandler(onItemClickHandler);					
					lvCustomer.setAdapter(searchListCustomerAdapter);
					imgBackToTop.setVisibility(View.VISIBLE);
					app.hideKeyboard(SearchListCustomer.this, edtSearchOnline);
				} else {
					imgBackToTop.setVisibility(View.GONE);
				}
			}
		}
	}
}
