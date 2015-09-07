package com.viviproject.deliver;

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
import com.viviproject.adapter.ListviewPrepareAdapter;
import com.viviproject.entities.EnBasket;
import com.viviproject.entities.EnOrder;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.ResponseCreateSales;
import com.viviproject.entities.ResponseOrders;
import com.viviproject.entities.ResponsePrepare;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringUtils;

public class ChangeOrderActivity extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private TextView tvCreateOrder, tvNameStore, tvChangeOrder, tvAddressStore;	
	private LinearLayout linSubCreateOrder;
	private ListView lvChangeOrder;
	private Button btnOk, btnCancel;
	private TextView tvSubTotal, tvCK, tvDiscount, tvTotal;
	private CheckBox ckDiliver;
	
	private Bundle bundle;
	private AppPreferences app;
	private EnOrder enOrder;
//	private EnProductSales items;
	private EnProducts itemProduct;
	private ChangeOrderAdapter changeOrderAdapter;
	private ProgressDialog progressDialog;
	private PrepareSale prepareSale;
	private ResponsePrepare responsePrepare;
	private EnBasket enBasket;
	private ArrayList<EnBasket> arrBasket;
	private ListView lvPrepare;
	private ListviewPrepareAdapter prepareAdapter;
	private CreateSale createSale;
	private ResponseCreateSales responseCreateSales;
	private String nowDelivery = "0";
	private ArrayList<EnProducts> products;
	private int index;
	private GetSales getSales;
	private ResponseOrders responseOrders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_order);
		app = new AppPreferences(this);
		itemProduct = new EnProducts();
		enOrder = new EnOrder(); 
//		items = new EnProductSales();
		responsePrepare = new ResponsePrepare();
		enBasket = new EnBasket();
		arrBasket = new ArrayList<EnBasket>();
		responseCreateSales = new ResponseCreateSales();	
		products = new ArrayList<EnProducts>();
		bundle = app.getBundle(this);
//		enOrder = (EnOrder) bundle.getSerializable(GlobalParams.CHANGE_ORDER);
//		products = (ArrayList<EnProducts>) bundle.getSerializable(GlobalParams.PRODUCTS);
		
		index = bundle.getInt(GlobalParams.CHANGE_ORDER_INDEX);
		responseOrders = new ResponseOrders();		
		
//		initLayout();
		
		getSales = new GetSales();
		getSales.execute();
		
//		tvNameStore.setText(enOrder.getName());
//		tvChangeOrder.setText("- thay đổi đơn hàng ngày " + enOrder.getDate_book());
//		tvAddressStore.setText(enOrder.getAddress());
//		for (int i = 0; i < enOrder.getProducts().size(); i++) {
//			enOrder.getProducts().get(i).setTempProductQty("0");
//		}
//		for (int i = 0; i < products.size(); i++) {
//			products.get(i).setCheckTD(GlobalParams.FALSE);
//			products.get(i).setCheckCK(GlobalParams.FALSE);
//			products.get(i).setCheckOther(GlobalParams.FALSE);
//		}
//		changeOrderAdapter = new ChangeOrderAdapter(ChangeOrderActivity.this, enOrder, products);
//		changeOrderAdapter.setOnTDClickHandler(onTDClickHandler);
//		changeOrderAdapter.setOnCKClickHandler(onCKClickHandler);
//		changeOrderAdapter.setOnOtherClickHandler(onOtherClickHandler);
//		changeOrderAdapter.setOnMinusClickHandler(onMinusClickHandler);
//		changeOrderAdapter.setOnPlusClickHandler(onPlusClickHandler);
//		lvChangeOrder.setAdapter(changeOrderAdapter);
//		app.setListViewHeight(lvChangeOrder, changeOrderAdapter);
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.ORDER_CHANGE));
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
	
		tvCreateOrder = (TextView) findViewById(R.id.tvCreateOrder);
		tvCreateOrder.setOnClickListener(this);	
		linSubCreateOrder = (LinearLayout) findViewById(R.id.linSubCreateOrder);
		
		tvNameStore = (TextView) findViewById(R.id.tvNameStore);
		tvChangeOrder = (TextView) findViewById(R.id.tvChangeOrder);
		tvAddressStore = (TextView) findViewById(R.id.tvAddressStore);
		
		lvChangeOrder = (ListView) findViewById(R.id.lvChangeOrder);
		lvPrepare = (ListView) findViewById(R.id.lvPrepare);
		
		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		
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
				try {
					for (int i = 0; i < products.size(); i++) {
						if (products != null && products.size() > 0) {
							enBasket = new EnBasket();
							
							enBasket.setQuantity(Integer.parseInt(products.get(i).getTempProductQty()));							
							
							if (products.get(i).getDiscount() != null && products.get(i).getDiscount().getPoint() != null
									&& products.get(i).getCheckTD().equals(GlobalParams.TRUE)) {
								enBasket.setPoint(Integer.parseInt(products.get(i).getDiscount().getPoint().getDiscount_id()));
							} else {
								enBasket.setPoint(Integer.parseInt("0"));
							}
						
							if (products.get(i).getDiscount() != null && products.get(i).getDiscount().getSale() != null
									&& products.get(i).getCheckCK().equals(GlobalParams.TRUE)) {
								enBasket.setSale(Integer.parseInt(products.get(i).getDiscount().getSale().getDiscount_id()));
							} else {
								enBasket.setSale(Integer.parseInt("0"));
							}
						
							if (products.get(i).getDiscount() != null && products.get(i).getDiscount().getOther() != null
									&& products.get(i).getCheckOther().equals(GlobalParams.TRUE)) {
								enBasket.setOther(Integer.parseInt(products.get(i).getDiscount().getOther().getDiscount_id()));
							} else {
								enBasket.setOther(Integer.parseInt("0"));
							}													
						
							enBasket.setProduct_id(Integer.parseInt(products.get(i).getId()));

							if (products.get(i).getDiscount() != null) {
								if (products.get(i).getDiscount().getPoint() != null 
										&& products.get(i).getDiscount().getPoint().getPoint() != null) {
									enBasket.setDiscount_point(Float.parseFloat("0"));
								}
								if (products.get(i).getDiscount().getSale() != null 
										&& products.get(i).getDiscount().getSale().getDiscount() != null) {
									enBasket.setDiscount_sale(Integer.parseInt("0"));
								}								
							}							
							
							if (enOrder.getTotal() != null) {
								enBasket.setTotal(Integer.parseInt(enOrder.getTotal()));
							}

							arrBasket.add(enBasket);
						}													
					}
				} catch (Exception e) {
					e.printStackTrace();
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
			linSubCreateOrder.setVisibility(View.GONE);
			tvCreateOrder.setBackgroundResource(R.color.BLUE);
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
        	int position = ((ItemListViewOrder) v).get_position();        
        	itemProduct = products.get(position);           
            	
            if (Integer.parseInt(itemProduct.getTempProductQty()) > 0) {
            	products.get(position).setTempProductQty(String.valueOf(Integer.parseInt(itemProduct.getTempProductQty()) - 1));            	
				
            	changeOrderAdapter = new ChangeOrderAdapter(ChangeOrderActivity.this, enOrder, products);
    			changeOrderAdapter.setOnTDClickHandler(onTDClickHandler);
    			changeOrderAdapter.setOnCKClickHandler(onCKClickHandler);
    			changeOrderAdapter.setOnOtherClickHandler(onOtherClickHandler);
            	changeOrderAdapter.setOnMinusClickHandler(onMinusClickHandler);
    			changeOrderAdapter.setOnPlusClickHandler(onPlusClickHandler);
    			lvChangeOrder.setAdapter(changeOrderAdapter);
    			app.setListViewHeight(lvChangeOrder, changeOrderAdapter);
			}			
        }
    };
    
    OnClickListener onPlusClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewOrder) v).get_position();
        	itemProduct = products.get(position);
          
        	products.get(position).setTempProductQty(String.valueOf(Integer.parseInt(itemProduct.getTempProductQty()) + 1));
        	
            changeOrderAdapter = new ChangeOrderAdapter(ChangeOrderActivity.this, enOrder, products);
            changeOrderAdapter.setOnTDClickHandler(onTDClickHandler);
            changeOrderAdapter.setOnCKClickHandler(onCKClickHandler);
            changeOrderAdapter.setOnOtherClickHandler(onOtherClickHandler);
            changeOrderAdapter.setOnMinusClickHandler(onMinusClickHandler);
            changeOrderAdapter.setOnPlusClickHandler(onPlusClickHandler);
            lvChangeOrder.setAdapter(changeOrderAdapter);
			app.setListViewHeight(lvChangeOrder, changeOrderAdapter);
        }
    };

    OnClickListener onTDClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewOrder) v).get_position();        
        	itemProduct = products.get(position);         
    		if (itemProduct.getDiscount() != null && itemProduct.getDiscount().getPoint() != null) {
            	if (itemProduct.getDiscount().getOther() != null) {
            		if (itemProduct.getDiscount().getOther().getWith_point().equals("0")) {
                    	if (itemProduct.getCheckTD() != null) {            	
                        	if (itemProduct.getCheckTD().equals(GlobalParams.TRUE)) {
                        		itemProduct.setCheckTD(GlobalParams.FALSE);            		
            				} else {
            					itemProduct.setCheckTD(GlobalParams.TRUE);					
            				}
            			}
        			} else if (itemProduct.getDiscount().getOther().getWith_point().equals("1")) {
        				if (itemProduct.getCheckOther() != null) {
        					if (itemProduct.getDiscount().getPoint() != null) {
        						if (itemProduct.getCheckOther().equals(GlobalParams.TRUE)) {
        							itemProduct.setCheckOther(GlobalParams.FALSE);  
        							itemProduct.setCheckTD(GlobalParams.TRUE);  
        	    				} else {
        	    					itemProduct.setCheckOther(GlobalParams.TRUE);  
        	    					itemProduct.setCheckTD(GlobalParams.FALSE);
        	    				}	            		
        	            	} else {
        	            		if (itemProduct.getCheckOther().equals(GlobalParams.TRUE)) {
        	            			itemProduct.setCheckOther(GlobalParams.FALSE);                  	
        	    				} else {
        	    					itemProduct.setCheckOther(GlobalParams.TRUE);				
        	    				}
        					}                 	
        				}
        			}
				} else if (itemProduct.getDiscount().getSale() != null) {
            		if (itemProduct.getDiscount().getSale().getWith_point().equals("0")) {
                    	if (itemProduct.getCheckTD() != null) {            	
                        	if (itemProduct.getCheckTD().equals(GlobalParams.TRUE)) {
                        		itemProduct.setCheckTD(GlobalParams.FALSE);            		
            				} else {
            					itemProduct.setCheckTD(GlobalParams.TRUE);					
            				}
            			}
        			} else if (itemProduct.getDiscount().getSale().getWith_point().equals("1")) {
        				if (itemProduct.getCheckCK() != null) {
        					if (itemProduct.getDiscount().getPoint() != null) {
        						if (itemProduct.getCheckCK().equals(GlobalParams.TRUE)) {
        							itemProduct.setCheckCK(GlobalParams.FALSE);  
        							itemProduct.setCheckTD(GlobalParams.TRUE);  
        	    				} else {
        	    					itemProduct.setCheckCK(GlobalParams.TRUE);  
        	    					itemProduct.setCheckTD(GlobalParams.FALSE);
        	    				}	            		
        	            	} else {
        	            		if (itemProduct.getCheckCK().equals(GlobalParams.TRUE)) {
        	            			itemProduct.setCheckCK(GlobalParams.FALSE);                  	
        	    				} else {
        	    					itemProduct.setCheckCK(GlobalParams.TRUE);				
        	    				}
        					}                 	
        				}
        			}
				} else {
					if (itemProduct.getCheckTD() != null) {            	
                    	if (itemProduct.getCheckTD().equals(GlobalParams.TRUE)) {
                    		itemProduct.setCheckTD(GlobalParams.FALSE);            		
        				} else {
        					itemProduct.setCheckTD(GlobalParams.TRUE);					
        				}
        			}
				}
            	
            	changeOrderAdapter = new ChangeOrderAdapter(ChangeOrderActivity.this, enOrder, products);
                changeOrderAdapter.setOnTDClickHandler(onTDClickHandler);
                changeOrderAdapter.setOnCKClickHandler(onCKClickHandler);
                changeOrderAdapter.setOnOtherClickHandler(onOtherClickHandler);
                changeOrderAdapter.setOnMinusClickHandler(onMinusClickHandler);
                changeOrderAdapter.setOnPlusClickHandler(onPlusClickHandler);
                lvChangeOrder.setAdapter(changeOrderAdapter);
    			app.setListViewHeight(lvChangeOrder, changeOrderAdapter);                    	
			}
        }
    };
    
    OnClickListener onCKClickHandler = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewOrder) v).get_position();
        	itemProduct = products.get(position);
        
    		if (itemProduct.getDiscount() != null && itemProduct.getDiscount().getSale() != null) {
            	if (itemProduct.getDiscount().getSale().getWith_point().equals("0")) {
            		if (itemProduct.getCheckCK() != null) {            
                    	if (itemProduct.getCheckCK().equals(GlobalParams.TRUE)) {
                    		itemProduct.setCheckCK(GlobalParams.FALSE);            		
        				} else {
        					itemProduct.setCheckCK(GlobalParams.TRUE);					
        				}
        			}
            	} else if (itemProduct.getDiscount().getSale().getWith_point().equals("1")) {
            		if (itemProduct.getCheckCK() != null) {							
						if (itemProduct.getDiscount().getPoint() != null) {
							if (itemProduct.getCheckCK().equals(GlobalParams.TRUE)) {
								itemProduct.setCheckCK(GlobalParams.FALSE);  
								itemProduct.setCheckTD(GlobalParams.TRUE);  
		    				} else {
		    					itemProduct.setCheckCK(GlobalParams.TRUE);  
		    					itemProduct.setCheckTD(GlobalParams.FALSE);
		    				}	            		
		            	} else {
		            		if (itemProduct.getCheckCK().equals(GlobalParams.TRUE)) {
		            			itemProduct.setCheckCK(GlobalParams.FALSE);                  	
		    				} else {
		    					itemProduct.setCheckCK(GlobalParams.TRUE);				
		    				}
						}                 	
					}
            	}            	
                
            	changeOrderAdapter = new ChangeOrderAdapter(ChangeOrderActivity.this, enOrder, products);
                changeOrderAdapter.setOnTDClickHandler(onTDClickHandler);
                changeOrderAdapter.setOnCKClickHandler(onCKClickHandler);
                changeOrderAdapter.setOnOtherClickHandler(onOtherClickHandler);
                changeOrderAdapter.setOnMinusClickHandler(onMinusClickHandler);
                changeOrderAdapter.setOnPlusClickHandler(onPlusClickHandler);
                lvChangeOrder.setAdapter(changeOrderAdapter);
    			app.setListViewHeight(lvChangeOrder, changeOrderAdapter);
            }                     
        }
    };
    
    OnClickListener onOtherClickHandler = new OnClickListener() 
	{	
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewOrder) v).get_position();
        	itemProduct = products.get(position);
     
    		if (itemProduct.getDiscount() != null && itemProduct.getDiscount().getOther() != null) {
				if (itemProduct.getDiscount().getOther().getWith_point().equals("0")) {
	            	if (itemProduct.getCheckOther() != null) {            	
	            		if (itemProduct.getCheckOther().equals(GlobalParams.TRUE)) {
	            			itemProduct.setCheckOther(GlobalParams.FALSE);            		
	     				} else {
	     					itemProduct.setCheckOther(GlobalParams.TRUE);					
	     				}
	     			}
				} else if (itemProduct.getDiscount().getOther().getWith_point().equals("1")) {
					if (itemProduct.getCheckOther() != null) {							
						if (itemProduct.getDiscount().getPoint() != null) {
							if (itemProduct.getCheckOther().equals(GlobalParams.TRUE)) {
								itemProduct.setCheckOther(GlobalParams.FALSE);  
								itemProduct.setCheckTD(GlobalParams.TRUE); 								
		    				} else {
		    					itemProduct.setCheckOther(GlobalParams.TRUE);  
		    					itemProduct.setCheckTD(GlobalParams.FALSE);								
		    				}	            		
		            	} else {
		            		if (itemProduct.getCheckOther().equals(GlobalParams.TRUE)) {
		            			itemProduct.setCheckOther(GlobalParams.FALSE);                  	
		    				} else {
		    					itemProduct.setCheckOther(GlobalParams.TRUE);				
		    				}
						}                 	
					}
				}           
	            
				changeOrderAdapter = new ChangeOrderAdapter(ChangeOrderActivity.this, enOrder, products);
                changeOrderAdapter.setOnTDClickHandler(onTDClickHandler);
                changeOrderAdapter.setOnCKClickHandler(onCKClickHandler);
                changeOrderAdapter.setOnOtherClickHandler(onOtherClickHandler);
                changeOrderAdapter.setOnMinusClickHandler(onMinusClickHandler);
                changeOrderAdapter.setOnPlusClickHandler(onPlusClickHandler);
                lvChangeOrder.setAdapter(changeOrderAdapter);
    			app.setListViewHeight(lvChangeOrder, changeOrderAdapter);
			}	            
        }
    };
    
    /**
     * Get Prepare api
     * @author hoangnh11
     *
     */
    class PrepareSale extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(ChangeOrderActivity.this);
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
					data = HttpNetServices.Instance.prepareSale(netParameter, BuManagement.getToken(ChangeOrderActivity.this));					
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
					
					prepareAdapter = new ListviewPrepareAdapter(ChangeOrderActivity.this, responsePrepare);				
					lvPrepare.setAdapter(prepareAdapter);
					app.setListViewHeight(lvPrepare, prepareAdapter);
					btnOk.setEnabled(true);
					btnOk.setBackgroundResource(R.drawable.bg_blue_gray);
					
					try {						
						tvSubTotal.setText(GlobalParams.SPACE_CHARACTER 
								+ StringUtils.formatEnglishValueNumber(responsePrepare.getSubtotal()));
						tvCK.setText(GlobalParams.SPACE_CHARACTER 
								+ StringUtils.formatEnglishValueNumber(responsePrepare.getTotal_discount()));
						tvDiscount.setText(GlobalParams.SPACE_CHARACTER 
								+ StringUtils.formatEnglishValueNumber(responsePrepare.getTotal_point()));
						tvTotal.setText(GlobalParams.SPACE_CHARACTER 
								+ StringUtils.formatEnglishValueNumber(responsePrepare.getTotal()));
					} catch (Exception e) {
						Logger.error("responsePrepare: " + e);
					}
					
				} else {
					try {
						app.alertErrorMessageString(responsePrepare.getMessage(),
								getString(R.string.COMMON_MESSAGE), ChangeOrderActivity.this);
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
			progressDialog = new ProgressDialog(ChangeOrderActivity.this);
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
				netParameter[0] = new NetParameter("store_id", enOrder.getStore_id());
				netParameter[1] = new NetParameter("delivery", nowDelivery);
				netParameter[2] = new NetParameter("discount_point_value", String.valueOf(responsePrepare.getTotal_point()));
				netParameter[3] = new NetParameter("discount_sale_value", String.valueOf(responsePrepare.getTotal_discount()));
				netParameter[4] = new NetParameter("subtotal", String.valueOf(responsePrepare.getSubtotal()));
				netParameter[5] = new NetParameter("total", String.valueOf(responsePrepare.getTotal()));
				netParameter[6] = new NetParameter("basket", DataParser.convertObjectToString(arrBasket));
				netParameter[7] = new NetParameter("id", enOrder.getId());
				
				try {
					data = HttpNetServices.Instance.createSale(netParameter,
							BuManagement.getToken(ChangeOrderActivity.this), enOrder.getId());
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
							getString(R.string.COMMON_MESSAGE), ChangeOrderActivity.this);
					btnOk.setEnabled(false);
					btnOk.setBackgroundResource(R.drawable.bg_gray9e_blue);
					linSubCreateOrder.setVisibility(View.GONE);
					tvCreateOrder.setBackgroundResource(R.color.BLUE);
				} else {
					try {
						app.alertErrorMessageString(responseCreateSales.getMessage(),
								getString(R.string.COMMON_MESSAGE), ChangeOrderActivity.this);
					} catch (Exception e) {
						Logger.error("responseCreateSales: " + e);
					}
				}
			}
		}
	}
    
    class GetSales extends AsyncTask<Void, Void, String> {
    	String data;
    	
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(ChangeOrderActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.LOADING));
			progressDialog.show();
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getSales.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[1];
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(ChangeOrderActivity.this));
			
				try {
					data = HttpNetServices.Instance.getSales(netParameter);					
					responseOrders = DataParser.getResponseOrders(data);
					Logger.error("hoangnh11: ", data);
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
				if (result.equals(GlobalParams.TRUE) && responseOrders != null && responseOrders.getOrders() != null
						&& responseOrders.getOrders().size() > 0
						&& responseOrders.getStatus().equalsIgnoreCase("success")) {
					
					enOrder = responseOrders.getOrders().get(index);
					products = responseOrders.getProducts();
					
					initLayout();
					
					tvNameStore.setText(enOrder.getName());
					tvChangeOrder.setText("- thay đổi đơn hàng ngày " + enOrder.getDate_book());
					tvAddressStore.setText(enOrder.getAddress());
//					for (int i = 0; i < enOrder.getProducts().size(); i++) {
//						enOrder.getProducts().get(i).setTempProductQty("0");
//					}
					
					for (int i = 0; i < products.size(); i++) {
						products.get(i).setCheckTD(GlobalParams.FALSE);
						products.get(i).setCheckCK(GlobalParams.FALSE);
						products.get(i).setCheckOther(GlobalParams.FALSE);
						products.get(i).setTempProductQty("0");
					}
					changeOrderAdapter = new ChangeOrderAdapter(ChangeOrderActivity.this, enOrder, products);
					changeOrderAdapter.setOnTDClickHandler(onTDClickHandler);
					changeOrderAdapter.setOnCKClickHandler(onCKClickHandler);
					changeOrderAdapter.setOnOtherClickHandler(onOtherClickHandler);
					changeOrderAdapter.setOnMinusClickHandler(onMinusClickHandler);
					changeOrderAdapter.setOnPlusClickHandler(onPlusClickHandler);
					lvChangeOrder.setAdapter(changeOrderAdapter);
					app.setListViewHeight(lvChangeOrder, changeOrderAdapter);
					
				}
			}
		}
	}
}
