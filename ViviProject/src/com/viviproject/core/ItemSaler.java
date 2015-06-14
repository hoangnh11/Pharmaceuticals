package com.viviproject.core;

import android.content.Context;
import android.view.View;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemSaler extends BaseLinearLayout{
	private int _position;	
	private OnClickListener _onItemClick;
	
	public ItemSaler(Context context)
	{
		super(context);
		initControl(R.layout.item_list_saler, context);		
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
                _onItemClick.onClick(ItemSaler.this);
        }
    };
    
    public void setOnThisItemClickHandler(OnClickListener itemClick)
    {
        _onItemClick = itemClick;
    }
}
