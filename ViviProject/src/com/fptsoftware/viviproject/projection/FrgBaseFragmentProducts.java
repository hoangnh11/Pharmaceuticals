package com.fptsoftware.viviproject.projection;

import android.support.v4.app.Fragment;

public class FrgBaseFragmentProducts extends Fragment{
	protected String frgName;
	
	public String getFragmentName(){
		return frgName;
	}
	
	public void setFragmentName(String frgName){
		this.frgName = frgName;
	}
}
