package com.fptsoftware.library;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class BaseLinearLayout extends LinearLayout{
	
	public BaseLinearLayout(Context context)
    {
        super(context);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }

    protected void initControl(int reID, Context context)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(reID, this, true);
    }

    public Activity getActivity()
    {
        return (Activity)this.getContext();
    }
}
