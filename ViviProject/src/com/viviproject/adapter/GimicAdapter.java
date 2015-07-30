package com.viviproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnElement;
import com.viviproject.entities.EnGimic;
import com.viviproject.visit.ItemListviewGimic;

public class GimicAdapter extends BaseAdapter{
	private EnElement _data;
    private EnGimic items;
    private Activity mActivity;
    private OnClickListener _onMinusClick, _onPlusClick;
	
    public GimicAdapter(Activity activity, EnElement data) 
	{
		 mActivity = activity;
        _data = data;
	}
    
    @Override
	public int getCount()
	{
		 return (_data == null ? 0 : _data.getElements().size());
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
            convertView = new ItemListviewGimic(mActivity.getApplicationContext());
            ((ItemListviewGimic) convertView).setOnMinusClickHandler(onMinusClick);
            ((ItemListviewGimic) convertView).setOnPlusClickHandler(onPlusClick);
            
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
        
        items = _data.getElements().get(position);
        
        if (items != null) {
        	holder.tvName.setText(items.getName());
        	        	
        	if (items.getUnit() != null) {
        		holder.tvQuantity.setText(items.getUnit());
			}
		}
        
        ((ItemListviewGimic) convertView).set_position(position);
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
