package com.viviproject.adapter;

import java.util.List;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.customerline.ItemEditCustomer;
import com.viviproject.entities.EnStaff;

public class EditStaffAdapter extends BaseAdapter {
	private List<EnStaff> _data;
    private EnStaff items;
    private Activity mActivity;
    private OnClickListener _onItemClick;  
    private TextWatcher _TextWatcher, _TextWatcherName, _TextWatcherPhone, _TextWatcherBirthday;   
	
    public EditStaffAdapter(Activity activity, List<EnStaff> data)
	{
		 mActivity = activity;
        _data = data;      
	}
    
    @Override
	public int getCount()
	{
		 return (_data == null ? 0 : _data.size());
	}

	@Override
	public Object getItem(int position) 
	{		
		return position;
	}

	@Override
	public long getItemId(int position)
	{		
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
        if (convertView == null)
        {
            convertView = new ItemEditCustomer(mActivity.getApplicationContext());
            ((ItemEditCustomer) convertView).setOnThisItemClickHandler(onItemClickHandler);
            ((ItemEditCustomer) convertView).setTextChangedHandler(textWatcher);
            ((ItemEditCustomer) convertView).setTextChangedNameHandler(textWatcherName);
            ((ItemEditCustomer) convertView).setTextChangedPhoneHandler(textWatcherPhone);
            ((ItemEditCustomer) convertView).setTextChangedBirthDayHandler(textWatcherBirthDay);
            
            holder = new ViewHolder();
            holder.tvName = (EditText) convertView.findViewById(R.id.tvName);
            holder.tvPhone = (EditText) convertView.findViewById(R.id.tvPhone);
            holder.tvBirthDay = (EditText) convertView.findViewById(R.id.tvBirthDay);
            holder.tvRole = (TextView) convertView.findViewById(R.id.tvRole);
            holder.tvNote = (EditText) convertView.findViewById(R.id.tvNote);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {
        	holder.tvName.setText(items.getFullname());
        	holder.tvPhone.setText(items.getPhone());
        	holder.tvBirthDay.setText(items.getBirthday());
        	holder.tvRole.setText(items.getRole());
        	holder.tvNote.setText(items.getNote());
		}
        
        ((ItemEditCustomer) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
		TextView tvRole;
        EditText tvName, tvPhone, tvBirthDay, tvNote;
        Button btnDelete;
    }
	
	OnClickListener onItemClickHandler = new OnClickListener() 
	{

        @Override
        public void onClick(View v)
        {
            if (_onItemClick != null)
                _onItemClick.onClick(v);
        }
    };
    
    public void setOnItemClickHandler(OnClickListener itemClick)
    {
        _onItemClick = itemClick;
    }
    
    TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
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
