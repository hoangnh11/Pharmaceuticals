package com.viviproject.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnProducts;

/**
 * adapter for products spinner
 * @author Do Thin
 *
 */
public class AdapterSpProducts extends ArrayAdapter<EnProducts> {
	private ArrayList<EnProducts> listProducts = new ArrayList<EnProducts>();
	private Context context;
	
	public AdapterSpProducts(Context context, ArrayList<EnProducts> list) {
		super(context, R.layout.custom_spinner_items);
		this.context = context;
		this.listProducts = list;
	}

	@Override
	public int getCount() {
		if(null == listProducts){
			return -1;
		}
		
		return listProducts.size();
	}

	@Override
	public EnProducts getItem(int position) {
		if(getCount() <= 0){
			return null;
		}
		
		return listProducts.get(position);
	}

	public void setListProduct(ArrayList<EnProducts> list){
		for (int i = 0; i < listProducts.size(); i++) {
			listProducts.remove(i);
		}
		listProducts = list;
	}
	
	private class ViewHolder{
		TextView tvProductName;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_spinner_items, parent, false);
			holder = new ViewHolder();
			
			holder.tvProductName = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		EnProducts item = getItem(position);
		if(null != item){
			holder.tvProductName.setText(item.getName());
		} 
		return convertView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_spinner_items, parent, false);
			holder = new ViewHolder();
			
			holder.tvProductName = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		EnProducts item = getItem(position);
		if(null != item){
			holder.tvProductName.setText(item.getName());
		} 
		
		return convertView;
	}
}
