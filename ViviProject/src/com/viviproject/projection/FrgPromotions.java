package com.viviproject.projection;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.viviproject.R;
import com.viviproject.entities.EnDiscountProgramItem;
import com.viviproject.ultilities.StringUtils;

public class FrgPromotions extends FrgBaseFragmentPromotions {
	private Context mContext;
	private Activity mActivity;
	private View mView;
	
	public static FrgPromotions newInstance(Context context, EnDiscountProgramItem enDiscountProgram){
		FrgPromotions f = new FrgPromotions();
		f.enDiscountProgram = enDiscountProgram;
		f.setmContext(context);
		return f;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public String getFragmentName() {
		return super.getFragmentName();
	}
	
	/* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
     */
    @Override
    public void onAttach(Activity activity) {
    	// TODO Auto-generated method stub
    	super.onAttach(activity);
    	this.setmActivity(getActivity());
    	this.setmContext(getActivity());
    }
    
    private class NewsWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
    
    /**
	 * 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.frg_promotions_layout, container, false);    	
		//TextView tvPromotionContents = (TextView) mView.findViewById(R.id.tvPromotionContents);
		WebView webDetails = (WebView) mView.findViewById(R.id.webDetail);
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
		
		if(StringUtils.isNotBlank(enDiscountProgram.getContent())){
			//tvPromotionContents.setText(Html.fromHtml(enDiscountProgram.getContent()));
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			    String base64 = Base64.encodeToString(enDiscountProgram.getContent().getBytes(), Base64.DEFAULT);
			    webDetails.loadData(base64, "text/html; charset=utf-8", "base64");
			} else {
			    String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
			    webDetails.loadData(header + enDiscountProgram.getContent(), "text/html; charset=UTF-8", null);

			}
		}
		
		return mView;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public Activity getmActivity() {
		return mActivity;
	}

	public void setmActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}
}
