package com.viviproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.viviproject.R;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.SharedPreferenceManager;
import com.viviproject.ultilities.StringUtils;

public class LoginActivity extends Activity implements OnClickListener{
	public static final String LOGIN_SHARE_PREFERENT_KEY = "login complete";
	private EditText edtUsername, edtPassword;
	private Button btnLogin;
	private AppPreferences app;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
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
			int errorCode = validateInput();
			if (errorCode == 0) {
//				loginAsyncTask = new LoginAsyncTask();
//				loginAsyncTask.execute();
				saveStatusLoginningCompleted();
				app.goHome(LoginActivity.this);				
			} else {				
				app.alertErrorMessageInt(errorCode, getString(R.string.COMMON_MESSAGE), this);
			}
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
}