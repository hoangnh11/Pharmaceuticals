package com.viviproject.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.Products;
import com.viviproject.ultilities.GlobalParams;
import com.viviproject.visit.ItemListViewForsale;
import com.viviproject.visit.PlaceOrderActivity;

public class ForsaleAdapter extends BaseAdapter{
	private Products _data;
    private EnProducts items;
    private Activity mActivity;
    private OnClickListener _onTDClick, _onCKClick, _onOtherClick, _onMinusClick, _onPlusClick;
	
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
		final ViewHolder holder;
        if (convertView == null)
        {
            convertView = new ItemListViewForsale(mActivity.getApplicationContext());
            ((ItemListViewForsale) convertView).setOnTDClickHandler(onTDClickHandler);
            ((ItemListViewForsale) convertView).setOnCKClickHandler(onCKClickHandler);
            ((ItemListViewForsale) convertView).setOnOtherClickHandler(onOtherClickHandler);
            ((ItemListViewForsale) convertView).setOnMinusClickHandler(onMinusClick);
            ((ItemListViewForsale) convertView).setOnPlusClickHandler(onPlusClick);
           
            holder = new ViewHolder();
            holder.linHeader = (LinearLayout) convertView.findViewById(R.id.linHeader);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvQuantity = (EditText) convertView.findViewById(R.id.tvQuantity);
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
        holder.ref = position;
        
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
        	
        	if (items.getUnit() != null) {
        		holder.tvQuantity.setText(items.getUnit());
        		holder.tvQuantity.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,	int after) {						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						if (s.length() > 0) {								
							_data.getProducts().get(holder.ref).setUnit(s.toString());
							PlaceOrderActivity.enProducts.getProducts().get(holder.ref).setUnit(s.toString());
						} else {
							_data.getProducts().get(holder.ref).setUnit("0");
							PlaceOrderActivity.enProducts.getProducts().get(holder.ref).setUnit("0");
						}
					}
				});
			}
        	
        	if (items.getDiscount() != null) {
        		if (items.getDiscount().getPoint() != null) {
            		if (items.getCheckTD() != null) {
                    	if (items.getCheckTD().equals(GlobalParams.TRUE)) {
                    		holder.imgTD.setImageResource(R.drawable.checkbox_true);
        				} else {
        					holder.imgTD.setImageResource(R.drawable.checkbox_false);
        				}
        			}
    			}
            	
            	if (items.getDiscount().getSale() != null) {
            		if (items.getCheckCK() != null) {
                    	if (items.getCheckCK().equals(GlobalParams.TRUE)) {
                    		holder.imgCK.setImageResource(R.drawable.checkbox_true);
        				} else {
        					holder.imgCK.setImageResource(R.drawable.checkbox_false);
        				}
        			}         		
    			}
            	
            	if (items.getDiscount().getOther() != null) {
            		if (items.getCheckOther() != null) {
                    	if (items.getCheckOther().equals(GlobalParams.TRUE)) {
                    		holder.imgOther.setImageResource(R.drawable.checkbox_true);                        		
        				} else {
        					holder.imgOther.setImageResource(R.drawable.checkbox_false);        					
        				}
        			}         		
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
        EditText tvQuantity;
        ImageView imgTD, imgCK, imgOther, imgMinus, imgPlus;
        int ref;
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
