package com.viviproject.projection;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.internal.fg;
import com.viviproject.R;
import com.viviproject.adapter.AdapterFrgPromotions;
import com.viviproject.entities.EnDiscountProgram;
import com.viviproject.entities.EnDiscountProgramItem;
import com.viviproject.library.TabPageIndicator;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringConverter;

public class AcPromotions extends FragmentActivity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ViewPager promotionsPager;
	private TabPageIndicator promotionsIndicator;
	
	private ArrayList<Fragment> listFrgPromotion;
	private AdapterFrgPromotions adapterFrgPromotion;
	
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_promotions);
		
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
		tvHeader.setText(getResources().getString(R.string.DISCOUNT_PROGRAM));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.INVISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.INVISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		linOptionRefresh.setOnClickListener(this);
		
		promotionsPager = (ViewPager) findViewById(R.id.promotions_pager);
		promotionsIndicator = (TabPageIndicator) findViewById(R.id.promotions_title_indicator);
		
		listFrgPromotion = new ArrayList<Fragment>();
		/*listFrgPromotion.add(FrgPromotions.newInstance(getApplicationContext(), getResources().getString(R.string.TEXT_EARN_POINTS)));
		listFrgPromotion.add(FrgPromotions.newInstance(getApplicationContext(), getResources().getString(R.string.TEXT_DISCOUNT)));
		listFrgPromotion.add(FrgPromotions.newInstance(getApplicationContext(), getResources().getString(R.string.TEXT_ORTHER_PROMOTION)));
		listFrgPromotion.add(FrgPromotions.newInstance(getApplicationContext(), getResources().getString(R.string.TEXT_PROMOTION_FOR_PRODUCT)));*/
		adapterFrgPromotion = new AdapterFrgPromotions(getSupportFragmentManager(), listFrgPromotion);
		promotionsPager.setAdapter(adapterFrgPromotion);
		promotionsIndicator.setViewPager(promotionsPager);
		
		refreshData();
	}

	private void refreshData() {
		getDiscountProgramFromServer();
	}

	private void updateDataScreen(EnDiscountProgram enDisCountProgram){
		if(null == enDisCountProgram) return;
		
		ArrayList<Fragment> listFrgPromotionNew = new ArrayList<Fragment>();
		for (int i = 0; i < enDisCountProgram.getDiscounts().size(); i++) {
			Fragment fragment =  FrgPromotions.newInstance(getApplicationContext(), enDisCountProgram.getDiscounts().get(i));
			listFrgPromotionNew.add(fragment);
		}
		
		adapterFrgPromotion.setListFragment(listFrgPromotionNew);
		adapterFrgPromotion.notifyDataSetChanged();
		promotionsIndicator.setViewPager(promotionsPager);
		promotionsIndicator.notifyDataSetChanged();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcPromotions.this.finish();
			break;
		
		case R.id.linRefresh:
			refreshData();
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * get newest data from server
	 */
	private void getDiscountProgramFromServer(){
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .setConverter(new StringConverter())
                    .build();
        }
		
		String token = BuManagement.getToken(getApplicationContext());
		
		if(null == dialog){
			dialog = new ProgressDialog(AcPromotions.this);
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
		
		restAdapter.create(ViviApi.class).getDiscountProgram(token, myCallback);
	}
	
	Callback<String> myCallback = new Callback<String>() {

		@Override
		public void failure(RetrofitError retrofitError) {
			retrofitError.printStackTrace();
			
			if(null != AcPromotions.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			if(null != retrofitError){
				if(retrofitError.isNetworkError()){
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG_LOAD_DATA_LOCAL)
							, "Error", AcPromotions.this);
					
					//display lastest data
					try{
						EnDiscountProgram enDiscountPrograms = DataStorage
								.getInstance().read_EnDiscountProgram(
										getApplicationContext());
						if(null != enDiscountPrograms){
							updateDataScreen(enDiscountPrograms);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
							, "Error", AcPromotions.this);
				}
			}
		}

		@Override
		public void success(String response, Response arg1) {
			Logger.error("Get list discount program success:" + response);
			if(null != AcPromotions.this && null != dialog){
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
			
			EnDiscountProgram enDiscountPrograms;
			try {
				enDiscountPrograms = DataParser.getEnDiscountProgram(response);
				if(null != enDiscountPrograms && (null != enDiscountPrograms.getDiscounts()) 
						&& (enDiscountPrograms.getDiscounts().size() > 0)){
					try {
						DataStorage.getInstance().save_EnDiscountProgram(enDiscountPrograms, getApplicationContext());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					updateDataScreen(enDiscountPrograms);
				} else {
					try{
						BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG_LOAD_DATA_LOCAL)
								, "Error", AcPromotions.this);
						
						EnDiscountProgram enDiscountProgramsLast = DataStorage
								.getInstance().read_EnDiscountProgram(getApplicationContext());
						if(null != enDiscountPrograms){
							updateDataScreen(enDiscountProgramsLast);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				BuManagement.alertErrorMessageString(getResources().getString(R.string.COMMON_ERROR_MSG)
						, "Error", AcPromotions.this);
			}
			
		}	
	};
}
