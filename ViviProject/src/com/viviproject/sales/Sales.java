package com.viviproject.sales;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.viviproject.visit.VisitDetailsActivity;

public class Sales extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private ListView lvCustomer;
	private ImageView imgBackToTop;
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_layout);
		enStores = new EnArrayStores();
		items = new EnStores();
		
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
				selectDay = mapDay.get(listWeek.get(arg2));
				if (selectDay.equals("1")) {
					getStores = new GetStores();
					getStores.execute();
				} else {
					getStoresLine = new GetStoresLine();
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
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
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
            intent = new Intent(Sales.this, VisitDetailsActivity.class);            
            intent.putExtra(GlobalParams.STORES, items);
//            startActivity(intent);
        }
    };
    
    /**
     * Get Stores list
     * @author hoangnh11
     *
     */
    class GetStores extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Sales.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
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
				NetParameter[] netParameter = new NetParameter[1];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(Sales.this));
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
					listVisitAdapter = new VisitAdapter(Sales.this, enStores);
					listVisitAdapter.setOnItemClickHandler(onItemClickHandler);
					lvCustomer.setAdapter(listVisitAdapter);
					imgBackToTop.setVisibility(View.VISIBLE);
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
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Sales.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
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
				NetParameter[] netParameter = new NetParameter[2];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(Sales.this));
				netParameter[1] = new NetParameter("line", selectDay);
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
					listVisitAdapter = new VisitAdapter(Sales.this, enStores);
					listVisitAdapter.setOnItemClickHandler(onItemClickHandler);
					lvCustomer.setAdapter(listVisitAdapter);
					imgBackToTop.setVisibility(View.VISIBLE);
				}
			}
		}
	}
}
