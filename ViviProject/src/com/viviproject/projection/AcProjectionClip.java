package com.viviproject.projection;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.AdapterProjectionGridClip;
import com.viviproject.entities.EnVideos;
import com.viviproject.entities.EnVideosResponse;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringConverter;

public class AcProjectionClip extends Activity implements OnClickListener, OnItemClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	
	private GridView griProjectionClip;
	private AdapterProjectionGridClip adapterProjectionGridClip;
	private ArrayList<EnVideos> listyoutubeUrl;
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	private int page = 0;
	private int perPage = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_projection_clip);
		initLayout();
	}

	private void initLayout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.CLIP));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.VISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.VISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		linOptionRefresh.setOnClickListener(this);
		
		griProjectionClip = (GridView) findViewById(R.id.griProjectionClip);
		listyoutubeUrl = new ArrayList<EnVideos>();
		
		adapterProjectionGridClip = new AdapterProjectionGridClip(getApplicationContext(), listyoutubeUrl);
		griProjectionClip.setAdapter(adapterProjectionGridClip);
		griProjectionClip.setOnItemClickListener(this);
		
		refreshData();
	}

	/**
	 * 
	 */
	private void refreshData() {
		getdataFromServer();
	}

	protected void updateDataScreen(ArrayList<EnVideos> enVideos) {
		if(null == enVideos) return;
		
		adapterProjectionGridClip.setListVideo(enVideos);
		adapterProjectionGridClip.notifyDataSetChanged();
		
	} 
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String url = adapterProjectionGridClip.getItem(position).getVideo_url();
		String idVideo = getYoutubeVideoId(url);
		Logger.error("ClipId: " + idVideo);
		
		Intent i = new Intent(AcProjectionClip.this, FullscreenDemoActivity.class);
		i.putExtra("VIDEO_ID", idVideo);
		i.putExtra("VIDEO_NAME", adapterProjectionGridClip.getItem(position).getPreview_text());
		startActivity(i);
	}
	
	private String getYoutubeVideoId(String youbeURL){
		String url = "";
		try {
			String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
		
		    Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		    Matcher matcher = compiledPattern.matcher(youbeURL);
		
		    if(matcher.find()){
		       String strID =  matcher.group(1);
		       return strID;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcProjectionClip.this.finish();
			break;

		case R.id.linRefresh:
			refreshData();
			break;
			
		default:
			break;
		}
	}
	
	private void getdataFromServer() {
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .setConverter(new StringConverter())
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		Logger.error("access-token: " + token);
		
		if(null == dialog){
			dialog = new ProgressDialog(AcProjectionClip.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					//cancel threat 
					clipsCalback.failure(null);
				}
			});
		}
		dialog.show();
		
		restAdapter.create(ViviApi.class).getVideos(token, page, perPage, clipsCalback);
	}
	
	private Callback<String> clipsCalback = new Callback<String>() {
		
		@Override
		public void success(String responseString, Response arg1) {
			Logger.error("GET videos response: " + responseString);
			if(null != AcProjectionClip.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			try {
				EnVideosResponse enVideosResponse = DataParser.getEnVideosResponse(responseString);
				
				if(null != enVideosResponse){
					ArrayList<EnVideos> enVideosList = enVideosResponse.getVideos();
					if(null != enVideosList && enVideosList.size() > 0){
						updateDataScreen(enVideosList);
					} else {
						BuManagement.alertErrorMessageString(enVideosResponse.getStatus()
								, "Error", AcProjectionClip.this);
					}
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcProjectionClip.this);
				}
			} catch (Exception e) {
				e.printStackTrace();
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcProjectionClip.this);
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void failure(RetrofitError retrofitError) {
			retrofitError.printStackTrace();
			if(null != AcProjectionClip.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			if(retrofitError.isNetworkError()){
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_INTERNET_CONNECTION)
						, "Error", AcProjectionClip.this);
			} else {
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcProjectionClip.this);
			}
			
		}
	};
}
