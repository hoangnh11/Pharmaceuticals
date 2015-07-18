package com.viviproject.customerline;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.VisitAdapter;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.visit.VisitAcitvity;

public class CreateCustormer extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private Button btnHere, btnSendRequest;
	
	private Spinner spDay, spMonth, spYear, spDayStaff, spMonthStaff, spYearStaff;
	private List<String> listDay, listMonth, listYear;
	
	private ProgressDialog progressDialog;
	private CreateStores createStores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_customer_layout);
		initLayout();
		
		String[] day = getResources().getStringArray(R.array.day);
		listDay = Arrays.asList(day);
		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listDay);
		spDay.setAdapter(dayAdapter);
		spDayStaff.setAdapter(dayAdapter);
		
		String[] month = getResources().getStringArray(R.array.month);
		listMonth = Arrays.asList(month);
		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listMonth);
		spMonth.setAdapter(monthAdapter);
		spMonthStaff.setAdapter(monthAdapter);
		
		String[] year = getResources().getStringArray(R.array.year);
		listYear = Arrays.asList(year);
		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listYear);
		spYear.setAdapter(yearAdapter);
		spYearStaff.setAdapter(yearAdapter);
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.CUSTOMER_CREATE));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		btnHere = (Button) findViewById(R.id.btnHere);
		btnHere.setOnClickListener(this);
		
		btnSendRequest = (Button) findViewById(R.id.btnSendRequest);
		btnSendRequest.setOnClickListener(this);
		
		spDay = (Spinner) findViewById(R.id.spDay);
		spMonth = (Spinner) findViewById(R.id.spMonth);
		spYear = (Spinner) findViewById(R.id.spYear);
		spDayStaff = (Spinner) findViewById(R.id.spDayStaff);
		spMonthStaff = (Spinner) findViewById(R.id.spMonthStaff);
		spYearStaff = (Spinner) findViewById(R.id.spYearStaff);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;

		case R.id.btnSendRequest:
			createStores = new CreateStores();
			createStores.execute();
			break;
			
		case R.id.btnHere:
			intent = new Intent(this, ListCustomerPending.class);
			startActivity(intent);
			break;	
			
		default:
			break;
		}
	}
	
	class CreateStores extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(CreateCustormer.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					createStores.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[12];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(CreateCustormer.this));
				netParameter[1] = new NetParameter("uid", "");
				netParameter[2] = new NetParameter("code", "");
				netParameter[3] = new NetParameter("name", "");
				netParameter[4] = new NetParameter("address", "");
				netParameter[5] = new NetParameter("phone", "");
				netParameter[6] = new NetParameter("longitude", "");
				netParameter[7] = new NetParameter("latitude", "");
				netParameter[8] = new NetParameter("region_id", "");
				netParameter[9] = new NetParameter("district", "");
				netParameter[10] = new NetParameter("vip", "");
				netParameter[11] = new NetParameter("staff", "");
				try {
					data = HttpNetServices.Instance.createStores(netParameter);
					Logger.error(":         "+data);
//					enStores = DataParser.getStores(data);
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
//				if (result.equals(GlobalParams.TRUE) && enStores != null) {
//					
//				}
			}
		}
	}
}
