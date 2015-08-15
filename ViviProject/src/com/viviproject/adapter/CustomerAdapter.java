package com.viviproject.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemCustomer;
import com.viviproject.entities.EnStores;
import com.viviproject.overview.CustomerProfitActivity;

@SuppressLint("DefaultLocale") 
public class CustomerAdapter extends BaseAdapter implements Filterable{
	private ArrayList<EnStores> _data, arraylist;
    private EnStores items;
    private Activity mActivity;
    private OnClickListener _onItemClick;
    private ValueFilter valueFilter;
    String lines;
	
    public CustomerAdapter(Activity activity, ArrayList<EnStores> data) 
	{
		 mActivity = activity;
        _data = data;
        arraylist = data;
        lines = "";
	}
    
    @Override
	public int getCount()
	{
		 return (_data == null ? 0 : _data.size());
	}

	@Override
	public Object getItem(int position) 
	{		
		return position;
	}

	@Override
	public long getItemId(int position)
	{		
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
        if (convertView == null)
        {
            convertView = new ItemCustomer(mActivity.getApplicationContext());
            ((ItemCustomer) convertView).setOnThisItemClickHandler(onItemClick);
            holder = new ViewHolder();
         
            holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
            holder.imgLocationBlue = (ImageView) convertView.findViewById(R.id.imgLocationBlue);
            holder.imgLocationGray = (ImageView) convertView.findViewById(R.id.imgLocationGray);
            holder.tvNamePharmacy = (TextView) convertView.findViewById(R.id.tvNamePharmacy);
            holder.tvCodeName = (TextView) convertView.findViewById(R.id.tvCodeName);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            holder.tvRound = (TextView) convertView.findViewById(R.id.tvRound);
            holder.tvVisitTime = (TextView) convertView.findViewById(R.id.tvVisitTime);
            holder.tvProfit = (TextView) convertView.findViewById(R.id.tvProfit);
            holder.tvLateOrder = (TextView) convertView.findViewById(R.id.tvLateOrder);
            
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {
        	holder.tvId.setText(items.getId());
        	holder.tvNamePharmacy.setText(items.getName());
        	holder.tvCodeName.setText(items.getCode());
        	holder.tvAddress.setText(items.getAddress());
        	
        	if (items.getLines() != null && items.getLines().length > 0) {
        		
        		for (int i = 0; i < items.getLines().length; i++) {
        			lines += items.getLines()[i] + ", ";
    			}
			}
        	
        	if (!lines.equals("")) {
        		holder.tvRound.setText(lines.substring(0, lines.length() - 2));
			} else {
				holder.tvRound.setText(lines);
			}
        	
        	lines = "";
        	holder.tvVisitTime.setText(items.getTotal_visit());
        	holder.tvProfit.setText(items.getLast_month_revenue());
        	holder.tvLateOrder.setText(items.getLatest_order());
        	holder.tvLocation.setText(items.getDistance());
			holder.imgLocationGray.setVisibility(View.VISIBLE);    		
    		holder.imgLocationBlue.setVisibility(View.GONE);
		}
        
        ((ItemCustomer) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvId, tvNamePharmacy, tvCodeName, tvAddress, tvRound, tvProfit, tvVisitTime, tvLateOrder, tvLocation;
        ImageView imgLocationBlue, imgLocationGray;
    }
	
	OnClickListener onItemClick = new OnClickListener() 
	{

        @Override
        public void onClick(View v)
        {
            if (_onItemClick != null)
                _onItemClick.onClick(v);
        }
    };
    
    public void setOnThisItemClickHandler(OnClickListener itemClick)
    {
        _onItemClick = itemClick;
    }
    
    @Override
	public Filter getFilter() {
		if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
	}
    
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<EnStores> filterList = new ArrayList<EnStores>();
                for (int i = 0; i < arraylist.size(); i++) {
                    if ( (arraylist.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(arraylist.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = arraylist.size();
                results.values = arraylist;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            _data = (ArrayList<EnStores>) results.values;
            CustomerProfitActivity.arrEnStores = _data;         
            notifyDataSetChanged();
        }
    }
}
