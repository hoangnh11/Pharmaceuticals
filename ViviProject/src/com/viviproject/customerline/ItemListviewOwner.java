package com.viviproject.customerline;

import android.content.Context;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemListviewOwner extends BaseLinearLayout{
	private int _position;	

	public ItemListviewOwner(Context context)
	{
		super(context);
		initControl(R.layout.item_listview_owner, context);		
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
