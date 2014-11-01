package com.mileem;


import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mileem.fragments.PublicationSlidesFragment;

public class PublicationSlidesFragmentAdapter extends FragmentStatePagerAdapter {

	 private ArrayList<String> Url_images;

	public PublicationSlidesFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public PublicationSlidesFragmentAdapter(
			FragmentManager fm, ArrayList<String> urls_image) {
		super(fm);
		Url_images = urls_image;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return new PublicationSlidesFragment(Url_images.get(arg0));
	}
	
	@Override
	public int getCount() {
		return Url_images.size();
	}

}
