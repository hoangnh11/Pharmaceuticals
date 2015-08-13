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
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.deliver.ItemListviewReturnImport;
import com.viviproject.deliver.OrderImportActivity;
import com.viviproject.entities.EnProductBasket;
import com.viviproject.entities.EnProducts;
import com.viviproject.entities.Products;

public class ReturnImportAdapter extends BaseAdapter{
	private Products _data;
    private EnProducts items;
    private Activity mActivity;
    private OnClickListener _onMinusClick, _onPlusClick;
    private EnProductBasket enProductBasket;
	
    public ReturnImportAdapter(Activity activity, Products data) 
	{
		 mActivity = activity;
        _data = data;
        enProductBasket = new EnProductBasket();
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
            convertView = new ItemListviewReturnImport(mActivity.getApplicationContext());       
            ((ItemListviewReturnImport) convertView).setOnMinusClickHandler(onMinusClick);
            ((ItemListviewReturnImport) convertView).setOnPlusClickHandler(onPlusClick);
           
            holder = new ViewHolder();         
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvQuantity = (EditText) convertView.findViewById(R.id.tvQuantity);           
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
							OrderImportActivity.enProducts.getProducts().get(holder.ref).setUnit(s.toString());
							
							enProductBasket = new EnProductBasket();
			    			enProductBasket.setProduct_id(Integer.parseInt
			    					(OrderImportActivity.enProducts.getProducts().get(holder.ref).getId()));
							enProductBasket.setQuantity(Integer.parseInt
									(OrderImportActivity.enProducts.getProducts().get(holder.ref).getUnit()));
							OrderImportActivity.arrProductBasket.set(holder.ref, enProductBasket);
						} else {
							_data.getProducts().get(holder.ref).setUnit("0");
							OrderImportActivity.enProducts.getProducts().get(holder.ref).setUnit("0");
							
							enProductBasket = new EnProductBasket();
			    			enProductBasket.setProduct_id(Integer.parseInt
			    					(OrderImportActivity.enProducts.getProducts().get(holder.ref).getId()));
							enProductBasket.setQuantity(Integer.parseInt
									(OrderImportActivity.enProducts.getProducts().get(holder.ref).getUnit()));
							OrderImportActivity.arrProductBasket.set(holder.ref, enProductBasket);
						}
					}
				});
			}
		}
        
        ((ItemListviewReturnImport) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {            
        TextView tvName;
        EditText tvQuantity;
        ImageView imgMinus, imgPlus;
        int ref;
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
