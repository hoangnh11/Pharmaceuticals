package com.viviproject.deliver;

import android.content.Context;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemSubOrder extends BaseLinearLayout{
	private int _position;
	
	public ItemSubOrder(Context context)
	{
		super(context);
		initControl(R.layout.item_sub_order, context);
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
}
