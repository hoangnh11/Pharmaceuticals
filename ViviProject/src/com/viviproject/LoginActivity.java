package com.viviproject;

import java.io.IOException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.google.gson.JsonSyntaxException;
import com.viviproject.entities.ResponseLogin;
import com.viviproject.entities.UserInformation;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.service.TrackingLocationService;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.SharedPreferenceManager;
import com.viviproject.ultilities.StringUtils;

public class LoginActivity extends Activity implements OnClickListener{
	public static final String LOGIN_SHARE_PREFERENT_KEY = "login complete";
	private EditText edtUsername, edtPassword;
	private Button btnLogin;
	private AppPreferences app;
	private LoginAsyncTask loginAsyncTask;
	private ProgressDialog dialog;
	private ResponseLogin responseLogin;
	private UserInformation userInformation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		responseLogin = null;
		app = new AppPreferences(this);
		if (LoginActivity.checkLogin(this)) {
			app.goHome(this);
		} else {
			setContentView(R.layout.login_layout);
			InitLayout();
		}
	}

	public void InitLayout() {
		edtUsername = (EditText) findViewById(R.id.edtUsername);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		
		edtPassword.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if(checkAdminSignIn()){
						Intent i = new Intent(LoginActivity.this, AcSettingUrl.class);
						startActivity(i);						
					} else {
//						int errorCode = validateInput();
//						if (errorCode == 0) {
							loginAsyncTask = new LoginAsyncTask();
							loginAsyncTask.execute();								
//						} else {				
//							app.alertErrorMessageInt(errorCode, getString(R.string.COMMON_MESSAGE), this);
//						}
					}
	            }
				return false;
			}
		});
	}
	
	/**
	 * check admin signin
	 * @return
	 */
	private boolean checkAdminSignIn(){
		// Get user name and password from screen
		String username = edtUsername.getEditableText().toString();
		String password = edtPassword.getEditableText().toString();
		if(username.equalsIgnoreCase("admin") && password.equals("1234")){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Validate the login screen with two parameters user name and password
	 * 
	 * @return the error code
	 */
	private int validateInput() {
		int errorCode = 0;
		// Get user name and password from screen
		String username = edtUsername.getEditableText().toString();
		String password = edtPassword.getEditableText().toString();
		
		if (StringUtils.isBlank(username)) {
			errorCode = R.string.USERNAME_NOT_BLANK;
		} else if (StringUtils.isNumeric(username.substring(0, 1))) {			
			errorCode = R.string.BEGIN_NOT_NUMBER;
		} else if (StringUtils.isBlank(password)) {
			errorCode = R.string.PASSWORD_NOT_BLANK;
		} else if (password.length() < 7) {
			errorCode = R.string.PASSWORD_MORE_SEVEN;
		} else if (!StringUtils.isAtLeatNumber(password)) {
			errorCode = R.string.PASSWORD_HAS_NUMBER;
		} else if (!StringUtils.isAtLeatLetterUppercase(password)) {
			errorCode = R.string.PASSWORD_HAS_LETTER_UPPER_;
		}
		
		return errorCode;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			if(checkAdminSignIn()){
				Intent i = new Intent(LoginActivity.this, AcSettingUrl.class);
				startActivity(i);
				break;
			}
			
//			int errorCode = validateInput();
//			if (errorCode == 0) {
				loginAsyncTask = new LoginAsyncTask();
				loginAsyncTask.execute();								
//			} else {				
//				app.alertErrorMessageInt(errorCode, getString(R.string.COMMON_MESSAGE), this);
//			}
			break;

		default:
			break;
		}		
	}
	
	private void saveStatusLoginningCompleted() {
		SharedPreferenceManager sharedPreference = new SharedPreferenceManager(this);
		sharedPreference.saveBoolean(LOGIN_SHARE_PREFERENT_KEY, true);
	}
	
	public static final boolean checkLogin(Activity activity) {
		SharedPreferenceManager sharedPreference = new SharedPreferenceManager(activity);
		return sharedPreference.getBoolean(LOGIN_SHARE_PREFERENT_KEY, false);
	}

	class LoginAsyncTask extends AsyncTask<Void, Void, String[]> {
		String username, password;		
		String data, dataInfor;		

		@Override
		protected void onPreExecute() {
			username = StringUtils.trim(edtUsername.getText().toString());
			password = edtPassword.getText().toString();
			
			dialog = new ProgressDialog(LoginActivity.this);
			dialog.setMessage(getResources().getString(R.string.SIGNING_IN));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					loginAsyncTask.cancel(true);
				}
			});
			dialog.show();
		}

		@Override
		protected String[] doInBackground(Void... params) {
			String[] result = new String[2];
			try {
				NetParameter[] netParameter = new NetParameter[4];					
				netParameter[0] = new NetParameter("username", username);
				netParameter[1] = new NetParameter("password", password);
				netParameter[2] = new NetParameter("deviceUUID", app.getIMEI(LoginActivity.this));
				netParameter[3] = new NetParameter("deviceIMEI", app.getIMEI(LoginActivity.this));
				data = HttpNetServices.Instance.login(netParameter);
				responseLogin = DataParser.getLogin(data);
			
				if (responseLogin != null && responseLogin.getStatus() != null
						&& responseLogin.getStatus().equalsIgnoreCase("success")) {
					result[0] = GlobalParams.TRUE;
					result[1] = data;
					NetParameter[] netParameterInfor = new NetParameter[1];					
					netParameterInfor[0] = new NetParameter("access-token", responseLogin.getAccessToken());					
					dataInfor = HttpNetServices.Instance.getUserInformation(netParameterInfor);					
					userInformation = DataParser.getUserInformation(dataInfor);
				} else {				
					result[0] = GlobalParams.FALSE;
					result[1] = data;
				}
				
			} catch (JsonSyntaxException e) {
				result[0] = GlobalParams.FALSE;
				result[1] = data;
			} catch (Exception e) {
				result[0] = GlobalParams.FALSE;
				result[1] = e.getMessage();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String[] result) {
			dialog.dismiss();
			if (loginAsyncTask.isCancelled()) {
				return;
			} else if (GlobalParams.TRUE.equals(result[0]) && userInformation.getStatus() == 5) {
				BuManagement.saveToken(LoginActivity.this, responseLogin.getAccessToken());		
				saveStatusLoginningCompleted();
				
				Logger.error("----------------------------");
				Intent i = new Intent(LoginActivity.this, TrackingLocationService.class);
				// In reality, you would want to have a static variable for the request code instead of 192837
				PendingIntent sender = PendingIntent.getService(LoginActivity.this, 0, i, 0);
				 
				// Get the AlarmManager service
				AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
				am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5 * 60 * 1000, sender); // 1 menute
				Logger.error("--------------END--------------") ;
				
				DataStorage dataStorage = DataStorage.getInstance();
				try {
					dataStorage.save_UserInformation(userInformation, LoginActivity.this);
				} catch (IOException e) {					
					e.printStackTrace();
				}
				app.goHome(LoginActivity.this);
			} else if (GlobalParams.TRUE.equals(result[0]) && userInformation.getStatus() != 5) {
				app.alertErrorMessageString(userInformation.getMessage(),
						getString(R.string.COMMON_ERROR), LoginActivity.this);
			} else {
				try {
					app.alertErrorMessageString(responseLogin.getMessage(),
							getString(R.string.COMMON_ERROR), LoginActivity.this);
				} catch (NullPointerException e) {
					app.alertErrorMessageString(getString(R.string.COMMON_INTERNET_CONNECTION),
							getString(R.string.COMMON_ERROR), LoginActivity.this);
				}
			}
		}
	}	
}
