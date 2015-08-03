package com.viviproject.core;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemDelivedOrder extends BaseLinearLayout{
	private int _position;	
	private OnClickListener _onItemClick;
	private TextView tvCancel;
	
	public ItemDelivedOrder(Context context)
	{
		super(context);
		initControl(R.layout.item_list_delived_order, context);
		tvCancel = (TextView) findViewById(R.id.tvCancel);
		tvCancel.setOnClickListener(onItemClick);
	}
	
	/**
	 * @return the _position
	 */
	public int get_position() 
	{
		return _position;
	}

	/**
	 * @param _position the _position to set
	 */
	public void set_position(int _position)
	{
		this._position = _position;
	}	
	
	OnClickListener onItemClick = new OnClickListener() 
	{

        @Override
        public void onClick(View v)
        {
            if (_onItemClick != null)
                _onItemClick.onClick(ItemDelivedOrder.this);
        }
    };
    
    public void setOnThisItemClickHandler(OnClickListener itemClick)
    {
        _onItemClick = itemClick;
    }    
}
