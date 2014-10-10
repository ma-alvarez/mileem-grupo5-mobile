package com.mileem;


import java.util.ArrayList;

import com.example.mileem.R;
import com.mileem.fragments.PublicationSlidesFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

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
		// TODO Auto-generated method stub
		return new PublicationSlidesFragment(Url_images.get(arg0));
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Url_images.size();
	}

}
