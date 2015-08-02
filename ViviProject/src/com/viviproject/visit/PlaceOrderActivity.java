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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.ForsaleAdapter;
import com.viviproject.adapter.ListviewPrepareAdapter;
import com.viviproject.entities.EnBasket;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.EnStores;
import com.viviproject.entities.Products;
import com.viviproject.entities.ResponseCreateSales;
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
	
	private TextView tvCreateOrder, tvNameStore, tvAddressStores;	
	private LinearLayout linSubCreateOrder;
	private ListView lvForsale;
	private TextView tvSubTotal, tvCK, tvDiscount, tvTotal;
	private CheckBox ckDiliver;
	private Button btnOk, btnCancel;
	
	private AppPreferences app;
	private Bundle bundle;
	private EnStores itemStore;
	private ProgressDialog progressDialog;
	private GetProduct getProduct;
	private Products enProducts;
	private ForsaleAdapter forsaleAdapter;
	private EnProducts items;
	
	private PrepareSale prepareSale;
	private ResponsePrepare responsePrepare;
	private EnBasket enBasket;
	private ArrayList<EnBasket> arrBasket;
	private ListView lvPrepare;
	private ListviewPrepareAdapter prepareAdapter;
	
	private CreateSale createSale;
	private ResponseCreateSales responseCreateSales;
	private String nowDelivery = "0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_order_layout);
		app = new AppPreferences(this);
		bundle = app.getBundle(this);
		itemStore = new EnStores();
		itemStore = (EnStores) bundle.getSerializable(GlobalParams.STORES);
		enProducts = new Products();
		items = new EnProducts();
		responseCreateSales = new ResponseCreateSales();		
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
		
		tvNameStore = (TextView) findViewById(R.id.tvNameStore);
		tvNameStore.setText(itemStore.getName());
		tvAddressStores = (TextView) findViewById(R.id.tvAddressStores);
		tvAddressStores.setText(itemStore.getAddress());
		
		tvCreateOrder = (TextView) findViewById(R.id.tvCreateOrder);
		tvCreateOrder.setOnClickListener(this);	
		linSubCreateOrder = (LinearLayout) findViewById(R.id.linSubCreateOrder);
		
		lvForsale = (ListView) findViewById(R.id.lvForsale);
		lvPrepare = (ListView) findViewById(R.id.lvPrepare);
		
		tvSubTotal = (TextView) findViewById(R.id.tvSubTotal);
		tvCK = (TextView) findViewById(R.id.tvCK);
		tvDiscount = (TextView) findViewById(R.id.tvDiscount);
		tvTotal = (TextView) findViewById(R.id.tvTotal);
		
		ckDiliver = (CheckBox) findViewById(R.id.ckDiliver);
		ckDiliver.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {				
				if (isChecked) {
					nowDelivery = "1";
				} else {
					nowDelivery = "0";
				}
			}
		});
		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.tvCreateOrder:
			btnOk.setEnabled(false);
			btnOk.setBackgroundResource(R.drawable.bg_gray9e_blue);
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
			
		case R.id.btnOk:
			createSale = new CreateSale();
			createSale.execute();
			break;
			
		case R.id.btnCancel:
			btnOk.setEnabled(false);
			btnOk.setBackgroundResource(R.drawable.bg_gray9e_blue);
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
     * Get Prepare api
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
					
					prepareAdapter = new ListviewPrepareAdapter(PlaceOrderActivity.this, responsePrepare);				
					lvPrepare.setAdapter(prepareAdapter);
					app.setListViewHeight(lvPrepare, prepareAdapter);
					btnOk.setEnabled(true);
					btnOk.setBackgroundResource(R.drawable.bg_blue_gray);
					
					try {						
						tvSubTotal.setText(GlobalParams.SPACE_CHARACTER + String.valueOf(responsePrepare.getSubtotal()));
						tvCK.setText(GlobalParams.SPACE_CHARACTER + String.valueOf(responsePrepare.getTotal_discount()));
						tvDiscount.setText(GlobalParams.SPACE_CHARACTER + String.valueOf(responsePrepare.getTotal_point()));
						tvTotal.setText(GlobalParams.SPACE_CHARACTER + String.valueOf(responsePrepare.getTotal()));
					} catch (Exception e) {
						Logger.error("responsePrepare: " + e);
					}
					
				} else {
					try {
						app.alertErrorMessageString(responsePrepare.getMessage(),
								getString(R.string.COMMON_MESSAGE), PlaceOrderActivity.this);
						btnOk.setEnabled(true);
						btnOk.setBackgroundResource(R.drawable.bg_blue_gray);
					} catch (Exception e) {
						Logger.error("responsePrepare: " + e);
					}
				}
			}
		}
	}
    
    /**
     * Create Sales Api
     * @author hoangnh11
     *
     */
    class CreateSale extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(PlaceOrderActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.PROCESSING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					createSale.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[8];
				netParameter[0] = new NetParameter("uid", app.getIMEI(PlaceOrderActivity.this));
				netParameter[1] = new NetParameter("store_id", itemStore.getStore_id());
				netParameter[2] = new NetParameter("delivery", nowDelivery);
				netParameter[3] = new NetParameter("discount_point_value", String.valueOf(responsePrepare.getTotal_point()));
				netParameter[4] = new NetParameter("discount_sale_value", String.valueOf(responsePrepare.getTotal_discount()));
				netParameter[5] = new NetParameter("subtotal", String.valueOf(responsePrepare.getSubtotal()));
				netParameter[6] = new NetParameter("total", String.valueOf(responsePrepare.getTotal()));
				netParameter[7] = new NetParameter("basket", DataParser.convertObjectToString(arrBasket));
				
				try {
					data = HttpNetServices.Instance.createSale(netParameter, BuManagement.getToken(PlaceOrderActivity.this));					
					responseCreateSales = DataParser.createSale(data);
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
				if (result.equals(GlobalParams.TRUE) && responseCreateSales != null 
						&& responseCreateSales.getStatus() != null
						&& responseCreateSales.getStatus().equalsIgnoreCase("success")) {					
					app.alertErrorMessageString(responseCreateSales.getStatus(),
							getString(R.string.COMMON_MESSAGE), PlaceOrderActivity.this);
					btnOk.setEnabled(false);
					btnOk.setBackgroundResource(R.drawable.bg_gray9e_blue);
					linSubCreateOrder.setVisibility(View.GONE);
				} else {
					try {
						app.alertErrorMessageString(responseCreateSales.getMessage(),
								getString(R.string.COMMON_MESSAGE), PlaceOrderActivity.this);
					} catch (Exception e) {
						Logger.error("responseCreateSales: " + e);
					}
				}
			}
		}
	}
}
