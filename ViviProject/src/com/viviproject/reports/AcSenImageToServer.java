package com.viviproject.reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class AcSenImageToServer extends Activity implements OnClickListener {
	private static final int CAMERA_REQUEST = 1888; 
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final String IMAGE_DIRECTORY_NAME = "ViviPharma";
	private LinearLayout linBack;
	private TextView tvHeader;
	private LinearLayout linOptionSearch, linOptionFilter, linOptionRefresh;
	private ImageView imageView;
	private Button btSendImage;
	private EditText edtTitle, edtNote;
    
    private int pictureReportType = 0;
    private ProgressDialog dialog;
	private GPSTracker gpsTracker;
	private String imageBase64;
	private String imageCreateTime;
	private Uri fileUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_report_image_capture);
		
		Bundle bundle = getIntent().getExtras();
		if(null != bundle && bundle.containsKey(GlobalParams.EXTRA_PICTURE_REPORT_TYPE)){
			pictureReportType = bundle.getInt(GlobalParams.EXTRA_PICTURE_REPORT_TYPE);
		}
		
		gpsTracker = new GPSTracker(getApplicationContext());
		
		initLayout();
		
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST); 
	}

	public void initLayout(){
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
		edtNote = (EditText) findViewById(R.id.edtNote);
		
		btSendImage = (Button) findViewById(R.id.btSendImage);
		btSendImage.setOnClickListener(this);
		
		imageView = (ImageView) findViewById(R.id.imgCapture);
		
	}
	
	@SuppressWarnings("deprecation")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
        	try{
	            //Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            //imageView.setImageBitmap(photo);
	            //imageBase64 = base64Imate(photo);
	            
	            Calendar calendar = Calendar.getInstance();
	            Date date = calendar.getTime();
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            imageCreateTime = sdf.format(date);
	            
				// bimatp factory
				BitmapFactory.Options options = new BitmapFactory.Options();

				// downsizing image as it throws OutOfMemory Exception for
				// larger
				// images
				options.inSampleSize = 2;
				Bitmap bitmap = BitmapFactory.decodeFile(
						fileUri.getPath(), options);
				
				ExifInterface ei = new ExifInterface(fileUri.getPath());
				int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					//rotate image 90
					try{
						bitmap = rotateImage(bitmap, 90);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				
				case ExifInterface.ORIENTATION_ROTATE_180:
					//rotate image 180
					try{
						bitmap = rotateImage(bitmap, 180);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
					
				default:
					break;
				}
				
				try {
					bitmap = getResizedBitmap(bitmap, 800, 400);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				imageView.setImageBitmap(bitmap);
				imageBase64  = base64Imate(bitmap);
				galleryAddPic();
        	} catch (Exception e) {
				e.printStackTrace();
				AlertDialog alertDialog = new AlertDialog.Builder(AcSenImageToServer.this).create();
				alertDialog.setTitle("Message");
				alertDialog.setMessage(getResources().getString(R.string.CAPTURE_FAIL));
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								AcSenImageToServer.this.finish();
								dialog.dismiss();
							}
				});

				// Showing Alert Message
				alertDialog.show();
			}
        } else {
        	AcSenImageToServer.this.finish();
        }
    } 
	
	/**
	 * rotate bitmap image
	 * @param bitmap
	 * @param i 
	 */
	private Bitmap rotateImage(Bitmap bmp, int i) throws Exception{
		Matrix mat = new Matrix();
		mat.postRotate(i);
		Bitmap bmpRotate = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);
		return bmpRotate;
	}

	/**
	 * getResizedBitmap
	 * @param bm
	 * @param newHeight
	 * @param newWidth
	 * @return
	 */
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

		int width = bm.getWidth();

		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;

		float scaleHeight = ((float) newHeight) / height;

		// CREATE A MATRIX FOR THE MANIPULATION

		Matrix matrix = new Matrix();

		// RESIZE THE BIT MAP
		
		if(height >= width){
			matrix.postScale(scaleHeight, scaleHeight);
		} else {
			matrix.postScale(scaleWidth, scaleWidth);
		}
		// RECREATE THE NEW BITMAP

		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

		return resizedBitmap;

	}

	private void galleryAddPic() {
		if(null != fileUri){
			Intent mediaScanIntent = new Intent(
					Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			// File f = new File(mCurrentPhotoPath);
			// Uri contentUri = Uri.fromFile(f);
			mediaScanIntent.setData(fileUri);
			this.sendBroadcast(mediaScanIntent);
		}
	}
	
	/**
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	}
	
	/*
	 * Here we restore the fileUri again
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	 
	    // get the file url
	    fileUri = savedInstanceState.getParcelable("file_uri");
	}
	
	private String base64Imate(Bitmap bitmap){
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream .toByteArray();
		
		String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
		Logger.error("base64 of image:" + encoded);
		
		if(StringUtils.isBlank(encoded)){
			return "";
		} else {
			return encoded;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linBack:
			this.finish();
			break;
		
		case R.id.btSendImage:
			String title = edtTitle.getText().toString();
			if(StringUtils.isBlank(title)){
				AlertDialog alertDialog = new AlertDialog.Builder(AcSenImageToServer.this).create();
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
	 * ------------ Helper Methods ----------------------
	 * */
	/**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    
    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
 
        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
 
        return mediaFile;
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
			dialog = new ProgressDialog(AcSenImageToServer.this);
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
				UserInformation userInformation = dataStorage.read_UserInformation(AcSenImageToServer.this);
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
		
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(null != dialog && dialog.isShowing()){
				dialog.dismiss();
			}
			
			if(!isCancelled()){
				switch (reponseCode) {
				case ResponeCodeInf.REPONSE_SUCCESS:
					AlertDialog alertDialog = new AlertDialog.Builder(AcSenImageToServer.this).create();
					alertDialog.setTitle("Message");
					alertDialog.setMessage(getResources().getString(R.string.REPORT_SUCCESS));
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									AcSenImageToServer.this.finish();
									dialog.dismiss();
								}
					});

					// Showing Alert Message
					alertDialog.show();
					break;

				case ResponeCodeInf.REPONSE_EXCEPTION:
					AlertDialog alertDialogFaile = new AlertDialog.Builder(AcSenImageToServer.this).create();
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
