package com.viviproject.projection;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;

public class AcPharmacer extends Activity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private TextView tvName, tvQualification, tvWorkTime, tvWorkPosition;
	private TextView tvLifeOpinion, tvCarrierOpinion, tvMucTieuCongViec, tvLinkWeb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_pharmacer);
		initLayout();
	}
	
	private void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.PRODUCT));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		
		tvName = (TextView) findViewById(R.id.tvName);
		String sourceString = "<b>" + getResources().getString(R.string.TITLE_PHARMACIER_NAME) + "</b> " 
								+ getResources().getString(R.string.EXAMPLE_TRUONG_NHU_HO); 
		tvName.setText(Html.fromHtml(sourceString));
		
		tvQualification = (TextView) findViewById(R.id.tvQualification);
		String qualificationText = "<b>" + getResources().getString(R.string.TITLE_PHARMACIER_QUALIFICATION) + "</b> " 
				+ getResources().getString(R.string.EXAMPLE_DUOC_SY_TRUNG_CAP); 
		tvQualification.setText(Html.fromHtml(qualificationText));
		
		tvWorkTime = (TextView) findViewById(R.id.tvWorkTime);
		String workTimeText = "<b>" + getResources().getString(R.string.TITLE_WORKING_TIME) + "</b> " 
				+ getResources().getString(R.string.EXAMPLE_TIME); 
		tvWorkTime.setText(Html.fromHtml(workTimeText));
		
		tvWorkPosition  = (TextView) findViewById(R.id.tvWorkPosition);
		String workPositionText = "<b>" + getResources().getString(R.string.TITLE_WORKING_POSITION) + "</b> " 
				+ getResources().getString(R.string.EXAMPLE_POSITION); 
		tvWorkPosition.setText(Html.fromHtml(workPositionText));
		
		tvLifeOpinion = (TextView) findViewById(R.id.tvLifeOpinion);
		String lifeOpinionText = "<b>" + getResources().getString(R.string.TITLE_LIFE_OPINION) + "</b> " 
				+ getResources().getString(R.string.EXAMPLE_LIFE_OPINION); 
		tvLifeOpinion.setText(Html.fromHtml(lifeOpinionText));
		
		tvCarrierOpinion = (TextView) findViewById(R.id.tvCarrierOpinion);
		String carrierOpinionText = "<b>" + getResources().getString(R.string.TITLE_WORK_OPINION) + "</b> " 
				+ getResources().getString(R.string.EXAMPLE_WORK_OPINION); 
		tvCarrierOpinion.setText(Html.fromHtml(carrierOpinionText));
		
		tvMucTieuCongViec = (TextView) findViewById(R.id.tvMucTieuCongViec);
		String mucTieuCongViecText = "<b>" + getResources().getString(R.string.TITLE_MUC_TIEU_CONG_VIEC) + "</b> " 
				+ getResources().getString(R.string.EXAMPLE_MUC_TIEU_CONG_VIEC); 
		tvMucTieuCongViec.setText(Html.fromHtml(mucTieuCongViecText));
		
		tvLinkWeb = (TextView) findViewById(R.id.tvLinkWeb);
		String linkWebText = "<b>" + getResources().getString(R.string.TITLE_LINK_WEB) + "</b><br/> "
				+ "<a href ='" +  getResources().getString(R.string.EXAMPLE_LINK_WEB) + "'>" 
				+ getResources().getString(R.string.EXAMPLE_LINK_WEB) + "</a>"; 
		tvLinkWeb.setText(Html.fromHtml(linkWebText));
		tvLifeOpinion.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcPharmacer.this.finish();
			break;

		default:
			break;
		}
		
	}
}
