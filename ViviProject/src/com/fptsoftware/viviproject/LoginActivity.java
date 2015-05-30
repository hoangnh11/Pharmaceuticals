package com.fptsoftware.viviproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.fptsoftware.ultilities.AppPreferences;
import com.fptsoftware.ultilities.Logger;
import com.fptsoftware.ultilities.StringUtils;

public class LoginActivity extends Activity implements OnClickListener{
	private EditText edtUsername, edtPassword;
	private Button btnLogin;
	private AppPreferences app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		app = new AppPreferences(this);
		InitLayout();
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
			} else {				
				app.alertErrorMessageInt(errorCode, getString(R.string.COMMON_MESSAGE), this);
			}
			break;

		default:
			break;
		}		
	}
}
