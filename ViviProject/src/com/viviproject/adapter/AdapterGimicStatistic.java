package com.viviproject.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnGimicItem;

public class AdapterGimicStatistic extends ArrayAdapter<EnGimicItem> {
	private Context context;
	private ArrayList<EnGimicItem> listGimicItem = new ArrayList<EnGimicItem>();
	public AdapterGimicStatistic(Context context, ArrayList<EnGimicItem> listGimicStatistic) {
		super(context, R.layout.item_gimic_statistic);
		this.context = context;
		this.listGimicItem = listGimicStatistic;
	}

	@Override
	public int getCount() {
		return listGimicItem.size();
	}

	@Override
	public EnGimicItem getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}
	
	public void setListGimic(ArrayList<EnGimicItem> list){
		for (int i = 0; i < listGimicItem.size(); i++) {
			listGimicItem.remove(i);
		}
		listGimicItem = list;
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
        
        EnGimicItem item = listGimicItem.get(position);
        
        holder.tvSTT.setText(String.valueOf(position));
        holder.tvProduct.setText(item.getName());
        holder.tvImport.setText(item.getImport_quantity());
        holder.tvExport.setText(item.getExport_quantity());
        holder.tvInventory.setText("" + item.getLeft_quantity());
		return convertView;
	}
	
}
