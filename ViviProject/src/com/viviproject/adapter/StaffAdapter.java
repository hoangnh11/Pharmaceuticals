package com.viviproject.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viviproject.R;
import com.viviproject.customerline.ItemListviewStaff;
import com.viviproject.entities.EnCreateStaff;
import com.viviproject.ultilities.BuManagement;
import com.viviproject.ultilities.GlobalParams;

public class StaffAdapter extends BaseAdapter{
	private List<EnCreateStaff> _data;
    private EnCreateStaff items;
    private Activity mActivity;
    private OnClickListener _onItemClick;
    private int index;
	
    public StaffAdapter(Activity activity, List<EnCreateStaff> data) 
	{
		 mActivity = activity;
        _data = data;
        index = 0;
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
            convertView = new ItemListviewStaff(mActivity.getApplicationContext());
            ((ItemListviewStaff) convertView).setOnThisItemClickHandler(onItemClickHandler);
                  
            holder = new ViewHolder();    
            holder.tvIndex = (TextView) convertView.findViewById(R.id.tvIndex);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
            holder.tvBirthDay = (TextView) convertView.findViewById(R.id.tvBirthDay);
            holder.tvRole = (TextView) convertView.findViewById(R.id.tvRole);
            holder.tvNote = (TextView) convertView.findViewById(R.id.tvNote);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        items = _data.get(position);
        
        if (items != null) {        
        	if (position == 0) {
        		index = 0;
			}
        	
        	index++;
        	
        	holder.tvIndex.setText(String.valueOf(index));
        	holder.tvName.setText(items.getFullname());
        	holder.tvPhone.setText(items.getPhone());
        	holder.tvBirthDay.setText(items.getBirthday());
        	holder.tvRole.setText(items.getRole());
        	holder.tvNote.setText(items.getNote());
        	
        	if (BuManagement.Instance.getCheckDelete(mActivity).toString().equals(GlobalParams.TRUE))
	        {            
        		holder.btnDelete.setVisibility(View.VISIBLE);				
			}
	        else
	        {
        		holder.btnDelete.setVisibility(View.GONE);
			}
		}
        
        ((ItemListviewStaff) convertView).set_position(position);
        return convertView;
	}
	
	static class ViewHolder
    {      
        TextView tvIndex, tvName, tvPhone, tvBirthDay, tvRole, tvNote;
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
}
