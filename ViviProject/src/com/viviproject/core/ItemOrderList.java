package com.viviproject.core;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemOrderList extends BaseLinearLayout{
	private int _position;	
	private OnClickListener _onItemClick, _onCheckboxClick;
	private CheckBox checkboxDeliver;
	private TextView tvEdit;
	
	public ItemOrderList(Context context)
	{
		super(context);
		initControl(R.layout.item_order_list, context);
		checkboxDeliver = (CheckBox) findViewById(R.id.checkboxDeliver);
		checkboxDeliver.setOnClickListener(onCheckboxClick);
		tvEdit = (TextView) findViewById(R.id.tvEdit);
		tvEdit.setOnClickListener(onItemClick);
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
                _onItemClick.onClick(ItemOrderList.this);
        }
    };
    
    public void setOnThisItemClickHandler(OnClickListener itemClick)
    {
        _onItemClick = itemClick;
    }
    
    OnClickListener onCheckboxClick = new OnClickListener() 
	{

        @Override
        public void onClick(View v)
        {
            if (_onCheckboxClick != null)
            	_onCheckboxClick.onClick(ItemOrderList.this);
        }
    };
    
    public void setOnCheckboxItemClickHandler(OnClickListener itemClick)
    {
    	_onCheckboxClick = itemClick;
    }
}
