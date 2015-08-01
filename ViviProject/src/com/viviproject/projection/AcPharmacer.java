package com.viviproject.projection;

import java.io.IOException;
import java.io.StreamCorruptedException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.viviproject.R;
import com.viviproject.entities.UserInformation;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringUtils;

public class AcPharmacer extends Activity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private TextView tvName, tvQualification, tvWorkTime, tvWorkPosition;
	private TextView tvLifeOpinion, tvCarrierOpinion, tvMucTieuCongViec, tvLinkWeb;
	private ImageView imgAvata;
	
	private static RestAdapter restAdapter;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_pharmacer);
		
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoaderConfiguration imageLoaderConfiguration = 
				new ImageLoaderConfiguration.Builder(AcPharmacer.this)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(imageLoaderConfiguration);

		initLayout();
	}
	
	/**
	 * initial layout of screen
	 */
	private void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.PHARMACIER));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.INVISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.INVISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		linOptionRefresh.setOnClickListener(this);
		
		imgAvata = (ImageView) findViewById(R.id.imgPharmaceAvata);
		tvName = (TextView) findViewById(R.id.tvName);
		tvQualification = (TextView) findViewById(R.id.tvQualification);
		tvWorkTime = (TextView) findViewById(R.id.tvWorkTime);
		tvWorkPosition  = (TextView) findViewById(R.id.tvWorkPosition);
		tvLifeOpinion = (TextView) findViewById(R.id.tvLifeOpinion);
		tvCarrierOpinion = (TextView) findViewById(R.id.tvCarrierOpinion);
		tvMucTieuCongViec = (TextView) findViewById(R.id.tvMucTieuCongViec);
		tvLinkWeb = (TextView) findViewById(R.id.tvLinkWeb);
		
		refreshData(false);
	}

	/**
	 * refresh data
	 * @param getNewestData true: load newest data from service, 
	 * false: load data from service, if service cannot load from local 
	 */
	private void refreshData(boolean getNewestData) {
		if(getNewestData){
			getUserInfoFromServer();
		} else {
			UserInformation userInformation;
			try {
				userInformation = DataStorage.getInstance().read_UserInformation(getApplicationContext());
				if(null != userInformation){
					displayPharmacierInfo(userInformation);
				}
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
				getUserInfoFromServer();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				getUserInfoFromServer();
			} catch (IOException e) {
				e.printStackTrace();
				getUserInfoFromServer();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcPharmacer.this.finish();
			break;
		
		case R.id.linRefresh:
			refreshData(true);
			break;
			
		default:
			break;
		}
		
	}
	
	/**
	 * displayPharmacierInfo
	 * @param userInformation
	 */
	public void displayPharmacierInfo(UserInformation userInformation) {
		
		if(StringUtils.isNotBlank(userInformation.getAvatar())){
			imageLoader.displayImage(userInformation.getAvatar(), imgAvata, options);
		} else {
			imgAvata.setImageResource(R.drawable.ic_stub);
		}
		
		//display name
		if(StringUtils.isNotBlank(userInformation.getUsername())){
			String sourceString = "<b>" + getResources().getString(R.string.TITLE_PHARMACIER_NAME) + "</b> " 
									+ userInformation.getUsername(); 
			tvName.setText(Html.fromHtml(sourceString));
		} else {
			String sourceString = "<b>" + getResources().getString(R.string.TITLE_PHARMACIER_NAME) + "</b> "; 
			tvName.setText(Html.fromHtml(sourceString));
		}
		
		// display education
		if(StringUtils.isNotBlank(userInformation.getEducation())){
			String qualificationText = "<b>" + getResources().getString(R.string.TITLE_PHARMACIER_QUALIFICATION) + "</b> " 
					+ userInformation.getEducation(); 
			tvQualification.setText(Html.fromHtml(qualificationText));
		} else {
			String qualificationText = "<b>" + getResources().getString(R.string.TITLE_PHARMACIER_QUALIFICATION) + "</b> "; 
			tvQualification.setText(Html.fromHtml(qualificationText));
		}
		
		// display working time
		if(StringUtils.isNotBlank(userInformation.getWorking_start())){
			String workTimeText = "<b>" + getResources().getString(R.string.TITLE_WORKING_TIME) + "</b> " 
					+ userInformation.getWorking_start(); 
			tvWorkTime.setText(Html.fromHtml(workTimeText));
		} else {
			String workTimeText = "<b>" + getResources().getString(R.string.TITLE_WORKING_TIME) + "</b> "; 
			tvWorkTime.setText(Html.fromHtml(workTimeText));
		}
		
		String workPositionText = "<b>" + getResources().getString(R.string.TITLE_WORKING_POSITION) + "</b> " 
				+ userInformation.getWorking_position() + ": " + userInformation.getRole(); 
		tvWorkPosition.setText(Html.fromHtml(workPositionText));
		
		if(StringUtils.isNotBlank(userInformation.getPersonal_opinion())){
			String lifeOpinionText = "<b>" + getResources().getString(R.string.TITLE_LIFE_OPINION) + "</b> " 
					+ userInformation.getPersonal_opinion(); 
			tvLifeOpinion.setText(Html.fromHtml(lifeOpinionText));
		} else {
			String lifeOpinionText = "<b>" + getResources().getString(R.string.TITLE_LIFE_OPINION) + "</b> ";
			tvLifeOpinion.setText(Html.fromHtml(lifeOpinionText));
		}
		
		if(StringUtils.isNotBlank(userInformation.getPersonal_work_opinion())){
			String carrierOpinionText = "<b>" + getResources().getString(R.string.TITLE_WORK_OPINION) + "</b> " 
					+ userInformation.getPersonal_work_opinion(); 
			tvCarrierOpinion.setText(Html.fromHtml(carrierOpinionText));
		}else {
			String carrierOpinionText = "<b>" + getResources().getString(R.string.TITLE_WORK_OPINION) + "</b> "; 
			tvCarrierOpinion.setText(Html.fromHtml(carrierOpinionText));
		}
		
		if(StringUtils.isNotBlank(userInformation.getPersonal_target())){
			String mucTieuCongViecText = "<b>" + getResources().getString(R.string.TITLE_MUC_TIEU_CONG_VIEC) + "</b> " 
					+ userInformation.getPersonal_target(); 
			tvMucTieuCongViec.setText(Html.fromHtml(mucTieuCongViecText));
		}else {
			String mucTieuCongViecText = "<b>" + getResources().getString(R.string.TITLE_MUC_TIEU_CONG_VIEC) + "</b> "; 
			tvMucTieuCongViec.setText(Html.fromHtml(mucTieuCongViecText));
		}
		
		if(StringUtils.isNotBlank(userInformation.getUrl())){
			String linkWebText = "<b>" + getResources().getString(R.string.TITLE_LINK_WEB) + "</b><br/> "
					+ "<a href ='" +  userInformation.getUrl() + "'>" 
					+ userInformation.getUrl() + "</a>"; 
			tvLinkWeb.setText(Html.fromHtml(linkWebText));
			tvLinkWeb.setMovementMethod(LinkMovementMethod.getInstance());
		} else {
			String linkWebText = "<b>" + getResources().getString(R.string.TITLE_LINK_WEB) + "</b>="; 
			tvLinkWeb.setText(Html.fromHtml(linkWebText));
			tvLinkWeb.setMovementMethod(LinkMovementMethod.getInstance());
		}
	}
	
	/**
	 * get newest data from server
	 */
	private void getUserInfoFromServer(){
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		
		if(null == dialog){
			dialog = new ProgressDialog(AcPharmacer.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					
				}
			});
		}
		dialog.show();
		
		restAdapter.create(ViviApi.class).getUserInfo(token, myCallback);
		
	}
	
	Callback<UserInformation> myCallback = new Callback<UserInformation>() {

		@Override
		public void failure(RetrofitError arg0) {
			if(null != AcPharmacer.this && null != dialog){
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
			
			BuManagement.alertErrorMessageString("error", "error", AcPharmacer.this);
		}

		@Override
		public void success(UserInformation userInformation, Response arg1) {
			Logger.error("Get user info success");
			if(null != AcPharmacer.this && null != dialog){
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
			
			try {
				DataStorage.getInstance().save_UserInformation(userInformation, getApplicationContext());
				displayPharmacierInfo(userInformation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	};
}
