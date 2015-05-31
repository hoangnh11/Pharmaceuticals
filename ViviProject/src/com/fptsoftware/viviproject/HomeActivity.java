package com.fptsoftware.viviproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeActivity extends Activity implements OnClickListener{
	
	private ImageView imgSetting;
	private LinearLayout linSetting;
	private boolean showSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		InitLayout();
	}

	public void InitLayout() {
		showSetting = true;
		imgSetting = (ImageView) findViewById(R.id.imgSetting);
		imgSetting.setOnClickListener(this);
		linSetting = (LinearLayout) findViewById(R.id.linSetting);
		
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

		default:
			break;
		}
	}

	
}
