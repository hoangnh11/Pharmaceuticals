package com.viviproject.projection;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
		f.mContext = context;
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
    	this.mActivity = getActivity();
    	this.mContext = getActivity();
    }
    
    /**
	 * 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.frg_promotions_layout, container, false);    	
		TextView tvPromotionContents = (TextView) mView.findViewById(R.id.tvPromotionContents);
		
		if(StringUtils.isNotBlank(enDiscountProgram.getContent())){
			tvPromotionContents.setText(Html.fromHtml(enDiscountProgram.getContent()));
		}
		
		return mView;
	}
}
