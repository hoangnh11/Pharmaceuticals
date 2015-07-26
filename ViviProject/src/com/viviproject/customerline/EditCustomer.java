package com.viviproject.customerline;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
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
import com.viviproject.entities.EnStaff;
import com.viviproject.entities.ResponseCreateStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringUtils;

public class EditCustomer extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private Button btnUpdate;
	private EditText edtStoreName, edtStorePhone, edtStoreAddress;
	
	private Spinner spDay, spMonth, spYear, spDayStaff, spMonthStaff, spYearStaff;
	private List<String> listDay, listMonth, listYear;
	
	private ProgressDialog progressDialog;
	private UpdateStores updateStores;
	private AppPreferences app;
	private Bundle bundle;
	private String storeId;
	private ResponseCreateStores responseUpdateStores;
	private ArrayList<EnStaff> arrStaff;
	private EnStaff enStaff;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_customer_layout);
		app = new AppPreferences(this);
		arrStaff = new ArrayList<EnStaff>();
		enStaff = new EnStaff();
		bundle = app.getBundle(this);
		responseUpdateStores = new ResponseCreateStores();
		storeId = bundle.getString(GlobalParams.STORES_ID);
		
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
		
		enStaff.setId("1");
		enStaff.setFullname("Nguyen Van A");
		enStaff.setBirthday("1984-07-02");
		enStaff.setPhone("123456");
		enStaff.setRole("owner");
		enStaff.setNote("");
		arrStaff.add(enStaff);
		
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.INFORMATION_EDIT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(this);
		
		edtStoreName = (EditText) findViewById(R.id.edtStoreName);
		edtStorePhone = (EditText) findViewById(R.id.edtStorePhone);
		edtStoreAddress = (EditText) findViewById(R.id.edtStoreAddress);
		
		spDay = (Spinner) findViewById(R.id.spDay);
		spMonth = (Spinner) findViewById(R.id.spMonth);
		spYear = (Spinner) findViewById(R.id.spYear);
		spDayStaff = (Spinner) findViewById(R.id.spDayStaff);
		spMonthStaff = (Spinner) findViewById(R.id.spMonthStaff);
		spYearStaff = (Spinner) findViewById(R.id.spYearStaff);
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
		switch (v.getId()) {
		
		case R.id.linBack:
			finish();
			break;
			
		case R.id.btnUpdate:
			
			int errorCode = validateInput();
			if (errorCode == 0) {
				updateStores = new UpdateStores();
				updateStores.execute();
			} else {				
				app.alertErrorMessageInt(errorCode, getString(R.string.COMMON_MESSAGE), this);
			}
			
			break;

		default:
			break;
		}
	}

	class UpdateStores extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(EditCustomer.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					updateStores.cancel(true);
				}
			});
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[11];				
				netParameter[0] = new NetParameter("uid", app.getIMEI(EditCustomer.this));
				netParameter[1] = new NetParameter("code", "");
				netParameter[2] = new NetParameter("name", edtStoreName.getEditableText().toString());
				netParameter[3] = new NetParameter("address", edtStoreAddress.getEditableText().toString());
				netParameter[4] = new NetParameter("phone", edtStorePhone.getEditableText().toString());
				netParameter[5] = new NetParameter("longitude", BuManagement.getLongitude(EditCustomer.this));
				netParameter[6] = new NetParameter("latitude", BuManagement.getLatitude(EditCustomer.this));
				netParameter[7] = new NetParameter("region_id", "");
				netParameter[8] = new NetParameter("district", "");
				netParameter[9] = new NetParameter("vip", "");
				netParameter[10] = new NetParameter("staff", DataParser.convertObjectToString(arrStaff));
				try {
					data = HttpNetServices.Instance.updateStores(netParameter, BuManagement.getToken(EditCustomer.this), storeId);
					Logger.error(":         "+data);
					responseUpdateStores = DataParser.updateStores(data);
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
				if (result.equals(GlobalParams.TRUE) && responseUpdateStores != null
						&& String.valueOf(responseUpdateStores.getStatus()).equalsIgnoreCase("success")) {
					app.alertErrorMessageString(String.valueOf(responseUpdateStores.getStatus()),
							getString(R.string.COMMON_MESSAGE), EditCustomer.this);
				} else {
					try {
						app.alertErrorMessageString(responseUpdateStores.getMessage(),
								getString(R.string.COMMON_MESSAGE), EditCustomer.this);
					} catch (Exception e) {
						Logger.error("responseUpdateStores: " + e);
					}					
				}
			}
		}
	}
}
