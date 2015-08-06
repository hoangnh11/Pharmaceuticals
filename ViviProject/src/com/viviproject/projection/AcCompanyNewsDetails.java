package com.viviproject.projection;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnCompanyNewsDetails;
import com.viviproject.entities.EnCompanyNewsDetailsRespone;
import com.viviproject.entities.EnNews;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringConverter;

@SuppressLint("SetJavaScriptEnabled")
public class AcCompanyNewsDetails extends Activity implements OnClickListener {
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private TextView tvTitle, tvPreviewText;
	private WebView webDetails;
	
	private EnNews enNews;
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_company_news_details);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle.containsKey("com.vivi.EnNews")){
			enNews = (EnNews) bundle.get("com.vivi.EnNews");
		}
		
		intLayout();
	}
	
	/**
	 * initial layout of screen
	 */
	private void intLayout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.COMPANY));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.INVISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.INVISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		linOptionRefresh.setOnClickListener(this);
		
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvPreviewText = (TextView) findViewById(R.id.tvPreviewText);
		webDetails = (WebView) findViewById(R.id.webDetail);
		if (Build.VERSION.SDK_INT >= 11) {
			webDetails.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		}
		
		webDetails.setBackgroundColor(0x00000000);
		webDetails.setWebViewClient(new NewsWebViewClient());
		webDetails.getSettings().setJavaScriptEnabled(true);
		webDetails.getSettings().setSupportZoom(true);
		webDetails.getSettings().setBuiltInZoomControls(true);
		webDetails.getSettings().setLoadWithOverviewMode(true);
		// these mode is default of android
		webDetails.getSettings().setUseWideViewPort(false);
		webDetails.getSettings().setDefaultTextEncodingName("utf-8");
		webDetails.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webDetails.setScrollbarFadingEnabled(false);
		
		refreshData(enNews);
	}

	/**
	 * refresh data 
	 */
	private void refreshData(EnNews enNews) {
		if(null == enNews ) return;
		
		getNewsDetailsFromServer(enNews);
	}

	/**
	 * 
	 * @param newsDtails
	 */
	protected void updateDataScreen(EnCompanyNewsDetailsRespone newsDtails) {
		if(null == newsDtails ) return;
		
		EnCompanyNewsDetails news = newsDtails.getDetail();
		tvTitle.setText(news.getTitle());
		tvPreviewText.setText(news.getPreview_text());
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
		    String base64 = Base64.encodeToString(news.getDetail_text().getBytes(), Base64.DEFAULT);
		    webDetails.loadData(base64, "text/html; charset=utf-8", "base64");
		} else {
		    String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    webDetails.loadData(header + news.getDetail_text(), "text/html; charset=UTF-8", null);

		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			this.finish();
			break;

		default:
			break;
		}

	}

	private class NewsWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	
	/**
	 * get news data from server
	 */
	private void getNewsDetailsFromServer(EnNews news) {
		if(null == news ) return;
		
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .setConverter(new StringConverter())
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		Logger.error("access-token: " + token);
		
		if(null == dialog){
			dialog = new ProgressDialog(AcCompanyNewsDetails.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					//cancel threat 
					companyNewsCallBack.failure(null);
				}
			});
		}
		dialog.show();
		
		restAdapter.create(ViviApi.class).getPresentationsNewsDetail(news.getId(), token, companyNewsCallBack);
	}
	
	Callback<String> companyNewsCallBack = new Callback<String>() {
		
		@Override
		public void success(String responseString, Response response) {
			Logger.error("GET Company new response: " + responseString);
			if(null != AcCompanyNewsDetails.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			try {
				EnCompanyNewsDetailsRespone newsDtails = DataParser.getNewsDetailResponse(responseString);
				
				if(null != newsDtails){
					updateDataScreen(newsDtails);
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcCompanyNewsDetails.this);
				}
			} catch (Exception e) {
				e.printStackTrace();
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcCompanyNewsDetails.this);
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			retrofitError.printStackTrace();
			if(null != AcCompanyNewsDetails.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			if(retrofitError.isNetworkError()){
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_INTERNET_CONNECTION)
						, "Error", AcCompanyNewsDetails.this);
			} else {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcCompanyNewsDetails.this);
			}
		}
	};
}
