/**
 * 
 *//*
package com.viviproject.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnGimicCustomerList;

*//**
 * @author Do Thin
 *
 *//*
public class AdapterGimicListCutomer extends ArrayAdapter<EnGimicCustomerList> {
	private Context context;
	private ArrayList<EnGimicCustomerList> listGimicCustomerList = new ArrayList<EnGimicCustomerList>();
	public AdapterGimicListCutomer(Context context, ArrayList<EnGimicCustomerList> listGimicStatistic) {
		super(context, R.layout.item_gimic_list_customer);
		this.context = context;
		this.listGimicCustomerList = listGimicStatistic;
	}

	@Override
	public int getCount() {
		return listGimicCustomerList.size();
	}

	@Override
	public EnGimicCustomerList getItem(int position) {
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
		TextView tvDrugStoreName;
		TextView tvDrugStoreAdrress;
		TextView tvPen;
		TextView tvMagazin;
		TextView tvClock;
		TextView tvWaterBottle;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
        if (convertView == null){
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	convertView = inflater.inflate(R.layout.item_gimic_list_customer, parent, false);
        	holder = new ViewHolder();
        	holder.tvSTT = (TextView) convertView.findViewById(R.id.tvCustomerSTT);
        	holder.tvDrugStoreName = (TextView) convertView.findViewById(R.id.tvCustomerDrugstoretName);
        	holder.tvDrugStoreAdrress = (TextView) convertView.findViewById(R.id.tvCustomerDrugstoretAddress);
        	holder.tvPen = (TextView) convertView.findViewById(R.id.tvCustomerPen);
        	holder.tvMagazin = (TextView) convertView.findViewById(R.id.tvCustomerMagazine);
        	holder.tvClock = (TextView) convertView.findViewById(R.id.tvCustomerClock);
        	holder.tvWaterBottle = (TextView) convertView.findViewById(R.id.tvCustomerWaterBottle);
        	convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        EnGimicCustomerList item = listGimicCustomerList.get(position);
        
        holder.tvSTT.setText(String.valueOf(position));
        holder.tvDrugStoreName.setText(item.getDrugStoreName());
        holder.tvDrugStoreAdrress.setText(item.getDrugStoreAddress());
        holder.tvPen.setText(String.valueOf(item.getNumPen()));
        holder.tvMagazin.setText(String.valueOf(item.getNumMagazin()));
        holder.tvClock.setText(String.valueOf(item.getNumClock()));
        holder.tvWaterBottle.setText(String.valueOf(item.getNumWaterBottle()));
		return convertView;
	}
}
*/