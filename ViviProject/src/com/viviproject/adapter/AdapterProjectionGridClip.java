package com.viviproject.adapter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.viviproject.R;

public class AdapterProjectionGridClip extends BaseAdapter {
	private static ArrayList<String> IMAGE_URLS = new ArrayList<String>();
	private LayoutInflater inflater;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;
	private Context context;
	
	public AdapterProjectionGridClip(Context context, ArrayList<String> listUrl) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		
		ImageLoaderConfiguration imageLoaderConfiguration = 
				new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(imageLoaderConfiguration);
		
		IMAGE_URLS = listUrl;
	}
	
	@Override
	public int getCount() {
		return IMAGE_URLS.size();
	}

	@Override
	public String getItem(int position) {
		if(position < 0 || null == IMAGE_URLS || getCount() == 0){
			return null;
		}
		
		return IMAGE_URLS.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public ArrayList<String> getAllList(){
		return IMAGE_URLS;
	}
	
	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_grid_clip, parent, false);
			holder = new ViewHolder();
			assert view != null;
			holder.imageView = (ImageView) view.findViewById(R.id.product_image);
			holder.progressBar = (ProgressBar) view.findViewById(R.id.product_progress);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		imageLoader.displayImage(normalnazationUrl(getItem(position)), holder.imageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				holder.progressBar.setProgress(0);
				holder.progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				holder.progressBar.setVisibility(View.GONE);
			}

			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				holder.progressBar.setVisibility(View.GONE);
			}
		}, new ImageLoadingProgressListener() {
			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
				holder.progressBar.setProgress(Math.round(100.0f * current / total));
			}
		});
		
		return view;
	}
	
	private String normalnazationUrl(String youbeURL){
		String url = "";
		String partenUrl = "%s/default.jpg";
		try {
			String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
		
		    Pattern compiledPattern = Pattern.compile(pattern);
		    Matcher matcher = compiledPattern.matcher(youbeURL);
		
		    if(matcher.find()){
		        String strID =  matcher.group();
		        url = "http://img.youtube.com/vi/" + strID + "/default.jpg";
				Log.e("thindv", "url:" + url);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
}
