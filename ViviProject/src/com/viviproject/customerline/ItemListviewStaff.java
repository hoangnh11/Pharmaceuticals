package com.viviproject.customerline;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemListviewStaff extends BaseLinearLayout{
	private int _position;	
	private OnClickListener _onItemClick;
	private Button btnDelete;

	public ItemListviewStaff(Context context)
	{
		super(context);
		initControl(R.layout.item_listview_staff, context);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnDelete.setOnClickListener(onItemClick);
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
                _onItemClick.onClick(ItemListviewStaff.this);
        }
    };
    
    public void setOnThisItemClickHandler(OnClickListener itemClick)
    {
        _onItemClick = itemClick;
    }
}
