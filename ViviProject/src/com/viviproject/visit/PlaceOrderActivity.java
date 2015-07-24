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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.ForsaleAdapter;
import com.viviproject.entities.EnBasket;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.Products;
import com.viviproject.entities.ResponsePrepare;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

public class PlaceOrderActivity extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private TextView tvCreateOrder;	
	private LinearLayout linSubCreateOrder;
	private ListView lvForsale;
	
	private AppPreferences app;
	private ProgressDialog progressDialog;
	private GetProduct getProduct;
	private Products enProducts;
	private ForsaleAdapter forsaleAdapter;
	private EnProducts items;
	
	private PrepareSale prepareSale;
	private ResponsePrepare responsePrepare;
	private EnBasket enBasket;
	private ArrayList<EnBasket> arrBasket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_order_layout);
		app = new AppPreferences(this);
		enProducts = new Products();
		items = new EnProducts();
		
		responsePrepare = new ResponsePrepare();
		enBasket = new EnBasket();
		arrBasket = new ArrayList<EnBasket>();
		
		initLayout();
		
		getProduct = new GetProduct();
		getProduct.execute();
	}

	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.CREATE_ORDER));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);	
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		
		tvCreateOrder = (TextView) findViewById(R.id.tvCreateOrder);
		tvCreateOrder.setOnClickListener(this);	
		linSubCreateOrder = (LinearLayout) findViewById(R.id.linSubCreateOrder);
		
		lvForsale = (ListView) findViewById(R.id.lvForsale);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.tvCreateOrder:
			
			if (linSubCreateOrder.getVisibility() == View.GONE) {
				arrBasket = new ArrayList<EnBasket>();
				for (int i = 0; i < enProducts.getProducts().size(); i++) {
					enBasket = new EnBasket();
					enBasket.setProduct_id(Integer.parseInt(enProducts.getProducts().get(i).getId()));
					enBasket.setQuantity(Integer.parseInt(enProducts.getProducts().get(i).getUnit()));
					
					if (enProducts.getProducts().get(i).getDiscount() != null 
						&& enProducts.getProducts().get(i).getDiscount().getPoint() != null) {
						enBasket.setPoint(Integer.parseInt(enProducts.getProducts().get(i).getDiscount().getPoint().getDiscount_id()));
					} else {
						enBasket.setPoint(Integer.parseInt("0"));
					}
					
					if (enProducts.getProducts().get(i).getDiscount() != null 
						&& enProducts.getProducts().get(i).getDiscount().getSale() != null ) {
						enBasket.setSale(Integer.parseInt(enProducts.getProducts().get(i).getDiscount().getSale().getDiscount_id()));
					} else {
						enBasket.setSale(Integer.parseInt("0"));
					}
					
					if (enProducts.getProducts().get(i).getDiscount() != null 
						&& enProducts.getProducts().get(i).getDiscount().getOther() != null ) {
						enBasket.setOther(Integer.parseInt(enProducts.getProducts().get(i).getDiscount().getOther().getDiscount_id()));
					} else {
						enBasket.setOther(Integer.parseInt("0"));
					}
					
					arrBasket.add(enBasket);
				}
				
				prepareSale = new PrepareSale();
				prepareSale.execute();
				tvCreateOrder.setBackgroundResource(R.color.BG_GRAY9E);
				linSubCreateOrder.setVisibility(View.VISIBLE);
			} else {
				linSubCreateOrder.setVisibility(View.GONE);
				tvCreateOrder.setBackgroundResource(R.color.BLUE);
			}
			
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
        	int position = ((ItemListViewForsale) v).get_position();        
            items = enProducts.getProducts().get(position);           
            	
            if (Integer.parseInt(items.getUnit()) > 0) {
            	enProducts.getProducts().get(position).setUnit(String.valueOf(Integer.parseInt(items.getUnit()) - 1));            	
				
            	forsaleAdapter = new ForsaleAdapter(PlaceOrderActivity.this, enProducts);
    			forsaleAdapter.setOnTDClickHandler(onTDClickHandler);
    			forsaleAdapter.setOnCKClickHandler(onCKClickHandler);
    			forsaleAdapter.setOnOtherClickHandler(onOtherClickHandler);
    			forsaleAdapter.setOnMinusClickHandler(onMinusClickHandler);
    			forsaleAdapter.setOnPlusClickHandler(onPlusClickHandler);
    			lvForsale.setAdapter(forsaleAdapter);
    			app.setListViewHeight(lvForsale, forsaleAdapter);
			}
			
        }
    };
    
    OnClickListener onPlusClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewForsale) v).get_position();        
            items = enProducts.getProducts().get(position);
          
        	enProducts.getProducts().get(position).setUnit(String.valueOf(Integer.parseInt(items.getUnit()) + 1));
        	
        	forsaleAdapter = new ForsaleAdapter(PlaceOrderActivity.this, enProducts);
			forsaleAdapter.setOnTDClickHandler(onTDClickHandler);
			forsaleAdapter.setOnCKClickHandler(onCKClickHandler);
			forsaleAdapter.setOnOtherClickHandler(onOtherClickHandler);
			forsaleAdapter.setOnMinusClickHandler(onMinusClickHandler);
			forsaleAdapter.setOnPlusClickHandler(onPlusClickHandler);
			lvForsale.setAdapter(forsaleAdapter);
			app.setListViewHeight(lvForsale, forsaleAdapter);		
        }
    };
	
	OnClickListener onTDClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewForsale) v).get_position();        
            items = enProducts.getProducts().get(position);
            
            if (items.getDiscount() != null && items.getDiscount().getPoint() != null) {
            	if (items.getDiscount().getOther() != null) {
            		if (items.getDiscount().getOther().getWith_point().equals("0")) {
                    	if (items.getCheckTD() != null) {            	
                        	if (items.getCheckTD().equals(GlobalParams.TRUE)) {
                        		enProducts.getProducts().get(position).setCheckTD(GlobalParams.FALSE);            		
            				} else {
            					enProducts.getProducts().get(position).setCheckTD(GlobalParams.TRUE);					
            				}
            			}
        			} else if (items.getDiscount().getOther().getWith_point().equals("1")) {
        				if (items.getCheckOther() != null) {
        					if (items.getDiscount().getPoint() != null) {
        						if (items.getCheckOther().equals(GlobalParams.TRUE)) {
        							enProducts.getProducts().get(position).setCheckOther(GlobalParams.FALSE);  
        							enProducts.getProducts().get(position).setCheckTD(GlobalParams.TRUE);  
        	    				} else {
        	    					enProducts.getProducts().get(position).setCheckOther(GlobalParams.TRUE);  
        							enProducts.getProducts().get(position).setCheckTD(GlobalParams.FALSE);
        	    				}	            		
        	            	} else {
        	            		if (items.getCheckOther().equals(GlobalParams.TRUE)) {
        	            			enProducts.getProducts().get(position).setCheckOther(GlobalParams.FALSE);                  	
        	    				} else {
        	    					enProducts.getProducts().get(position).setCheckOther(GlobalParams.TRUE);				
        	    				}
        					}                 	
        				}
        			}
				} else if (items.getDiscount().getSale() != null) {
            		if (items.getDiscount().getSale().getWith_point().equals("0")) {
                    	if (items.getCheckTD() != null) {            	
                        	if (items.getCheckTD().equals(GlobalParams.TRUE)) {
                        		enProducts.getProducts().get(position).setCheckTD(GlobalParams.FALSE);            		
            				} else {
            					enProducts.getProducts().get(position).setCheckTD(GlobalParams.TRUE);					
            				}
            			}
        			} else if (items.getDiscount().getSale().getWith_point().equals("1")) {
        				if (items.getCheckCK() != null) {
        					if (items.getDiscount().getPoint() != null) {
        						if (items.getCheckCK().equals(GlobalParams.TRUE)) {
        							enProducts.getProducts().get(position).setCheckCK(GlobalParams.FALSE);  
        							enProducts.getProducts().get(position).setCheckTD(GlobalParams.TRUE);  
        	    				} else {
        	    					enProducts.getProducts().get(position).setCheckCK(GlobalParams.TRUE);  
        							enProducts.getProducts().get(position).setCheckTD(GlobalParams.FALSE);
        	    				}	            		
        	            	} else {
        	            		if (items.getCheckCK().equals(GlobalParams.TRUE)) {
        	            			enProducts.getProducts().get(position).setCheckCK(GlobalParams.FALSE);                  	
        	    				} else {
        	    					enProducts.getProducts().get(position).setCheckCK(GlobalParams.TRUE);				
        	    				}
        					}                 	
        				}
        			}
				} else {
					if (items.getCheckTD() != null) {            	
                    	if (items.getCheckTD().equals(GlobalParams.TRUE)) {
                    		enProducts.getProducts().get(position).setCheckTD(GlobalParams.FALSE);            		
        				} else {
        					enProducts.getProducts().get(position).setCheckTD(GlobalParams.TRUE);					
        				}
        			}
				}
            	
            	forsaleAdapter = new ForsaleAdapter(PlaceOrderActivity.this, enProducts);
    			forsaleAdapter.setOnTDClickHandler(onTDClickHandler);
    			forsaleAdapter.setOnCKClickHandler(onCKClickHandler);
    			forsaleAdapter.setOnOtherClickHandler(onOtherClickHandler);
    			forsaleAdapter.setOnMinusClickHandler(onMinusClickHandler);
				forsaleAdapter.setOnPlusClickHandler(onPlusClickHandler);
    			lvForsale.setAdapter(forsaleAdapter);
    			app.setListViewHeight(lvForsale, forsaleAdapter);
			}
        }
    };
    
    OnClickListener onCKClickHandler = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewForsale) v).get_position();
            items = enProducts.getProducts().get(position);
            
            if (items.getDiscount() != null && items.getDiscount().getSale() != null) {
            	if (items.getDiscount().getSale().getWith_point().equals("0")) {
            		if (items.getCheckCK() != null) {            
                    	if (items.getCheckCK().equals(GlobalParams.TRUE)) {
                    		enProducts.getProducts().get(position).setCheckCK(GlobalParams.FALSE);            		
        				} else {
        					enProducts.getProducts().get(position).setCheckCK(GlobalParams.TRUE);					
        				}
        			}
            	} else if (items.getDiscount().getSale().getWith_point().equals("1")) {
            		if (items.getCheckCK() != null) {							
						if (items.getDiscount().getPoint() != null) {
							if (items.getCheckCK().equals(GlobalParams.TRUE)) {
								enProducts.getProducts().get(position).setCheckCK(GlobalParams.FALSE);  
								enProducts.getProducts().get(position).setCheckTD(GlobalParams.TRUE);  
		    				} else {
		    					enProducts.getProducts().get(position).setCheckCK(GlobalParams.TRUE);  
								enProducts.getProducts().get(position).setCheckTD(GlobalParams.FALSE);
		    				}	            		
		            	} else {
		            		if (items.getCheckCK().equals(GlobalParams.TRUE)) {
		            			enProducts.getProducts().get(position).setCheckCK(GlobalParams.FALSE);                  	
		    				} else {
		    					enProducts.getProducts().get(position).setCheckCK(GlobalParams.TRUE);				
		    				}
						}                 	
					}
            	}            	
                
                forsaleAdapter = new ForsaleAdapter(PlaceOrderActivity.this, enProducts);
    			forsaleAdapter.setOnTDClickHandler(onTDClickHandler);
    			forsaleAdapter.setOnCKClickHandler(onCKClickHandler);
    			forsaleAdapter.setOnOtherClickHandler(onOtherClickHandler);
    			forsaleAdapter.setOnMinusClickHandler(onMinusClickHandler);
				forsaleAdapter.setOnPlusClickHandler(onPlusClickHandler);
    			lvForsale.setAdapter(forsaleAdapter);
    			app.setListViewHeight(lvForsale, forsaleAdapter);
            }            
        }
    };
    
    OnClickListener onOtherClickHandler = new OnClickListener() 
	{	
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewForsale) v).get_position();
            items = enProducts.getProducts().get(position);
			if (items.getDiscount() != null && items.getDiscount().getOther() != null) {
				if (items.getDiscount().getOther().getWith_point().equals("0")) {
	            	if (items.getCheckOther() != null) {            	
	            		if (items.getCheckOther().equals(GlobalParams.TRUE)) {
	                 		enProducts.getProducts().get(position).setCheckOther(GlobalParams.FALSE);            		
	     				} else {
	     					enProducts.getProducts().get(position).setCheckOther(GlobalParams.TRUE);					
	     				}
	     			}
				} else if (items.getDiscount().getOther().getWith_point().equals("1")) {
					if (items.getCheckOther() != null) {							
						if (items.getDiscount().getPoint() != null) {
							if (items.getCheckOther().equals(GlobalParams.TRUE)) {
								enProducts.getProducts().get(position).setCheckOther(GlobalParams.FALSE);  
								enProducts.getProducts().get(position).setCheckTD(GlobalParams.TRUE); 								
		    				} else {
		    					enProducts.getProducts().get(position).setCheckOther(GlobalParams.TRUE);  
								enProducts.getProducts().get(position).setCheckTD(GlobalParams.FALSE);								
		    				}	            		
		            	} else {
		            		if (items.getCheckOther().equals(GlobalParams.TRUE)) {
		            			enProducts.getProducts().get(position).setCheckOther(GlobalParams.FALSE);                  	
		    				} else {
		    					enProducts.getProducts().get(position).setCheckOther(GlobalParams.TRUE);				
		    				}
						}                 	
					}
				}           
	            
	            forsaleAdapter = new ForsaleAdapter(PlaceOrderActivity.this, enProducts);
				forsaleAdapter.setOnTDClickHandler(onTDClickHandler);
				forsaleAdapter.setOnCKClickHandler(onCKClickHandler);
				forsaleAdapter.setOnOtherClickHandler(onOtherClickHandler);
				forsaleAdapter.setOnMinusClickHandler(onMinusClickHandler);
				forsaleAdapter.setOnPlusClickHandler(onPlusClickHandler);
				lvForsale.setAdapter(forsaleAdapter);
				app.setListViewHeight(lvForsale, forsaleAdapter);
			}            
        }
    };
	
	/**
     * Get Stores list follow line
     * @author hoangnh11
     *
     */
    class GetProduct extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(PlaceOrderActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getProduct.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[1];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(PlaceOrderActivity.this));				
				try {
					data = HttpNetServices.Instance.getProducts(netParameter);					
					enProducts = DataParser.getProducts(data);
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
				if (result.equals(GlobalParams.TRUE) && enProducts != null && enProducts.getStatus().equalsIgnoreCase("success")) {
					for (int i = 0; i < enProducts.getProducts().size(); i++) {
						enProducts.getProducts().get(i).setCheckTD(GlobalParams.FALSE);
						enProducts.getProducts().get(i).setCheckCK(GlobalParams.FALSE);
						enProducts.getProducts().get(i).setCheckOther(GlobalParams.FALSE);						
					}
					
					forsaleAdapter = new ForsaleAdapter(PlaceOrderActivity.this, enProducts);
					forsaleAdapter.setOnTDClickHandler(onTDClickHandler);
					forsaleAdapter.setOnCKClickHandler(onCKClickHandler);
					forsaleAdapter.setOnOtherClickHandler(onOtherClickHandler);
					forsaleAdapter.setOnMinusClickHandler(onMinusClickHandler);
					forsaleAdapter.setOnPlusClickHandler(onPlusClickHandler);
					lvForsale.setAdapter(forsaleAdapter);
					app.setListViewHeight(lvForsale, forsaleAdapter);				
				}
			}
		}
	}
    
    /**
     * Get Stores list follow line
     * @author hoangnh11
     *
     */
    class PrepareSale extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(PlaceOrderActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					prepareSale.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[1];
				netParameter[0] = new NetParameter("basket", DataParser.convertObjectToString(arrBasket));
				
				try {
					data = HttpNetServices.Instance.prepareSale(netParameter, BuManagement.getToken(PlaceOrderActivity.this));					
					responsePrepare = DataParser.prepareSale(data);
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
				if (result.equals(GlobalParams.TRUE) && responsePrepare != null 
						&& responsePrepare.getStatus() != null
						&& responsePrepare.getStatus().equalsIgnoreCase("success")) {
					Logger.error(responsePrepare.getStatus());
				} else {
					try {
						app.alertErrorMessageString(responsePrepare.getMessage(),
								getString(R.string.COMMON_MESSAGE), PlaceOrderActivity.this);
					} catch (Exception e) {
						Logger.error("responsePrepare: " + e);
					}
				}
			}
		}
	}
}
