package com.viviproject.reports;

import java.io.File;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.UserInformation;
import com.viviproject.network.NetParameter;
import com.viviproject.network.access.HttpNetServices;
import com.viviproject.service.GPSTracker;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.DataParser;
import com.viviproject.ultilities.DataStorage;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;
import com.viviproject.ultilities.ResponeCodeInf;
import com.viviproject.ultilities.StringUtils;

@SuppressLint("SimpleDateFormat")
public class AcReportImageCapture extends Activity implements OnClickListener{
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ImageView imageView;
	private Button btSendImage;
	private EditText edtTitle, edtNote;
	
	private ProgressDialog dialog;
	private GPSTracker gpsTracker;
	private String imagePath;
	private int pictureReportType = 0;
	private String imageBase64;
	private String imageCreateTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_report_image_capture);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle.containsKey(GlobalParams.EXTRA_PICTURE_REPORT_TYPE)){
			pictureReportType = bundle.getInt(GlobalParams.EXTRA_PICTURE_REPORT_TYPE);
		}
		
		if(bundle.containsKey(GlobalParams.EXTRA_IMAGE_PATH)){
			imagePath = bundle.getString(GlobalParams.EXTRA_IMAGE_PATH);
		}
		
		gpsTracker = new GPSTracker(getApplicationContext());
		initLayout();
	}
	
	/**
	 * initial layout of screen
	 */
	private void initLayout() {
		linBack = (LinearLayout) findViewById(R.id.linBack);
		linBack.setVisibility(View.VISIBLE);
		linBack.setOnClickListener(this);
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.SETTING));
		tvHeader.setVisibility(View.VISIBLE);
		
		linOptionSearch = (LinearLayout) findViewById(R.id.linSearch);
		linOptionSearch.setVisibility(View.INVISIBLE);
		
		linOptionFilter = (LinearLayout) findViewById(R.id.linUpdate);
		linOptionFilter.setVisibility(View.INVISIBLE);
		
		linOptionRefresh = (LinearLayout) findViewById(R.id.linRefresh);
		linOptionRefresh.setVisibility(View.INVISIBLE);
		
		edtTitle = (EditText) findViewById(R.id.edtTitle);
		edtNote = (EditText) findViewById(R.id.edtTitle);
		
		btSendImage = (Button) findViewById(R.id.btSendImage);
		btSendImage.setOnClickListener(this);
		
		imageView = (ImageView) findViewById(R.id.imgCapture);
		if(StringUtils.isNotBlank(imagePath)){
			File imgFile = new  File(imagePath);

			if(imgFile.exists()){
			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    imageView.setImageBitmap(myBitmap);
			}
			
			imageBase64 = BuManagement.endCodeImageFromFile(imagePath);
			Logger.error("imageBase64:" + imageBase64);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			imageCreateTime = sdf.format(imgFile.lastModified());
			Logger.error("image_datetime:" + imageCreateTime);
			
			/*byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			imageView.setImageBitmap(decodedByte);*/
			
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			this.finish();
			break;

		case R.id.btSendImage:
			String title = edtTitle.getText().toString();
			if(StringUtils.isBlank(title)){
				AlertDialog alertDialog = new AlertDialog.Builder(AcReportImageCapture.this).create();
				alertDialog.setTitle("Warning");
				alertDialog.setMessage(getResources().getString(R.string.REPORT_WARNING_TITLE));
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
				});
				alertDialog.show();
				break;
			}
			
			SendImageToServerAsyn sendImageToServerAsyn = new SendImageToServerAsyn();
			sendImageToServerAsyn.execute();
			break;
			
		default:
			break;
		}
		
	}
	
	/**
	 * 	SendImageToServerAsyn
	 * @author Do Thin
	 *
	 */
	private class SendImageToServerAsyn extends AsyncTask<Void, Void, Void>{
		private int reponseCode = ResponeCodeInf.REPONSE_FAILE;
		
		public SendImageToServerAsyn(){
			
		}
		
		@Override
		protected void onPreExecute()
		{
			dialog = new ProgressDialog(AcReportImageCapture.this);
			dialog.setMessage(getResources().getString(R.string.REPORTING));	
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					SendImageToServerAsyn.this.cancel(true);
				}
			});
			dialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Logger.error("----------SendImageToServerAsyn: START------------");
				String token = BuManagement.getToken(getApplicationContext());
				Logger.error("access-token:" + token);
				
				DataStorage dataStorage = DataStorage.getInstance();
				UserInformation userInformation = dataStorage.read_UserInformation(AcReportImageCapture.this);
				String  uid = "AP_" + userInformation.getId() + "_" + System.currentTimeMillis();
				
				double strLong = gpsTracker.getLongitude();
				double strLat = gpsTracker.getLatitude();
				
				NetParameter[] netParameters = new NetParameter[8];
				netParameters[0] = new NetParameter("uid", uid);
				netParameters[1] = new NetParameter("title", edtTitle.getText().toString());
				netParameters[2] = new NetParameter("note", "" + edtNote.getText().toString());
				netParameters[3] = new NetParameter("type", String.valueOf(pictureReportType));
				netParameters[4] = new NetParameter("long", String.valueOf(strLong));
				netParameters[5] = new NetParameter("lat", String.valueOf(strLat));
				netParameters[6] = new NetParameter("image_datetime", imageCreateTime);
				netParameters[7] = new NetParameter("image_data", imageBase64);
				
				for (int i = 0; i < netParameters.length; i++) {
					Logger.error("param[" + i + "]:" + netParameters[i].getName() + "-" + netParameters[i].getValue());
				}
				String response = HttpNetServices.Instance.reportImages(token, netParameters);
				Logger.error("response:" + response);
				DataParser.getEnReportImageResponse(response);
				reponseCode = ResponeCodeInf.REPONSE_SUCCESS;
				Logger.error("----------SendImageToServerAsyn: END------------");
			} catch (Exception e) {
				e.printStackTrace();
				reponseCode = ResponeCodeInf.REPONSE_EXCEPTION;
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(null != dialog && dialog.isShowing()){
				dialog.dismiss();
			}
			
			if(!isCancelled()){
				switch (reponseCode) {
				case ResponeCodeInf.REPONSE_SUCCESS:
					AlertDialog alertDialog = new AlertDialog.Builder(AcReportImageCapture.this).create();
					alertDialog.setTitle("Message");
					alertDialog.setMessage(getResources().getString(R.string.REPORT_SUCCESS));
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									AcReportImageCapture.this.finish();
									dialog.dismiss();
								}
					});

					// Showing Alert Message
					alertDialog.show();
					break;

				case ResponeCodeInf.REPONSE_EXCEPTION:
					AlertDialog alertDialogFaile = new AlertDialog.Builder(AcReportImageCapture.this).create();
					alertDialogFaile.setTitle("Error");
					alertDialogFaile.setMessage(getResources().getString(R.string.REPORT_FAILE));
					alertDialogFaile.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
					});

					// Showing Alert Message
					alertDialogFaile.show();
					break;
				default:
					break;
				}
			}
		}
	}
}
