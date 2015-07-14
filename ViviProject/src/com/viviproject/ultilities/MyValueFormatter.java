package com.viviproject.ultilities;

import java.text.DecimalFormat;

import com.github.mikephil.charting.utils.ValueFormatter;

public class MyValueFormatter implements ValueFormatter {
	
	private DecimalFormat mFormat;
	
	public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0");
    }
	
	@Override
	public String getFormattedValue(float value) {
		return mFormat.format(value) + "%";
	}

}
