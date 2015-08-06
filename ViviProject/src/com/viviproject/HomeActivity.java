package com.viviproject;

import java.io.IOException;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.viviproject.customerline.CreateCustormer;
import com.viviproject.customerline.ListCustomer;
import com.viviproject.customerline.MapActivity;
import com.viviproject.deliver.Delived_Order;
import com.viviproject.deliver.OrderActivity;
import com.viviproject.entities.UserInformation;
import com.viviproject.gimic.AcGimicMangager;
import com.viviproject.overview.CoverProductReport;
import com.viviproject.overview.CustomerProfitActivity;
import com.viviproject.overview.ProfitReportActivity;
import com.viviproject.projection.AcCompany;
import com.viviproject.projection.AcPharmacer;
import com.viviproject.projection.AcProducts;
import com.viviproject.projection.AcProjectionClip;
import com.viviproject.projection.AcPromotions;
import com.viviproject.reports.AcProfitFollowCustomer;
import com.viviproject.reports.AcSalesChart;
import com.viviproject.reports.AcTotalSales;
import com.viviproject.sales.Sales;
import com.viviproject.service.TrackingLocationService;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.SharedPreferenceManager;
import com.viviproject.ultilities.StringUtils;
import com.viviproject.visit.PictureReportActivity;
import com.viviproject.visit.VisitAcitvity;

public class HomeActivity extends Activity implements OnClickListener{
	public static final String LOGIN_SHARE_PREFERENT_KEY = "login complete";
	private ImageView imgSetting;
	private LinearLayout linSetting, linLogout, linRefresh;
	private LinearLayout linTongquan, linSubTongquan, linBaocaodoanhso, linBaocaodophu, linkhachhangchuaphatsinhdoanhso;
	private LinearLayout linRoundCustomer, linSubRoundCustomer, linListCustomer, linMap, linCreateNewCustomer;
	private LinearLayout linVisit;
	private LinearLayout linSales;
	private LinearLayout linDeliver, linSubDeliver, linOrder, linDelivedOrder;
	private LinearLayout linProjection, linSubProjection, linDiscountProgram, linProduct, linCompany, linPharmacier, linClip;
	private LinearLayout linGimic;
	private LinearLayout linReport, linSubReport, linSumProfit, linProfitFollowCustomer, linProfitGraphic, linPosterCamera,
							linUnfriendCamera, linTradeMarketingCamera;
	private TextView tvChangePassword, tvName;
	private ScrollView scrollView;
	private boolean showSetting;
	private boolean checkScrollBottom = false;
	SharedPreferenceManager sm;
	private AppPreferences appPreferences;
	AlertDialog _alertDialog;
	private UserInformation userInformation;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		userInformation = new UserInformation();
		appPreferences = new AppPreferences(this);
		
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				HomeActivity.this).threadPriority(Thread.NORM_PRIORITY - 2)
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
		
		InitLayout();	
		
	}

	public void InitLayout() {
		showSetting = true;
		linRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linRefresh.setOnClickListener(this);
		imgSetting = (ImageView) findViewById(R.id.imgSetting);
		imgSetting.setOnClickListener(this);
		linSetting = (LinearLayout) findViewById(R.id.linSetting);
		linLogout = (LinearLayout) findViewById(R.id.linLogout);
		linLogout.setOnClickListener(this);
		tvChangePassword = (TextView) findViewById(R.id.tvChangePassword);
		tvChangePassword.setOnClickListener(this);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		
		tvName = (TextView) findViewById(R.id.tvName);
		if(StringUtils.isNotBlank(userInformation.getAvatar())){
			tvName.setText(userInformation.getUsername());
		} else {
			tvName.setText(getResources().getString(R.string.BLANK));
		}
		
		ImageView imgAvata =(ImageView) findViewById(R.id.imgAvata);
		if(StringUtils.isNotBlank(userInformation.getAvatar())){
			imageLoader.displayImage(userInformation.getAvatar(), imgAvata, options);
		} else {
			imgAvata.setImageResource(R.drawable.ic_stub);
		}
		
		linTongquan = (LinearLayout) findViewById(R.id.linTongquan);
		linTongquan.setOnClickListener(this);
		linSubTongquan = (LinearLayout) findViewById(R.id.linSubTongquan);
		linBaocaodoanhso = (LinearLayout) findViewById(R.id.linBaocaodoanhso);
		linBaocaodoanhso.setOnClickListener(this);
		linBaocaodophu = (LinearLayout) findViewById(R.id.linBaocaodophu);
		linBaocaodophu.setOnClickListener(this);
		linkhachhangchuaphatsinhdoanhso = (LinearLayout) findViewById(R.id.linkhachhangchuaphatsinhdoanhso);
		linkhachhangchuaphatsinhdoanhso.setOnClickListener(this);
		
		linRoundCustomer = (LinearLayout) findViewById(R.id.linRoundCustomer);
		linRoundCustomer.setOnClickListener(this);
		linSubRoundCustomer = (LinearLayout) findViewById(R.id.linSubRoundCustomer);
		linSubRoundCustomer.setOnClickListener(this);
		linListCustomer = (LinearLayout) findViewById(R.id.linListCustomer);
		linListCustomer.setOnClickListener(this);
		linMap = (LinearLayout) findViewById(R.id.linMap);
		linMap.setOnClickListener(this);
		linCreateNewCustomer = (LinearLayout) findViewById(R.id.linCreateNewCustomer);
		linCreateNewCustomer.setOnClickListener(this);
		
		linVisit = (LinearLayout) findViewById(R.id.linVisit);
		linVisit.setOnClickListener(this);
		
		linSales = (LinearLayout) findViewById(R.id.linSales);
		linSales.setOnClickListener(this);
		
		linDeliver = (LinearLayout) findViewById(R.id.linDeliver);
		linDeliver.setOnClickListener(this);
		linSubDeliver = (LinearLayout) findViewById(R.id.linSubDeliver);
		linSubDeliver.setOnClickListener(this);
		linOrder = (LinearLayout) findViewById(R.id.linOrder);
		linOrder.setOnClickListener(this);
		linDelivedOrder = (LinearLayout) findViewById(R.id.linDelivedOrder);
		linDelivedOrder.setOnClickListener(this);	
				
		linProjection = (LinearLayout) findViewById(R.id.linProjection);
		linProjection.setOnClickListener(this);
		linSubProjection = (LinearLayout) findViewById(R.id.linSubProjection);
		linSubProjection.setOnClickListener(this);
		linDiscountProgram = (LinearLayout) findViewById(R.id.linDiscountProgram);
		linDiscountProgram.setOnClickListener(this);
		linProduct = (LinearLayout) findViewById(R.id.linProduct);
		linProduct.setOnClickListener(this);
		linCompany = (LinearLayout) findViewById(R.id.linCompany);
		linCompany.setOnClickListener(this);
		linPharmacier = (LinearLayout) findViewById(R.id.linPharmacier);
		linPharmacier.setOnClickListener(this);
		linClip = (LinearLayout) findViewById(R.id.linClip);
		linClip.setOnClickListener(this);
		
		linGimic = (LinearLayout) findViewById(R.id.linGimic);
		linGimic.setOnClickListener(this);
	
		linReport = (LinearLayout) findViewById(R.id.linReport);
		linReport.setOnClickListener(this);
		linSubReport = (LinearLayout) findViewById(R.id.linSubReport);
		linSubReport.setOnClickListener(this);
		linSumProfit = (LinearLayout) findViewById(R.id.linSumProfit);
		linSumProfit.setOnClickListener(this);
		linProfitFollowCustomer = (LinearLayout) findViewById(R.id.linProfitFollowCustomer);
		linProfitFollowCustomer.setOnClickListener(this);
		linProfitGraphic = (LinearLayout) findViewById(R.id.linProfitGraphic);
		linProfitGraphic.setOnClickListener(this);
		linPosterCamera = (LinearLayout) findViewById(R.id.linPosterCamera);
		linPosterCamera.setOnClickListener(this);
		linUnfriendCamera = (LinearLayout) findViewById(R.id.linUnfriendCamera);
		linUnfriendCamera.setOnClickListener(this);
		linTradeMarketingCamera = (LinearLayout) findViewById(R.id.linTradeMarketingCamera);
		linTradeMarketingCamera.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.imgSetting:
			if (showSetting) {
				linSetting.setVisibility(View.VISIBLE);
				showSetting = false;
			} else {
				linSetting.setVisibility(View.INVISIBLE);
				showSetting = true;
			}
			break;
			
		case R.id.linLogout:
			logout();
			break;
			
		case R.id.linRefresh:
			
			break;
			
		case R.id.tvChangePassword:

			break;
			
		case R.id.linTongquan:			
			if (expandLayout(linSubTongquan.getVisibility(), linSubTongquan)) {
				hideLayout(linSubRoundCustomer);
				hideLayout(linSubDeliver);
				hideLayout(linSubProjection);
				hideLayout(linSubReport);				
			}
			break;
			
		case R.id.linBaocaodoanhso:
			intent = new Intent(this, ProfitReportActivity.class);
			startActivity(intent);
			break;
			
		case R.id.linBaocaodophu:
			intent = new Intent(this, CoverProductReport.class);
			startActivity(intent);
			break;
			
		case R.id.linkhachhangchuaphatsinhdoanhso:
			intent = new Intent(this, CustomerProfitActivity.class);
			startActivity(intent);
			break;	
			
		case R.id.linRoundCustomer:			
			if (expandLayout(linSubRoundCustomer.getVisibility(), linSubRoundCustomer)) {
				hideLayout(linSubTongquan);
				hideLayout(linSubDeliver);
				hideLayout(linSubProjection);
				hideLayout(linSubReport);				
			}
			break;
			
		case R.id.linListCustomer:
			intent = new Intent(this, ListCustomer.class);
			startActivity(intent);
			break;	
			
		case R.id.linMap:
			intent = new Intent(this, MapActivity.class);
			startActivity(intent);
			break;
			
		case R.id.linCreateNewCustomer:
			intent = new Intent(this, CreateCustormer.class);
			startActivity(intent);
			break;
		
		case R.id.linVisit:
			intent = new Intent(this, VisitAcitvity.class);
			startActivity(intent);
			break;
			
		case R.id.linSales:
			intent = new Intent(this, Sales.class);
			startActivity(intent);
			break;
			
		case R.id.linDeliver:		
			if (expandLayout(linSubDeliver.getVisibility(), linSubDeliver)) {
				hideLayout(linSubTongquan);
				hideLayout(linSubRoundCustomer);
				hideLayout(linSubProjection);
				hideLayout(linSubReport);				
			}
			break;
			
		case R.id.linOrder:
			intent = new Intent(this, OrderActivity.class);
			startActivity(intent);
			break;
		
		case R.id.linDelivedOrder:
			intent = new Intent(this, Delived_Order.class);
			startActivity(intent);
			break;
			
		case R.id.linProjection:
			if (expandLayout(linSubProjection.getVisibility(), linSubProjection)) {
				hideLayout(linSubTongquan);
				hideLayout(linSubRoundCustomer);
				hideLayout(linSubDeliver);
				hideLayout(linSubReport);				
			}
			break;
		
		case R.id.linDiscountProgram:
			Intent intentPromotions = new Intent(HomeActivity.this, AcPromotions.class);
			startActivity(intentPromotions);
			break;
		
		case R.id.linProduct:
			Intent intentProject = new Intent(HomeActivity.this, AcProducts.class);
			startActivity(intentProject);
			break;	
			
		case R.id.linCompany:
			Intent intentCompany = new Intent(HomeActivity.this, AcCompany.class);
			startActivity(intentCompany);
			break;
			
		case R.id.linPharmacier:
			Intent intentPharmacer = new Intent(HomeActivity.this, AcPharmacer.class);
			startActivity(intentPharmacer);
			break;	
			
		case R.id.linClip:
			Intent intentClip = new Intent(HomeActivity.this, AcProjectionClip.class);
			startActivity(intentClip);
			break;	
			
		case R.id.linGimic:
			Intent intentGimic = new Intent(HomeActivity.this, AcGimicMangager.class);
			startActivity(intentGimic);
			break;
			
		case R.id.linReport:
			checkScrollBottom = true;		
			if (expandLayout(linSubReport.getVisibility(), linSubReport)) {
				hideLayout(linSubTongquan);
				hideLayout(linSubRoundCustomer);
				hideLayout(linSubDeliver);
				hideLayout(linSubProjection);				
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
			
		case R.id.linSumProfit:
			Intent intentSumProfit = new Intent(HomeActivity.this, AcTotalSales.class);
			startActivity(intentSumProfit);
			break;
			
		case R.id.linProfitFollowCustomer:
			Intent intentProfitFollowCustomer = new Intent(HomeActivity.this, AcProfitFollowCustomer.class);
			startActivity(intentProfitFollowCustomer);
			break;	
			
		case R.id.linProfitGraphic:
			Intent intentSalesChart = new Intent(HomeActivity.this, AcSalesChart.class);
			startActivity(intentSalesChart);
			break;	
			
		case R.id.linPosterCamera:
			Logger.error("Tracking PosterCamera");
			Intent intentPictureReport = new Intent(HomeActivity.this, PictureReportActivity.class);
			intentPictureReport.putExtra(GlobalParams.EXTRA_PICTURE_REPORT_TYPE, GlobalParams.CAPTURE_TYPE_POSTER);
			startActivity(intentPictureReport);
			break;
			
		case R.id.linUnfriendCamera:
			Intent intentPictureRevalReport = new Intent(HomeActivity.this, PictureReportActivity.class);
			intentPictureRevalReport.putExtra(GlobalParams.EXTRA_PICTURE_REPORT_TYPE, GlobalParams.CAPTURE_TYPE_RIVAL);
			startActivity(intentPictureRevalReport);
			break;	
			
		case R.id.linTradeMarketingCamera:
			Intent intentPictureMaketingReport = new Intent(HomeActivity.this, PictureReportActivity.class);
			intentPictureMaketingReport.putExtra(GlobalParams.EXTRA_PICTURE_REPORT_TYPE, GlobalParams.CAPTURE_TYPE_TRADE_MARKETING);
			startActivity(intentPictureMaketingReport);
			break;		
			
		default:
			break;
		}
	}

	/**
	 * Expand layout preferences
	 * @param visible
	 * @param lin
	 * @param tv
	 */
	public boolean expandLayout(int visible, LinearLayout lin){		
		if (visible == View.GONE) {
			lin.setVisibility(View.VISIBLE);			
			return true;
		} else {			
			lin.setVisibility(View.GONE);				
			return false;
		}		
	}
	
	/**
	 * Hide layout preferences
	 * @param lin
	 * @param tv
	 */
	public void hideLayout(LinearLayout lin){
		lin.setVisibility(View.GONE);		
	}
	
	@Override
	public void onBackPressed()
	{
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(R.string.MORE_ARE_YOU_SURE));
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.COMMON_OK),
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						alertDialog.dismiss();					
						finish();
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.COMMON_CANCEL),
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						alertDialog.dismiss();
					}
				});
		alertDialog.show();
	}
	
	private void saveStatusLogoutCompleted() {
		SharedPreferenceManager sharedPreference = new SharedPreferenceManager(this);
		sharedPreference.saveBoolean(LOGIN_SHARE_PREFERENT_KEY, false);
	}
	
	public void logout() {
		android.content.DialogInterface.OnClickListener btDialogListener = new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					_alertDialog.dismiss();
					
					Intent intent = new Intent(HomeActivity.this, TrackingLocationService.class);
					PendingIntent sender = PendingIntent.getService(HomeActivity.this, 0, intent, 0);
					AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
					alarmManager.cancel(sender);
					
					saveStatusLogoutCompleted();
					appPreferences.goLogin(HomeActivity.this);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					_alertDialog.dismiss();
					break;
				default:
					break;
				}
			}
		};
		_alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
		_alertDialog.setTitle(getString(R.string.MORE_ARE_YOU_SURE));
		_alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.COMMON_OK), btDialogListener);
		_alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.COMMON_CANCEL), btDialogListener);
		_alertDialog.show();
	}
	
}
