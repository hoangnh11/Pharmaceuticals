package com.viviproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemDelivedOrder;
import com.viviproject.entities.EnOrder;
import com.viviproject.entities.ResponseOrders;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.GlobalParams;

public class DelivedOrderAdapter extends BaseAdapter{
	private ResponseOrders _data;
    private EnOrder items;
    private Activity mActivity;
    private OnClickListener _onItemClick;
    private SubOrderDeliverAdapter subOrderDeliverAdapter;
    private AppPreferences app;
	
    public DelivedOrderAdapter(Activity activity, ResponseOrders data) 
	{
		 mActivity = activity;
        _data = data;
        app = new AppPreferences(mActivity);
	}
    
    @Override
	public int getCount()
	{
		 return (_data == null ? 0 : _data.getOrders().size());
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
            convertView = new ItemDelivedOrder(mActivity.getApplicationContext());
            ((ItemDelivedOrder) convertView).setOnThisItemClickHandler(onItemClickHandler);
   
            holder = new ViewHolder();
         
            holder.tvCancel = (TextView) convertView.findViewById(R.id.tvCancel);       
            holder.tvNameStore = (TextView) convertView.findViewById(R.id.tvNameStore);
            holder.tvAddressStore = (TextView) convertView.findViewById(R.id.tvAddressStore);
            holder.tvDateBook = (TextView) convertView.findViewById(R.id.tvDateBook);
            holder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
            holder.lvSubOrderDeliver = (ListView) convertView.findViewById(R.id.lvSubOrderDeliver);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.getOrders().get(position);
        
        if (items != null) {
        	holder.tvNameStore.setText(items.getName());
        	holder.tvAddressStore.setText(items.getAddress());
        	holder.tvDateBook.setText(items.getDate_book());
        	holder.tvTotal.setText(items.getTotal() + GlobalParams.BLANK_CHARACTER + "(vnd)");
        	
        	if (items.getProducts() != null && items.getProducts().size() > 0) {
        		subOrderDeliverAdapter = new SubOrderDeliverAdapter(mActivity, items.getProducts());
            	holder.lvSubOrderDeliver.setAdapter(subOrderDeliverAdapter);
            	app.setListViewHeight(holder.lvSubOrderDeliver, subOrderDeliverAdapter);
			}
		}
        
        ((ItemDelivedOrder) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvCancel, tvNameStore, tvAddressStore, tvDateBook, tvTotal;
        ListView lvSubOrderDeliver;
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
