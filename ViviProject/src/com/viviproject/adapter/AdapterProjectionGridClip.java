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
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.viviproject.R;
import com.viviproject.entities.EnVideos;

public class AdapterProjectionGridClip extends BaseAdapter {
	private static ArrayList<EnVideos> IMAGE_URLS = new ArrayList<EnVideos>();
	private LayoutInflater inflater;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;
	private Context context;
	
	public AdapterProjectionGridClip(Context context, ArrayList<EnVideos> listUrl) {
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
	public EnVideos getItem(int position) {
		if(position < 0 || null == IMAGE_URLS || getCount() == 0){
			return null;
		}
		
		return IMAGE_URLS.get(position);
	}

	public void setListVideo(ArrayList<EnVideos> list){
		for (int i = 0; i < IMAGE_URLS.size(); i++) {
			IMAGE_URLS.remove(i);
		}
		
		IMAGE_URLS = list;
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}

	public ArrayList<EnVideos> getAllList(){
		return IMAGE_URLS;
	}
	
	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
		TextView tvVideoName;
		
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
			holder.tvVideoName = (TextView) view.findViewById(R.id.tvVideoName);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		EnVideos item = getItem(position);
		if(null != item){
			holder.tvVideoName.setText(item.getPreview_text());
			
			imageLoader.displayImage(normalnazationUrl(item.getVideo_url()), holder.imageView, options, new SimpleImageLoadingListener() {
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
		}
		return view;
	}
	
	private String normalnazationUrl(String youbeURL){
		String url = "";
		String partenUrl = "%s/default.jpg";
		try {
			String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
		
		    Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		    Matcher matcher = compiledPattern.matcher(youbeURL);
		
		    if(matcher.find()){
		        String strID =  matcher.group(1);
		        url = "http://img.youtube.com/vi/" + strID + "/default.jpg";
				Log.e("thindv", "url:" + url);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
}
