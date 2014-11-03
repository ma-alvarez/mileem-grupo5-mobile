package com.mileem;


import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mileem.fragments.PublicationSlidesFragment;
import com.mileem.fragments.PublicationVideoFragment;

public class PublicationSlidesFragmentAdapter extends FragmentStatePagerAdapter {

	 private ArrayList<String> Url_images;
	 private String Url_video;

	public PublicationSlidesFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public PublicationSlidesFragmentAdapter(
			FragmentManager fm, ArrayList<String> urls_image, String url_video) {
		super(fm);
		Url_images = urls_image;
		Url_video = url_video;
	}
	
	@Override
	public Fragment getItem(int arg0) {

		if ( Url_images.size() == arg0 ) {
			return PublicationVideoFragment.newInstance(Url_video);
		}
		else return PublicationSlidesFragment.newInstance(Url_images.get(arg0));
		
	}
	
	@Override
	public int getCount() {
		return (Url_video.equals("null")) ? Url_images.size():Url_images.size()+1;
	}

}