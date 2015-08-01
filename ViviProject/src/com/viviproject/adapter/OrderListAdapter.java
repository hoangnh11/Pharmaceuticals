package com.viviproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ItemOrderList;
import com.viviproject.deliver.OrderActivity;
import com.viviproject.entities.EnOrder;
import com.viviproject.entities.ResponseOrders;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.GlobalParams;

public class OrderListAdapter extends BaseAdapter{
	private ResponseOrders _data;
    private EnOrder items;
    private Activity mActivity;
    private OnClickListener _onItemClick, _onCheckboxClick;
    private SubOrderAdapter subOrderAdapter;
    private AppPreferences app;
	
    public OrderListAdapter(Activity activity, ResponseOrders data) 
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
            convertView = new ItemOrderList(mActivity.getApplicationContext());
            ((ItemOrderList) convertView).setOnThisItemClickHandler(onItemClickHandler);
            ((ItemOrderList) convertView).setOnCheckboxItemClickHandler(onCheckboxClick);
           
            holder = new ViewHolder();
         
            holder.tvNameStore = (TextView) convertView.findViewById(R.id.tvNameStore);
            holder.tvAddressStore = (TextView) convertView.findViewById(R.id.tvAddressStore);
            holder.tvDateBook = (TextView) convertView.findViewById(R.id.tvDateBook);
            holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
            holder.checkboxDeliver = (CheckBox) convertView.findViewById(R.id.checkboxDeliver);
            holder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
            holder.lvSubOrder = (ListView) convertView.findViewById(R.id.lvSubOrder);
        
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
        	
        	if (items.getDelivery().equals("0")) {
        		holder.checkboxDeliver.setChecked(false);
			} else {
				holder.checkboxDeliver.setChecked(true);
			}
        	
        	if (items.getProducts() != null && items.getProducts().size() > 0) {
        		subOrderAdapter = new SubOrderAdapter(mActivity, items.getProducts());		
            	holder.lvSubOrder.setAdapter(subOrderAdapter);
            	app.setListViewHeight(holder.lvSubOrder, subOrderAdapter);
			}
        	
        	holder.tvTotal.setText(items.getTotal() + GlobalParams.BLANK_CHARACTER + "(vnd)");
		}
        
        ((ItemOrderList) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvNameStore, tvAddressStore, tvDateBook, tvTotal, tvEdit;
        CheckBox checkboxDeliver;
        ListView lvSubOrder;
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
