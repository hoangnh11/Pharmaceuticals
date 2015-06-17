package com.viviproject.core;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.viviproject.R;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.ultilities.Logger;

public class MediaUtilities {
	
	/**
	 * @param filePath
	 * @return
	 */
	public final static boolean checkAndDeleteFile(String filePath)
	{
		if (!TextUtils.isEmpty(filePath))
		{
			File delFile = new File(filePath);
			return delFile.delete();
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * add video file to video gallery
	 * @param videoFile
	 */
	public final static void addVideoToGallery(Context context, File videoFile)
	{
		ContentValues values = new ContentValues();
		values.put(MediaStore.Video.Media.TITLE, GlobalParams.APP_NAME);
		values.put(MediaStore.Video.Media.DESCRIPTION, GlobalParams.APP_NAME);
		values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
		values.put(MediaStore.Video.Media.DISPLAY_NAME, GlobalParams.APP_NAME);
		values.put(MediaStore.Video.Media.DATA, videoFile.getAbsolutePath());
		ContentResolver cr = context.getContentResolver();
		cr.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
	}

	/**
	 * add file video to video gallery
	 * 
	 * @param videoFile
	 */
	public static Uri addVideoToGallery(String videoFile, Context context) {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Video.Media.TITLE, context.getString(R.string.app_name));
		values.put(MediaStore.Video.Media.DESCRIPTION, context.getString(R.string.app_name));
		values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
		values.put(MediaStore.Video.Media.DISPLAY_NAME, context.getString(R.string.app_name));
		values.put(MediaStore.Video.Media.DATA, videoFile);
		ContentResolver cr = context.getContentResolver();
		Uri result = cr.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				values);
		return result;
	}
	
	
	public static String getMimeType(String fileUrl) {
	    String extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl);
	    String result =MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
	    Logger.error(result);
	    return result;
	}
	
	/**
	 * close FileInputStream Cause: Resource leak warning
	 * 
	 * @param fis
	 */
	public static void closeFIS(FileInputStream fis) {
		try 
		{
			if (fis != null)
			{
				fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method to check the encode of input ( video or audio)
	 * 
	 * @param mediaPath
	 * @return
	 */
	public static String getEncodeMIME(String mediaPath) {
		String mime = "";
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(mediaPath);
		String duration = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		mime = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
		Log.e("mime", "mime" + mime);
		return mime;
	}

	/**
	 * GET PICTURE AT FRAME
	 */
	public static Bitmap getPictureAtFrame(String videoPAth) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();

		File audioFile = new File(videoPAth);
		FileInputStream fs = null;
		FileDescriptor fd;
		try {
			fs = new FileInputStream(audioFile);
			fd = fs.getFD();
			retriever.setDataSource(fd);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeFIS(fs);
		}

		Bitmap bitmap = retriever.getFrameAtTime(1);
		Logger.error("bitmap.getWidth() " + bitmap.getWidth()
				+ " bitmap.getHeight() " + bitmap.getHeight());
		return bitmap;
	}
}
