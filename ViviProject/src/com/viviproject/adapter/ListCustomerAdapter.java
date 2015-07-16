package com.viviproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemListCustomer;
import com.viviproject.entities.EnArrayStores;
import com.viviproject.entities.EnStores;

public class ListCustomerAdapter extends BaseAdapter{
	private EnArrayStores _data;
    private EnStores items;
    private Activity mActivity;
    private OnClickListener _onItemClick;
	
    public ListCustomerAdapter(Activity activity, EnArrayStores data) 
	{
		 mActivity = activity;
        _data = data;
	}
    
    @Override
	public int getCount()
	{
		 return (_data == null ? 0 : _data.getStores().size());
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
          
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.getStores().get(position);
        
        if (items != null) {        
        	holder.tvId.setText(items.getId());
        	holder.tvNamePharmacy.setText(items.getName());
        	holder.tvCodeName.setText(items.getCode());
        	holder.tvAddress.setText(items.getAddress());
        	holder.tvRound.setText(items.getRegion_id());
        	if (items.getId().equals("2")) {
				holder.imgLocationGray.setVisibility(View.VISIBLE);
        		holder.tvLocation.setVisibility(View.VISIBLE);
        		holder.imgLocationBlue.setVisibility(View.GONE);
			} else {
				holder.imgLocationGray.setVisibility(View.GONE);
        		holder.tvLocation.setVisibility(View.GONE);
        		holder.imgLocationBlue.setVisibility(View.VISIBLE);
			}
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
}
