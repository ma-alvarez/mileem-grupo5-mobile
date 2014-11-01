package com.mileem;

import android.view.View;

import com.mileem.ListPublicacionesViewAdapter.ViewHolder;


public class PublicationFreeAdapter extends PublicationAdapter{

	@Override
	public int getLayoutId() {
		return R.layout.listview_item_free;
	}

	@Override
	public void addImages(ViewHolder viewHolder, View v) {

		if(mPublication.getListImagesURL().size() > 0){
			ImageLoader.displayImage(mPublication.getImageURLAtIndex(0), viewHolder.main_icon, 100,100);
		}

	}
}
