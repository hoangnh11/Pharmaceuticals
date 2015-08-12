package com.viviproject.visit;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemListViewInventory extends BaseLinearLayout{
	private int _position;	
	private OnClickListener _onMinusClick, _onPlusClick;
	private ImageView imgMinus, imgPlus;
	private EditText tvQuantity;
	private TextWatcher _TextWatcher;
	
	public ItemListViewInventory(Context context)
	{
		super(context);
		initControl(R.layout.item_listview_tonkho, context);

		imgMinus = (ImageView) findViewById(R.id.imgMinus);
		imgPlus = (ImageView) findViewById(R.id.imgPlus);
		
		imgMinus.setOnClickListener(onMinusClick);
		imgPlus.setOnClickListener(onPlusClick);
		
		tvQuantity = (EditText) findViewById(R.id.tvQuantity);
		tvQuantity.addTextChangedListener(textWatcher);
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
            	_onMinusClick.onClick(ItemListViewInventory.this);
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
            	_onPlusClick.onClick(ItemListViewInventory.this);
        }
    };
    
    public void setOnPlusClickHandler(OnClickListener itemClick)
    {
    	_onPlusClick = itemClick;
    }
	    
    TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			VisitDetailsActivity.indexWareHouse = _position - 1;
			if (_TextWatcher != null) {
				_TextWatcher.onTextChanged(s, start, before, count);
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {			
		}
		
		@Override
		public void afterTextChanged(Editable s) {			
		}
	};
	
	public void setTextChangedHandler(TextWatcher onTextChanged) {
		_TextWatcher = onTextChanged;
	}
}
