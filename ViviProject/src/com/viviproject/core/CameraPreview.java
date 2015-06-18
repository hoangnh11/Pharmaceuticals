package com.viviproject.core;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.viviproject.ultilities.Logger;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private int cameraID;
    private boolean isFrontCam;
    private PreviewCallback previewCallback;
    private Activity activity;
    
	public CameraPreview(Context context) {
		super(context);
		activity = (Activity) context;
        this.mSurfaceHolder = this.getHolder();
        this.mSurfaceHolder.addCallback(this);
        this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	/**
	 * @return
	 */
	public Camera getCamera()
	{
		return mCamera;
	}
	
	/**
	 * @return the isFrontCam
	 */
	public final boolean isFrontCam()
	{
		return isFrontCam;
	}

	/**
	 * @param previewCallback
	 */
	public void setPreviewCallback(PreviewCallback previewCallback)
	{
		if (previewCallback != null)
		{
			this.previewCallback = previewCallback;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Logger.error("surfaceChanged()");
		if (mCamera == null)
		{
			Logger.error("surfaceChanged: mCamera == null");
			return;
		}
		// Now that the size is known, set up the camera parameters and begin the preview.
		final Camera.Parameters parameters = mCamera.getParameters();
		//fixed bug java.lang.RuntimeException: startPreview failed
		// on Samsung s2
		mCamera.setParameters(parameters);
		try
		{
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		}
		catch (Exception e)
		{
			Logger.error(e);
//			try
//			{
//				mCamera.stopPreview();
//				mCamera.setPreviewDisplay(holder);
//				mCamera.startPreview();
//			}
//			catch (Exception e2)
//			{
//				Logger.exceptionError(e2);
//			}
		}
		mCamera.setPreviewCallback(previewCallback);
		// mCamera.autoFocus(autoFocusCallback);
		Camera.Parameters params = mCamera.getParameters();
		List<String> focusModes = params.getSupportedFocusModes();
		for (String str : focusModes)
		{
			Logger.error("focus mode : " + str);
		}
		if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE))
		{
			params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
			Logger.error("Set FOCUS_MODE_CONTINUOUS_PICTURE");
		}
		else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO))
		{
			params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
			Logger.error("Set FOCUS_MODE_AUTO");
		}
		else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_INFINITY))
		{
			params.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
			Logger.error("Set FOCUS_MODE_INFINITY");
		}

		mCamera.setParameters(params);
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		boolean hasCamera = checkCameraHardware(activity);
		if (hasCamera)
		{
			int backCameraID = findBackFacingCamera();
			// If No backing camera, use the Front
			if (backCameraID == -1)
			{
				// Because has only one front
				try
				{
					mCamera = Camera.open(0);
					isFrontCam = true;
					// Set for the onActivityResult use
					cameraID = 0;
				}
				catch (Exception e)
				{
					Logger.error(e);
				}
			}
			else
			{
				try
				{
					mCamera = Camera.open(backCameraID);
					cameraID = backCameraID;
					isFrontCam = false;
				}
				catch (Exception e)
				{
					Logger.error(e);
				}
			}
			setCameraDisplayOrientation(activity, cameraID, mCamera);
			onOrientationChanged(90, cameraID, mCamera);
			/**
			 *  Set the camera size : need to do 2 things: Check all support size has width or height < 1000
			 *  Arrange the list, and get the largerst size  ( to avoid take the too small size)
			 */
			setupFocusMode();
			setCameraDisplayOrientation(activity, cameraID, mCamera);
			onOrientationChanged(90, cameraID, mCamera);
		}
		else
		{
			Toast.makeText(activity, "This device has no camera", Toast.LENGTH_LONG).show();
		}
		if (mCamera == null)
		{
			Logger.error("surfaceCreated(): mCamera == null");
			Toast.makeText(activity, "This device has no camera", Toast.LENGTH_LONG).show();
			return;
		}
		try
		{
			mCamera.setPreviewDisplay(mSurfaceHolder);
		}
		catch (final IOException e)
		{
			mCamera.release();
			mCamera = null;
			Logger.error(e);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Logger.error("surfaceDestroyed()");
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		if (mCamera == null)
		{
			Logger.error("surfaceDestroyed(): mCamera == null");
			return;
		}
		mCamera.setPreviewCallback(null);
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
		
		Logger.error("surfaceDestroyed()-----------------------------------------------");
	}
	
	/**
	 * Helper method to access the camera returns null if it cannot get the
	 * camera or does not exist
	 * 
	 * @return
	 */
	private boolean checkCameraHardware(Context context)
	{
		if (context == null)
		{
			Logger.error("checkCameraHardware:  context == null");
			return false;
		}
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)
				|| context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT))
		{
			// this device has a camera
			return true;
		}
		else
		{
			// no camera on this device
			return false;
		}
	}
	
	private int findBackFacingCamera()
	{
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++)
		{
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_BACK)
			{
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}
	
	/**
     * Setup the focus mode
     */
	private void setupFocusMode()
	{
		if (mCamera == null)
		{
			Logger.error("setupFocusMode:  camera == null");
			return;
		}
		// Get the list support size
		Camera.Parameters params = mCamera.getParameters();

		// maybe null
		List<String> focusModes = params.getSupportedFocusModes();
		if (focusModes != null)
		{
			for (String str : focusModes)
			{
				Logger.error("focus mode : " + str);
			}
			if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE))
			{
				params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
				Logger.error("Set FOCUS_MODE_CONTINUOUS_PICTURE");
			}
			else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO))
			{
				params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				Logger.error("Set FOCUS_MODE_AUTO");
			}
			else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_INFINITY))
			{
				params.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
				Logger.error("Set FOCUS_MODE_INFINITY");
			}
			
			mCamera.setParameters(params);
		}

		
	}
	
	private void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera)
	{
		if (activity == null || camera == null)
		{
			Logger.error("setCameraDisplayOrientation:  activity == null || camera == null");
			return;
		}
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation)
		{
			case Surface.ROTATION_0:
				degrees = 0;
				break;
			case Surface.ROTATION_90:
				degrees = 90;
				break;
			case Surface.ROTATION_180:
				degrees = 180;
				break;
			case Surface.ROTATION_270:
				degrees = 270;
				break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
		{
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		}
		else
		{  // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}
	
	private void onOrientationChanged(int orientation, int cameraId, android.hardware.Camera camera)
	{
		if (camera == null)
		{
			Logger.error("onOrientationChanged: camera == null");
			return;
		}
		if (orientation != 0 && orientation != 90 && orientation != 180 && orientation != 270)
			return;
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		orientation = (orientation + 45) / 90 * 90;
		int rotation = 0;
		if (info.facing == CameraInfo.CAMERA_FACING_FRONT)
		{
			rotation = (info.orientation - orientation + 360) % 360;
		}
		else
		{  // back-facing camera
			rotation = (info.orientation + orientation) % 360;
		}
		camera.getParameters().setRotation(rotation);
	}
}
