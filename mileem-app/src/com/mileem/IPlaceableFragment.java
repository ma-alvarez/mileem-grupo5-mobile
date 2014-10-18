package com.mileem;

import android.support.v4.app.Fragment;

public interface IPlaceableFragment {

	public int getTargetContainer();
	public Fragment getFragment();
	public void setDefault();
}
