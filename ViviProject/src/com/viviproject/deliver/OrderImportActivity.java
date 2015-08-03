package com.viviproject.deliver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.ReturnImportAdapter;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.EnStores;
import com.viviproject.entities.Products;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

public class OrderImportActivity extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;	
	private TextView tvConfirm, tvNameStore, tvReturnProduct, tvAddressStore;
	private LinearLayout linSubConfirm;
	private EditText edtContent;
	private ListView lvReturnImport;
	
	private AppPreferences app;
	private ProgressDialog progressDialog;
	private GetProduct getProduct;
	private Products enProducts;
	private Bundle bundle;
	private EnStores itemStore;
	private ReturnImportAdapter returnImportAdapter;
	private EnProducts items;
	
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
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);	
	
		tvConfirm = (TextView) findViewById(R.id.tvConfirm);
		tvConfirm.setOnClickListener(this);
		linSubConfirm = (LinearLayout) findViewById(R.id.linSubConfirm);
		
		edtContent = (EditText) findViewById(R.id.edtContent);
		
		tvNameStore = (TextView) findViewById(R.id.tvNameStore);
		tvReturnProduct = (TextView) findViewById(R.id.tvReturnProduct);
		tvAddressStore = (TextView) findViewById(R.id.tvAddressStore);
		
		lvReturnImport = (ListView) findViewById(R.id.lvReturnImport); 
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.tvConfirm:
			if (linSubConfirm.getVisibility() == View.GONE) {
				tvConfirm.setBackgroundResource(R.color.BLUE);
				linSubConfirm.setVisibility(View.VISIBLE);
			} else {
				tvConfirm.setBackgroundResource(R.color.BG_GRAY9E);
				linSubConfirm.setVisibility(View.GONE);
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
					Logger.error(data);
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
					returnImportAdapter = new ReturnImportAdapter(OrderImportActivity.this, enProducts);
					returnImportAdapter.setOnMinusClickHandler(onMinusClickHandler);
					returnImportAdapter.setOnPlusClickHandler(onPlusClickHandler);
					lvReturnImport.setAdapter(returnImportAdapter);
					app.setListViewHeight(lvReturnImport, returnImportAdapter);				
				}
			}
		}
	}
}
