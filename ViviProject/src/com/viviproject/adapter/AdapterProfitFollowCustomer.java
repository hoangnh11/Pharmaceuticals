package com.viviproject.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnStoreReportItem;

public class AdapterProfitFollowCustomer extends ArrayAdapter<EnStoreReportItem> {
	private ArrayList<EnStoreReportItem> listCustomer;
	private Context context;
	
	public AdapterProfitFollowCustomer(Context context, ArrayList<EnStoreReportItem> listCustomer) {
		super(context, R.layout.item_profit_follow_customer);
		this.listCustomer = listCustomer;
		this.context = context;
	}

	@Override
	public int getCount() {
		if( null == listCustomer){
			return -1;
		}
		
		return listCustomer.size();
	}
	
	@Override
	public EnStoreReportItem getItem(int position) {
		if(getCount() <= 0){
			return null;
		}
		
		return listCustomer.get(position);
	}
	
	public void setListProfitCustomer(ArrayList<EnStoreReportItem> list){
		for (int i = 0; i < listCustomer.size(); i++) {
			listCustomer.remove(i);
		}
		
		listCustomer = list;
	}
	
	public void addListProfitCustomer(ArrayList<EnStoreReportItem> list){
		for (int i = 0; i < list.size(); i++) {
			listCustomer.add(list.get(i));
		}
	}
	
	static class ViewHolder {      
        TextView tvSTT;
        TextView tvNamePharmacyName;
        TextView tvPrice;
        TextView tvAddress;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null == convertView){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	convertView = inflater.inflate(R.layout.item_profit_follow_customer, parent, false);
        	holder = new ViewHolder();
        	
        	holder.tvSTT = (TextView) convertView.findViewById(R.id.tvSTT);
        	holder.tvNamePharmacyName = (TextView) convertView.findViewById(R.id.tvNamePharmacyName);
        	holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        	holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        	
         	convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		EnStoreReportItem item = getItem(position);
		holder.tvSTT.setText(String.valueOf(position + 1));
		
		if(null != item){
			holder.tvNamePharmacyName.setText(item.getName());
			holder.tvPrice.setText(item.getSumary());
		}
		return convertView;
	}
}
