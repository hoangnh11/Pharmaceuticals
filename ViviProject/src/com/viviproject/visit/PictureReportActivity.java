package com.viviproject.visit;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.CameraPreview;
import com.viviproject.core.ImageHelper;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

public class PictureReportActivity extends Activity implements OnClickListener{

	private CameraPreview mCameraPreview;
	private FrameLayout preview;
	private ImageView buttonCapture;
	private ImageView imgfromGallery;
	private TextView txvCancel;
	
	private boolean hasCamera = false;
	private static boolean isFrontCam = false;
	int cameraID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_layout);
	
		buttonCapture = (ImageView) findViewById(R.id.button_capture);
		buttonCapture.setClickable(true);
		buttonCapture.setOnClickListener(this);
		imgfromGallery = (ImageView) findViewById(R.id.imageViewGallery);
		txvCancel = (TextView) findViewById(R.id.ivCameraCancel);
		txvCancel.setOnClickListener(this);
		SensorManager sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        		SensorManager.SENSOR_DELAY_FASTEST);
		imgfromGallery.setOnClickListener(this);
		
    	mCameraPreview = new CameraPreview(this);
    	preview = (FrameLayout) findViewById(R.id.camera_preview);
    	ViewGroup.LayoutParams layoutParams = preview.getLayoutParams();
    	layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
    	layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
    	preview.addView(mCameraPreview, layoutParams);
	}

	 @Override
	    protected void onResume()
	    {
	    	mCameraPreview.setVisibility(View.VISIBLE);
	    	Bitmap thumbnail = ImageHelper.getTop1Thumbnail(this);
	    	if (thumbnail != null)
			{
	    		imgfromGallery.setImageBitmap(thumbnail);
			}
			else
			{
				imgfromGallery.setVisibility(View.INVISIBLE);
			}
	    	
	    	super.onResume();
	    }
	    
	    @Override
	    protected void onPause()
	    {
	    	mCameraPreview.setVisibility(View.GONE);
	    	super.onPause();
	    }
	    
	    @Override
	    public void onBackPressed()
	    {
	    	if (!findViewById(R.id.progressLayout).isShown())
	    		findViewById(R.id.camera_preview).setBackgroundResource(android.R.color.transparent);
	    	super.onBackPressed();
	    }
	    
	    public void finish()
	    {
	    	if (savingImageTask != null)
			{
				if (findViewById(R.id.progressLayout).isShown())
				{
					savingImageTask.cancel(true);
					findViewById(R.id.progressLayout).setVisibility(View.GONE);
					if (mCameraPreview.getCamera() != null)
					{
						mCameraPreview.getCamera().startPreview();
					}
					return;
				}
			}
	    	SensorManager sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
	        sensorManager.unregisterListener(sensorEventListener);
	    	super.finish();
	    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_capture:
			if(mCameraPreview.getCamera() != null){
				Logger.error("take picture");
				try
				{
					mCameraPreview.getCamera().takePicture(null, null, mPictureCallback);
				}
				catch (Exception e)
				{
					Logger.error(e);
				}
			}
			else
			{
				Logger.error("onClick camera == null");
			}
			break;
		
		case R.id.ivCameraCancel:
			findViewById(R.id.camera_preview).setBackgroundResource(android.R.color.transparent);
			finish();
			break;
			
		case R.id.imageViewGallery:
			Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			startActivityForResult(intent, GlobalParams.GALERRY_GET_IMAGE);
			
		default:
			break;		
		}
	}
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        switch (requestCode){
	        	case GlobalParams.GALERRY_GET_IMAGE:
					switch (resultCode) {
					case RESULT_OK:
						Intent recordIntent = new Intent(PictureReportActivity.this, VisitDetailsActivity.class);
						retreiveFilePathFromGallery(data, false);
						recordIntent.putExtra(GlobalParams.EXTRA_IMAGE_PATH, filePath);
						recordIntent.putExtra(GlobalParams.EXTRA_IMAGE_ORIENTATION, orientation);
//						resultIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(recordIntent);
						PictureReportActivity.this.finish();
						break;
					case RESULT_CANCELED:
						/* onRestart(); */
						/*preview.removeView(mCameraPreview);
						if (hasCamera) {
							mCamera = Camera.open(cameraID);
							mCamera.setParameters(params);
							mCameraPreview = new CameraPreview(this, mCamera);
							preview.addView(mCameraPreview);
						}*/
						break;
					default:
						break;
					}
	        		break;
	        	
	        	default:
	        		break;
	        }
	    }
	    
	    private String filePath;
		private int orientation;
		
	    /**
		 * 
		 * @param data
		 * @param isVideo
		 */
		private void retreiveFilePathFromGallery(Intent data, boolean isVideo){
			Uri selectedImage = data.getData();
			String [] filePathColumn;
			if (!isVideo)
			{
				filePathColumn = new String[2];
				filePathColumn[0] = MediaStore.Images.Media.DATA;
				filePathColumn[1] = MediaStore.Images.Media.ORIENTATION;
			}
			else
			{
				filePathColumn = new String[1];
				filePathColumn[0] = MediaStore.Images.Media.DATA;
			}
	    	Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	    	if (cursor == null)
			{
				filePath = null;
				return;
			}
	    	if (cursor.getCount() <= 0)
			{
				filePath = null;
				return;
			}
	    	cursor.moveToFirst();
	    	
	    	// get file path of image & video
	    	int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	    	filePath = cursor.getString(columnIndex);
	    	Logger.error("retreiveFilePathFromGallery : " + filePath);
	    	
	    	// get orientation of image
	    	if (!isVideo)
	    	{
	    		int columnIndex_orient = cursor.getColumnIndexOrThrow(filePathColumn[1]);
	    		orientation = cursor.getInt(columnIndex_orient);
	    		Logger.error("retreiveFilePathFromGallery orientation : " + orientation);
			}
			
	    	cursor.close();
		}
		
	    /**
	     * initial variable to capture
	     */
	    PictureCallback mPictureCallback = new PictureCallback() {
			
			public void onPictureTaken(byte[] data, Camera camera) {
				savingImageTask = new SavingImageTask(data);
//				savingImageTask.execute();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
					savingImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
				else
					savingImageTask.execute((Void[])null);
			}
			
		};
		
		private SavingImageTask savingImageTask;
		private class SavingImageTask extends AsyncTask<Void, Void, Void>
		{
			String path;
			byte[] data;
			public SavingImageTask(byte[] data)
			{
				this.data = data;
			}
			@Override
			protected void onPreExecute()
			{
				PictureReportActivity.this.findViewById(R.id.progressLayout).setVisibility(View.VISIBLE);
				SensorManager sensorManager = (SensorManager) PictureReportActivity.this.getSystemService(Context.SENSOR_SERVICE);
		        sensorManager.unregisterListener(sensorEventListener);
				super.onPreExecute();
			}
			
			@Override
			protected void onCancelled()
			{
				SensorManager sensorManager = (SensorManager) PictureReportActivity.this.getSystemService(Context.SENSOR_SERVICE);
		        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
				super.onCancelled();
			}
			
			@Override
			protected Void doInBackground(Void... params)
			{
				path = savePictureToFileSystem(data, orientationSensor);
				return null;
			}
			@Override
			protected void onPostExecute(Void result)
			{
				PictureReportActivity.this.findViewById(R.id.progressLayout).setVisibility(View.GONE);
				if (isCancelled())
				{
					return;
				}
				ImageView ivTemp = (ImageView) PictureReportActivity.this.findViewById(R.id.ivTemp);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(path, options);
				options.inJustDecodeBounds = false;
				Bitmap bitmap = BitmapFactory.decodeFile(path, options);;
				ivTemp.setImageBitmap(bitmap);
				mCameraPreview.setVisibility(View.GONE);
				ivTemp.setVisibility(View.VISIBLE);
				Logger.error("start finish()----------------------------------------------------");
				Intent intent = new Intent(PictureReportActivity.this, VisitDetailsActivity.class);
				intent.putExtra(GlobalParams.EXTRA_IMAGE_PATH, path);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
		        finish();
				super.onPostExecute(result);
			}
		}
		
		/**
		 * The method is used to save a picture to file system
		 * @param data
		 * @return path of image
		 */
		private String savePictureToFileSystem(byte[] data, int orientation) {
			if (mCameraPreview == null || data == null)
			{
				return null;
			}
			if (mCameraPreview.getCamera() == null)
			{
				return null;
			}
			Logger.error("data.length " + data.length ) ;
	        File file = getOutputMediaFile();
	        Logger.error("quality: " + mCameraPreview.getCamera().getParameters().getJpegQuality());
	        isFrontCam = mCameraPreview.isFrontCam();

	        String path = ImageHelper.saveToFile
	        		(data, file, isFrontCam, mCameraPreview.getCamera().getParameters().getJpegQuality(), orientation,
	        				PictureReportActivity.this, 480, 480);	        
//	    	addImageGallery(file);
			
	        return path;
		}
		
		private static File getOutputMediaFile() {
			File mediaStorageDir = new File(GlobalParams.FOLDER_DATA);

			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Logger.error("failed to create directory: Audiophoto");
					return null;
				}
			}

			File mediaFile;
			mediaFile = new File(mediaStorageDir.getAbsolutePath() + File.separator
					+ "IMG_" + "TEMP" + ".jpg");
			if (mediaFile.exists()) {
				mediaFile.delete();
				try {
					mediaFile.createNewFile();
				} catch (IOException e) {
					Logger.error(e);
				}
			}
			return mediaFile;
		}

		private int orientationSensor = -1;
		private SensorEventListener sensorEventListener = new SensorEventListener()
		{

			@Override
			public void onSensorChanged(SensorEvent event)
			{
				if ( (event.values[0] > 4 || event.values[0] < -4) && (event.values[1] < 3 && event.values[1] > -3))
				{
					if (event.values[0] > 6 && orientationSensor != ImageHelper.ORIENTATION_LANDSCAPE_LEFT)
					{
						Logger.debug("sensor: landscape left " + event.values[0] + "; " + event.values[1]);
						buttonCapture.setImageResource(R.drawable.bg_bt_capture_portrait);
						orientationSensor = ImageHelper.ORIENTATION_LANDSCAPE_LEFT;
					}
					if (event.values[0] < -6 && orientationSensor != ImageHelper.ORIENTATION_LANDSCAPE_RIGHT)
					{
						Logger.debug("sensor: landscape right " + event.values[0] + "; " + event.values[1]);
						buttonCapture.setImageResource(R.drawable.bg_bt_capture_portrait);
						orientationSensor = ImageHelper.ORIENTATION_LANDSCAPE_RIGHT;
					}
				}
				if ((event.values[0] < 2 && event.values[0] > -2) && (event.values[1] > 4 || event.values[1] < -4))
				{
					if (orientationSensor != ImageHelper.ORIENTATION_PORTRAIT)
					{
						Logger.debug("sensor: Portrait: " + event.values[0] + "; " + event.values[1]);
						buttonCapture.setImageResource(R.drawable.bg_bt_capture_portrait);
					}
					orientationSensor = ImageHelper.ORIENTATION_PORTRAIT;
				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{
			}
		};
}
