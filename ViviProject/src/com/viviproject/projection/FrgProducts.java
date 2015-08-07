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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.viviproject.R;
import com.viviproject.core.ImageLoaderSinggleton;
import com.viviproject.entities.EnImageUrl;
import com.viviproject.entities.EnProduct;


public class FrgProducts extends FrgBaseFragmentProducts implements OnItemClickListener{
	private GridView productsGrid;
	
	private ArrayList<EnImageUrl> listUrlPicture;
	private ImageAdapter imageAdapter;
	private View mView;
	private Activity mActivity;
	private Context mContext;
	public static DisplayImageOptions options;
	public static ImageLoader imageLoader;
	
	public static FrgProducts newInstance(Context context, EnProduct enProduct, ArrayList<EnImageUrl> listUrlPicture){
		FrgProducts f = new FrgProducts();
		f.enProduct = enProduct;
		f.mContext = context;
		
		if(null != listUrlPicture){
			f.listUrlPicture = listUrlPicture;
		}
		
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		
		imageLoader = ImageLoaderSinggleton.getInstance(context);
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
	
	public void updateListImage(ArrayList<EnImageUrl> enImageUrlList) {
		for (int i = 0; i < listUrlPicture.size(); i++) {
			listUrlPicture.remove(i);
		}
		
		listUrlPicture = enImageUrlList;
		imageAdapter.updateList(enImageUrlList);
		imageAdapter.notifyDataSetChanged();
	}
	
	/**
	 * ImageAdapter
	 * @author Do Thin
	 *
	 */
	private static class ImageAdapter extends BaseAdapter {
		private static ArrayList<EnImageUrl> IMAGE_URLS = new ArrayList<EnImageUrl>();
		private LayoutInflater inflater;
		
		ImageAdapter(Context context, ArrayList<EnImageUrl> listUrl) {
			inflater = LayoutInflater.from(context);

			IMAGE_URLS = listUrl;
		}

		public void updateList(ArrayList<EnImageUrl> enImageUrlList) {
			for (int i = 0; i < IMAGE_URLS.size(); i++) {
				IMAGE_URLS.remove(i);
			}
			
			IMAGE_URLS = enImageUrlList;
		}

		@Override
		public int getCount() {
			return IMAGE_URLS.size();
		}

		@Override
		public EnImageUrl getItem(int position) {
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
			ArrayList<String> list = new ArrayList<String>();
			for (EnImageUrl enImageUrl : IMAGE_URLS) {
				list.add(enImageUrl.getImage_url());
			}
			return list;
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
			
			EnImageUrl item = getItem(position);
			if(null != item){
				imageLoader.displayImage(item.getImage_url(), holder.imageView, options, new SimpleImageLoadingListener() {
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
	}
	
}
