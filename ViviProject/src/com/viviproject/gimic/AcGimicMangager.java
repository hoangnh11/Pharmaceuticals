package com.viviproject.gimic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.viviproject.R;
import com.viviproject.adapter.AdapterGimicStatistic;
import com.viviproject.core.ScrollViewExt;
import com.viviproject.core.ScrollViewListener;
import com.viviproject.entities.EnGimicItem;
import com.viviproject.entities.EnGimicManager;
import com.viviproject.entities.EnGimicStoreItem;
import com.viviproject.entities.EnStoreItem;
import com.viviproject.network.access.HttpFunctionFactory;
import com.viviproject.network.access.ViviApi;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.StringConverter;
import com.viviproject.ultilities.StringUtils;

@SuppressLint("SimpleDateFormat")
public class AcGimicMangager extends FragmentActivity implements OnClickListener, ScrollViewListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ListView lvGmicStatistic;
	private TableLayout tblGimicCustomer;
	private ScrollViewExt scrollViewGimicCustomer;
	private ImageView imgIconCalendarFrom, imgIconCalendarTo;
	private TextView tvGimicTimeFrom, tvGimicTimeTo;
	private Button btGimicSearch;
	
	private ArrayList<EnGimicItem> listGimicItems = new ArrayList<EnGimicItem>();
	private AdapterGimicStatistic adapterGimicStatistic;
	
	private CaldroidListener listener;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private CaldroidFragment dialogCaldroidFragment;
	private static RestAdapter restAdapter;
	private ProgressDialog dialog;
	private String dateFrom = "04/07/2015";
	private String dateTo = "04/08/2015";
	private int page = 0, perPage = 10;
	private int paddingTitle = 5;
	private boolean flagOnBottomOfScroolView = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_gimic_manager);
		getCurrentDate();
		paddingTitle = (int) BuManagement.convertDpToPixel(5, getApplicationContext());
		initLayout();
	}

	private void initLayout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.MANAGE_GIMIC));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.INVISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.INVISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.VISIBLE);
		linOptionRefresh.setOnClickListener(this);
		
		imgIconCalendarFrom = (ImageView) findViewById(R.id.imgIconCalendarFrom);
		imgIconCalendarFrom.setOnClickListener(this);
		imgIconCalendarTo = (ImageView) findViewById(R.id.imgIconCalendarTo);
		imgIconCalendarTo.setOnClickListener(this);
		
		tvGimicTimeFrom = (TextView) findViewById(R.id.tvGimicTimeFrom);
		tvGimicTimeFrom.setText(dateFrom);
		tvGimicTimeTo = (TextView) findViewById(R.id.tvGimicTimeTo);
		tvGimicTimeTo.setText(dateTo);
		btGimicSearch = (Button) findViewById(R.id.btGimicSearchOK);
		btGimicSearch.setOnClickListener(this);
		
		//initSampleData();
		adapterGimicStatistic = new AdapterGimicStatistic(getApplicationContext(), listGimicItems);
		lvGmicStatistic = (ListView) findViewById(R.id.lvGimicStatistic);
		lvGmicStatistic.setAdapter(adapterGimicStatistic);
		
		scrollViewGimicCustomer =(ScrollViewExt) findViewById(R.id.scrlGimicCustomer);
		scrollViewGimicCustomer.setScrollViewListener(this);
		tblGimicCustomer = (TableLayout) findViewById(R.id.tlGimicGridTable);
		
		refreshData(0, dateFrom, dateTo);
	}

	/**
	 * get current date and update to from field and to field
	 */
	public void getCurrentDate(){
		try {
			Calendar calendar = Calendar.getInstance();
			Date toDate = calendar.getTime();
			dateTo = formatter.format(toDate);
			
			calendar.add(Calendar.MONTH, -1);
			Date fromDate = calendar.getTime();
			dateFrom = formatter.format(fromDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * refresh data
	 * @param from
	 * @param to
	 */
	private void refreshData(int page, String from, String to) {
		getGimicManagerFromServer(page, from, to);
	}

	/**
	 * update data to screen
	 * @param enGimicManager
	 */
	protected void updateDataScreen(EnGimicManager enGimicManager) {
		if(null == enGimicManager ){
			return;
		}
		
		ArrayList<EnGimicItem> listGimicItems = enGimicManager.getGimics();
		if(page == 0){
			// refresh new data
			adapterGimicStatistic.setListGimic(listGimicItems);
			adapterGimicStatistic.notifyDataSetChanged();
			
			tblGimicCustomer.removeAllViews();
			
			//header
			TableRow tbRow = new TableRow(AcGimicMangager.this);
			tbRow.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
	                LayoutParams.WRAP_CONTENT));
			tbRow.setGravity(Gravity.CENTER_VERTICAL);
			tbRow.setBackgroundColor(getResources().getColor(R.color.PINK_LIGHT));
			
			TextView tvSTT = new TextView(this);
			tvSTT.setText(getResources().getString(R.string.INDEX));
			tvSTT.setPadding(paddingTitle, paddingTitle, paddingTitle, paddingTitle);
			tvSTT.setTypeface(Typeface.DEFAULT_BOLD);
			tvSTT.setTextSize(12);
			tbRow.addView(tvSTT);
			
			TextView tvStoreTitle = new TextView(this);
			tvStoreTitle.setText(getResources().getString(R.string.TEXT_STORE));
			tvStoreTitle.setPadding(paddingTitle, paddingTitle, paddingTitle, paddingTitle);
			tvStoreTitle.setMinWidth((int) BuManagement.convertDpToPixel(100, getApplicationContext()));
			tvStoreTitle.setGravity(Gravity.CENTER);
			tvStoreTitle.setTypeface(Typeface.DEFAULT_BOLD);
			tvStoreTitle.setTextSize(12);
			tbRow.addView(tvStoreTitle);
			
			for (int i = 0; i < listGimicItems.size(); i++) {
				TextView tvItemTitle = new TextView(this);
				tvItemTitle.setText(listGimicItems.get(i).getName());
				tvItemTitle.setPadding(paddingTitle, paddingTitle, paddingTitle, paddingTitle);
				tvItemTitle.setTypeface(Typeface.DEFAULT_BOLD);
				tvItemTitle.setTextSize(12);
				tbRow.addView(tvItemTitle);
			}
			tblGimicCustomer.addView(tbRow);
			
			//content
			ArrayList<EnStoreItem> listStoreItem = enGimicManager.getStores();
			for(int i = 0; i < listStoreItem.size(); i++){
				TableRow tbRowItem = new TableRow(AcGimicMangager.this);
				tbRowItem.setGravity(Gravity.CENTER_VERTICAL);
				
				TextView tvSTTRow = new TextView(this);
				tvSTTRow.setText(String.valueOf(i+1));
				tvSTTRow.setPadding(5, 5, 5, 5);
				tvSTTRow.setGravity(Gravity.CENTER);
				tvSTTRow.setTypeface(Typeface.DEFAULT_BOLD);
				tvSTTRow.setTextSize(12);
				tbRowItem.addView(tvSTTRow);
				
				TextView tvStore = new TextView(this);
				tvStore.setText(listStoreItem.get(i).getName() + "\n" + listStoreItem.get(i).getAddress());
				tvStore.setPadding(5, 5, 5, 5);
				tvStore.setGravity(Gravity.CENTER);
				tvStore.setTypeface(Typeface.DEFAULT_BOLD);
				tvStore.setTextSize(12);
				tbRowItem.addView(tvStore);
				
				for (int j = 0; j < listGimicItems.size(); j++) {
					String id = listGimicItems.get(j).getId();
					EnGimicStoreItem enGimicStoreItem = getGimicStoreItemFromList(id, listStoreItem.get(i).getGimics());
					
					TextView tvItem = new TextView(this);
					if(null != enGimicStoreItem){
						tvItem.setText(enGimicStoreItem.getTotal());
					} else {
						tvItem.setText("");
					}
					tvItem.setPadding(5, 5, 5, 5);
					tvItem.setGravity(Gravity.CENTER);
					tvItem.setTypeface(Typeface.DEFAULT_BOLD);
					tvItem.setTextSize(12);
					tbRowItem.addView(tvItem);
				}
				tblGimicCustomer.addView(tbRowItem);
			}
		} else {
			
		}
		
	}
	
	private EnGimicStoreItem getGimicStoreItemFromList(String id, ArrayList<EnGimicStoreItem> gimics) {
		if(StringUtils.isBlank(id) || null == gimics || (gimics.size() == 0)){
			return null;
		}
		
		EnGimicStoreItem enGimicStoreItem = null;
		for (int i = 0; i < gimics.size(); i++) {
			if(id.equalsIgnoreCase(gimics.get(i).getGimic_id())){
				enGimicStoreItem = gimics.get(i);
				break;
			}
		}
		return enGimicStoreItem;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			AcGimicMangager.this.finish();
			break;
			
		case R.id.imgIconCalendarFrom:
			showCalender(R.id.imgIconCalendarFrom);
			break;
		
		case R.id.imgIconCalendarTo:
			showCalender(R.id.imgIconCalendarTo);
			break;
		
		case R.id.linRefresh:
			getCurrentDate();
			page = 0;
			tvGimicTimeFrom.setText(dateFrom);
			tvGimicTimeTo.setText(dateTo);
			refreshData(0, dateFrom, dateTo);
			break;
		
		case R.id.btGimicSearchOK:
			page = 0;
			refreshData(0, dateFrom, dateTo);
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
		// We take the last son in the scrollview
	    View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
	    int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

	    // if diff is zero, then the bottom has been reached
	    if (diff == 0 && !flagOnBottomOfScroolView) {
	        // do stuff
	    	Logger.error("Scrollview load more");
	    	flagOnBottomOfScroolView = true;
	    	page++;
	    	refreshData(page, dateFrom, dateTo);
	    } 
	}
	
	@SuppressLint("SimpleDateFormat")
	private void showCalender(final int viewID) {
		listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				dialogCaldroidFragment.dismiss();
				if(viewID == R.id.imgIconCalendarFrom){
					dateFrom = formatter.format(date);
					tvGimicTimeFrom.setText(dateFrom);
				} else if (viewID == R.id.imgIconCalendarTo){
					dateTo = formatter.format(date);
					tvGimicTimeTo.setText(dateTo);
				}
			}

			@Override
			public void onChangeMonth(int month, int year) {
			}

			@Override
			public void onLongClickDate(Date date, View view) {
			}

			@Override
			public void onCaldroidViewCreated() {
				if (dialogCaldroidFragment.getLeftArrowButton() != null) {
				}
			}

			@Override
			public void onNothingSelected() {
				Logger.error("Date onNothingSelected");
			}

		};
		
		dialogCaldroidFragment = new CaldroidFragment();
		dialogCaldroidFragment.setCaldroidListener(listener);
		dialogCaldroidFragment.show(getSupportFragmentManager(), "CalenderAndroid");
	}
	
	/**
	 * get newest data from server
	 */
	private void getGimicManagerFromServer(int page, String from, String to){
		if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpFunctionFactory.viviHostURLshort)
                    .setConverter(new StringConverter())
                    .build();
        }
		
		
		String token = BuManagement.getToken(getApplicationContext());
		
		if(null == dialog){
			dialog = new ProgressDialog(AcGimicMangager.this);
			dialog.setMessage(getResources().getString(R.string.LOADING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					//cancel get data
				}
			});
		}
		dialog.show();
		restAdapter.create(ViviApi.class).getGimicManager(token, from, to, page, perPage, myCallback);
	}
	
	Callback<String> myCallback = new Callback<String>() {

		@Override
		public void failure(RetrofitError retrofitError) {
			retrofitError.printStackTrace();
			if(null != AcGimicMangager.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			
			flagOnBottomOfScroolView = false;
			if(page > 0){
				page--;
			}
		}

		@Override
		public void success(String strData, Response response) {
			Logger.error("Get gimc manager:" + strData);
			if(null != AcGimicMangager.this && null != dialog){
				if(dialog.isShowing()) dialog.dismiss();
			}
			flagOnBottomOfScroolView = false;
			
			try {
				//Paser data
				EnGimicManager enGimicManager = DataParser.getEnGimicManager(strData);
				updateDataScreen(enGimicManager);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	};
	
}
