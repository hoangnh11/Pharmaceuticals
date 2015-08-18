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
import com.viviproject.core.ConvertUnsigned;
import com.viviproject.core.ItemListCustomer;
import com.viviproject.entities.EnStores;
import com.viviproject.sales.Sales;
import com.viviproject.visit.VisitAcitvity;

@SuppressLint("DefaultLocale")
public class VisitAdapter extends BaseAdapter implements Filterable{
	private ArrayList<EnStores> _data, arraylist;
    private EnStores items;
    private Activity mActivity;
    private OnClickListener _onItemClick;
    private ValueFilter valueFilter;
    String lines;
    private ConvertUnsigned crtUn;
	
    public VisitAdapter(Activity activity, ArrayList<EnStores> data) 
	{
		 mActivity = activity;
        _data = data;
        arraylist = data;
        lines = "";
        crtUn = new ConvertUnsigned();
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
            convertView = new ItemListCustomer(mActivity.getApplicationContext());
            ((ItemListCustomer) convertView).setOnThisItemClickHandler(onItemClickHandler);
           
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
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {        
        	holder.tvId.setText(String.valueOf(items.getId()));
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
        	holder.tvLocation.setText(items.getDistance());
        	holder.imgLocationGray.setVisibility(View.VISIBLE);    		
    		holder.imgLocationBlue.setVisibility(View.GONE);        	
		}
        
        ((ItemListCustomer) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvId, tvNamePharmacy, tvCodeName, tvAddress, tvRound, tvProfit, tvVisitTime, tvLateOrder, tvLocation;
        ImageView imgLocationBlue, imgLocationGray;
    }
	
	OnClickListener onItemClickHandler = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
            if (_onItemClick != null)
                _onItemClick.onClick(v);
        }
    };
    
    public void setOnItemClickHandler(OnClickListener itemClick)
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
                    if (crtUn.ConvertString(arraylist.get(i).getName().toLowerCase()).contains
                    		(crtUn.ConvertString(constraint.toString().toLowerCase()))) {
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
            VisitAcitvity.arrEnStores = _data;
            Sales.arrEnStores = _data;
            notifyDataSetChanged();
        }
    }
}
