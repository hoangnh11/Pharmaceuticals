package com.viviproject.deliver;

import java.util.ArrayList;

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
import com.viviproject.entities.EnOrder;
import com.viviproject.entities.EnProducts;
import com.viviproject.ultilities.GlobalParams;

public class ChangeOrderAdapter extends BaseAdapter{
	private ArrayList<EnProducts> _products;
	private EnOrder _data;   
    private EnProducts itemProduct;
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
		 return (_products == null ? 0 : _products.size());
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

        itemProduct = _products.get(position);
        holder.ref = position;
        
        if (itemProduct != null) {
        	if (position == 0) {
        		holder.linHeader.setVisibility(View.VISIBLE);
			} else {
				holder.linHeader.setVisibility(View.GONE);
			}
        	
        	holder.tvName.setText(itemProduct.getName());
        	
        	if (itemProduct.getColor() != null) {
				try {
	    			holder.tvName.setBackgroundColor(Color.parseColor("#" + itemProduct.getColor()));
				} catch (Exception e) {
					holder.tvName.setBackgroundColor(mActivity.getResources().getColor(R.color.GREEN));
				}
			} else {
				holder.tvName.setBackgroundColor(mActivity.getResources().getColor(R.color.GREEN));
			}
        	
        	if (itemProduct.getDiscount() != null) {
        		if (itemProduct.getDiscount().getPoint() != null) {
            		if (itemProduct.getCheckTD() != null) {
                    	if (itemProduct.getCheckTD().equals(GlobalParams.TRUE)) {
                    		holder.imgTD.setImageResource(R.drawable.checkbox_true);
        				} else {
        					holder.imgTD.setImageResource(R.drawable.checkbox_false);
        				}
        			}
    			}
            	
            	if (itemProduct.getDiscount().getSale() != null) {
            		if (itemProduct.getCheckCK() != null) {
                    	if (itemProduct.getCheckCK().equals(GlobalParams.TRUE)) {
                    		holder.imgCK.setImageResource(R.drawable.checkbox_true);
        				} else {
        					holder.imgCK.setImageResource(R.drawable.checkbox_false);
        				}
        			}         		
    			}
            	
            	if (itemProduct.getDiscount().getOther() != null) {
            		if (itemProduct.getCheckOther() != null) {
                    	if (itemProduct.getCheckOther().equals(GlobalParams.TRUE)) {
                    		holder.imgOther.setImageResource(R.drawable.checkbox_true);                        		
        				} else {
        					holder.imgOther.setImageResource(R.drawable.checkbox_false);        					
        				}
        			}         		
    			}
			}
        	
        	if (itemProduct.getTempProductQty() != null) {
        		holder.tvQuantity.setText(itemProduct.getTempProductQty());
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
							_products.get(holder.ref).setTempProductQty(s.toString());
							ChangeOrderActivity.products.get(holder.ref).setTempProductQty(s.toString());
						} else {
							_products.get(holder.ref).setTempProductQty("0");
							ChangeOrderActivity.products.get(holder.ref).setTempProductQty("0");
						}
					}
				});
			}
        	
        	for (int i = 0; i < _data.getProducts().size(); i++) {
        		if (itemProduct.getId().equals(_data.getProducts().get(i).getProduct_id())) {
        			holder.tvOldQuantity.setText(_data.getProducts().get(i).getProduct_qty());
        		}
        	}
        	
//        	holder.tvQuantity.setText(itemProduct.getTempProductQty());
		}

        ((ItemListViewOrder) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        LinearLayout linHeader;
        EditText tvQuantity;
        TextView tvName, tvOldQuantity;
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
