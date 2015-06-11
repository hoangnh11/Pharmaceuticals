package com.fptsoftware.viviproject.projection;


public class FrgProducts extends FrgBaseFragmentProducts{
	
	public static FrgProducts newInstance(String frgName){
		FrgProducts f = new FrgProducts();
		f.frgName = frgName;
		return f;
	}
	
	@Override
	public String getFragmentName() {
		return super.getFragmentName();
	}
}
