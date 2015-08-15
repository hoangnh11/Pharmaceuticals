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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.ReturnImportAdapter;
import com.viviproject.deliver.ItemListviewReturnImport;
import com.viviproject.entities.EnProductBasket;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.EnStores;
import com.viviproject.entities.Products;
import com.viviproject.entities.ResponseCreateSales;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringUtils;

public class OrderImportActivity extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;	
	private TextView tvNameStore, tvReturnProduct, tvAddressStore;	
	private EditText edtContent;
	private ListView lvReturnImport;
	private Button btnOk;
	
	private AppPreferences app;
	private ProgressDialog progressDialog;
	private GetProduct getProduct;
	public static Products enProducts;
	private Bundle bundle;
	private EnStores itemStore;
	private ReturnImportAdapter returnImportAdapter;
	private EnProducts items;
	private Refund refund;
	public static ArrayList<EnProductBasket> arrProductBasket;
	private ResponseCreateSales Response;
	private EnProductBasket enProductBasket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_import_layout);
		app = new AppPreferences(this);
		bundle = app.getBundle(this);
		itemStore = new EnStores();
		itemStore = (EnStores) bundle.getSerializable(GlobalParams.STORES);
		enProducts = new Products();
		items = new EnProducts();
		arrProductBasket = new ArrayList<EnProductBasket>();
		Response = new ResponseCreateSales();
		enProductBasket = new EnProductBasket();
		
		initLayout();
		
		tvNameStore.setText(itemStore.getName());
		
		try {
			tvReturnProduct.setText("- hàng trả lại ngày " + itemStore.getTimestamp_x().split(GlobalParams.SPACE)[0]);
		} catch (Exception e) {
		}
		
		tvAddressStore.setText(itemStore.getAddress());
		
		getProduct = new GetProduct();
		getProduct.execute();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.PRODUCT_IMPORT));
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
		
		edtContent = (EditText) findViewById(R.id.edtContent);
		
		tvNameStore = (TextView) findViewById(R.id.tvNameStore);
		tvReturnProduct = (TextView) findViewById(R.id.tvReturnProduct);
		tvAddressStore = (TextView) findViewById(R.id.tvAddressStore);
		
		lvReturnImport = (ListView) findViewById(R.id.lvReturnImport); 
		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
	}
	
	private int validateInput() {
		int errorCode = 0;		
		String content = edtContent.getEditableText().toString();
		
		if (StringUtils.isBlank(content)) {
			errorCode = R.string.CONTENT_NOT_BLANK;
		}
		
		return errorCode;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
		
		case R.id.btnOk:
			int errorCode = validateInput();
			
			if (errorCode == 0) {
				refund = new Refund();
				refund.execute();							
			} else {				
				app.alertErrorMessageInt(errorCode, getString(R.string.COMMON_MESSAGE), this);
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
        	int position = ((ItemListviewReturnImport) v).get_position();        
            items = enProducts.getProducts().get(position);           
            	
            if (Integer.parseInt(items.getUnit()) > 0) {
            	enProducts.getProducts().get(position).setUnit(String.valueOf(Integer.parseInt(items.getUnit()) - 1));            	
				
            	returnImportAdapter = new ReturnImportAdapter(OrderImportActivity.this, enProducts);   
            	returnImportAdapter.setOnMinusClickHandler(onMinusClickHandler);
            	returnImportAdapter.setOnPlusClickHandler(onPlusClickHandler);
            	lvReturnImport.setAdapter(returnImportAdapter);
    			app.setListViewHeight(lvReturnImport, returnImportAdapter);
    			
    			enProductBasket = new EnProductBasket();
    			enProductBasket.setProduct_id(Integer.parseInt(enProducts.getProducts().get(position).getId()));
				enProductBasket.setQuantity(Integer.parseInt(enProducts.getProducts().get(position).getUnit()));
				arrProductBasket.set(position, enProductBasket);
			}
        }
    };
    
    OnClickListener onPlusClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListviewReturnImport) v).get_position();      
            items = enProducts.getProducts().get(position);
          
        	enProducts.getProducts().get(position).setUnit(String.valueOf(Integer.parseInt(items.getUnit()) + 1));
        	
        	returnImportAdapter = new ReturnImportAdapter(OrderImportActivity.this, enProducts);	
        	returnImportAdapter.setOnMinusClickHandler(onMinusClickHandler);
        	returnImportAdapter.setOnPlusClickHandler(onPlusClickHandler);
        	lvReturnImport.setAdapter(returnImportAdapter);
			app.setListViewHeight(lvReturnImport, returnImportAdapter);
			
			enProductBasket = new EnProductBasket();
			enProductBasket.setProduct_id(Integer.parseInt(enProducts.getProducts().get(position).getId()));
			enProductBasket.setQuantity(Integer.parseInt(enProducts.getProducts().get(position).getUnit()));
			arrProductBasket.set(position, enProductBasket);
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
			progressDialog = new ProgressDialog(OrderImportActivity.this);
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
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(OrderImportActivity.this));				
				try {
					data = HttpNetServices.Instance.getProducts(netParameter);			
					enProducts = DataParser.getProducts(data);					
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
						enProducts.getProducts().get(i).setUnit("0");
						
						enProductBasket = new EnProductBasket();
						enProductBasket.setProduct_id(Integer.parseInt(enProducts.getProducts().get(i).getId()));
						enProductBasket.setQuantity(Integer.parseInt(enProducts.getProducts().get(i).getUnit()));
						arrProductBasket.add(enProductBasket);
					}
					
					returnImportAdapter = new ReturnImportAdapter(OrderImportActivity.this, enProducts);
					returnImportAdapter.setOnMinusClickHandler(onMinusClickHandler);
					returnImportAdapter.setOnPlusClickHandler(onPlusClickHandler);
					lvReturnImport.setAdapter(returnImportAdapter);
					app.setListViewHeight(lvReturnImport, returnImportAdapter);				
				}
			}
		}
	}
    
    /**
     * Create refund
     * @author hoangnh11
     *
     */
    class Refund extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(OrderImportActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.PROCESSING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					refund.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[6];
				netParameter[0] = new NetParameter("uid", app.getIMEI(OrderImportActivity.this) + "|" + app.getCurrentTimeStamp());
				netParameter[1] = new NetParameter("store_id", itemStore.getStore_id());
				netParameter[2] = new NetParameter("long", BuManagement.getLongitude(OrderImportActivity.this));
				netParameter[3] = new NetParameter("lat", BuManagement.getLatitude(OrderImportActivity.this));
				netParameter[4] = new NetParameter("note", edtContent.getEditableText().toString());
				netParameter[5] = new NetParameter("basket", DataParser.convertObjectToString(arrProductBasket));
				try {
					data = HttpNetServices.Instance.refund(netParameter, BuManagement.getToken(OrderImportActivity.this));
					Response = DataParser.createSale(data);
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
				if (result.equals(GlobalParams.TRUE) && Response != null) {
					app.alertErrorMessageString(Response.getStatus(),
							getString(R.string.COMMON_MESSAGE), OrderImportActivity.this);
					
					for (int i = 0; i < enProducts.getProducts().size(); i++) {
						enProducts.getProducts().get(i).setUnit("0");
						
						enProductBasket = new EnProductBasket();
						enProductBasket.setProduct_id(Integer.parseInt(enProducts.getProducts().get(i).getId()));
						enProductBasket.setQuantity(Integer.parseInt(enProducts.getProducts().get(i).getUnit()));
						arrProductBasket.set(i, enProductBasket);
					}
					
					returnImportAdapter = new ReturnImportAdapter(OrderImportActivity.this, enProducts);
					returnImportAdapter.setOnMinusClickHandler(onMinusClickHandler);
					returnImportAdapter.setOnPlusClickHandler(onPlusClickHandler);
					lvReturnImport.setAdapter(returnImportAdapter);
					app.setListViewHeight(lvReturnImport, returnImportAdapter);
				} else {
					app.alertErrorMessageString(getString(R.string.COMMON_ERROR),
							getString(R.string.COMMON_MESSAGE), OrderImportActivity.this);
				}
			}
		}
	}
}
