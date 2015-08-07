package com.viviproject.projection;

import com.viviproject.entities.EnProduct;

import android.support.v4.app.Fragment;

public class FrgBaseFragmentProducts extends Fragment{
	protected EnProduct enProduct;
	
	public String getFragmentName(){
		return enProduct.getName();
	}
	
}
