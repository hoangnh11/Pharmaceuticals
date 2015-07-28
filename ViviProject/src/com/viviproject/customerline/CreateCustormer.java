package com.viviproject.customerline;

import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnCreateStaff;
import com.viviproject.entities.ResponseCreateStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringUtils;

public class CreateCustormer extends Activity implements OnClickListener {
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private Button btnHere, btnSendRequest;
	
	private Spinner spDay, spMonth, spYear, spDayStaff, spMonthStaff, spYearStaff;
	private List<String> listDay, listMonth, listYear;
	private String yearOwner, monthOwner, dayOwner, yearStaff, monthStaff, dayStaff;

	private EditText edtStoreName, edtStoreAddress, edtStorePhone;
	
	private EditText edtNameOwner, edtPhoneOwner, edtNoteOwner;
	private EditText edtNameStaff, edtPhoneStaff; 
	
	private CheckBox ckVipA, ckVipB, ckVipC, ckT2, ckT3, ckT4, ckT5, ckT6, ckT7;
	private String vip, line; 
	
	private AppPreferences app;
	private ProgressDialog progressDialog;
	private CreateStores createStores;
	private ResponseCreateStores responseCreateStores;
	private ArrayList<EnCreateStaff> arrCreateStaff;
	private EnCreateStaff enCreateStaff;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_customer_layout);
		app = new AppPreferences(this);
		responseCreateStores = new ResponseCreateStores();
		arrCreateStaff = new ArrayList<EnCreateStaff>();
		enCreateStaff = new EnCreateStaff();	
		vip = GlobalParams.BLANK_CHARACTER;
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
		
		edtNameOwner = (EditText) findViewById(R.id.edtNameOwner);
		edtPhoneOwner = (EditText) findViewById(R.id.edtPhoneOwner);
		edtNoteOwner = (EditText) findViewById(R.id.edtNoteOwner);
		
		edtNameStaff = (EditText) findViewById(R.id.edtNameStaff);
		edtPhoneStaff = (EditText) findViewById(R.id.edtPhoneStaff);
		
		ckVipA = (CheckBox) findViewById(R.id.ckVipA);	
		ckVipA.setOnCheckedChangeListener(new CheckBoxChecked());
		ckVipB = (CheckBox) findViewById(R.id.ckVipB);
		ckVipB.setOnCheckedChangeListener(new CheckBoxChecked());
		ckVipC = (CheckBox) findViewById(R.id.ckVipC);
		ckVipC.setOnCheckedChangeListener(new CheckBoxChecked());
		ckT2 = (CheckBox) findViewById(R.id.ckT2);
		ckT2.setOnCheckedChangeListener(new CheckBoxChecked());
		ckT3 = (CheckBox) findViewById(R.id.ckT3);
		ckT3.setOnCheckedChangeListener(new CheckBoxChecked());
		ckT4 = (CheckBox) findViewById(R.id.ckT4);
		ckT4.setOnCheckedChangeListener(new CheckBoxChecked());
		ckT5 = (CheckBox) findViewById(R.id.ckT5);
		ckT5.setOnCheckedChangeListener(new CheckBoxChecked());
		ckT6 = (CheckBox) findViewById(R.id.ckT6);
		ckT6.setOnCheckedChangeListener(new CheckBoxChecked());
		ckT7 = (CheckBox) findViewById(R.id.ckT7);
		ckT7.setOnCheckedChangeListener(new CheckBoxChecked());
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
				netParameter[0] = new NetParameter("uid", app.getIMEI(CreateCustormer.this) + "|" + app.getCurrentTimeStamp());
				netParameter[1] = new NetParameter("code", app.getIMEI(CreateCustormer.this));
				netParameter[2] = new NetParameter("name", edtStoreName.getEditableText().toString());
				netParameter[3] = new NetParameter("address", edtStoreAddress.getEditableText().toString());
				netParameter[4] = new NetParameter("phone", edtStorePhone.getEditableText().toString());
				netParameter[5] = new NetParameter("longitude", BuManagement.getLongitude(CreateCustormer.this));
				netParameter[6] = new NetParameter("latitude", BuManagement.getLatitude(CreateCustormer.this));
				netParameter[7] = new NetParameter("region_id", "");
				netParameter[8] = new NetParameter("district", "");
				netParameter[9] = new NetParameter("vip", vip);
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
	
	class CheckBoxChecked implements CheckBox.OnCheckedChangeListener {
       @Override
       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {       
			if (isChecked) {
				if (buttonView == ckVipA) {
					ckVipB.setChecked(false);
		        	ckVipC.setChecked(false);
		        	vip = "A";
				}

				if (buttonView == ckVipB) {
					ckVipA.setChecked(false);
		        	ckVipC.setChecked(false);
		        	vip = "B";
				}

				if (buttonView == ckVipC) {
					ckVipA.setChecked(false);
		        	ckVipB.setChecked(false);
		        	vip = "C";
				}
				
				if (buttonView == ckT2) {
					ckT3.setChecked(false);
					ckT4.setChecked(false);
					ckT5.setChecked(false);
					ckT6.setChecked(false);
					ckT7.setChecked(false);
					line = "2";
				}
				
				if (buttonView == ckT3) {
					ckT2.setChecked(false);
					ckT4.setChecked(false);
					ckT5.setChecked(false);
					ckT6.setChecked(false);
					ckT7.setChecked(false);
					line = "3";
				}
				
				if (buttonView == ckT4) {
					ckT3.setChecked(false);
					ckT2.setChecked(false);
					ckT5.setChecked(false);
					ckT6.setChecked(false);
					ckT7.setChecked(false);
					line = "4";
				}
				
				if (buttonView == ckT5) {
					ckT3.setChecked(false);
					ckT4.setChecked(false);
					ckT2.setChecked(false);
					ckT6.setChecked(false);
					ckT7.setChecked(false);
					line = "5";
				}
				
				if (buttonView == ckT6) {
					ckT3.setChecked(false);
					ckT4.setChecked(false);
					ckT5.setChecked(false);
					ckT2.setChecked(false);
					ckT7.setChecked(false);
					line = "6";
				}
				
				if (buttonView == ckT7) {
					ckT3.setChecked(false);
					ckT4.setChecked(false);
					ckT5.setChecked(false);
					ckT6.setChecked(false);
					ckT2.setChecked(false);
					line = "7";
				}
			}
		}
    }
}
