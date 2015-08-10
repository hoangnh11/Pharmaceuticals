package com.viviproject.customerline;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.EditStaffAdapter;
import com.viviproject.entities.EnStaff;
import com.viviproject.entities.EnStores;
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
	
	public static int indexEditCustomer = -1;
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private Button btnUpdate;
	private EditText edtStoreName, edtStorePhone, edtStoreAddress;		

	private EditStaffAdapter editStaffAdapter;
	private ListView lvAddStaff;
	
	private CheckBox ckVipA, ckVipB, ckVipC, ckT2, ckT3, ckT4, ckT5, ckT6, ckT7;
	private CheckBox ckTimeOne, ckTimeTwo, ckTimeThree, ckTimeFour;
	private String vip, times;
	private ArrayList<Integer> line;
	
	private ProgressDialog progressDialog;
	private UpdateStores updateStores;
	private AppPreferences app;
	private Bundle bundle;
	private EnStores store;
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
		store = new EnStores();
		store = (EnStores) bundle.getSerializable(GlobalParams.STORES);
		line = new ArrayList<Integer>();		
		times = "1";
		
		initLayout();
		
		if (store != null) {
			edtStoreName.setText(store.getName());
			edtStorePhone.setText(store.getPhone());
			edtStoreAddress.setText(store.getAddress());
			if (store.getVip().equals("A")) {
				ckVipA.setChecked(true);
			} else if (store.getVip().equals("B")) {
				ckVipB.setChecked(true);
			} else {
				ckVipC.setChecked(true);
			}
			
			if (store.getOwners() != null && store.getOwners().size() > 0) {
				for (int i = 0; i < store.getOwners().size(); i++) {
					enStaff = new EnStaff();
					enStaff.setId(store.getOwners().get(i).getId());
					enStaff.setFullname(store.getOwners().get(i).getFullname());
					enStaff.setBirthday(store.getOwners().get(i).getBirthday());
					enStaff.setPhone(store.getOwners().get(i).getPhone());
					enStaff.setRole(store.getOwners().get(i).getRole());
					enStaff.setNote(store.getOwners().get(i).getNote());
					arrStaff.add(enStaff);
				}
			}			
			
			if (store.getEmployees() != null && store.getEmployees().size() > 0) {
				for (int i = 0; i < store.getEmployees().size(); i++) {
					enStaff = new EnStaff();
					enStaff.setId(store.getEmployees().get(i).getId());
					enStaff.setFullname(store.getEmployees().get(i).getFullname());
					enStaff.setBirthday(store.getEmployees().get(i).getBirthday());
					enStaff.setPhone(store.getEmployees().get(i).getPhone());
					enStaff.setRole(store.getEmployees().get(i).getRole());
					enStaff.setNote(store.getEmployees().get(i).getNote());
					arrStaff.add(enStaff);
				}
			}			
		}
		
		editStaffAdapter = new EditStaffAdapter(this, arrStaff);	
		editStaffAdapter.setTextChangedHandler(edtNote);
		editStaffAdapter.setTextChangedNameHandler(edtName);
		editStaffAdapter.setTextChangedPhoneHandler(edtPhone);
		editStaffAdapter.setTextChangedBirthDayHandler(edtBirthDay);
		lvAddStaff.setAdapter(editStaffAdapter);
		app.setListViewHeight(lvAddStaff, editStaffAdapter);
		indexEditCustomer = -1;
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
		ckTimeOne= (CheckBox) findViewById(R.id.ckTimeOne);
		ckTimeOne.setOnCheckedChangeListener(new CheckBoxChecked());
		ckTimeTwo= (CheckBox) findViewById(R.id.ckTimeTwo);
		ckTimeTwo.setOnCheckedChangeListener(new CheckBoxChecked());
		ckTimeThree= (CheckBox) findViewById(R.id.ckTimeThree);
		ckTimeThree.setOnCheckedChangeListener(new CheckBoxChecked());
		ckTimeFour= (CheckBox) findViewById(R.id.ckTimeFour);
		ckTimeFour.setOnCheckedChangeListener(new CheckBoxChecked());
	
		lvAddStaff = (ListView) findViewById(R.id.lvAddStaff);
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

    TextWatcher edtNote = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (indexEditCustomer != -1) {
				enStaff = new EnStaff();
				enStaff.setId(arrStaff.get(indexEditCustomer).getId());
				enStaff.setFullname(arrStaff.get(indexEditCustomer).getFullname());
				enStaff.setBirthday(arrStaff.get(indexEditCustomer).getBirthday());
				enStaff.setPhone(arrStaff.get(indexEditCustomer).getPhone());
				enStaff.setRole(arrStaff.get(indexEditCustomer).getRole());
				enStaff.setNote(s.toString());
				arrStaff.set(indexEditCustomer, enStaff);
			} 
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	TextWatcher edtName = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (indexEditCustomer != -1) {
				enStaff = new EnStaff();
				enStaff.setId(arrStaff.get(indexEditCustomer).getId());
				enStaff.setFullname(s.toString());
				enStaff.setBirthday(arrStaff.get(indexEditCustomer).getBirthday());
				enStaff.setPhone(arrStaff.get(indexEditCustomer).getPhone());
				enStaff.setRole(arrStaff.get(indexEditCustomer).getRole());
				enStaff.setNote(arrStaff.get(indexEditCustomer).getNote());
				arrStaff.set(indexEditCustomer, enStaff);
			} 
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	TextWatcher edtPhone = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (indexEditCustomer != -1) {
				enStaff = new EnStaff();
				enStaff.setId(arrStaff.get(indexEditCustomer).getId());
				enStaff.setFullname(arrStaff.get(indexEditCustomer).getFullname());
				enStaff.setBirthday(arrStaff.get(indexEditCustomer).getBirthday());
				enStaff.setPhone(s.toString());
				enStaff.setRole(arrStaff.get(indexEditCustomer).getRole());
				enStaff.setNote(arrStaff.get(indexEditCustomer).getNote());
				arrStaff.set(indexEditCustomer, enStaff);
			} 
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	TextWatcher edtBirthDay = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (indexEditCustomer != -1) {
				enStaff = new EnStaff();
				enStaff.setId(arrStaff.get(indexEditCustomer).getId());
				enStaff.setFullname(arrStaff.get(indexEditCustomer).getFullname());
				enStaff.setBirthday(s.toString());
				enStaff.setPhone(arrStaff.get(indexEditCustomer).getPhone());
				enStaff.setRole(arrStaff.get(indexEditCustomer).getRole());
				enStaff.setNote(arrStaff.get(indexEditCustomer).getNote());
				arrStaff.set(indexEditCustomer, enStaff);
			} 
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.linBack:
			finish();
			break;
			
		case R.id.btnUpdate:
			
			int errorCode = validateInput();
			if (errorCode == 0) {

				line = new ArrayList<Integer>();
				if (ckT2.isChecked()) {
					line.add(2);
				}
								
				if (ckT3.isChecked()) {						
					line.add(3);
				}
				
				if (ckT4.isChecked()) {						
					line.add(4);
				}
				
				if (ckT5.isChecked()) {						
					line.add(5);
				}
				
				if (ckT6.isChecked()) {						
					line.add(6);
				}
				
				if (ckT7.isChecked()) {						
					line.add(7);
				}
				
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

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[13];				
				netParameter[0] = new NetParameter("uid", app.getIMEI(EditCustomer.this) + "|" + app.getCurrentTimeStamp());
				netParameter[1] = new NetParameter("code", app.getIMEI(EditCustomer.this));
				netParameter[2] = new NetParameter("name", edtStoreName.getEditableText().toString());
				netParameter[3] = new NetParameter("address", edtStoreAddress.getEditableText().toString());
				netParameter[4] = new NetParameter("phone", edtStorePhone.getEditableText().toString());
				netParameter[5] = new NetParameter("longitude", BuManagement.getLongitude(EditCustomer.this));
				netParameter[6] = new NetParameter("latitude", BuManagement.getLatitude(EditCustomer.this));
				netParameter[7] = new NetParameter("region_id", store.getCode());
				netParameter[8] = new NetParameter("district", store.getDistrict());
				netParameter[9] = new NetParameter("vip", vip);
				netParameter[10] = new NetParameter("staff", DataParser.convertObjectToString(arrStaff));
				netParameter[11] = new NetParameter("lines", DataParser.convertObjectToString(line));
				netParameter[12] = new NetParameter("repeat", times);
				try {
					data = HttpNetServices.Instance.updateStores(netParameter, BuManagement.getToken(EditCustomer.this),
							store.getStore_id());
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
					
					if (buttonView == ckTimeOne) {
						ckTimeTwo.setChecked(false);
						ckTimeThree.setChecked(false);
						ckTimeFour.setChecked(false);
						times = "1";
					}
					
					if (buttonView == ckTimeTwo) {
						ckTimeOne.setChecked(false);
						ckTimeThree.setChecked(false);
						ckTimeFour.setChecked(false);
						times = "2";
					}
					
					if (buttonView == ckTimeThree) {
						ckTimeOne.setChecked(false);
						ckTimeTwo.setChecked(false);
						ckTimeFour.setChecked(false);
						times = "3";
					}
					
					if (buttonView == ckTimeFour) {
						ckTimeOne.setChecked(false);
						ckTimeThree.setChecked(false);
						ckTimeTwo.setChecked(false);
						times = "4";
					}
				}
			}
	    }
}
