package com.viviproject.projection;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
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


public class FrgProducts extends FrgBaseFragmentProducts implements OnItemClickListener{
	private GridView productsGrid;
	
	private ArrayList<String> listUrlPicture;
	private ImageAdapter imageAdapter;
	private View mView;
	private Activity mActivity;
	private Context mContext;
	
	public static FrgProducts newInstance(Context context, String frgName, ArrayList<String> listUrlPicture){
		FrgProducts f = new FrgProducts();
		f.frgName = frgName;
		f.mContext = context;
		
		if(null != listUrlPicture){
			f.listUrlPicture = listUrlPicture;
		}
		return f;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public String getFragmentName() {
		return super.getFragmentName();
	}
	
	/* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
     */
    @Override
    public void onAttach(Activity activity) {
    	// TODO Auto-generated method stub
    	super.onAttach(activity);
    	this.mActivity = activity;
    	this.mContext = activity;
    }
    
	/**
	 * 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.frg_products_image_grid, container, false);    	
		initLayout(inflater);
		return mView;
	}
	
	/**
	 * initial screen data for product fragment
	 * @param inflater
	 */
	private void initLayout(LayoutInflater inflater){
		productsGrid = (GridView) mView.findViewById(R.id.productsGrid);
		imageAdapter = new ImageAdapter(mContext, listUrlPicture);
		productsGrid.setAdapter(imageAdapter);
		productsGrid.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent i = new Intent(getActivity(), AcImagePager.class);
		i.putExtra("IMAGE_POSITION", position);
		i.putExtra("LIST_URL", imageAdapter.getAllList());
		startActivity(i);
	}
	
	/**
	 * ImageAdapter
	 * @author Do Thin
	 *
	 */
	private static class ImageAdapter extends BaseAdapter {
		private static ArrayList<String> IMAGE_URLS = new ArrayList<String>();
		private LayoutInflater inflater;
		private DisplayImageOptions options;
		protected ImageLoader imageLoader;
		
		ImageAdapter(Context context, ArrayList<String> listUrl) {
			inflater = LayoutInflater.from(context);

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
				view = inflater.inflate(R.layout.item_grid_product, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.product_image);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.product_progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			
			imageLoader.displayImage(getItem(position), holder.imageView, options, new SimpleImageLoadingListener() {
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
	}


	
}
