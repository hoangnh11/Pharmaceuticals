package com.viviproject.visit;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.GimicAdapter;
import com.viviproject.entities.EnElement;
import com.viviproject.entities.EnGimic;
import com.viviproject.entities.EnGimicBasket;
import com.viviproject.entities.EnStores;
import com.viviproject.entities.ResponseCreateGimics;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

public class GiveGimic extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private TextView tvNameStore, tvAddressStore;
	private ListView lvGimic;
	private Button btnOk;
	
	private AppPreferences app;
	private Bundle bundle;
	private EnStores itemStore;
	private ProgressDialog progressDialog;
	private GetGimics getGimics;
	public static EnElement elements;
	private EnGimic items;
	private GimicAdapter gimicAdapter;
	private CreateGimics createGimics;
	private ResponseCreateGimics responseCreateGimics;
	private EnGimicBasket enGimicBasket;
	public static ArrayList<EnGimicBasket> arrGimicBasket;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.give_gimic_layout);
		app = new AppPreferences(this);
		bundle = app.getBundle(this);
		itemStore = new EnStores();
		itemStore = (EnStores) bundle.getSerializable(GlobalParams.STORES);
		elements = new EnElement();
		items = new EnGimic();
		responseCreateGimics = new ResponseCreateGimics();
		enGimicBasket = new EnGimicBasket();
		arrGimicBasket = new ArrayList<EnGimicBasket>();
		
		initLayout();
		
		if (itemStore != null) {
			tvNameStore.setText(itemStore.getName());
			tvAddressStore.setText(itemStore.getAddress());
		}
		
		getGimics = new GetGimics();
		getGimics.execute();
		
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.GIVE_GIMIC_TITLE));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.GONE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.GONE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		linRefresh.setVisibility(View.GONE);
		
		tvNameStore = (TextView) findViewById(R.id.tvNameStore);
		tvAddressStore = (TextView) findViewById(R.id.tvAddressStore);
		
		lvGimic = (ListView) findViewById(R.id.lvGimic);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;	
			
		case R.id.btnOk:
			createGimics = new CreateGimics();
			createGimics.execute();
			break;	
			
		default:
			break;
		}
	}
	
	OnClickListener onMinusClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListviewGimic) v).get_position();
            items = elements.getElements().get(position);
        
            if (Integer.parseInt(items.getUnit()) > 0) {
            	elements.getElements().get(position).setUnit(String.valueOf(Integer.parseInt(items.getUnit()) - 1));            	
				
            	gimicAdapter = new GimicAdapter(GiveGimic.this, elements);
            	gimicAdapter.setOnMinusClickHandler(onMinusClickHandler);
	        	gimicAdapter.setOnPlusClickHandler(onPlusClickHandler);
            	lvGimic.setAdapter(gimicAdapter);            
    			app.setListViewHeight(lvGimic, gimicAdapter);
    			
    			enGimicBasket = new EnGimicBasket();
    			enGimicBasket.setGimic_id(Integer.parseInt(elements.getElements().get(position).getId()));
    			enGimicBasket.setQuantity(Integer.parseInt(elements.getElements().get(position).getUnit()));
    			arrGimicBasket.set(position, enGimicBasket);
			}
			
        }
    };
    
    OnClickListener onPlusClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {      
        	int position = ((ItemListviewGimic) v).get_position();
            items = elements.getElements().get(position);
            if (Integer.parseInt(items.getUnit()) < Integer.parseInt(elements.getElements().get(position).getQuantity_max())) {
            	elements.getElements().get(position).setUnit(String.valueOf(Integer.parseInt(items.getUnit()) + 1));
        	
	        	gimicAdapter = new GimicAdapter(GiveGimic.this, elements);		
	        	gimicAdapter.setOnMinusClickHandler(onMinusClickHandler);
	        	gimicAdapter.setOnPlusClickHandler(onPlusClickHandler);
	        	lvGimic.setAdapter(gimicAdapter);	        
				app.setListViewHeight(lvGimic, gimicAdapter);
				
				enGimicBasket = new EnGimicBasket();
				enGimicBasket.setGimic_id(Integer.parseInt(elements.getElements().get(position).getId()));
				enGimicBasket.setQuantity(Integer.parseInt(elements.getElements().get(position).getUnit()));
				arrGimicBasket.set(position, enGimicBasket);
            }
        }
    };

	/**
     * Get Gimics list follow line
     * @author hoangnh11
     *
     */
    class GetGimics extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(GiveGimic.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getGimics.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[1];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(GiveGimic.this));				
				try {
					data = HttpNetServices.Instance.getGimics(netParameter);					
					elements = DataParser.getElements(data);
					Logger.error(data);
					return GlobalParams.TRUE;
				} catch (Exception e) {
					return GlobalParams.FALSE;
				}
			} else {
				return GlobalParams.FALSE;
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if (!isCancelled()) {
				if (result.equals(GlobalParams.TRUE) && elements != null && elements.getElements() != null
						&& elements.getElements().size() > 0
						&& elements.getStatus().equalsIgnoreCase("success")) {
					
					for (int i = 0; i < elements.getElements().size(); i++) {
						elements.getElements().get(i).setUnit("0");
						
						enGimicBasket = new EnGimicBasket();
						enGimicBasket.setGimic_id(Integer.parseInt(elements.getElements().get(i).getId()));
						enGimicBasket.setQuantity(Integer.parseInt(elements.getElements().get(i).getUnit()));
						arrGimicBasket.add(enGimicBasket);
					}
					
					gimicAdapter = new GimicAdapter(GiveGimic.this, elements);
					gimicAdapter.setOnMinusClickHandler(onMinusClickHandler);
					gimicAdapter.setOnPlusClickHandler(onPlusClickHandler);
					lvGimic.setAdapter(gimicAdapter);	
					app.setListViewHeight(lvGimic, gimicAdapter);
				}
			}
		}
	}
    
    /**
     * Create Gimics
     * @author hoangnh11
     *
     */
    class CreateGimics extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(GiveGimic.this);
			progressDialog.setMessage(getResources().getString(R.string.PROCESSING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					createGimics.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[5];
				netParameter[0] = new NetParameter("uid", app.getIMEI(GiveGimic.this) + "|" + app.getCurrentTimeStamp());
				netParameter[1] = new NetParameter("store_id", itemStore.getStore_id());
				netParameter[2] = new NetParameter("long", BuManagement.getLongitude(GiveGimic.this));
				netParameter[3] = new NetParameter("lat", BuManagement.getLatitude(GiveGimic.this));
				netParameter[4] = new NetParameter("basket", DataParser.convertObjectToString(arrGimicBasket));
				try {
					data = HttpNetServices.Instance.createGimic(netParameter, BuManagement.getToken(GiveGimic.this));					
					responseCreateGimics = DataParser.responseCreateGimics(data);
					Logger.error(data);
					return GlobalParams.TRUE;
				} catch (Exception e) {
					return GlobalParams.FALSE;
				}
			} else {
				return GlobalParams.FALSE;
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if (!isCancelled()) {
				if (result.equals(GlobalParams.TRUE) && responseCreateGimics != null) {
					app.alertErrorMessageString(responseCreateGimics.getMessage(),
							getString(R.string.COMMON_MESSAGE), GiveGimic.this);
					
					for (int i = 0; i < elements.getElements().size(); i++) {
						elements.getElements().get(i).setUnit("0");
						
						enGimicBasket = new EnGimicBasket();
						enGimicBasket.setGimic_id(Integer.parseInt(elements.getElements().get(i).getId()));
						enGimicBasket.setQuantity(Integer.parseInt(elements.getElements().get(i).getUnit()));					
						arrGimicBasket.set(i, enGimicBasket);
					}
					
					gimicAdapter = new GimicAdapter(GiveGimic.this, elements);
					gimicAdapter.setOnMinusClickHandler(onMinusClickHandler);
					gimicAdapter.setOnPlusClickHandler(onPlusClickHandler);
					lvGimic.setAdapter(gimicAdapter);				
					app.setListViewHeight(lvGimic, gimicAdapter);
				}
			}
		}
	}
}
