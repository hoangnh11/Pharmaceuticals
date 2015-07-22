package com.viviproject.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.Products;
import com.viviproject.visit.ItemListViewForsale;

public class ForsaleAdapter extends BaseAdapter{
	private Products _data;
    private EnProducts items;
    private Activity mActivity;
    private OnClickListener _onItemClick;
	
    public ForsaleAdapter(Activity activity, Products data) 
	{
		 mActivity = activity;
        _data = data;
	}
    
    @Override
	public int getCount()
	{
		 return (_data == null ? 0 : _data.getProducts().size());
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
            convertView = new ItemListViewForsale(mActivity.getApplicationContext());
            ((ItemListViewForsale) convertView).setOnThisItemClickHandler(onItemClickHandler);
           
            holder = new ViewHolder();
            holder.linHeader = (LinearLayout) convertView.findViewById(R.id.linHeader);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
          
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.getProducts().get(position);
        
        if (items != null) {
        	
        	if (position == 0) {
        		holder.linHeader.setVisibility(View.VISIBLE);
			} else {
				holder.linHeader.setVisibility(View.GONE);
			}
        	
        	holder.tvName.setText(items.getName());
        	
        	if (items.getColor() != null) {
        		try {
        			holder.tvName.setBackgroundColor(Color.parseColor("#"+items.getColor()));
				} catch (Exception e) {					
				}        		
			}      	
		} 
        
        ((ItemListViewForsale) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        LinearLayout linHeader;
        TextView tvName;
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
