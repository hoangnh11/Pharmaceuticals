package com.fptsoftware.viviproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fptsoftware.ultilities.AppPreferences;
import com.fptsoftware.ultilities.SharedPreferenceManager;

public class HomeActivity extends Activity implements OnClickListener{
	public static final String LOGIN_SHARE_PREFERENT_KEY = "login complete";
	private ImageView imgSetting;
	private LinearLayout linSetting, linLogout;
	private LinearLayout linRefresh;
	private boolean showSetting;
	SharedPreferenceManager sm;
	private AppPreferences appPreferences;
	AlertDialog _alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		appPreferences = new AppPreferences(this);
		InitLayout();
	}

	public void InitLayout() {
		showSetting = true;
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		imgSetting = (ImageView) findViewById(R.id.imgSetting);
		imgSetting.setOnClickListener(this);
		linSetting = (LinearLayout) findViewById(R.id.linSetting);
		linLogout = (LinearLayout) findViewById(R.id.linLogout);
		linLogout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgSetting:
			if (showSetting) {
				linSetting.setVisibility(View.VISIBLE);
				showSetting = false;
			} else {
				linSetting.setVisibility(View.INVISIBLE);
				showSetting = true;
			}
			break;
			
		case R.id.linLogout:
			logout();
			break;
			
		case R.id.linRefresh:
			
			break;
			
		default:
			break;
		}
	}

	@Override
	public void onBackPressed()
	{
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(R.string.MORE_ARE_YOU_SURE));
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.COMMON_OK),
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						alertDialog.dismiss();					
						finish();
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.COMMON_CANCEL),
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						alertDialog.dismiss();
					}
				});
		alertDialog.show();
	}
	
	private void saveStatusLogoutCompleted() {
		SharedPreferenceManager sharedPreference = new SharedPreferenceManager(this);
		sharedPreference.saveBoolean(LOGIN_SHARE_PREFERENT_KEY, false);
	}
	
	public void logout() {
		android.content.DialogInterface.OnClickListener btDialogListener = new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					_alertDialog.dismiss();
					saveStatusLogoutCompleted();
					appPreferences.goLogin(HomeActivity.this);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					_alertDialog.dismiss();
					break;
				default:
					break;
				}
			}
		};
		_alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
		_alertDialog.setTitle(getString(R.string.MORE_ARE_YOU_SURE));
		_alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.COMMON_OK), btDialogListener);
		_alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.COMMON_CANCEL), btDialogListener);
		_alertDialog.show();
	}
}
