package com.viviproject.projection;

import android.support.v4.app.Fragment;

import com.viviproject.entities.EnDiscountProgramItem;

public class FrgBaseFragmentPromotions extends Fragment{

	protected EnDiscountProgramItem enDiscountProgram;
	
	public String getFragmentName(){
		return enDiscountProgram.getName();
	}
}
