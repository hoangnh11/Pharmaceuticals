/* Name: CameraView.java $
 * Version:  1.0$
 * Date: 11:15:55 PM - Aug 11, 2013 $
 * $
 * Copyright (C) 2013 FPT Software. All rights reserved.$
 */
package com.viviproject.core;

import java.io.IOException;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.viviproject.R;
import com.viviproject.ultilities.Logger;

/**
 * A basic Camera preview class
 * This class created to : 
 * <br><br><i>Copyright (C) 2013 FPT Software. All rights reserved.</i><br>
 * @author TiepTD
 * @since 11:15:55 PM - Aug 11, 2013
 * @version 1.0
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback
{
	//COMMENT INTO CLASS
	// ===========================================================
	// CONSTANTS
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private Camera mCamera;
	private final SurfaceHolder mHolder;
	private PreviewCallback previewCallback;
	private Handler autoFocusHandler;
	private int currentZoomValue = 0;
	boolean previewing;
	public boolean taking;
	private Context context;

	private int matchParentScreenWidth,matchParentScreenHeight;
	// ===========================================================
	// Constructors
	// ===========================================================
	public CameraView(Context context, AttributeSet attributes)
	{
		super(context, attributes);
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		this.context = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		if (android.os.Build.VERSION.SDK_INT < 11)
		{
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Camera getCamera()
	{
		return mCamera;
	}
	public void setCamera(Camera camera)
	{
		mCamera = camera;
	}

	public void setPreviewCallback(PreviewCallback previewCallback)
	{
		this.previewCallback = previewCallback;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		Logger.error("surfaceChanged()");
		if (mCamera == null)
		{
			Logger.error("surfaceChanged() mCamera == null");
			return;
		}
		try
		{
			refreshCamera();
		}
		catch (Exception e)
		{
			Logger.error(e);
		}
	}

	@Override
	@TargetApi(Build.VERSION_CODES.FROYO)
	public void surfaceCreated(SurfaceHolder holder)
	{
		Logger.error("surfaceCreated()");
		if (mCamera == null)
		{
			Logger.error("surfaceCreated() mCamera == null");
			return;
		}
		try
		{
			/**The Surface has been created, acquire the camera and tell it where to draw.**/
			if (android.os.Build.VERSION.SDK_INT >= 8)
			{
				mCamera.setDisplayOrientation(90);
			}
			else
			{
				Camera.Parameters parameters = mCamera.getParameters();
				parameters.set("orientation", "portrait");
				parameters.set("rotation", 90);
				mCamera.setParameters(parameters);
				
			}
			try
			{
				mCamera.setPreviewDisplay(holder);
			}
			catch (final IOException e)
			{
				mCamera.release();
				mCamera = null;
				e.printStackTrace();
			}
			
			Camera.Parameters parameters = mCamera.getParameters();
			
			  //Get the width of the screen
			if (matchParentScreenWidth == 0 || matchParentScreenHeight == 0) {
				matchParentScreenWidth = getWidth();
				matchParentScreenHeight = getHeight();
			}
		    int cameraPreviewHeight = parameters.getPreviewSize().width;
		    int cameraPreviewWidth = parameters.getPreviewSize().height;
		    float screenRatio = (float ) matchParentScreenWidth/matchParentScreenHeight;
		    float cameraRatio = (float ) cameraPreviewWidth/cameraPreviewHeight;
		    //Get the SurfaceView layout parameters
		    android.view.ViewGroup.LayoutParams lp = this.getLayoutParams();
		    
		    //Set the width of the SurfaceView to the width of the screen
		    if (cameraRatio > screenRatio){
		    	lp.width = LayoutParams.MATCH_PARENT;
		    	lp.height = (int) (matchParentScreenWidth * cameraPreviewHeight / cameraPreviewWidth );
		    	Logger.error("lp.height " + lp.height);
		    } else {
		    	lp.width = (int) (matchParentScreenHeight * cameraPreviewWidth / cameraPreviewHeight);
		    	Logger.error("lp.width " + lp.width);
		    	lp.height = LayoutParams.MATCH_PARENT;
		    }

		    //Commit the layout parameters
		    this.setLayoutParams(lp);  
		}
		catch (Exception e)
		{
			Logger.error(e);
		}
	}
	
//	/* (non-Javadoc)
//	 * @see android.view.View#onDraw(android.graphics.Canvas)
//	 */
//	@Override
//	protected void onDraw(Canvas canvas)
//	{
//		canvas.rotate(90f);
//		super.onDraw(canvas);
//	}
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility)
	{
		super.onVisibilityChanged(changedView, visibility);
		if (mCamera == null)
		{
			Logger.error("onVisibilityChanged() mCamera == null");
		}
		if (View.GONE == visibility && mCamera != null)
		{
			
			if (mCamera.getParameters() != null && mCamera.getParameters().isZoomSupported())
			{
				currentZoomValue = mCamera.getParameters().getZoom();
			}
			Logger.error("close camera");
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Logger.error("surfaceDestroyed()");
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the context is paused.
		if (mCamera == null)
		{
			Logger.error("surfaceDestroyed() mCamera == null");
			return;
		}
		if (mCamera.getParameters() != null && mCamera.getParameters().isZoomSupported())
		{
			currentZoomValue = mCamera.getParameters().getZoom();
		}
		mCamera.setPreviewCallback(null);
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * refresh camera scanner
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public void refreshCamera()
	{
		if (mCamera == null)
		{
			Logger.error("surfaceChanged() mCamera == null");
			return;
		}
		// Now that the size is known, set up the camera parameters and begin the preview.
		final Camera.Parameters parameters = mCamera.getParameters();
		mCamera.setParameters(parameters);
		try
		{
			mCamera.startPreview();
		}
		catch (Exception e)
		{
			Logger.error(e);
		}
		mCamera.setPreviewCallback(previewCallback);
		previewing = true;
		Camera.Parameters params = mCamera.getParameters();
		List<String> focusModes = params.getSupportedFocusModes();
		if (focusModes != null)
		{
			for (String str : focusModes)
			{
				Logger.error("focus mode : " + str);
			}
			if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)){
				params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				Logger.error("Set FOCUS_MODE_AUTO");
			} 
			else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE))
			{
				params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
				Logger.error("Set FOCUS_MODE_CONTINUOUS_PICTURE");
			}
			else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO))
			{
				params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				taking = false;
				autoFocusHandler = new Handler();
				try
				{
					mCamera.cancelAutoFocus();
					mCamera.autoFocus(autoFocusCB);
				}
				catch (Exception e)
				{
					Logger.error(e);
				}
				Logger.error("Set FOCUS_MODE_AUTO");
			}
			else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
			{
				params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
				Logger.error("Set FOCUS_MODE_CONTINUOUS_VIDEO");
			}
			else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_INFINITY))
			{
				params.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
				Logger.error("Set FOCUS_MODE_INFINITY");
			}
		}
		if (params.isZoomSupported())
		{
			params.setZoom(currentZoomValue);
			setOnTouchListener(new CameraZoomListener(params.getMaxZoom()));
		}

		mCamera.setParameters(params);
	}

	/**
	 * manual request focus camera
	 */
	public void requestCameraFocus()
	{
		if (previewing && !taking && mCamera != null)
		{
			if (autoFocusHandler == null)
			{
				autoFocusHandler = new Handler();
			}
			try
			{
				mCamera.cancelAutoFocus();
				mCamera.autoFocus(autoFocusCB);
			}
			catch (Exception e)
			{
				Logger.error(e);
			}
		}
	}

	/**
	 * set zoom of camera
	 * @param zoomValue
	 */
	public void setZoom(int zoomValue)
	{
		Camera.Parameters params =  mCamera.getParameters();
		params.setZoom(zoomValue);
		try
		{
			mCamera.setParameters(params);
		}
		catch (RuntimeException e)
		{
			Logger.error(e);
		}
		setVisibleZoomView(View.VISIBLE, zoomValue);
	}

	/**
	 * set zoom pinch view by zoom value
	 * @param visibility
	 * @param zoomValue
	 */
	public void setVisibleZoomView(int visibility, int zoomValue)
	{
		if (context instanceof Activity)
		{
			View viewZoomCamera = ((Activity)context).findViewById(R.id.viewZoomCamera);
			int shapeSize = calZoomValueToShapeSize(zoomValue);
			if (viewZoomCamera != null)
			{
				ViewGroup.LayoutParams layoutParams = viewZoomCamera.getLayoutParams();
				layoutParams.width = shapeSize;
				layoutParams.height = shapeSize;
				viewZoomCamera.setLayoutParams(layoutParams);
				viewZoomCamera.setVisibility(visibility);
			}
		}
	}

	/**
	 * calculate zoom pinch view by zoom value
	 * @param zoomValue
	 * @return
	 */
	private int calZoomValueToShapeSize(int zoomValue)
	{
		if (mCamera == null)
		{
			Logger.error("calZoomValueToShapeSize() mCamera == null");
			return -1;
		}

		Camera.Parameters params =  mCamera.getParameters();
		if (zoomValue < 0 || params.getMaxZoom() < zoomValue)
		{
			return -1;
		}

		final int MIN_SHAPE_SIZE = getScreenWidth() / 4;
		final int MAX_SHAPE_SIZE = Math.round(getScreenWidth() / 1.5f);
		int shapeSize = Math.round(((float)zoomValue / (float)params.getMaxZoom() * ((MAX_SHAPE_SIZE - MIN_SHAPE_SIZE))) + MIN_SHAPE_SIZE);
		return shapeSize;
	}

	/**
	 * get width of screen
	 * @return
	 */
	private int getScreenWidth()
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
	    Point size = new Point();
		display.getSize(size);
		return size.x;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	// Mimic continuous auto-focusing
	private AutoFocusCallback autoFocusCB = new AutoFocusCallback()
	{
		@Override
		public void onAutoFocus(boolean success, Camera camera)
		{
			if (!success && autoFocusHandler != null && doAutoFocus != null)
			{
				autoFocusHandler.postDelayed(doAutoFocus, 1500L);
			}
			else if (mCamera != null)
			{
				mCamera.cancelAutoFocus();
			}
		}
	};

	private Runnable doAutoFocus = new Runnable()
	{
		@Override
		public void run()
		{
			if (previewing && !taking && mCamera != null)
			{
				try
				{
					mCamera.cancelAutoFocus();
					mCamera.autoFocus(autoFocusCB);
				}
				catch (Exception e)
				{
					Logger.error(e);
				}
			}
		}
	};
}
