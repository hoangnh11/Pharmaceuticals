package com.viviproject.visit;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemListViewForsale extends BaseLinearLayout{
	private int _position;	
	private OnClickListener _onItemClick;
	private LinearLayout linItemListView;
	
	public ItemListViewForsale(Context context)
	{
		super(context);
		initControl(R.layout.item_listview_forsale, context);
//		linItemListView = (LinearLayout) findViewById(R.id.linItemListView);
//		linItemListView.setOnClickListener(onItemClick);
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
                _onItemClick.onClick(ItemListViewForsale.this);
        }
    };
    
    public void setOnThisItemClickHandler(OnClickListener itemClick)
    {
        _onItemClick = itemClick;
    }
}
