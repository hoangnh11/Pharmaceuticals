package com.viviproject.customerline;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.ResponseCreateStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringUtils;

public class CreateCustormer extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private Button btnHere, btnSendRequest;
	
	private Spinner spDay, spMonth, spYear, spDayStaff, spMonthStaff, spYearStaff;
	private List<String> listDay, listMonth, listYear;
	private EditText edtStoreName, edtStoreAddress, edtStorePhone;
	
	private AppPreferences app;
	private ProgressDialog progressDialog;
	private CreateStores createStores;
	private ResponseCreateStores responseCreateStores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_customer_layout);
		app = new AppPreferences(this);
		responseCreateStores = new ResponseCreateStores();
		
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
		
		edtStoreName = (EditText) findViewById(R.id.edtStoreName);
		edtStoreAddress = (EditText) findViewById(R.id.edtStoreAddress);
		edtStorePhone = (EditText) findViewById(R.id.edtStorePhone);
	}
	
	private int validateInput() {
		int errorCode = 0;		
		String storeName = edtStoreName.getEditableText().toString();
		String storeAddress = edtStoreAddress.getEditableText().toString();
		String storePhone = edtStorePhone.getEditableText().toString();

		if (StringUtils.isBlank(storeName)) {
			errorCode = R.string.STORES_NAME_NOT_BLANK;
		} else if (StringUtils.isBlank(storeAddress)) {
			errorCode = R.string.STORES_ADDRESS_NOT_BLANK;
		} else if (StringUtils.isBlank(storePhone)) {
			errorCode = R.string.STORES_PHONE_NOT_BLANK;
		}

		return errorCode;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;

		case R.id.btnSendRequest:
			
			int errorCode = validateInput();
			if (errorCode == 0) {
				createStores = new CreateStores();
				createStores.execute();
			} else {				
				app.alertErrorMessageInt(errorCode, getString(R.string.COMMON_MESSAGE), this);
			}
			
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

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[11];				
				netParameter[0] = new NetParameter("uid", app.getIMEI(CreateCustormer.this));
				netParameter[1] = new NetParameter("code", "HBT1006");
				netParameter[2] = new NetParameter("name", URLEncoder.encode(edtStoreName.getEditableText().toString()));
				netParameter[3] = new NetParameter("address", URLEncoder.encode(edtStoreAddress.getEditableText().toString()));
				netParameter[4] = new NetParameter("phone", URLEncoder.encode(edtStorePhone.getEditableText().toString()));
				netParameter[5] = new NetParameter("longitude", "");
				netParameter[6] = new NetParameter("latitude", "");
				netParameter[7] = new NetParameter("region_id", "");
				netParameter[8] = new NetParameter("district", "");
				netParameter[9] = new NetParameter("vip", "");
				netParameter[10] = new NetParameter("staff", "");
				try {
					data = HttpNetServices.Instance.createStores(netParameter, BuManagement.getToken(CreateCustormer.this));
					Logger.error(":         "+data);
					responseCreateStores = DataParser.createStores(data);
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
				if (result.equals(GlobalParams.TRUE) && responseCreateStores != null
						&& String.valueOf(responseCreateStores.getStatus()).equalsIgnoreCase("success")) {
					app.alertErrorMessageString(String.valueOf(responseCreateStores.getStatus()),
							getString(R.string.COMMON_MESSAGE), CreateCustormer.this);
				} else {
					try {
						app.alertErrorMessageString(responseCreateStores.getMessage(),
								getString(R.string.COMMON_MESSAGE), CreateCustormer.this);
					} catch (Exception e) {
						Logger.error("responseCreateStores: " + e);
					}					
				}
			}
		}
	}
}
