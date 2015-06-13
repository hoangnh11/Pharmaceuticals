package com.viviproject.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.viviproject.projection.FrgBaseFragmentProducts;
import com.viviproject.projection.InfFrgProducts;

public class AdapterFrgProducts extends FragmentPagerAdapter{
	ArrayList<Fragment> listFragment = new ArrayList<Fragment>();
	FragmentManager oFragmentManager;
	
	public AdapterFrgProducts(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		this.listFragment = list;
		oFragmentManager = fm;
	}

	@Override
	public Fragment getItem(int position) {
		if(position < 0 || null == listFragment || listFragment.size() == 0){
			return null;
		}
		return listFragment.get(position);
	}
	
	/**
	 * get all objects in list fragment
	 * @return
	 */
	public ArrayList<Fragment> getListFragment() {
		return listFragment;
	}
	
	/**
	 * set list Fragment
	 * @param fragment
	 */
	public void setListFragment(ArrayList<Fragment> fragment) {
		if(this.listFragment != null){
    		FragmentTransaction ft	 = oFragmentManager.beginTransaction();
    		for(Fragment f:listFragment){
    			ft.remove(f);
    		}
    		ft.commit();
    		ft= null;
    		this.listFragment = fragment;
    		//notifyDataSetChanged();
    	} else {
    		this.listFragment = fragment;
    	}
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
	 */
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return listFragment.size();
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
		String strReturn = "";
		try{
			Fragment mFragment = getItem(position);
			if(mFragment instanceof FrgBaseFragmentProducts){
				strReturn = ((FrgBaseFragmentProducts)mFragment).getFragmentName();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return strReturn;
    }
}
