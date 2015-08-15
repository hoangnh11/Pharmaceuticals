package com.viviproject.visit;

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
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnFeedback;
import com.viviproject.entities.EnStores;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

public class FeedbackActivity extends Activity implements OnClickListener{
	
	private LinearLayout linBack, linSearch, linUpdate, linRefresh;
	private TextView tvHeader;
	private Button btSendFeedBack;
	private EditText edtCustomerName, edtCustomerFeedback;
	private RatingBar rtbCustomerRate;
	
	private AppPreferences app;
	private Bundle bundle;
	private Feedback feedback;
	private ProgressDialog progressDialog;
	private EnStores itemStore;
	private EnFeedback enFeedback;
	private String _rating;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_layout);
		app = new AppPreferences(this);
		bundle = app.getBundle(this);
		itemStore = new EnStores();
		itemStore = (EnStores) bundle.getSerializable(GlobalParams.STORES);
		enFeedback = new EnFeedback();
		initLayout();
	}
	
	public void initLayout(){
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setOnClickListener(this);
		linBack.setVisibility(View.VISIBLE);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.FEED_BACK));
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
		
		btSendFeedBack = (Button) findViewById(R.id.btSendFeedBack);
		btSendFeedBack.setOnClickListener(this);
		
		edtCustomerName = (EditText) findViewById(R.id.edtCustomerName);
		edtCustomerFeedback = (EditText) findViewById(R.id.edtCustomerFeedback);
		rtbCustomerRate = (RatingBar) findViewById(R.id.rtbCustomerRate);
		rtbCustomerRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				_rating = String.valueOf(rating);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			finish();
			break;
			
		case R.id.btSendFeedBack:
			feedback = new Feedback();
			feedback.execute();
			break;
			
		default:
			break;
		}
	}
	
	 /**
     * Send report inventory
     * @author hoangnh11
     *
     */
    class Feedback extends AsyncTask<Void, Void, String> {
		String data;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(FeedbackActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.PROCESSING));
			progressDialog.show();
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					feedback.cancel(true);
				}
			});
		}

		@Override
		protected String doInBackground(Void... params) {
			if (!isCancelled()) {				
				NetParameter[] netParameter = new NetParameter[6];
				netParameter[0] = new NetParameter("store_id", itemStore.getStore_id());
				netParameter[1] = new NetParameter("rate", _rating.substring(0, 1));
				netParameter[2] = new NetParameter("content", edtCustomerFeedback.getEditableText().toString());
				netParameter[3] = new NetParameter("customer_name", edtCustomerName.getEditableText().toString());
				netParameter[4] = new NetParameter("long", BuManagement.getLongitude(FeedbackActivity.this));
				netParameter[5] = new NetParameter("lat", BuManagement.getLatitude(FeedbackActivity.this));
				try {
					data = HttpNetServices.Instance.feedback(netParameter, BuManagement.getToken(FeedbackActivity.this));					
					enFeedback = DataParser.responseFeedback(data);
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
				if (result.equals(GlobalParams.TRUE) && enFeedback != null) {			
					app.alertErrorMessageString(enFeedback.getMessage(),
							getString(R.string.COMMON_MESSAGE), FeedbackActivity.this);
				}
			}
		}
	}
}
