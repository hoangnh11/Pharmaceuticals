package com.viviproject.projection;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
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

public class AcProjectionClip extends Activity implements OnClickListener, OnItemClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	
	private GridView griProjectionClip;
	private AdapterProjectionGridClip adapterProjectionGridClip;
	private ArrayList<String> listyoutubeUrl;
	
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
		
		griProjectionClip = (GridView) findViewById(R.id.griProjectionClip);
		listyoutubeUrl = new ArrayList<String>();
		listyoutubeUrl.add("https://www.youtube.com/watch?v=B8uOnK_cZpo");
		listyoutubeUrl.add("https://www.youtube.com/watch?v=vZk72cJv924");
		listyoutubeUrl.add("https://www.youtube.com/watch?v=rMgSuPVyw5Y");
		listyoutubeUrl.add("https://www.youtube.com/watch?v=Z_0n-YEkMlU");
		listyoutubeUrl.add("https://www.youtube.com/watch?v=bfXRGHOOt-Q");
		listyoutubeUrl.add("https://www.youtube.com/watch?v=E1nsMwVUi8c");
		listyoutubeUrl.add("https://www.youtube.com/watch?v=B8uOnK_cZpo");
		listyoutubeUrl.add("https://www.youtube.com/watch?v=B8uOnK_cZpo");
		
		adapterProjectionGridClip = new AdapterProjectionGridClip(getApplicationContext(), listyoutubeUrl);
		griProjectionClip.setAdapter(adapterProjectionGridClip);
		griProjectionClip.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String url = listyoutubeUrl.get(position);
		String idVideo = getYoutubeVideoId(url);
		Intent i = new Intent(AcProjectionClip.this, FullscreenDemoActivity.class);
		i.putExtra("VIDEO_ID", idVideo);
		startActivity(i);
	}
	
	private String getYoutubeVideoId(String youbeURL){
		String url = "";
		try {
			String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
		
		    Pattern compiledPattern = Pattern.compile(pattern);
		    Matcher matcher = compiledPattern.matcher(youbeURL);
		
		    if(matcher.find()){
		       String strID =  matcher.group();
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

		default:
			break;
		}
		
	}
}
