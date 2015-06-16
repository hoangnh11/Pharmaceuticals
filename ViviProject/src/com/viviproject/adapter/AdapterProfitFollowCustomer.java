package com.viviproject.adapter;

import java.util.ArrayList;

import com.viviproject.R;
import com.viviproject.entities.EnCustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterProfitFollowCustomer extends ArrayAdapter<EnCustomer> {
	private ArrayList<EnCustomer> listCustomer;
	private Context context;
	
	public AdapterProfitFollowCustomer(Context context, ArrayList<EnCustomer> listCustomer) {
		super(context, R.layout.item_profit_follow_customer);
		this.listCustomer = listCustomer;
		this.context = context;
	}

	@Override
	public int getCount() {
		return listCustomer.size();
	}
	
	@Override
	public EnCustomer getItem(int position) {
		if(position < 0 || null == listCustomer || listCustomer.size() == 0){
			return null;
		}
		return listCustomer.get(position);
	}
	
	static class ViewHolder {      
        TextView tvId;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null == convertView){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	convertView = inflater.inflate(R.layout.item_profit_follow_customer, parent, false);
        	holder = new ViewHolder();
        	holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
        	
        	convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		EnCustomer enCustomer = getItem(position);
		holder.tvId.setText(String.valueOf(position + 1));
		return convertView;
	}
}
