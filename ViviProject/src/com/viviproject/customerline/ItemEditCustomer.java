package com.viviproject.customerline;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.viviproject.R;
import com.viviproject.ultilities.BaseLinearLayout;

public class ItemEditCustomer extends BaseLinearLayout {
	private int _position;
	private OnClickListener _onItemClick;
	private Button btnDelete;
	private EditText edtNote;
	private TextWatcher _TextWatcher;	

	public ItemEditCustomer(Context context) {
		super(context);
		initControl(R.layout.item_edit_customer, context);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnDelete.setOnClickListener(onItemClick);
		edtNote = (EditText) findViewById(R.id.tvNote);
		edtNote.addTextChangedListener(textWatcher);		
	}

	/**
	 * @return the _position
	 */
	public int get_position() {
		return _position;
	}

	/**
	 * @param _position
	 *            the _position to set
	 */
	public void set_position(int _position) {		
		this._position = _position;
	}

	OnClickListener onItemClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (_onItemClick != null)
				_onItemClick.onClick(ItemEditCustomer.this);
		}
	};

	public void setOnThisItemClickHandler(OnClickListener itemClick) {
		_onItemClick = itemClick;
	}
	
	TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			EditCustomer.indexEditCustomer = _position;
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
