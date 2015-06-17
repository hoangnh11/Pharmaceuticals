/*
 * Name: CameraViewTouchListener.java $
 * Version:  1.0 $
 * Date: 2:37:52 PM - Mar 12, 2014 $
 * $
 * Copyright (C) 2014 FPT Software. All rights reserved.$
 */
package com.viviproject.core;

import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.viviproject.ultilities.Logger;

/**
 * This class created to :
 * <br><br><i>Copyright (C) 2014 FPT Software. All rights reserved.</i><br>
 * @author TiepTD
 * @since 2:37:52 PM - Mar 12, 2014
 * @version 1.0
 */
public class CameraZoomListener implements OnTouchListener
{
	// ===========================================================
	// CONSTANTS
	// ===========================================================
	private final int MODE_ZOOM = 1;
	private final float MINIMUM_SPACE_OF_2_FINGER = 10f;
	private final float CHANGING_ZOOM_SPACE = 10f;

	// ===========================================================
	// Fields
	// ===========================================================
	private CameraView m_cameraView;
	private int m_mode = 0;
	private double oldDist, newDist;
	private final int MAX_ZOOM;
	private int oldZoomValue;

	// ===========================================================
	// Constructors
	// ===========================================================
	/**
	 * Constructors created to:
	 */
	public CameraZoomListener(final int maxZoom)
	{
		this.MAX_ZOOM = maxZoom;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	/* (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (v instanceof CameraView)
		{
			m_cameraView = (CameraView) v;
		}
		else
		{
			return false;
		}
		boolean isHandleInThisMethod = false;
//		Logger.debug("motion event" + event.getPointerCount());
		switch (event.getActionMasked())
		{
			case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = spacing(event);
//				Logger.infor("oldDist=" + oldDist);
				if (oldDist > MINIMUM_SPACE_OF_2_FINGER)
				{
					m_mode = MODE_ZOOM;
					Logger.error("mode=ZOOM");
					if (m_cameraView.getCamera() != null && m_cameraView.getCamera().getParameters() != null)
					{
						Camera.Parameters params =  m_cameraView.getCamera().getParameters();
						oldZoomValue = params.getZoom();
					}
					isHandleInThisMethod = true;
					m_cameraView.setVisibleZoomView(View.VISIBLE, oldZoomValue);
				}
				else
				{
					m_cameraView.setVisibleZoomView(View.GONE, oldZoomValue);
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if (m_mode == MODE_ZOOM)
				{
					newDist = spacing(event);
//					Logger.infor("newDist=" + newDist);
					if (newDist > MINIMUM_SPACE_OF_2_FINGER)
					{
						if (m_cameraView.getCamera() != null && m_cameraView.getCamera().getParameters() != null)
						{
							/*Camera.Parameters params =  m_cameraView.getCamera().getParameters();*/
							int zoomValue = calculateZoom(oldZoomValue, oldDist, newDist, MAX_ZOOM, CHANGING_ZOOM_SPACE);
							Logger.error("zoomValue=" + zoomValue);
							/*params.setZoom(zoomValue);*/
							m_cameraView.setZoom(zoomValue);
							// fixed bug: force close when stress test zoom
							// java.lang.RuntimeException: setParameters failed
							/*try
							{
								m_cameraView.getCamera().setParameters(params);
							}
							catch (RuntimeException e)
							{
								Logger.exceptionError(e);
							}*/
						}
						isHandleInThisMethod = true;
					}
				}
				break;

			default:
				m_mode = 0;
				m_cameraView.setVisibleZoomView(View.GONE, oldZoomValue);
				break;
		}
		return isHandleInThisMethod;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * calculate space between 2 fingers
	 * @param event
	 * @return
	 */
	private double spacing(MotionEvent event)
	{
		if (event.getPointerCount() > 1)
		{
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return Math.sqrt((double) (x * x + y * y));
		}
		else
		{
			return 0;
		}
	}

	/**
	 * calculate zoom
	 * @param beginZoomValue
	 * @param beginDist
	 * @param newDist
	 * @param MAX_ZOOM
	 * @param changingZoomDist
	 * @return
	 */
	private int calculateZoom(final int beginZoomValue, final double beginDist, final double newDist, final int MAX_ZOOM, final float changingZoomDist)
	{
		long zoomValue = Math.round((newDist - beginDist) / changingZoomDist) + beginZoomValue;
		if (zoomValue < 0)
		{
			zoomValue = 0;
		}
		else if (zoomValue > MAX_ZOOM)
		{
			zoomValue = MAX_ZOOM;
		}
		return (int) zoomValue;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
