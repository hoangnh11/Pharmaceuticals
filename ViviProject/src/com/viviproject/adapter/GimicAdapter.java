package com.viviproject.adapter;

import android.app.Activity;
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
import com.viviproject.entities.EnElement;
import com.viviproject.entities.EnGimic;
import com.viviproject.entities.EnGimicBasket;
import com.viviproject.visit.GiveGimic;
import com.viviproject.visit.ItemListviewGimic;

public class GimicAdapter extends BaseAdapter{
	private EnElement _data, temp;
    private EnGimic items;
    private Activity mActivity;
    private OnClickListener _onMinusClick, _onPlusClick;
    private EnGimicBasket enGimicBasket;;
	
    public GimicAdapter(Activity activity, EnElement data) 
	{
		 mActivity = activity;
        _data = data;
        temp = data;
        enGimicBasket = new EnGimicBasket();
	}
    
    @Override
	public int getCount()
	{
		 return (_data == null ? 0 : _data.getElements().size());
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
            convertView = new ItemListviewGimic(mActivity.getApplicationContext());
            ((ItemListviewGimic) convertView).setOnMinusClickHandler(onMinusClick);
            ((ItemListviewGimic) convertView).setOnPlusClickHandler(onPlusClick);
        
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
        
        items = _data.getElements().get(position);
        holder.ref = position;
        
        if (items != null) {
        	holder.tvName.setText(items.getName());
        	        	
        	if (items.getUnit() != null) {
        		holder.tvQuantity.setText(temp.getElements().get(holder.ref).getUnit());
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
							try {
								int val = Integer.parseInt(s.toString());
								if (val > Integer.parseInt(GiveGimic.elements.getElements().get(holder.ref).getQuantity_max())) {
									s.replace(0, s.length(), GiveGimic.elements.getElements().get(holder.ref).getQuantity_max(), 0, 2);									
								} else if (val < 0) {
									s.replace(0, s.length(), "0", 0, 1);									
								}
								
								temp.getElements().get(holder.ref).setUnit(s.toString());
								GiveGimic.elements.getElements().get(holder.ref).setUnit(s.toString());
								
								enGimicBasket = new EnGimicBasket();
								enGimicBasket.setGimic_id(Integer.parseInt(GiveGimic.elements.getElements().get(holder.ref).getId()));
								enGimicBasket.setQuantity(Integer.parseInt(GiveGimic.elements.getElements().get(holder.ref).getUnit()));
								GiveGimic.arrGimicBasket.set(holder.ref, enGimicBasket);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						} else {
							temp.getElements().get(holder.ref).setUnit("0");
							GiveGimic.elements.getElements().get(holder.ref).setUnit("0");	
							
							enGimicBasket = new EnGimicBasket();
							enGimicBasket.setGimic_id(Integer.parseInt(GiveGimic.elements.getElements().get(holder.ref).getId()));
							enGimicBasket.setQuantity(Integer.parseInt(GiveGimic.elements.getElements().get(holder.ref).getUnit()));
							GiveGimic.arrGimicBasket.set(holder.ref, enGimicBasket);
						}						
					}
				});
			}
		}
        
        ((ItemListviewGimic) convertView).set_position(position);
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
