package com.viviproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viviproject.R;
import com.viviproject.projection.AcProducts;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.SharedPreferenceManager;

public class HomeActivity extends Activity implements OnClickListener{
	public static final String LOGIN_SHARE_PREFERENT_KEY = "login complete";
	private ImageView imgSetting;
	private LinearLayout linSetting, linLogout, linRefresh;
	private LinearLayout linTongquan, linSubTongquan, linBaocaodoanhso, linBaocaodophu, linkhachhangchuaphatsinhdoanhso;
	private LinearLayout linRoundCustomer, linSubRoundCustomer, linListCustomer, linMap, linCreateNewCustomer;
	private LinearLayout linVisit;
	private LinearLayout linSales;
	private LinearLayout linDeliver, linSubDeliver, linOrder, linDelivedOrder, linProductImport;
	private LinearLayout linProjection, linSubProjection, linDiscountProgram, linProduct, linCompany, linPharmacier, linClip;
	private LinearLayout linGimic;
	private LinearLayout linReport, linSubReport, linSumProfit, linProfitFollowCustomer, linProfitGraphic, linPosterCamera,
							linUnfriendCamera, linTradeMarketingCamera;
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
		
		linTongquan = (LinearLayout) findViewById(R.id.linTongquan);
		linTongquan.setOnClickListener(this);
		linSubTongquan = (LinearLayout) findViewById(R.id.linSubTongquan);
		linBaocaodoanhso = (LinearLayout) findViewById(R.id.linBaocaodoanhso);
		linBaocaodoanhso.setOnClickListener(this);
		linBaocaodophu = (LinearLayout) findViewById(R.id.linBaocaodophu);
		linBaocaodophu.setOnClickListener(this);
		linkhachhangchuaphatsinhdoanhso = (LinearLayout) findViewById(R.id.linkhachhangchuaphatsinhdoanhso);
		linkhachhangchuaphatsinhdoanhso.setOnClickListener(this);
		
		linRoundCustomer = (LinearLayout) findViewById(R.id.linRoundCustomer);
		linRoundCustomer.setOnClickListener(this);
		linSubRoundCustomer = (LinearLayout) findViewById(R.id.linSubRoundCustomer);
		linSubRoundCustomer.setOnClickListener(this);
		linListCustomer = (LinearLayout) findViewById(R.id.linListCustomer);
		linListCustomer.setOnClickListener(this);
		linMap = (LinearLayout) findViewById(R.id.linMap);
		linMap.setOnClickListener(this);
		linCreateNewCustomer = (LinearLayout) findViewById(R.id.linCreateNewCustomer);
		linCreateNewCustomer.setOnClickListener(this);
		
		linProjection = (LinearLayout) findViewById(R.id.linProjection);
		linProjection.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
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
			
		case R.id.linRoundCustomer:
			if (linSubRoundCustomer.getVisibility() == View.GONE) {
				linSubRoundCustomer.setVisibility(View.VISIBLE);
			} else {
				linSubRoundCustomer.setVisibility(View.GONE);
			}
			break;
			
		case R.id.linListCustomer:
			
			break;	
			
		case R.id.linMap:
			
			break;
			
		case R.id.linCreateNewCustomer:
			
			break;
			
		case R.id.linProjection:
			Intent intentProject = new Intent(HomeActivity.this, AcProducts.class);
			startActivity(intentProject);
			break;
			
		case R.id.linTongquan:
			if (linSubTongquan.getVisibility() == View.GONE) {
				linSubTongquan.setVisibility(View.VISIBLE);
			} else {
				linSubTongquan.setVisibility(View.GONE);
			}
			break;
			
		case R.id.linBaocaodoanhso:
			intent = new Intent(this, ProfitReportActivity.class);
			startActivity(intent);
			break;
			
		case R.id.linBaocaodophu:
			intent = new Intent(this, CoverProductReport.class);
			startActivity(intent);
			break;
			
		case R.id.linkhachhangchuaphatsinhdoanhso:
			intent = new Intent(this, CustomerProfitActivity.class);
			startActivity(intent);
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
