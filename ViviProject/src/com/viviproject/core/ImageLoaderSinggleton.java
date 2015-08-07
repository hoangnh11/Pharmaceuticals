package com.viviproject.core;

import android.content.Context;
import android.text.NoCopySpan.Concrete;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class ImageLoaderSinggleton {

	private static ImageLoader imageLoader;
	
	public static ImageLoader getInstance(Context context){
		if(null == imageLoader || !imageLoader.isInited()){
			if(null != imageLoader){
				imageLoader.destroy();
			}
			
			ImageLoaderConfiguration imageLoaderConfiguration = 
					new ImageLoaderConfiguration.Builder(context)
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory()
					.diskCacheFileNameGenerator(new Md5FileNameGenerator())
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.build();
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(imageLoaderConfiguration);
		}
		
		return imageLoader;
	}
}
