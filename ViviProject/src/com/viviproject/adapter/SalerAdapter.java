package com.viviproject.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemSaler;
import com.viviproject.entities.EnEmployee;

public class SalerAdapter extends BaseAdapter{
	private List<EnEmployee> _data;
    private EnEmployee items;
    private Activity mActivity;
    private OnClickListener _onItemClick;
	
    public SalerAdapter(Activity activity, List<EnEmployee> data) 
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
            convertView = new ItemSaler(mActivity.getApplicationContext());
           
            holder = new ViewHolder();
            
            holder.tvSalerName = (TextView) convertView.findViewById(R.id.tvSalerName);
            holder.tvPoint = (TextView) convertView.findViewById(R.id.tvPoint);
            holder.tvDateOfBirth = (TextView) convertView.findViewById(R.id.tvDateOfBirth);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
            
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {        
        	if (items.getFullname() != null) {
        		holder.tvSalerName.setText(items.getFullname());
			}
        	
//			if (items.getBirthday() != null) {
				holder.tvPoint.setText("10");
//			}
			
			if (items.getBirthday() != null) {
				holder.tvDateOfBirth.setText(items.getBirthday());
			}
			
			if (items.getPhone() != null) {
				holder.tvPhone.setText(items.getPhone());
			}
		}
        
        ((ItemSaler) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvSalerName, tvPoint, tvDateOfBirth, tvPhone;
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
}
