package com.viviproject.customerline;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnCreateStaff;
import com.viviproject.entities.EnRegions;
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
	
	private Spinner spLine, spDay, spMonth, spYear, spDayStaff, spMonthStaff, spYearStaff;
	private List<String> listLine, listDay, listMonth, listYear;
	private String yearOwner, monthOwner, dayOwner, yearStaff, monthStaff, dayStaff, regions_id, district;
	private Map<String, String> mapRegions;
	private EditText edtStoreName, edtStoreAddress, edtStorePhone;
	
	private EditText edtNameOwner, edtPhoneOwner, edtNoteOwner;
	private EditText edtNameStaff, edtPhoneStaff; 
	
	private AppPreferences app;
	private ProgressDialog progressDialog;
	private CreateStores createStores;
	private ResponseCreateStores responseCreateStores;
	private ArrayList<EnCreateStaff> arrCreateStaff;
	private EnCreateStaff enCreateStaff;
	private GetLine getLine;
	private ArrayList<EnRegions> enRegions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_customer_layout);	
		app = new AppPreferences(this);
		responseCreateStores = new ResponseCreateStores();
		arrCreateStaff = new ArrayList<EnCreateStaff>();
		enCreateStaff = new EnCreateStaff();
		enRegions = new ArrayList<EnRegions>();
		
		initLayout();
		
		String[] day = getResources().getStringArray(R.array.day);
		listDay = Arrays.asList(day);
		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listDay);
		spDay.setAdapter(dayAdapter);
		spDayStaff.setAdapter(dayAdapter);
		
		spDay.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				dayOwner = listDay.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});
		
		spDayStaff.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				dayStaff = listDay.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});
		
		String[] month = getResources().getStringArray(R.array.month);
		listMonth = Arrays.asList(month);
		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listMonth);
		spMonth.setAdapter(monthAdapter);
		spMonthStaff.setAdapter(monthAdapter);
		
		spMonth.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				monthOwner = listMonth.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});
		
		spMonthStaff.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				monthStaff = listMonth.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});
		
		String[] year = getResources().getStringArray(R.array.year);
		listYear = Arrays.asList(year);
		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listYear);
		spYear.setAdapter(yearAdapter);
		spYearStaff.setAdapter(yearAdapter);
		
		spYear.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				yearOwner = listYear.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});
		
		spYearStaff.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				yearStaff = listYear.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});
		
		getLine = new GetLine();
		getLine.execute();
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
		spLine = (Spinner) findViewById(R.id.spLine);
		
		edtStoreName = (EditText) findViewById(R.id.edtStoreName);
		edtStoreAddress = (EditText) findViewById(R.id.edtStoreAddress);
		edtStorePhone = (EditText) findViewById(R.id.edtStorePhone);
		
		edtNameOwner = (EditText) findViewById(R.id.edtNameOwner);
		edtPhoneOwner = (EditText) findViewById(R.id.edtPhoneOwner);
		edtNoteOwner = (EditText) findViewById(R.id.edtNoteOwner);
		
		edtNameStaff = (EditText) findViewById(R.id.edtNameStaff);
		edtPhoneStaff = (EditText) findViewById(R.id.edtPhoneStaff);
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
				arrCreateStaff = new ArrayList<EnCreateStaff>();
				
				enCreateStaff = new EnCreateStaff();
				enCreateStaff.setFullname(edtNameOwner.getEditableText().toString());
				enCreateStaff.setBirthday(yearOwner + "-" + monthOwner + "-" + dayOwner);
				enCreateStaff.setPhone(edtPhoneOwner.getEditableText().toString());
				enCreateStaff.setRole("owner");
				enCreateStaff.setNote(edtNoteOwner.getEditableText().toString());
				
				arrCreateStaff.add(enCreateStaff);
				
				enCreateStaff = new EnCreateStaff();
				enCreateStaff.setFullname(edtNameStaff.getEditableText().toString());
				enCreateStaff.setBirthday(yearStaff + "-" + monthStaff + "-" + dayStaff);
				enCreateStaff.setPhone(edtPhoneStaff.getEditableText().toString());
				enCreateStaff.setRole("employee");
				enCreateStaff.setNote("");
				
				arrCreateStaff.add(enCreateStaff);
				
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
	
	class GetLine extends AsyncTask<Void, Void, String> {
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
					getLine.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[1];				
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(CreateCustormer.this));
				
			
				try {
					data = HttpNetServices.Instance.getRegions(netParameter);				
					enRegions = DataParser.getRegions(data);
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
				if (result.equals(GlobalParams.TRUE) && enRegions != null && enRegions.size() > 0 ) {
					listLine = new ArrayList<String>();
					mapRegions = new HashMap<String, String>();
					for (int i = 0; i < enRegions.size(); i++) {
						listLine.add(enRegions.get(i).getName());
						mapRegions.put(enRegions.get(i).getName(), String.valueOf(enRegions.get(i).getId()));
					}
					
					ArrayAdapter<String> lineAdapter = new ArrayAdapter<String>(CreateCustormer.this,
							R.layout.spinner_regions, listLine);
					spLine.setAdapter(lineAdapter);
					spLine.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,	View arg1, int arg2, long arg3) {
							regions_id = (mapRegions.get(listLine.get(arg2)));
							district = listLine.get(arg2);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {}
					});
				} else {
										
				}
			}
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
				NetParameter[] netParameter = new NetParameter[11];				
				netParameter[0] = new NetParameter("uid", app.getIMEI(CreateCustormer.this));
				netParameter[1] = new NetParameter("code", "HBT1006");
				netParameter[2] = new NetParameter("name", edtStoreName.getEditableText().toString());
				netParameter[3] = new NetParameter("address", edtStoreAddress.getEditableText().toString());
				netParameter[4] = new NetParameter("phone", edtStorePhone.getEditableText().toString());
				netParameter[5] = new NetParameter("longitude", BuManagement.getLongitude(CreateCustormer.this));
				netParameter[6] = new NetParameter("latitude", BuManagement.getLatitude(CreateCustormer.this));
				netParameter[7] = new NetParameter("region_id", regions_id);
				netParameter[8] = new NetParameter("district", district);
				netParameter[9] = new NetParameter("vip", "");
				netParameter[10] = new NetParameter("staff", DataParser.convertObjectToString(arrCreateStaff));
			
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
