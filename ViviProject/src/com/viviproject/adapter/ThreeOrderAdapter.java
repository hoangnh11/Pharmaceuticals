package com.viviproject.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.customerline.ItemListviewProfit;
import com.viviproject.entities.EnLatestThreeOrder;

public class ThreeOrderAdapter extends BaseAdapter{
	private List<EnLatestThreeOrder> _data;
    private EnLatestThreeOrder items;
    private Activity mActivity;
    private int index;
	
    public ThreeOrderAdapter(Activity activity, List<EnLatestThreeOrder> data) 
	{
		 mActivity = activity;
        _data = data;
        index = 0;
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
            convertView = new ItemListviewProfit(mActivity.getApplicationContext());
           
            holder = new ViewHolder();
            
            holder.tvNo = (TextView) convertView.findViewById(R.id.tvNo);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.tvProfit = (TextView) convertView.findViewById(R.id.tvProfit);        
          
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {
        	if (position == 0) {
        		index = 0;
			} 
        	
			index++;			
        	
    		holder.tvNo.setText(String.valueOf(index));
    		
			if (items.getMonth() != null) {
				holder.tvTime.setText(items.getMonth());
			}
			
			if (items.getTotal() != null) {
				holder.tvProfit.setText(items.getTotal());
			}	
		}
        
        ((ItemListviewProfit) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvNo, tvTime, tvProfit;
    }
}
