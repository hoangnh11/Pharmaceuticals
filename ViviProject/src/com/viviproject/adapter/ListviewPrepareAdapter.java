package com.viviproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnSalePrepare;
import com.viviproject.entities.ResponsePrepare;
import com.viviproject.ultilities.StringUtils;
import com.viviproject.visit.ItemListviewPrepare;

public class ListviewPrepareAdapter extends BaseAdapter{
	private ResponsePrepare _data;
    private EnSalePrepare items;
    private Activity mActivity;

	
    public ListviewPrepareAdapter(Activity activity, ResponsePrepare data) 
	{
		 mActivity = activity;
        _data = data;
	}
    
    @Override
	public int getCount()
	{
		 return (_data == null ? 0 : _data.getBasket().size());
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
            convertView = new ItemListviewPrepare(mActivity.getApplicationContext());
           
            holder = new ViewHolder();
            holder.tvSTT = (TextView) convertView.findViewById(R.id.tvSTT);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            holder.tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
            holder.tvDiscount = (TextView) convertView.findViewById(R.id.tvDiscount);
            holder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
            holder.tvNote = (TextView) convertView.findViewById(R.id.tvNote);
          
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.getBasket().get(position);
        
        if (items != null) {        
        	holder.tvSTT.setText(String.valueOf(items.getProduct_id()));
        	holder.tvName.setText(items.getName());
        	
        	if (items.getPrice() != null) {
        		holder.tvPrice.setText(StringUtils.formatEnglishValueNumber(items.getPrice()));
			}
        	
        	holder.tvQuantity.setText(String.valueOf(items.getQuantity()));
        	holder.tvDiscount.setText(String.valueOf(items.getDiscount_sale()));
    		holder.tvTotal.setText(StringUtils.formatEnglishValueNumber(items.getTotal()));
        	if (items.getNote() != null) {
        		holder.tvNote.setText(items.getNote());
			}        	
		}
        
        ((ItemListviewPrepare) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvSTT, tvName, tvPrice, tvQuantity, tvDiscount, tvTotal, tvNote;
    }
}
