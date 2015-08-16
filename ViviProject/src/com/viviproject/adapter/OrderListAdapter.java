package com.viviproject.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.core.ConvertUnsigned;
import com.viviproject.core.ItemOrderList;
import com.viviproject.deliver.OrderActivity;
import com.viviproject.entities.EnOrder;
import com.viviproject.ultilities.AppPreferences;
import com.viviproject.ultilities.GlobalParams;

@SuppressLint("DefaultLocale") 
public class OrderListAdapter extends BaseAdapter implements Filterable{
	private ArrayList<EnOrder> _data, arraylist;
    private EnOrder items;
    private Activity mActivity;
    private OnClickListener _onItemClick, _onCheckboxClick;
    private SubOrderAdapter subOrderAdapter;    
    private AppPreferences app;
    private ValueFilter valueFilter;
    private ConvertUnsigned crtUn;
	
    public OrderListAdapter(Activity activity, ArrayList<EnOrder> data) 
	{
		 mActivity = activity;
        _data = data;
        arraylist = data;
        app = new AppPreferences(mActivity);
        crtUn = new ConvertUnsigned();
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
        
        items = _data.get(position);
        
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
    
    @Override
	public Filter getFilter() {
		if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
	}
    
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<EnOrder> filterList = new ArrayList<EnOrder>();
                for (int i = 0; i < arraylist.size(); i++) {
                    if (crtUn.ConvertString(arraylist.get(i).getName().toLowerCase()).contains
                    		(crtUn.ConvertString(constraint.toString().toLowerCase()))) {
                        filterList.add(arraylist.get(i));                      
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = arraylist.size();
                results.values = arraylist;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            _data = (ArrayList<EnOrder>) results.values;
            OrderActivity.arrEnOrders = _data;
            notifyDataSetChanged();
        }
    }
}
