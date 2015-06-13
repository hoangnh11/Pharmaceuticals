package com.viviproject.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemCustomer;
import com.viviproject.entities.EnCustomer;

public class CustomerAdapter extends BaseAdapter{
	private List<EnCustomer> _data;
    private EnCustomer items;
    private Activity mActivity;
    private OnClickListener _onItemClick;
	
    public CustomerAdapter(Activity activity, List<EnCustomer> data) 
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
            convertView = new ItemCustomer(mActivity.getApplicationContext());
//            ((ItemCustomer) convertView).setOnThisItemClickHandler(onItemClickHandler);
           
            holder = new ViewHolder();
         
            holder.tvId = (TextView) convertView.findViewById(R.id.tvId);          
          
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {        
        	
		}
        
        ((ItemCustomer) convertView).set_position(position);
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
}
