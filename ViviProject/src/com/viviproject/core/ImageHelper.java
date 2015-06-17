package com.viviproject.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.text.TextUtils;
import android.view.Display;

import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

/**
 * Used to make file system use in the tutorial a bit more obvious
 * in a production environment you wouldn't make these calls static
 * as you have no way to mock them for testing
 *
 */
public class ImageHelper {
	public static final int ORIENTATION_PORTRAIT = 1;
	public static final int ORIENTATION_LANDSCAPE_LEFT = 2;
	public static final int ORIENTATION_LANDSCAPE_RIGHT = 3;
	public static final int ORIENTATION_PORTRAIT_REVERSE = 4;
	public static String saveToFile(byte[] bytes, File file, boolean isFrontcam, int quality, int orientation, Context activity, int width, int height){
		String imgPath = null;
		try {
			FileOutputStream fos = new FileOutputStream(file);
//			fos.write(bytes);

	        ExifInterface exif=new ExifInterface(file.toString());
	        exif.setAttribute(ExifInterface.TAG_IMAGE_LENGTH, "" + height);
	        exif.setAttribute(ExifInterface.TAG_IMAGE_WIDTH, "" + width);

	        BitmapFactory.Options options = new BitmapFactory.Options();
	     /* Remove this code to save a max size image 
	      *  options.inJustDecodeBounds = true;
	        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
	        options.inJustDecodeBounds = false;
	        int scale;
	        if (options.outHeight > options.outWidth)
			{
				scale = Math.round(options.outHeight/1000);
			}
	        else
	        {
	        	scale = Math.round(options.outWidth/1000);
	        }
	        options.inSampleSize = scale;
	        Logger.error("scale=" + scale + " ; size=" + options.outHeight + "x" + options.outWidth);*/
	        Bitmap realImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
	        Logger.error("size new =" + options.outHeight + "x" + options.outWidth);
//	        Bitmap realImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	        Bitmap tmp;
	        Logger.error("EXIF value " + exif.getAttribute(ExifInterface.TAG_ORIENTATION));
	        if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")){
	        	tmp= rotate(realImage, 90,isFrontcam);
	        } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")){
	        	tmp= rotate(realImage, 270,isFrontcam);
	        } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")){
	        	tmp= rotate(realImage, 180,isFrontcam);
	        } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")){
	        	tmp= rotate(realImage, 90,isFrontcam);
	        } else {
	        	tmp = realImage;
	        }
			switch (orientation)
			{
				case ORIENTATION_LANDSCAPE_LEFT:
					tmp = rotate(tmp, -90, isFrontcam);
					break;

				case ORIENTATION_LANDSCAPE_RIGHT:
					tmp = rotate(tmp, 90, isFrontcam);
					break;

				case ORIENTATION_PORTRAIT_REVERSE:
					tmp = rotate(tmp, 180, isFrontcam);
					break;

				default:
					break;
			}
	        realImage.recycle();
	        realImage = null;
//	        rotate(realImage, 180);
//	        boolean bo = tmp.compress(Bitmap.CompressFormat.JPEG, quality, fos);
	        String uriPathContent = insertImage(activity.getContentResolver(), tmp, "Audio Photo", "Audio Photo");
	        tmp.recycle();
	        tmp = null;
			fos.close();
			//imgPath may be null if reset factory on Nexus 4 with android OS 4.3
			if (uriPathContent != null)
			{
				imgPath = getPathImgFromContentUri(activity, Uri.parse(uriPathContent));
			}
			else
			{
				imgPath = null;
			}
			
			/**Update image to gallery**/
			/*MediaStore.Images.Media.insertImage(null,
					 file.getAbsolutePath(), null, null);*/
		} catch (FileNotFoundException e) {
			Logger.error(e);
		} catch (Exception e) {
			Logger.error(e);
		}
		return imgPath;
	}
	public static Bitmap rotate(Bitmap bitmap, int degree, boolean isFrontCam) {
	    int w = bitmap.getWidth();
	    int h = bitmap.getHeight();

	    Matrix mtx = new Matrix();
	   //       mtx.postRotate(degree);
	    mtx.setRotate(degree);
	    if (isFrontCam){
		mtx.preRotate(-180);
	    }
	    return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}
	 /**
     * convert bitmap to the right ratation of original image
     * 
     * @param photo
     * @param rotation
     * @return
     */
    public static Bitmap convertBitmapToCorrectOrientation(Bitmap photo,
        int rotation)
    {
        int width = photo.getWidth();
        int height = photo.getHeight();
        Matrix matrix = new Matrix();
        matrix.preRotate(rotation);

        return Bitmap.createBitmap(photo, 0, 0, width, height, matrix,
            false);
    }
    
	public static Bitmap decodeBitmapFromUri(String urlImg){
		Bitmap bitmap = null;
        int rotation = 0;
        try
        {	
        	bitmap = BitmapFactory.decodeFile(urlImg);
        	rotation = getRotateOfImage(urlImg);
            bitmap = convertBitmapToCorrectOrientation(bitmap, rotation);
            return bitmap;
        }
        catch (Exception e)
        {
        	Logger.error("MediaHelper.decodeBitmapFromUri()");
			Logger.error(e);
        	return null;
        }
        catch (OutOfMemoryError e)
        {
        	Logger.error("MediaHelper.decodeBitmapFromUri()");
			Logger.error(e);
        	return null;
        }
	}
	
	/**
	 * this method is used to get matrix of Images and rotate image.
	 * @param imgUrl
	 * @return image matrix
	 */
	public static int getRotateOfImage(String imgUrl){
		int matrix = 0;
		try {
			ExifInterface exifReader = new ExifInterface(imgUrl);
			int orientation = exifReader.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation == 0) {
				matrix = 90;
			}else{
				matrix = 0;
			}
		} catch (IOException e) {
			Logger.error("MediaHelper.getRotateOfImage()");
			Logger.error(e);
		}
		return matrix;
	}

	/**
	 * check size of screen to get bitmap with max size = screen resolution
	 * @param activity
	 * @param filePath
	 * @return
	 */
	public static Bitmap getBitmapFromFilePath(Activity activity, String filePath)
	{
		if (TextUtils.isEmpty(filePath) || activity == null)
		{
			return null;
		}
		Bitmap result;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		Logger.debug("============outHeight=" + options.outHeight);
        options.inJustDecodeBounds = false;
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);
        int maxSize = (int) (outSize.x > outSize.y ? outSize.x : outSize.y);
        result = BitmapFactory.decodeFile(filePath, options);
        // #378 - Repli crashes when selecting photos from Picasa on GS3:
        // cannot reproduce, this is bug on end user device of google play store,
        // currently, we will check width or height of bitmap, if width or height of bitmap <= 0 we return current bitmap
        if (options.outWidth > 0 && options.outHeight > 0)
		{
        	int maxWidth = options.outHeight > options.outWidth ? (maxSize * options.outWidth / options.outHeight) : maxSize;
            int maxHeight = options.outHeight > options.outWidth ? maxSize : (maxSize * options.outHeight / options.outWidth);
            result = scaleBitmapMaintainAspect(result, maxWidth, maxHeight, true);
            Logger.debug("============result=" + result.getHeight());
		}
        else
        {
        	Logger.error("===cannot get image===result width=" + options.outWidth + " and height=" + options.outHeight);
        }
		return result;
	}

	/**
	 * get top 1 thumbnail order by date
	 * @param activity
	 * @return
	 */
	public static Bitmap getTop1Thumbnail(Activity activity)
	{
		Logger.error("getTop1Thumbnail");
		String realPath = null;
		String[] projection = new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA,
				MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN,
				MediaStore.Images.ImageColumns.MIME_TYPE };
		Cursor cursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null,
				MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
		String id = null;
		if (cursor != null)
		{
			cursor.moveToFirst();
			// you will find the last taken picture here
			// according to Bojan Radivojevic Bomber comment do not close the cursor (he is right ^^)
			//cursor.close();
			Logger.error("cursor.getCount() = " + cursor.getCount());
			if (cursor.getCount() > 0)
			{
				String[] columnName = cursor.getColumnNames();
				id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
				Logger.error("id " + id);
				realPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
				Logger.error("path " + realPath);
				Logger.error("date taken " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN)));
				Logger.error("bucket display name " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)));
	//			Logger.error(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED)));
				for (String string : columnName)
				{
					Logger.error(string);
				}
			}
			cursor.close();
		}
		
		Bitmap thumbnail = null;
		try 
		{
			thumbnail = MediaStore.Images.Thumbnails.getThumbnail(activity.getContentResolver(), Long.parseLong(id), MediaStore.Images.Thumbnails.MICRO_KIND, null);
		} catch (Exception e) {
			Logger.error("get thumbnail ex : " + e.getMessage());
		}
		
		if (thumbnail == null)
		{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(realPath, options);
			options.inJustDecodeBounds = false;
			int scale;
	        if (options.outHeight > options.outWidth)
			{
				scale = Math.round(options.outHeight/100);
			}
	        else
	        {
	        	scale = Math.round(options.outWidth/100);
	        }
	        options.inSampleSize = scale;
			thumbnail = BitmapFactory.decodeFile(realPath, options);
		}
		else
		{
			Logger.error("size:" + thumbnail.getWidth() + "x" + thumbnail.getHeight());
		}
		return thumbnail;
	}
	
	/**
     * Insert an image and create a thumbnail for it.
     *
     * @param cr The content resolver to use
     * @param source The stream to use for the image
     * @param title The name of the image
     * @param description The description of the image
     * @return The URL to the newly created image, or <code>null</code> if the image failed to be stored
     *              for any reason.
     */
	public static final String insertImage(ContentResolver cr, Bitmap source, String title, String description)
	{
		long time = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.set(1970, 0, 1);
		ContentValues values = new ContentValues();
		values.put(Images.Media.TITLE, title);
		values.put(Images.Media.DESCRIPTION, description);
		values.put(Images.Media.DATE_TAKEN, time);
		values.put(Images.Media.DATE_ADDED, time);
//		values.put(Images.Media.DATE_MODIFIED, System.currentTimeMillis() - time);
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(Images.Media.BUCKET_DISPLAY_NAME, "DCIM");
		values.put(Images.Media.DISPLAY_NAME, "AudioPhoto_" + time);

		Uri url = null;
		String stringUrl = null; /* value to be returned */

		try
		{
			url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

			if (source != null)
			{
				OutputStream imageOut = cr.openOutputStream(url);
				try
				{
					source.compress(Bitmap.CompressFormat.JPEG, 100, imageOut);
				}
				finally
				{
					imageOut.close();
				}

				long id = ContentUris.parseId(url);
				// Wait until MINI_KIND thumbnail is generated.
				Bitmap miniThumb = Images.Thumbnails.getThumbnail(cr, id, Images.Thumbnails.MINI_KIND, null);
				// This is for backward compatibility.
				Bitmap microThumb = storeThumbnail(cr, miniThumb, id, 50F, 50F, Images.Thumbnails.MICRO_KIND);
			}
			else
			{
				Logger.error("Failed to create thumbnail, removing original");
				cr.delete(url, null, null);
				url = null;
			}
		}
		catch (Exception e)
		{
			Logger.error("MediaHelper.insertImage()");
			Logger.error(e);
			if (url != null)
			{
				cr.delete(url, null, null);
				url = null;
			}
		}

		if (url != null)
		{
			stringUrl = url.toString();
		}

		return stringUrl;
	}
	
	private static final Bitmap storeThumbnail(ContentResolver cr, Bitmap source, long id, float width, float height,
			int kind)
	{
		// create the matrix to scale it
		Matrix matrix = new Matrix();

		float scaleX = width / source.getWidth();
		float scaleY = height / source.getHeight();

		matrix.setScale(scaleX, scaleY);

		Bitmap thumb = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

		ContentValues values = new ContentValues(4);
		values.put(Images.Thumbnails.KIND, kind);
		values.put(Images.Thumbnails.IMAGE_ID, (int) id);
		values.put(Images.Thumbnails.HEIGHT, thumb.getHeight());
		values.put(Images.Thumbnails.WIDTH, thumb.getWidth());

		Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

		try
		{
			OutputStream thumbOut = cr.openOutputStream(url);

			thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
			thumbOut.close();
			return thumb;
		}
		catch (FileNotFoundException ex)
		{
			return null;
		}
		catch (IOException ex)
		{
			return null;
		}
	}
	
	private static String getPathImgFromContentUri(Context activity, Uri contentUri)
	{
		if (activity == null || contentUri == null)
		{
			return null;
		}
		String[] projection = new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA,
				MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN,
				MediaStore.Images.ImageColumns.MIME_TYPE };
		Cursor cursor = activity.getContentResolver().query(contentUri, projection, null, null,
				MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
		if (cursor == null)
		{
			Logger.error("getPathImgFromContentUri: cursor == null");
			return null;
		}
		if (cursor.getCount() == 0)
		{
			Logger.error("getPathImgFromContentUri: cursor.getCount() == 0");
			return null;
		}
		cursor.moveToFirst();
		String realPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
		Logger.error("path " + realPath);
		cursor.close();
		return realPath;
	}
	
	/**
	 * Given a media filename, returns it's id in the media content provider
	 *
	 * @param providerUri
	 * @param appContext
	 * @param fileName
	 * @return
	 */
	public long getMediaItemIdFromProvider(Uri providerUri, Context appContext, String fileName) {
	    //find id of the media provider item based on filename
	    String[] projection = { MediaColumns._ID, MediaColumns.DATA };
	    Cursor cursor = appContext.getContentResolver().query(
	            providerUri, projection,
	            MediaColumns.DATA + "=?", new String[] { fileName },
	            null);
	    if (null == cursor) {
	        Logger.error("Null cursor for file " + fileName);
	        return -1;
	    }
	    long id = -1;
	    if (cursor.getCount() > 0) {
	        cursor.moveToFirst();
	        id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
	    }
	    cursor.close();
	    return id;
	}
	/**
	 * 
	 * @param bmpOriginal
	 * @return
	 */
	public Bitmap toGrayscale(Bitmap bmpOriginal)
	{        
	    int width, height;
	    height = bmpOriginal.getHeight();
	    width = bmpOriginal.getWidth();    

	    Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	    Canvas c = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0);
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	    paint.setColorFilter(f);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmpGrayscale;
	}

	/**
	 * add image file to image gallery
	 * @param videoFile
	 */
	public final static void addImageToRepliGallery(Context context, File imageFile)
	{
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, GlobalParams.APP_NAME);
		values.put(MediaStore.Images.Media.DESCRIPTION, GlobalParams.APP_NAME);
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		values.put(MediaStore.Images.Media.DISPLAY_NAME, GlobalParams.APP_NAME);
		values.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
		ContentResolver cr = context.getContentResolver();
		cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}
	/**
	 * Compress bitmap with percent compress
	 */
	public static void compressBitmap(Bitmap bitmap, int percentCompress) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, percentCompress, out);
		bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(
				out.toByteArray()));
		out.close();
	}

	/**
	 * scale, rotate and save to cache
	 * @param context
	 * @param srcImagePath
	 * @param maxResolution
	 * @param newFileName
	 * @return
	 * @throws IOException
	 */
	public static String createTempScaleImage(Context context, String srcImagePath, final int maxResolution, String newFileName) throws IOException
	{
		if (TextUtils.isEmpty(srcImagePath) || maxResolution <= 0)
		{
			return srcImagePath;
		}

		int dstWidth, dstHeight;
		Bitmap srcBitmap, dstBitmap;
		FileOutputStream fos;
		File srcImage, dstImage;
		ExifInterface srcExif;
		srcExif = new ExifInterface(srcImagePath);
		srcImage = new File(srcImagePath);
		if (!srcImage.exists() || srcImage.isDirectory())
		{
			return srcImagePath;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(srcImagePath, options);
		if (options.outHeight < maxResolution && options.outWidth < maxResolution)
		{
			return srcImagePath;
		}

		options.inJustDecodeBounds = false;
		srcBitmap = BitmapFactory.decodeFile(srcImagePath, options);

		if (srcBitmap == null)
		{
			return srcImagePath;
		}

		if (TextUtils.isEmpty(newFileName))
		{
			newFileName = GlobalParams.TEMP_SCALE_IMG_FOR_SENDING_NAME;
		}

		dstImage = new File(context.getCacheDir(), newFileName);
		if (dstImage.exists())
		{
			if (dstImage.delete())
			{
				dstImage.createNewFile();
			}
		}
		else
		{
			dstImage.createNewFile();
		}

		Logger.debug("original image size=" + srcBitmap.getWidth() + "x" + srcBitmap.getHeight());
		if (srcExif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6"))
		{
			srcBitmap = rotate(srcBitmap, 90, false);
		}
		else if (srcExif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8"))
		{
			srcBitmap = rotate(srcBitmap, 270, false);
		}
		else if (srcExif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3"))
		{
			srcBitmap = rotate(srcBitmap, 180, false);
		}
		else if (srcExif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("2"))
		{
			srcBitmap = rotate(srcBitmap, 180, true);
		}
		else if (srcExif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("4"))
		{
			srcBitmap = rotate(srcBitmap, 0, true);
		}
		else if (srcExif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("5"))
		{
			srcBitmap = rotate(srcBitmap, 90, true);
		}
		else if (srcExif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("7"))
		{
			srcBitmap = rotate(srcBitmap, 270, true);
		}
		else
		{
			//do nothing
		}

		if (srcBitmap.getWidth() <= maxResolution && srcBitmap.getHeight() <= maxResolution)
		{
			dstWidth = srcBitmap.getWidth();
			dstHeight = srcBitmap.getHeight();
		}
		else if (srcBitmap.getWidth() > srcBitmap.getHeight())
		{
			// set new width = maxResolution
			dstWidth = maxResolution;
			dstHeight = (int)((double)srcBitmap.getHeight() * ((double)maxResolution / (double)srcBitmap.getWidth()));
		}
		else
		{
			// set new height = maxResolution
			dstWidth = (int)((double)srcBitmap.getWidth() * ((double)maxResolution / (double)srcBitmap.getHeight()));
			dstHeight = maxResolution;
		}

		Logger.debug("will scale image with new size=" + dstWidth + "x" + dstHeight);
		fos = new FileOutputStream(dstImage);
		dstBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, true);
		dstBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
		Logger.debug("scaled image before sending with new size=" + dstBitmap.getWidth() + "x" + dstBitmap.getHeight());
		return dstImage.getAbsolutePath();
	}

	/**
	 * scale bitmap by max resolution
	 * @param srcBitmap
	 * @param maxResolution
	 * @return
	 */
	public static Bitmap createScaleBitmap(Bitmap srcBitmap, final int maxResolution)
	{
		if (srcBitmap == null)
		{
			return srcBitmap;
		}
		if (srcBitmap.getHeight() <= maxResolution && srcBitmap.getWidth() <= maxResolution)
		{
			return srcBitmap;
		}

		int dstWidth, dstHeight;
		if (srcBitmap.getWidth() > srcBitmap.getHeight())
		{
			// set new width = maxResolution
			dstWidth = maxResolution;
			dstHeight = (int)((double)srcBitmap.getHeight() * ((double)maxResolution / (double)srcBitmap.getWidth()));
		}
		else
		{
			// set new height = maxResolution
			dstWidth = (int)((double)srcBitmap.getWidth() * ((double)maxResolution / (double)srcBitmap.getHeight()));
			dstHeight = maxResolution;
		}
		Bitmap dstBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, true);
		return dstBitmap;
	}

	/**
	 * delete cache imageFrame file
	 */
	public static void deleteCacheImage(File imageFileFrame)
	{
		if (imageFileFrame != null && imageFileFrame.exists())
		{
			imageFileFrame.delete();
			imageFileFrame = null;
		}
	}
	
	
	/**
	 * Scales a bitmap and maintains it's aspect ratio. Adds any necessary letterboxing
	 * 
	 * @param bmp		Original Bitmap
	 * @param width		Desired Width
	 * @param height	Desired Height
	 */
	public static Bitmap scaleBitmapMaintainAspect(Bitmap bmp, int width, int height, boolean filter)
	{
		RectF originalSize = new RectF(0, 0, bmp.getWidth(), bmp.getHeight());
		RectF newSize = new RectF(0, 0, width, height);
		
		// Set up a scaling matrix which will scale the original image to fit inside the new rectangle
		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setRectToRect(originalSize, newSize, Matrix.ScaleToFit.CENTER);
		
		// First, scale the bitmap to the correct size (make sure it fits into width x height
		Bitmap scaledBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), scaleMatrix, filter);
					
		// Next, create a new image using the correct height and width. This will be our black-background
		Bitmap scaledLetterBoxedBitmap = Bitmap.createBitmap(width, height, bmp.getConfig());

		// Draw the scaled image onto the empty canvas (which sends the pixels to the bitmap)
		Canvas canvas = new Canvas(scaledLetterBoxedBitmap);
		canvas.drawColor(Color.BLACK);
		
		// Determine how to best center the image
		int sideBorder = width - scaledBitmap.getWidth();
		int topBorder = height - scaledBitmap.getHeight();
		int left = sideBorder/2; // (integer math rounds down)
		int top = topBorder/2; // (integer math rounds down)
		
		// Draw the bitmap onto the black canvas (which is accessing "scaledLetterBoxedBitmap")
		canvas.drawBitmap(scaledBitmap, left, top, null);

		return scaledLetterBoxedBitmap;
	}
	
	/**
	 * 
	 * @param filepath
	 * @param width
	 * @param height
	 * @param filter
	 * @return
	 */
	public static Bitmap scaleBitmapMaintainAspect(String filepath, int width, int height, boolean filter){
		return scaleBitmapMaintainAspect(BitmapFactory.decodeFile(filepath), width, height, filter);
	}
	
	/**
	 * add color border around the rect
	 * @param bmp
	 * @param borderSize
	 * @return
	 */
	public static Bitmap addBorder(Bitmap bmp, int borderSize, int color) {
		// bmp may be null
		if (bmp == null)
		{
			return null;
		}
	    Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
	    Canvas canvas = new Canvas(bmpWithBorder);
	    canvas.drawColor(color);
	    canvas.drawBitmap(bmp, borderSize, borderSize, null);
	    return bmpWithBorder;
	}
	
	public static Bitmap createThumbnailImage(String filepath, int width, int height){
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(filepath), GlobalParams.THUMBNAIL_SIZE, GlobalParams.THUMBNAIL_SIZE);
		return bitmap;
	}
}
