package com.viviproject.visit;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.adapter.FiveOrderAdapter;
import com.viviproject.adapter.InventoryAdapter;
import com.viviproject.adapter.ThreeOrderAdapter;
import com.viviproject.customerline.CustomerDetails;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.EnReport;
import com.viviproject.entities.EnStores;
import com.viviproject.entities.Products;
import com.viviproject.entities.ResponseReport;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

public class VisitDetailsActivity extends Activity implements OnClickListener{

	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	
	private LinearLayout linCheckWarehouse, linSubChekcWarehouse;
	private LinearLayout linCustomerInformation;
	private TextView tvBuy, tvGivegimic, tvCloseDoor, tvFeedback;
	private LinearLayout linBuyHistory, linSubBuyHistory;
	private TextView tvNameStore, tvAddressStores, tvLineStore, tvVip, tvPointSum, tvTotalProfit, tvLocation;
	private ListView lvInventory, lvFiveOrder, lvThreeOrder;
	private Button btnSendReport, btnImport;
	
	private FiveOrderAdapter fiveOrderAdapter;
	private ThreeOrderAdapter threeOrderAdapter;
	private AppPreferences app;
	private Bundle bundle;
	private EnStores itemStore;
	private ProgressDialog progressDialog;
	private GetProduct getProduct;
	public static Products enProducts;
	private EnProducts items;
	private InventoryAdapter itemListViewInventory;
	private SendReportInventory sendReportInventory;
	private ResponseReport responseReport;
	private EnReport enReport;
	public static ArrayList<EnReport> arrReport;
	private String lines;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visit_details_layout);
		app = new AppPreferences(this);
		bundle = app.getBundle(this);
		itemStore = new EnStores();
		itemStore = (EnStores) bundle.getSerializable(GlobalParams.STORES);
		enProducts = new Products();
		items = new EnProducts();
		responseReport = new ResponseReport();
		enReport = new EnReport();
		arrReport = new ArrayList<EnReport>();
		lines = "";	
		initLayout();
		
		if (itemStore != null) {
			tvNameStore.setText(itemStore.getName());
			tvAddressStores.setText(itemStore.getAddress());
			tvLocation.setText(itemStore.getDistance());
			if (itemStore.getLines() != null && itemStore.getLines().length > 0) {
        		
        		for (int i = 0; i < itemStore.getLines().length; i++) {
        			lines += itemStore.getLines()[i] + ", ";
    			}
			}
        	
        	if (!lines.equals("")) {
        		tvLineStore.setText(lines.substring(0, lines.length() - 2));
			} else {
				tvLineStore.setText(lines);
			}        	
        	
			tvVip.setText(itemStore.getVip());
			tvPointSum.setText(itemStore.getActive());
			tvTotalProfit.setText(itemStore.getTotal_revenue() + " VND");
			
			if (itemStore.getLatest_5_orders() != null) {
				fiveOrderAdapter = new FiveOrderAdapter(this, itemStore.getLatest_5_orders());
				lvFiveOrder.setAdapter(fiveOrderAdapter);
				app.setListViewHeight(lvFiveOrder, fiveOrderAdapter);
			}
			
			if (itemStore.getLatest_3_months() != null) {
				threeOrderAdapter = new ThreeOrderAdapter(this, itemStore.getLatest_3_months());
				lvThreeOrder.setAdapter(threeOrderAdapter);
				app.setListViewHeight(lvThreeOrder, threeOrderAdapter);
			}
		}
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
		linSearch.setVisibility(View.GONE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.GONE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		linRefresh.setVisibility(View.GONE);
		
		lvInventory = (ListView) findViewById(R.id.lvInventory);
		linCustomerInformation = (LinearLayout) findViewById(R.id.linCustomerInformation);
		linCustomerInformation.setOnClickListener(this);
		
		tvBuy = (TextView) findViewById(R.id.tvBuy);
		tvBuy.setOnClickListener(this);
		tvGivegimic = (TextView) findViewById(R.id.tvGivegimic);
		tvGivegimic.setOnClickListener(this);
		tvCloseDoor = (TextView) findViewById(R.id.tvCloseDoor);
		tvCloseDoor.setOnClickListener(this);
		tvFeedback = (TextView) findViewById(R.id.tvFeedback);
		tvFeedback.setOnClickListener(this);
		
		linCheckWarehouse = (LinearLayout) findViewById(R.id.linCheckWarehouse);
		linCheckWarehouse.setOnClickListener(this);
		linSubChekcWarehouse = (LinearLayout) findViewById(R.id.linSubChekcWarehouse);
		
		linBuyHistory = (LinearLayout) findViewById(R.id.linBuyHistory);
		linBuyHistory.setOnClickListener(this);
		linSubBuyHistory = (LinearLayout) findViewById(R.id.linSubBuyHistory);
		
		tvLocation = (TextView) findViewById(R.id.tvLocation);
		tvNameStore = (TextView) findViewById(R.id.tvNameStore);		
		tvAddressStores = (TextView) findViewById(R.id.tvAddressStores);		
		tvLineStore = (TextView) findViewById(R.id.tvLineStore);		
		tvVip = (TextView) findViewById(R.id.tvVip);		
		tvPointSum = (TextView) findViewById(R.id.tvPointSum);		
		tvTotalProfit = (TextView) findViewById(R.id.tvTotalProfit);
		lvFiveOrder = (ListView) findViewById(R.id.lvFiveOrder);
		lvThreeOrder = (ListView) findViewById(R.id.lvThreeOrder);
		
		btnSendReport = (Button) findViewById(R.id.btnSendReport);
		btnSendReport.setOnClickListener(this);
		btnImport = (Button) findViewById(R.id.btnImport);
		btnImport.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
	
		case R.id.linBuyHistory:
			if (linSubBuyHistory.getVisibility() == View.GONE) {
				linBuyHistory.setBackgroundResource(R.color.BG_GRAY9E);
				linSubBuyHistory.setVisibility(View.VISIBLE);				
			} else {
				linBuyHistory.setBackgroundResource(R.color.BLUE);
				linSubBuyHistory.setVisibility(View.GONE);
			}
			break;
			
		case R.id.linCheckWarehouse:
			if (linSubChekcWarehouse.getVisibility() == View.GONE) {
				linCheckWarehouse.setBackgroundResource(R.color.BG_GRAY9E);
				linSubChekcWarehouse.setVisibility(View.VISIBLE);
				getProduct = new GetProduct();
				getProduct.execute();
			} else {
				linCheckWarehouse.setBackgroundResource(R.color.BLUE);
				linSubChekcWarehouse.setVisibility(View.GONE);
			}
			break;	
			
		case R.id.tvBuy:
			intent = new Intent(this, PlaceOrderActivity.class);
			intent.putExtra(GlobalParams.STORES, itemStore);
			startActivity(intent);
			break;
			
		case R.id.tvGivegimic:
			intent = new Intent(this, GiveGimic.class);
			intent.putExtra(GlobalParams.STORES, itemStore);
			startActivity(intent);
			break;
			
		case R.id.tvFeedback:
			intent = new Intent(this, FeedbackActivity.class);
			intent.putExtra(GlobalParams.STORES, itemStore);
			startActivity(intent);
			break;
			
		case R.id.tvCloseDoor:
			intent = new Intent(VisitDetailsActivity.this, PictureReportActivity.class);
			intent.putExtra(GlobalParams.EXTRA_PICTURE_REPORT_TYPE, GlobalParams.CAPTURE_TYPE_CLOSE);
			startActivity(intent);
			break;
			
		case R.id.btnSendReport:
			sendReportInventory = new SendReportInventory();
			sendReportInventory.execute();
			break;
			
		case R.id.btnImport:
			intent = new Intent(this, OrderImportActivity.class);
			intent.putExtra(GlobalParams.STORES, itemStore);
			startActivity(intent);
			break;
			
		case R.id.linCustomerInformation:
			intent = new Intent(this, CustomerDetails.class);
			intent.putExtra(GlobalParams.STORES, itemStore);
			startActivity(intent);
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
        	int position = ((ItemListViewInventory) v).get_position();
            items = enProducts.getProducts().get(position);          
            if (Integer.parseInt(items.getUnit()) > 0) {
            	enProducts.getProducts().get(position).setUnit(String.valueOf(Integer.parseInt(items.getUnit()) - 1));            	
				
            	itemListViewInventory = new InventoryAdapter(VisitDetailsActivity.this, enProducts);
            	itemListViewInventory.setOnMinusClickHandler(onMinusClickHandler);
            	itemListViewInventory.setOnPlusClickHandler(onPlusClickHandler);            	
            	lvInventory.setAdapter(itemListViewInventory);
    			app.setListViewHeight(lvInventory, itemListViewInventory);
    			
    			enReport = new EnReport();
    			enReport.setProduct_id(Integer.parseInt(enProducts.getProducts().get(position).getId()));
    			enReport.setQuantity(Integer.parseInt(enProducts.getProducts().get(position).getUnit()));
    			arrReport.set(position, enReport);
			}			
        }
    };
    
    OnClickListener onPlusClickHandler = new OnClickListener() 
	{		
        @Override
        public void onClick(View v)
        {
        	int position = ((ItemListViewInventory) v).get_position();
            items = enProducts.getProducts().get(position);
        	enProducts.getProducts().get(position).setUnit(String.valueOf(Integer.parseInt(items.getUnit()) + 1));
        	
        	itemListViewInventory = new InventoryAdapter(VisitDetailsActivity.this, enProducts);		
        	itemListViewInventory.setOnMinusClickHandler(onMinusClickHandler);
        	itemListViewInventory.setOnPlusClickHandler(onPlusClickHandler);        
        	lvInventory.setAdapter(itemListViewInventory);
			app.setListViewHeight(lvInventory, itemListViewInventory);
			
			enReport = new EnReport();
			enReport.setProduct_id(Integer.parseInt(enProducts.getProducts().get(position).getId()));
			enReport.setQuantity(Integer.parseInt(enProducts.getProducts().get(position).getUnit()));
			arrReport.set(position, enReport);
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
			progressDialog = new ProgressDialog(VisitDetailsActivity.this);
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
				netParameter[0] = new NetParameter("access-token", BuManagement.getToken(VisitDetailsActivity.this));				
				try {
					data = HttpNetServices.Instance.getProductsSimple(netParameter);					
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
						enProducts.getProducts().get(i).setUnit("-1");
						enReport = new EnReport();
						enReport.setProduct_id(Integer.parseInt(enProducts.getProducts().get(i).getId()));
						enReport.setQuantity(Integer.parseInt(enProducts.getProducts().get(i).getUnit()));
						arrReport.add(enReport);
					}
					
					itemListViewInventory = new InventoryAdapter(VisitDetailsActivity.this, enProducts);
					itemListViewInventory.setOnMinusClickHandler(onMinusClickHandler);
					itemListViewInventory.setOnPlusClickHandler(onPlusClickHandler);				
					lvInventory.setAdapter(itemListViewInventory);
					app.setListViewHeight(lvInventory, itemListViewInventory);
				}
			}
		}
	}
    
    /**
     * Send report inventory
     * @author hoangnh11
     *
     */
    class SendReportInventory extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(VisitDetailsActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.PROCESSING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					sendReportInventory.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[4];
				netParameter[0] = new NetParameter("store_id", itemStore.getStore_id());
				netParameter[1] = new NetParameter("long", BuManagement.getLongitude(VisitDetailsActivity.this));
				netParameter[2] = new NetParameter("lat", BuManagement.getLatitude(VisitDetailsActivity.this));
				netParameter[3] = new NetParameter("data", DataParser.convertObjectToString(arrReport));
				try {
					data = HttpNetServices.Instance.sendReportInventory(netParameter, BuManagement.getToken(VisitDetailsActivity.this));					
					responseReport = DataParser.responseReport(data);
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
				if (result.equals(GlobalParams.TRUE) && responseReport != null) {			
					app.alertErrorMessageString(responseReport.getMessage(),
							getString(R.string.COMMON_MESSAGE), VisitDetailsActivity.this);
					
					for (int i = 0; i < enProducts.getProducts().size(); i++) {
						enProducts.getProducts().get(i).setUnit("-1");
						enReport = new EnReport();
						enReport.setProduct_id(Integer.parseInt(enProducts.getProducts().get(i).getId()));
						enReport.setQuantity(Integer.parseInt(enProducts.getProducts().get(i).getUnit()));
						arrReport.add(enReport);
					}
					
					itemListViewInventory = new InventoryAdapter(VisitDetailsActivity.this, enProducts);
					itemListViewInventory.setOnMinusClickHandler(onMinusClickHandler);
					itemListViewInventory.setOnPlusClickHandler(onPlusClickHandler);					
					lvInventory.setAdapter(itemListViewInventory);
					app.setListViewHeight(lvInventory, itemListViewInventory);
				}
			}
		}
	}
}
