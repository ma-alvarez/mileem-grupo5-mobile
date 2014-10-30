package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mileem.ImageLoader;
import com.mileem.R;
import com.mileem.tasks.BitmapWorkerTask;

public class PublicationSlidesFragment extends Fragment {

	private String image_url;
	private ImageView image;
	private ImageLoader mImageLoader;
	
	
	public PublicationSlidesFragment(String url) {
		image_url = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            			 Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.image_detailpublication, container, false);
	    
	    image = (ImageView) itemView.findViewById(R.id.imageView);
	    
	    //mImageLoader = new ImageLoader(getActivity());
	    ImageLoader.displayImage(image_url, image, 1600, 1000);
//	    BitmapWorkerTask task = new BitmapWorkerTask(image, false);
//	    task.execute(image_url);
	 
	    return itemView;
	}

}
