package com.viviproject.projection;

import java.util.ArrayList;

import com.viviproject.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FrgPromotions extends FrgBaseFragmentProducts {
	private Context mContext;
	private Activity mActivity;
	private View mView;
	
	public static FrgPromotions newInstance(Context context, String frgName){
		FrgPromotions f = new FrgPromotions();
		f.frgName = frgName;
		return f;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
    }
    
    /**
	 * 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.frg_promotions_layout, container, false);    	
		return mView;
	}
}
