package com.mileem;

import android.view.View;
import android.widget.ImageView;

import com.mileem.ListPublicacionesViewAdapter.ViewHolder;



public class PublicationPremiumAdapter extends PublicationAdapter{

	@Override
	public int getLayoutId() {
		return R.layout.listview_item_premium;
	}

	@Override
	public void addImages(ViewHolder viewHolder, View v) {
		int images_number = mPublication.getListImagesURL().size();
		if (images_number >= 4) images_number = 4;
		
		switch (images_number) {
		case 4:
			viewHolder.icon3 = (ImageView) v.findViewById(R.id.house_thumbnail_4); 
			ImageLoader.displayImage(mPublication.getImageURLAtIndex(3), viewHolder.icon3, 100, 100);
		case 3:
			viewHolder.icon2 = (ImageView) v.findViewById(R.id.house_thumbnail_3); 
			ImageLoader.displayImage(mPublication.getImageURLAtIndex(2), viewHolder.icon2, 100, 100);
		case 2:
			viewHolder.icon1 = (ImageView) v.findViewById(R.id.house_thumbnail_2);
			ImageLoader.displayImage(mPublication.getImageURLAtIndex(1), viewHolder.icon1, 100, 100);
		case 1:
			ImageLoader.displayImage(mPublication.getImageURLAtIndex(0), viewHolder.main_icon, 1600, 1000);
			break;
		}
		
	}

}
