package com.viviproject.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemListCustomer;
import com.viviproject.entities.EnGimicStatistic;

public class AdapterGimicStatistic extends ArrayAdapter<EnGimicStatistic> {
	private Context context;
	private ArrayList<EnGimicStatistic> listGimicStatistic = new ArrayList<EnGimicStatistic>();
	public AdapterGimicStatistic(Context context, ArrayList<EnGimicStatistic> listGimicStatistic) {
		super(context, R.layout.item_gimic_statistic);
		this.context = context;
		this.listGimicStatistic = listGimicStatistic;
	}

	@Override
	public int getCount() {
		return listGimicStatistic.size();
	}

	@Override
	public EnGimicStatistic getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}
	
	class ViewHolder{
		TextView tvSTT;
		TextView tvProduct;
		TextView tvImport;
		TextView tvExport;
		TextView tvInventory;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
        if (convertView == null){
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	convertView = inflater.inflate(R.layout.item_gimic_statistic, parent, false);
        	holder = new ViewHolder();
        	holder.tvSTT = (TextView) convertView.findViewById(R.id.tvStatisticSTT);
        	holder.tvProduct = (TextView) convertView.findViewById(R.id.tvStatisticProduct);
        	holder.tvImport = (TextView) convertView.findViewById(R.id.tvStatisticImport);
        	holder.tvExport = (TextView) convertView.findViewById(R.id.tvStatisticExport);
        	holder.tvInventory = (TextView) convertView.findViewById(R.id.tvStatisticInventory);
        	convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        EnGimicStatistic item = listGimicStatistic.get(position);
        
        holder.tvSTT.setText(String.valueOf(position));
        holder.tvProduct.setText(item.getStrProduct());
        holder.tvImport.setText(String.valueOf(item.getNumImport()));
        holder.tvExport.setText(String.valueOf(item.getNumExport()));
        holder.tvInventory.setText(String.valueOf(item.getNumInventory()));
		return convertView;
	}
	
}
