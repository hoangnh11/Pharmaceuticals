package com.viviproject.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.customerline.ItemListviewOwner;
import com.viviproject.entities.EnOwner;

public class OwnerAdapter extends BaseAdapter{
	private List<EnOwner> _data;
    private EnOwner items;
    private Activity mActivity;
	
    public OwnerAdapter(Activity activity, List<EnOwner> data) 
	{
		 mActivity = activity;
        _data = data;
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
            convertView = new ItemListviewOwner(mActivity.getApplicationContext());
           
            holder = new ViewHolder();
            
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvDateOfBirth = (TextView) convertView.findViewById(R.id.tvDateOfBirth);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
            holder.tvNote = (TextView) convertView.findViewById(R.id.tvNote);
          
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {
        	if (items.getFullname() != null) {
        		holder.tvName.setText(items.getFullname());
			}
        	
			if (items.getBirthday() != null) {
				holder.tvDateOfBirth.setText(items.getBirthday());
			}
			
			if (items.getPhone() != null) {
				holder.tvPhone.setText(items.getPhone());
			}
			
			if (items.getNote() != null) {
				holder.tvNote.setText(items.getNote());
			}        	
		}
        
        ((ItemListviewOwner) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvName, tvDateOfBirth, tvPhone, tvNote;
    }	
	
}
