package com.viviproject.projection;

import com.viviproject.entities.EnProduct;
import com.viviproject.ultilities.StringUtils;

import android.support.v4.app.Fragment;

public class FrgBaseFragmentProducts extends Fragment{
	protected EnProduct enProduct;
	
	public String getFragmentName(){
		String name = enProduct.getName();
		if(StringUtils.isBlank(name) || name.length() <= 4){
			name = "  " + name + "  ";
		}
		return enProduct.getName();
	}
	
	public EnProduct getEnProduct(){
		return enProduct;
	}
}
