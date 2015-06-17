package com.viviproject.visit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.CameraView;
import com.viviproject.core.ImageHelper;

public class PictureReportActivity extends Activity implements OnClickListener{

	private CameraView mCameraView;
	private ImageView m_ivTemp;
	private ImageView buttonCapture;
	private ImageView imgfromGallery;
	private TextView flashCam;
	private ImageView switcherCam;
	private View m_ivCameraCancel;
	private boolean hasCamera = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_layout);
	
		buttonCapture = (ImageView) findViewById(R.id.button_capture);
		buttonCapture.setClickable(true);
		buttonCapture.setOnClickListener(this);
		imgfromGallery = (ImageView) findViewById(R.id.imageViewGallery);
		m_ivCameraCancel = this.findViewById(R.id.ivCameraCancel);
		m_ivCameraCancel.setOnClickListener(this);
		imgfromGallery.setOnClickListener(this);
		
		mCameraView = (CameraView) findViewById(R.id.mCameraView);
		mCameraView.setOnClickListener(this);
		m_ivTemp = (ImageView) this.findViewById(R.id.ivTemp);
		
		flashCam = (TextView) findViewById(R.id.flashCam);
		switcherCam = (ImageView) findViewById(R.id.switcherCam);	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();		
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Bitmap thumbnail = ImageHelper.getTop1Thumbnail(this);
		if (thumbnail != null)
		{
			imgfromGallery.setImageBitmap(thumbnail);
			imgfromGallery.setVisibility(View.VISIBLE);
		}
		else
		{
//			imgfromGallery.setVisibility(View.INVISIBLE);
		}
		if (hasCamera)
		{			
//			m_ivTemp.setVisibility(View.GONE);
		}
		else
		{
//			mCameraView.setVisibility(View.GONE);
//			switcherCam.setVisibility(View.GONE);
//			flashCam.setVisibility(View.GONE);
		}		
	}

	@Override
	protected void onPause()
	{
		super.onPause();
//		mCameraView.setVisibility(View.GONE);		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.button_capture:
				
				break;
			
			case R.id.imageViewGallery:
				
				break;
				
			case R.id.ivCameraCancel:			
				finish();
				break;
			case R.id.flashCam:
				
				break;
			case R.id.switcherCam:			
				
				break;
				
			case R.id.mCameraView:
				
				break;
				
			default:
				break;
		}
	}
}
