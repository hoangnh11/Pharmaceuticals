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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.StaffAdapter;
import com.viviproject.entities.EnCreateStaff;
import com.viviproject.entities.ResponseCreateStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.SharedPreferenceManager;
import com.viviproject.ultilities.StringUtils;

public class CreateCustormer extends Activity implements OnClickListener {
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private Button btnHere, btnSendRequest;
	
	private Spinner spDay, spMonth, spYear;
	private List<String> listDay, listMonth, listYear;
	private String yearOwner, monthOwner, dayOwner;

	private EditText edtStoreName, edtStoreAddress, edtStorePhone;	
	private EditText edtNameOwner, edtPhoneOwner, edtNoteOwner;	
	private RadioGroup radioGroup;
	private RadioButton radioOwner,radioStaff;
	
	private CheckBox ckVipA, ckVipB, ckVipC, ckT2, ckT3, ckT4, ckT5, ckT6, ckT7;
	private CheckBox ckTimeOne, ckTimeTwo, ckTimeThree, ckTimeFour;
	private String vip, times;
	private ArrayList<Integer> line;
	
	private LinearLayout linAddStaff, linRemoveStaff, linStaff;
	private StaffAdapter staffAdapter;
	private ListView lvAddStaff;
	
	private AppPreferences app;
	private SharedPreferenceManager sm;
	private ProgressDialog progressDialog;
	private CreateStores createStores;
	private ResponseCreateStores responseCreateStores;
	private ArrayList<EnCreateStaff> arrCreateStaff;
	private EnCreateStaff enCreateStaff;
	private boolean remove;
	private int positionDelete = 0;
	private String role;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_customer_layout);
		app = new AppPreferences(this);
		sm = new SharedPreferenceManager(this);
		sm.saveString(GlobalParams.DELETE, GlobalParams.FALSE);
		responseCreateStores = new ResponseCreateStores();
		arrCreateStaff = new ArrayList<EnCreateStaff>();
		enCreateStaff = new EnCreateStaff();		
		line = new ArrayList<Integer>();
		vip = GlobalParams.BLANK_CHARACTER;
		times = "1";
		remove = false;
		role = "owner";
		initLayout();
		
		String[] day = getResources().getStringArray(R.array.day);
		listDay = Arrays.asList(day);
		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listDay);
		spDay.setAdapter(dayAdapter);		
		
		spDay.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				dayOwner = listDay.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});		
		
		String[] month = getResources().getStringArray(R.array.month);
		listMonth = Arrays.asList(month);
		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listMonth);
		spMonth.setAdapter(monthAdapter);		
		
		spMonth.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				monthOwner = listMonth.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}			
		});		
		
		String[] year = getResources().getStringArray(R.array.year);
		listYear = Arrays.asList(year);
		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_items, listYear);
		spYear.setAdapter(yearAdapter);
		spYear.setSelection(40);
		spYear.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				yearOwner = listYear.get(arg2);
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
		linSearch.setVisibility(View.GONE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.GONE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		linRefresh.setVisibility(View.GONE);
		
		btnHere = (Button) findViewById(R.id.btnHere);
		btnHere.setOnClickListener(this);		
		
		btnSendRequest = (Button) findViewById(R.id.btnSendRequest);
		btnSendRequest.setOnClickListener(this);
		
		linStaff = (LinearLayout) findViewById(R.id.linStaff);
		linAddStaff = (LinearLayout) findViewById(R.id.linAddStaff);
		linAddStaff.setOnClickListener(this);		
		linRemoveStaff = (LinearLayout) findViewById(R.id.linRemoveStaff);
		linRemoveStaff.setOnClickListener(this);
		lvAddStaff = (ListView) findViewById(R.id.lvAddStaff);
		
		spDay = (Spinner) findViewById(R.id.spDay);
		spMonth = (Spinner) findViewById(R.id.spMonth);
		spYear = (Spinner) findViewById(R.id.spYear);		
		
		edtStoreName = (EditText) findViewById(R.id.edtStoreName);
		edtStoreAddress = (EditText) findViewById(R.id.edtStoreAddress);
		edtStorePhone = (EditText) findViewById(R.id.edtStorePhone);
		
		edtNameOwner = (EditText) findViewById(R.id.edtNameOwner);
		edtPhoneOwner = (EditText) findViewById(R.id.edtPhoneOwner);
		edtNoteOwner = (EditText) findViewById(R.id.edtNoteOwner);
	
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
		
		radioOwner = (RadioButton) findViewById(R.id.radioOwner);
		radioStaff = (RadioButton) findViewById(R.id.radioStaff);		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {			
				switch (checkedId) {
				case R.id.radioOwner:
					if(radioOwner.isChecked()){					
						role = "owner";			
					}
					break;
						
				case R.id.radioStaff:
					if(radioStaff.isChecked()){						
						role = "employee";	
					}
					break;
					
				default:
					break;
				}
			}
		});
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
	
	OnClickListener onItemClickDelete = new OnClickListener()
	{
        @Override
        public void onClick(View v)
        {
        	positionDelete = ((ItemListviewStaff) v).get_position();        	
        	arrCreateStaff.remove(positionDelete);
        	staffAdapter = new StaffAdapter(CreateCustormer.this, arrCreateStaff);
			staffAdapter.setOnItemClickHandler(onItemClickDelete);
			lvAddStaff.setAdapter(staffAdapter);
			app.setListViewHeight(lvAddStaff, staffAdapter);
			
			if (arrCreateStaff.size() == 0) {
				linStaff.setVisibility(View.GONE);
				linRemoveStaff.setVisibility(View.GONE);
			}
        }
    };
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.linAddStaff:
			linStaff.setVisibility(View.VISIBLE);
			linRemoveStaff.setVisibility(View.VISIBLE);
			enCreateStaff = new EnCreateStaff();
			enCreateStaff.setFullname(edtNameOwner.getEditableText().toString());
			enCreateStaff.setBirthday(yearOwner + "-" + monthOwner + "-" + dayOwner);
			enCreateStaff.setPhone(edtPhoneOwner.getEditableText().toString());
			enCreateStaff.setRole(role);
			enCreateStaff.setNote(edtNoteOwner.getEditableText().toString());			
			arrCreateStaff.add(enCreateStaff);
			
			staffAdapter = new StaffAdapter(this, arrCreateStaff);
			staffAdapter.setOnItemClickHandler(onItemClickDelete);
			lvAddStaff.setAdapter(staffAdapter);
			app.setListViewHeight(lvAddStaff, staffAdapter);
			
			edtNameOwner.setText("");
			edtPhoneOwner.setText("");
			edtNoteOwner.setText("");
			spDay.setSelection(0);
			spMonth.setSelection(0);
			spYear.setSelection(40);
			
			break;
			
		case R.id.linRemoveStaff:
			
			if (!remove) {
				sm.saveString(GlobalParams.DELETE, GlobalParams.TRUE);
				remove = true;
			} else {
				sm.saveString(GlobalParams.DELETE, GlobalParams.FALSE);
				remove = false;
			}
			
			lvAddStaff.setAdapter(staffAdapter);	
			break;
			
		case R.id.btnSendRequest:
			
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
				NetParameter[] netParameter = new NetParameter[13];				
				netParameter[0] = new NetParameter("uid", app.getIMEI(CreateCustormer.this) + "|" + app.getCurrentTimeStamp());
				netParameter[1] = new NetParameter("code", "");
				netParameter[2] = new NetParameter("name", edtStoreName.getEditableText().toString());
				netParameter[3] = new NetParameter("address", edtStoreAddress.getEditableText().toString());
				netParameter[4] = new NetParameter("phone", edtStorePhone.getEditableText().toString());
				netParameter[5] = new NetParameter("longitude", BuManagement.getLongitude(CreateCustormer.this));
				netParameter[6] = new NetParameter("latitude", BuManagement.getLatitude(CreateCustormer.this));
				netParameter[7] = new NetParameter("region_id", "");
				netParameter[8] = new NetParameter("district", "");
				netParameter[9] = new NetParameter("vip", vip);
				netParameter[10] = new NetParameter("staff", DataParser.convertObjectToString(arrCreateStaff));
				netParameter[11] = new NetParameter("lines", DataParser.convertObjectToString(line));
				netParameter[12] = new NetParameter("repeat", times);
		
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
					app.alertErrorMessageString(String.valueOf(responseCreateStores.getMessage()),
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
