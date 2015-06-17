package com.viviproject.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemOrderList;
import com.viviproject.entities.EnCustomer;

public class OrderListAdapter extends BaseAdapter{
	private List<EnCustomer> _data;
    private EnCustomer items;
    private Activity mActivity;
    private OnClickListener _onItemClick, _onCheckboxClick;
	
    public OrderListAdapter(Activity activity, List<EnCustomer> data) 
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
            convertView = new ItemOrderList(mActivity.getApplicationContext());
            ((ItemOrderList) convertView).setOnThisItemClickHandler(onItemClickHandler);
            ((ItemOrderList) convertView).setOnCheckboxItemClickHandler(onCheckboxClick);
           
            holder = new ViewHolder();
         
            holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
            holder.checkboxDeliver = (CheckBox) convertView.findViewById(R.id.checkboxDeliver);
        
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {        
        	        	
		}
        
        ((ItemOrderList) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvEdit;
        CheckBox checkboxDeliver;
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
    
    OnClickListener onCheckboxClick = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
            if (_onCheckboxClick != null)
            	_onCheckboxClick.onClick(v);
        }
    };
    
    public void setOnCheckboxItemClickHandler(OnClickListener itemClick)
    {
    	_onCheckboxClick = itemClick;
    }
}
