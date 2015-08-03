package com.viviproject.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.deliver.ItemListviewReturnImport;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.Products;
import com.viviproject.visit.ItemListviewGimic;

public class ReturnImportAdapter extends BaseAdapter{
	private Products _data;
    private EnProducts items;
    private Activity mActivity;
    private OnClickListener _onMinusClick, _onPlusClick;
	
    public ReturnImportAdapter(Activity activity, Products data) 
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
            convertView = new ItemListviewReturnImport(mActivity.getApplicationContext());       
            ((ItemListviewReturnImport) convertView).setOnMinusClickHandler(onMinusClick);
            ((ItemListviewReturnImport) convertView).setOnPlusClickHandler(onPlusClick);
           
            holder = new ViewHolder();         
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);           
            holder.imgMinus = (ImageView) convertView.findViewById(R.id.imgMinus);
            holder.imgPlus = (ImageView) convertView.findViewById(R.id.imgPlus);
          
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.getProducts().get(position);
        
        if (items != null) {
        
        	holder.tvName.setText(items.getName());
        	
        	if (items.getColor() != null) {
        		try {
        			holder.tvName.setBackgroundColor(Color.parseColor("#"+items.getColor()));
				} catch (Exception e) {		
				}
			}
        	
        	if (items.getUnit() != null) {
        		holder.tvQuantity.setText(items.getUnit());
			}
		}
        
        ((ItemListviewReturnImport) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {            
        TextView tvName, tvQuantity;
        ImageView imgMinus, imgPlus;
    }	
    
    OnClickListener onMinusClick = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
            if (_onMinusClick != null)
            	_onMinusClick.onClick(v);
        }
    };
    
    public void setOnMinusClickHandler(OnClickListener itemClick)
    {
    	_onMinusClick = itemClick;
    }
    
    OnClickListener onPlusClick = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
            if (_onPlusClick != null)
            	_onPlusClick.onClick(v);
        }
    };
    
    public void setOnPlusClickHandler(OnClickListener itemClick)
    {
    	_onPlusClick = itemClick;
    }
}
