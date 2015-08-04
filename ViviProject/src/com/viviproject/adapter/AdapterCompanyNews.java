package com.viviproject.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.viviproject.R;
import com.viviproject.entities.EnNews;
import com.viviproject.ultilities.StringUtils;

public class AdapterCompanyNews extends ArrayAdapter<EnNews> {
	private Context context;
	private ArrayList<EnNews> listNews = new ArrayList<EnNews>();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;
	
	public AdapterCompanyNews(Context context, ArrayList<EnNews> listNew) {
		super(context, R.layout.item_listview_company);
		this.context = context;
		this.listNews = listNew;
		
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(imageLoaderConfiguration);
	}

	@Override
	public int getCount() {
		if(null == listNews){
			return -1;
		}
		
		return listNews.size();
	}

	@Override
	public EnNews getItem(int position) {
		if(null == listNews || listNews.size() == 0){
			return null;
		}
		
		return listNews.get(position);
	}

	public void setListNew(ArrayList<EnNews> list){
		for (int i = 0; i < listNews.size(); i++) {
			listNews.remove(i);
		}
		listNews = list;
	}
	
	private class CompanyNewsHolder{
		ImageView imgPreview;
		TextView tvTitle;
		TextView tvTextPreview;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CompanyNewsHolder holder;
		if(null == convertView){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_listview_company, parent, false);
			holder = new CompanyNewsHolder();

			holder.imgPreview = (ImageView) convertView.findViewById(R.id.imgPreview);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvTextPreview = (TextView) convertView.findViewById(R.id.tvTextPreview);
			convertView.setTag(holder);
		} else {
			holder = (CompanyNewsHolder) convertView.getTag();
		}
		
		EnNews enNewsItem = getItem(position);
		if(null != enNewsItem){
			if(StringUtils.isNotBlank(enNewsItem.getPreview_image())){
				imageLoader.displayImage(enNewsItem.getPreview_image(), holder.imgPreview, options);
			} else {
				holder.imgPreview.setImageResource(R.drawable.ic_empty);
			}
			
			if(StringUtils.isNotBlank(enNewsItem.getTitle())){
				holder.tvTitle.setText(enNewsItem.getTitle());
			} else {
				holder.tvTitle.setText("");
			}
			
			if(StringUtils.isNotBlank(enNewsItem.getPreview_text())){
				holder.tvTextPreview.setText(enNewsItem.getPreview_text());
			} else {
				holder.tvTextPreview.setText("");
			}
		}
		return convertView;
	}
}
