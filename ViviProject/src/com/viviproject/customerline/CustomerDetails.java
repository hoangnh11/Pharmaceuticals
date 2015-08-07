package com.viviproject.customerline;

import java.io.IOException;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.viviproject.R;
import com.viviproject.adapter.FiveOrderAdapter;
import com.viviproject.adapter.OwnerAdapter;
import com.viviproject.adapter.SalerAdapter;
import com.viviproject.adapter.ThreeOrderAdapter;
import com.viviproject.entities.EnStores;
import com.viviproject.entities.UserInformation;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.StringUtils;

public class CustomerDetails extends Activity implements OnClickListener{
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;	
	private LinearLayout linEdit, linOwnerPharmacy;
	private LinearLayout linProfitFive, linSubProfitFive, linProfitThree, linSubProfitThree;
	private LinearLayout linSaler;
	private ListView lvOwner, lvSaler, lvFiveOrder, lvThreeOrder;
	private TextView tvNameStore, tvCode, tvPhoneStore, tvAddressStore, tvActive, tvVipStore, tvTotalRevenue;
	private ImageView imgAvata;
	
	private OwnerAdapter ownerAdapter;
	private SalerAdapter salerAdapter;
	private FiveOrderAdapter fiveOrderAdapter;
	private ThreeOrderAdapter threeOrderAdapter;
	private AppPreferences app;	
	private boolean checkScrollBottom = false;
	private ScrollView scrollView;
	private Bundle bundle;
	private EnStores store;
	private UserInformation userInformation;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_details);
		app = new AppPreferences(this);
		bundle = app.getBundle(this);
		store = new EnStores();
		store = (EnStores) bundle.getSerializable(GlobalParams.STORES);
		userInformation = new UserInformation();
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				CustomerDetails.this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(imageLoaderConfiguration);
		
		try {
			userInformation = DataStorage.getInstance().read_UserInformation(this);					
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		initLayout();
		
		if (store != null) {
			tvNameStore.setText(store.getName());
			tvCode.setText(store.getCode());
			tvPhoneStore.setText(store.getPhone());
			tvAddressStore.setText(store.getAddress());
			tvActive.setText(store.getApprove());
			tvVipStore.setText(store.getVip());
			tvTotalRevenue.setText(store.getTotal_revenue() + " VND");
			
			ownerAdapter = new OwnerAdapter(this, store.getOwners());
			lvOwner.setAdapter(ownerAdapter);
			app.setListViewHeight(lvOwner, ownerAdapter);			
			
			salerAdapter = new SalerAdapter(this, store.getEmployees());
			lvSaler.setAdapter(salerAdapter);
			app.setListViewHeight(lvSaler, salerAdapter);
			
			if (store.getLatest_5_orders() != null) {
				fiveOrderAdapter = new FiveOrderAdapter(this, store.getLatest_5_orders());
				lvFiveOrder.setAdapter(fiveOrderAdapter);
				app.setListViewHeight(lvFiveOrder, fiveOrderAdapter);
			}
			
			if (store.getLatest_3_months() != null) {
				threeOrderAdapter = new ThreeOrderAdapter(this, store.getLatest_3_months());
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
		tvHeader.setText(getResources().getString(R.string.CUSTOMER_INFORMATION));
		tvHeader.setVisibility(View.VISIBLE);
		
		linSearch = (LinearLayout) findViewById(R.id.linSearch);
		linSearch.setOnClickListener(this);
		linSearch.setVisibility(View.VISIBLE);
		
		linUpdate = (LinearLayout) findViewById(R.id.linUpdate);
		linUpdate.setOnClickListener(this);
		linUpdate.setVisibility(View.VISIBLE);
		
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
	
		linEdit = (LinearLayout) findViewById(R.id.linEdit);
		linEdit.setOnClickListener(this);
		
		tvNameStore = (TextView) findViewById(R.id.tvNameStore);
		tvCode = (TextView) findViewById(R.id.tvCode);
		tvPhoneStore = (TextView) findViewById(R.id.tvPhoneStore);
		tvAddressStore = (TextView) findViewById(R.id.tvAddressStore);
		tvActive = (TextView) findViewById(R.id.tvActive);
		tvVipStore = (TextView) findViewById(R.id.tvVipStore);
		tvTotalRevenue = (TextView) findViewById(R.id.tvTotalRevenue);
		
		linOwnerPharmacy = (LinearLayout) findViewById(R.id.linOwnerPharmacy);
		linOwnerPharmacy.setOnClickListener(this);
		
		linProfitFive = (LinearLayout) findViewById(R.id.linProfitFive);
		linProfitFive.setOnClickListener(this);
		linSubProfitFive = (LinearLayout) findViewById(R.id.linSubProfitFive);
		
		linProfitThree = (LinearLayout) findViewById(R.id.linProfitThree);
		linProfitThree.setOnClickListener(this);
		linSubProfitThree = (LinearLayout) findViewById(R.id.linSubProfitThree);
		
		scrollView = (ScrollView) findViewById(R.id.scrollview);
		
		linSaler = (LinearLayout) findViewById(R.id.linSaler);
		linSaler.setOnClickListener(this);
		lvOwner = (ListView) findViewById(R.id.lvOwner);
		lvSaler = (ListView) findViewById(R.id.lvSaler);
		imgAvata = (ImageView) findViewById(R.id.imgAvata);
		lvFiveOrder = (ListView) findViewById(R.id.lvFiveOrder);
		lvThreeOrder = (ListView) findViewById(R.id.lvThreeOrder);
		
		if(StringUtils.isNotBlank(userInformation.getAvatar())){
			imageLoader.displayImage(userInformation.getAvatar(), imgAvata, options);
		} else {
			imgAvata.setImageResource(R.drawable.icon_avatar_blue);
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;	
			
		case R.id.linEdit:
			intent = new Intent(this, EditCustomer.class);
			intent.putExtra(GlobalParams.STORES, store);
			startActivity(intent);
			break;
			
		case R.id.linOwnerPharmacy:
			if (lvOwner.getVisibility() == View.GONE) {
				linOwnerPharmacy.setBackgroundResource(R.color.BG_GRAY9E);
				lvOwner.setVisibility(View.VISIBLE);
			} else {				
				linOwnerPharmacy.setBackgroundResource(R.color.BLUE);
				lvOwner.setVisibility(View.GONE);
			}
			break;	
			
		case R.id.linSaler:
			if (lvSaler.getVisibility() == View.GONE) {
				linSaler.setBackgroundResource(R.color.BG_GRAY9E);
				lvSaler.setVisibility(View.VISIBLE);
			} else {
				linSaler.setBackgroundResource(R.color.BLUE);
				lvSaler.setVisibility(View.GONE);
			}
			break;
			
		case R.id.linProfitFive:
			checkScrollBottom = true;
			if (linSubProfitFive.getVisibility() == View.GONE) {
				linProfitFive.setBackgroundResource(R.color.BG_GRAY9E);
				linSubProfitFive.setVisibility(View.VISIBLE);
			} else {
				linProfitFive.setBackgroundResource(R.color.BLUE);
				linSubProfitFive.setVisibility(View.GONE);
			}
			
			scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
		        @Override
		        public void onGlobalLayout() {
		        	scrollView.post(new Runnable() {
		                public void run() {
		                	if (checkScrollBottom) {
		                		scrollView.fullScroll(View.FOCUS_DOWN);
		                		checkScrollBottom = false;
							}
		                }
		            });
		        }
		    });
			break;
			
		case R.id.linProfitThree:			
			if (linSubProfitThree.getVisibility() == View.GONE) {
				linProfitThree.setBackgroundResource(R.color.BG_GRAY9E);
				linSubProfitThree.setVisibility(View.VISIBLE);
			} else {
				linProfitThree.setBackgroundResource(R.color.BLUE);
				linSubProfitThree.setVisibility(View.GONE);
			}			
			break;	
			
		default:
			break;
		}
	}
}
