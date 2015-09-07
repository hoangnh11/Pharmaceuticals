package com.viviproject.deliver;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnOrder;
import com.viviproject.entities.EnProductSales;
import com.viviproject.entities.EnProducts;
import com.viviproject.ultilities.GlobalParams;

public class ChangeOrderAdapter extends BaseAdapter{
	private ArrayList<EnProducts> _products;
	private EnOrder _data;
    private EnProductSales items;
    private Activity mActivity;
    private OnClickListener _onTDClick, _onCKClick, _onOtherClick, _onMinusClick, _onPlusClick;   
	
    public ChangeOrderAdapter(Activity activity, EnOrder data, ArrayList<EnProducts> products) 
	{
		 mActivity = activity;
        _data = data;
        _products = products;
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
            convertView = new ItemListViewOrder(mActivity.getApplicationContext());
            ((ItemListViewOrder) convertView).setOnTDClickHandler(onTDClickHandler);
            ((ItemListViewOrder) convertView).setOnCKClickHandler(onCKClickHandler);
            ((ItemListViewOrder) convertView).setOnOtherClickHandler(onOtherClickHandler);
            ((ItemListViewOrder) convertView).setOnMinusClickHandler(onMinusClick);
            ((ItemListViewOrder) convertView).setOnPlusClickHandler(onPlusClick);
           
            holder = new ViewHolder();
            holder.linHeader = (LinearLayout) convertView.findViewById(R.id.linHeader);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvOldQuantity = (TextView) convertView.findViewById(R.id.tvOldQuantity);
            holder.tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
            holder.imgTD = (ImageView) convertView.findViewById(R.id.imgTD);
            holder.imgCK = (ImageView) convertView.findViewById(R.id.imgCK);
            holder.imgOther = (ImageView) convertView.findViewById(R.id.imgOther);
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
        	
        	if (position == 0) {
        		holder.linHeader.setVisibility(View.VISIBLE);
			} else {
				holder.linHeader.setVisibility(View.GONE);
			}
        	
        	holder.tvName.setText(items.getName());

    		for (int i = 0; i < _products.size(); i++) {
				if (items.getCode() != null && items.getCode().equals(_products.get(i).getCode())) {
					if (_products.get(i).getColor() != null) {
						try {
			    			holder.tvName.setBackgroundColor(Color.parseColor("#"+_products.get(i).getColor()));
						} catch (Exception e) {
							holder.tvName.setBackgroundColor(mActivity.getResources().getColor(R.color.GREEN));
						}
					} else {
						holder.tvName.setBackgroundColor(mActivity.getResources().getColor(R.color.GREEN));
					}
					
					if (_products.get(i).getDiscount() != null) {
		        		if (_products.get(i).getDiscount().getPoint() != null) {
		            		if (_products.get(i).getCheckTD() != null) {
		                    	if (_products.get(i).getCheckTD().equals(GlobalParams.TRUE)) {
		                    		holder.imgTD.setImageResource(R.drawable.checkbox_true);
		        				} else {
		        					holder.imgTD.setImageResource(R.drawable.checkbox_false);
		        				}
		        			}
		    			}
		            	
		            	if (_products.get(i).getDiscount().getSale() != null) {
		            		if (_products.get(i).getCheckCK() != null) {
		                    	if (_products.get(i).getCheckCK().equals(GlobalParams.TRUE)) {
		                    		holder.imgCK.setImageResource(R.drawable.checkbox_true);
		        				} else {
		        					holder.imgCK.setImageResource(R.drawable.checkbox_false);
		        				}
		        			}         		
		    			}
		            	
		            	if (_products.get(i).getDiscount().getOther() != null) {
		            		if (_products.get(i).getCheckOther() != null) {
		                    	if (_products.get(i).getCheckOther().equals(GlobalParams.TRUE)) {
		                    		holder.imgOther.setImageResource(R.drawable.checkbox_true);                        		
		        				} else {
		        					holder.imgOther.setImageResource(R.drawable.checkbox_false);        					
		        				}
		        			}         		
		    			}
					}
				}
			}
        	
        	if (items.getProduct_qty() != null) {
    			holder.tvOldQuantity.setText(items.getProduct_qty());       		
			}
        	
        	if (items.getTempProductQty() != null) {
        		holder.tvQuantity.setText(items.getTempProductQty());
			}        	
		}
        
        ((ItemListViewOrder) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        LinearLayout linHeader;
        TextView tvName, tvQuantity, tvOldQuantity;
        ImageView imgTD, imgCK, imgOther, imgMinus, imgPlus;
    }
	
	OnClickListener onTDClickHandler = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
            if (_onTDClick != null)
            	_onTDClick.onClick(v);
        }
    };
    
    public void setOnTDClickHandler(OnClickListener itemClick)
    {
    	_onTDClick = itemClick;
    }
    
    OnClickListener onCKClickHandler = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
            if (_onCKClick != null)
            	_onCKClick.onClick(v);
        }
    };
    
    public void setOnCKClickHandler(OnClickListener itemClick)
    {
    	_onCKClick = itemClick;
    }
    
    OnClickListener onOtherClickHandler = new OnClickListener() 
	{
        @Override
        public void onClick(View v)
        {
            if (_onOtherClick != null)
            	_onOtherClick.onClick(v);
        }
    };
    
    public void setOnOtherClickHandler(OnClickListener itemClick)
    {
    	_onOtherClick = itemClick;
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
