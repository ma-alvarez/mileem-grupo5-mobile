package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mileem.ImageLoader;
import com.mileem.R;

public class PublicationSlidesFragment extends Fragment {

	private String image_url;
	private ImageView image;
	
	
	public static final PublicationSlidesFragment newInstance(String url){
		PublicationSlidesFragment fragment = new PublicationSlidesFragment();
	    Bundle bundle = new Bundle(1);
	    bundle.putString("url", url);
	    fragment.setArguments(bundle);
	    return fragment ;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		image_url = getArguments().getString("url");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            			 Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.image_detailpublication, container, false);
	    
	    image = (ImageView) itemView.findViewById(R.id.imageView);
	    
	    ImageLoader.displayImage(image_url, image, 1600, 1000);
	 
	    return itemView;
	}

}
