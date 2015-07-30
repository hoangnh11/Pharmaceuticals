package com.viviproject.visit;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemListviewGimic extends BaseLinearLayout{
	private int _position;	
	private OnClickListener _onMinusClick, _onPlusClick;
	private ImageView imgMinus, imgPlus;
	
	public ItemListviewGimic(Context context)
	{
		super(context);
		initControl(R.layout.item_listview_gimic, context);

		imgMinus = (ImageView) findViewById(R.id.imgMinus);
		imgPlus = (ImageView) findViewById(R.id.imgPlus);
		
		imgMinus.setOnClickListener(onMinusClick);
		imgPlus.setOnClickListener(onPlusClick);
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
    
    OnClickListener onMinusClick = new OnClickListener() 
	{

        @Override
        public void onClick(View v)
        {
            if (_onMinusClick != null)
            	_onMinusClick.onClick(ItemListviewGimic.this);
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
            	_onPlusClick.onClick(ItemListviewGimic.this);
        }
    };
    
    public void setOnPlusClickHandler(OnClickListener itemClick)
    {
    	_onPlusClick = itemClick;
    }
}
