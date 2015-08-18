package com.viviproject.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ConvertUnsigned;
import com.viviproject.entities.EnStoreReportItem;

public class AdapterProfitFollowCustomer extends ArrayAdapter<EnStoreReportItem> implements Filterable{
	private ArrayList<EnStoreReportItem> listCustomer = new ArrayList<EnStoreReportItem>();
	private ArrayList<EnStoreReportItem> orionListCustomer = new ArrayList<EnStoreReportItem>();
	private Context context;
	private ItemFilter mFilter;
	private ConvertUnsigned crtUn = new ConvertUnsigned();;
	
	public AdapterProfitFollowCustomer(Context context, ArrayList<EnStoreReportItem> listCustomer) {
		super(context, R.layout.item_profit_follow_customer);
		this.listCustomer = listCustomer;
		this.orionListCustomer = listCustomer;
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
			orionListCustomer.remove(i);
		}
		
		listCustomer = list;
		orionListCustomer = list;
	}
	
	public void addListProfitCustomer(ArrayList<EnStoreReportItem> list){
		listCustomer = new ArrayList<EnStoreReportItem>();
		listCustomer = orionListCustomer;
		for (int i = 0; i < list.size(); i++) {
			listCustomer.add(list.get(i));
			orionListCustomer.add(list.get(i));
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
	
	public Filter getFilter() {
		if(null == mFilter){
			mFilter = new ItemFilter();
		}
		
        return mFilter;
    }
	
	private class ItemFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			ArrayList<EnStoreReportItem> listSearch = new ArrayList<EnStoreReportItem>();
			FilterResults results = new FilterResults();
			
			String filter = constraint.toString().toLowerCase();
			String srtConvert = crtUn.ConvertString(filter);
			for (int i = 0; i < orionListCustomer.size(); i++) {
				String strName = crtUn.ConvertString(orionListCustomer.get(i).getName());
				if(strName.contains(srtConvert)){
					listSearch.add(orionListCustomer.get(i));
				}
			}
			
			results.values = listSearch;
			results.count = listSearch.size();
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			listCustomer = (ArrayList<EnStoreReportItem>) results.values;
			
			if(null == listCustomer) {
				listCustomer = new ArrayList<EnStoreReportItem>();
			}
			notifyDataSetChanged();
		}
		
	}
}
