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
	private EditText edtNote, edtName, edtPhone, edtBirthday;
	private TextWatcher _TextWatcher, _TextWatcherName, _TextWatcherPhone, _TextWatcherBirthday;	

	public ItemEditCustomer(Context context) {
		super(context);
		initControl(R.layout.item_edit_customer, context);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnDelete.setOnClickListener(onItemClick);
		edtNote = (EditText) findViewById(R.id.tvNote);
		edtNote.addTextChangedListener(textWatcher);
		edtName = (EditText) findViewById(R.id.tvName);
		edtName.addTextChangedListener(textWatcherName);
		edtPhone = (EditText) findViewById(R.id.tvPhone);
		edtPhone.addTextChangedListener(textWatcherPhone);
		edtBirthday = (EditText) findViewById(R.id.tvBirthDay);
		edtBirthday.addTextChangedListener(textWatcherBirthDay);
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
	
	TextWatcher textWatcherName = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			EditCustomer.indexEditCustomer = _position;
			if (_TextWatcherName != null) {
				_TextWatcherName.onTextChanged(s, start, before, count);
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	public void setTextChangedNameHandler(TextWatcher onTextChanged) {
		_TextWatcherName = onTextChanged;
	}
	
	TextWatcher textWatcherPhone = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			EditCustomer.indexEditCustomer = _position;
			if (_TextWatcherPhone != null) {
				_TextWatcherPhone.onTextChanged(s, start, before, count);
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	public void setTextChangedPhoneHandler(TextWatcher onTextChanged) {
		_TextWatcherPhone = onTextChanged;
	}
	
	TextWatcher textWatcherBirthDay = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			EditCustomer.indexEditCustomer = _position;
			if (_TextWatcherBirthday != null) {
				_TextWatcherBirthday.onTextChanged(s, start, before, count);
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	public void setTextChangedBirthDayHandler(TextWatcher onTextChanged) {
		_TextWatcherBirthday = onTextChanged;
	}
}
