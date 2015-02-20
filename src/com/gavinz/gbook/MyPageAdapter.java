package com.gavinz.gbook;

import java.util.List;
import android.support.v4.app.*;
import android.view.ViewGroup;

class MyPageAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
	super(fm);
	this.fragments = fragments;
	}
	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}
	 
	@Override
	public int getCount() {
	return this.fragments.size();
	}
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        BookPage.viewPagerMap.remove(position);
    }
}