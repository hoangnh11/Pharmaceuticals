package com.viviproject.visit;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemListViewForsale extends BaseLinearLayout{
	private int _position;	
	private OnClickListener _onTDClick, _onCKClick, _onOtherClick;
	private ImageView imgTD, imgCK, imgOther;
	
	public ItemListViewForsale(Context context)
	{
		super(context);
		initControl(R.layout.item_listview_forsale, context);

		imgTD = (ImageView) findViewById(R.id.imgTD);
		imgCK = (ImageView) findViewById(R.id.imgCK);
		imgOther = (ImageView) findViewById(R.id.imgOther);
		
		imgTD.setOnClickListener(onTDClick);
		imgCK.setOnClickListener(onCKClick);
		imgOther.setOnClickListener(onOtherClick);
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
	
	OnClickListener onTDClick = new OnClickListener() 
	{

        @Override
        public void onClick(View v)
        {
            if (_onTDClick != null)
            	_onTDClick.onClick(ItemListViewForsale.this);
        }
    };
    
    public void setOnTDClickHandler(OnClickListener itemClick)
    {
    	_onTDClick = itemClick;
    }
    
    OnClickListener onCKClick = new OnClickListener() 
	{

        @Override
        public void onClick(View v)
        {
            if (_onCKClick != null)
            	_onCKClick.onClick(ItemListViewForsale.this);
        }
    };
    
    public void setOnCKClickHandler(OnClickListener itemClick)
    {
    	_onCKClick = itemClick;
    }
    
    OnClickListener onOtherClick = new OnClickListener() 
	{

        @Override
        public void onClick(View v)
        {
            if (_onOtherClick != null)
            	_onOtherClick.onClick(ItemListViewForsale.this);
        }
    };
    
    public void setOnOtherClickHandler(OnClickListener itemClick)
    {
    	_onOtherClick = itemClick;
    }
}
