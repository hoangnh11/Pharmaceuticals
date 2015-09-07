package com.viviproject.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.deliver.ItemSubOrder;
import com.viviproject.entities.EnProductSales;

public class SubOrderAdapter extends BaseAdapter{
	private ArrayList<EnProductSales> _data;
    private EnProductSales items;
    private Activity mActivity;
    private int index = -1;
    private int indexTwo = -1;
	
    public SubOrderAdapter(Activity activity, ArrayList<EnProductSales> data) 
	{
		 mActivity = activity;
        _data = data;
	}
    
    @Override
	public int getCount()
	{    
		return (_data == null ? 0 : (int) Math.ceil((double)_data.size() / 2));
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
            convertView = new ItemSubOrder(mActivity.getApplicationContext());
           
           
            holder = new ViewHolder();
         
            holder.linColumOne = (LinearLayout) convertView.findViewById(R.id.linColumOne);
            holder.linColumTwo = (LinearLayout) convertView.findViewById(R.id.linColumTwo);
            holder.tvDotOne = (TextView) convertView.findViewById(R.id.tvDotOne);
            holder.tvNameOne = (TextView) convertView.findViewById(R.id.tvNameOne);
            holder.tvQuantityOne = (TextView) convertView.findViewById(R.id.tvQuantityOne);
            holder.tvNameTwo = (TextView) convertView.findViewById(R.id.tvNameTwo);
            holder.tvQuantityTwo = (TextView) convertView.findViewById(R.id.tvQuantityTwo);
            holder.tvDotTwo = (TextView) convertView.findViewById(R.id.tvDotTwo);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {
        	
        	if (position == 0) {        		
        		holder.tvNameOne.setText(items.getName());
            	holder.tvQuantityOne.setText(items.getProduct_qty());
            	
        		try {
        			holder.tvNameTwo.setText(_data.get(1).getName());
    	        	holder.tvQuantityTwo.setText(_data.get(1).getProduct_qty());
				} catch (Exception e) {
					
				}
        		index = 0;
        		indexTwo = 0;
			} else if (((double)_data.size() % 2) == 1.0) {
				index++;
				try {
					holder.tvNameOne.setText(_data.get(position + index).getName());
		        	holder.tvQuantityOne.setText(_data.get(position + index).getProduct_qty());
				} catch (Exception e) {
					
				}
	        	
        		try {        			
        			holder.tvNameTwo.setText(_data.get(position + index + 1).getName());
    	        	holder.tvQuantityTwo.setText(_data.get(position + index + 1).getProduct_qty());
				} catch (Exception e) {
					holder.linColumTwo.setVisibility(View.GONE);				
				}
			} else if (((double)_data.size() % 2) == 0.0){
				indexTwo++;
				try {
					holder.tvNameOne.setText(_data.get(position + indexTwo).getName());
		        	holder.tvQuantityOne.setText(_data.get(position + indexTwo).getProduct_qty());
				} catch (Exception e) {
					
				}
	        	
        		try {        			
        			holder.tvNameTwo.setText(_data.get(position + indexTwo + 1).getName());
    	        	holder.tvQuantityTwo.setText(_data.get(position + indexTwo + 1).getProduct_qty());
				} catch (Exception e) {
					
				}
			}
        	
        	if (holder.tvQuantityOne.getText().equals("0") || holder.tvQuantityOne.getText().equals("")
        			|| holder.tvQuantityOne.getText() == null) {
        		holder.linColumOne.setVisibility(View.GONE);        	
			} else {
				holder.linColumOne.setVisibility(View.VISIBLE);        		
			}
        	
        	if (holder.tvQuantityTwo.getText().equals("0") || holder.tvQuantityTwo.getText().equals("")
        			|| holder.tvQuantityTwo.getText() == null) {
        		holder.linColumTwo.setVisibility(View.GONE);        
			} else {
				holder.linColumTwo.setVisibility(View.VISIBLE);        	
			}
		}
        
        ((ItemSubOrder) convertView).set_position(position);
       
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvDotOne, tvNameOne, tvQuantityOne, tvNameTwo, tvQuantityTwo, tvDotTwo;
        LinearLayout linColumOne, linColumTwo; 
    }
}
