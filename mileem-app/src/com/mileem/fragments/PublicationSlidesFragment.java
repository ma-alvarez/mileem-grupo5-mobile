package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mileem.R;
import com.mileem.tasks.BitmapWorkerTask;

public class PublicationSlidesFragment extends Fragment {

	private String image_url;
	private ImageView image;
	private BitmapWorkerTask task;
	
	
	public PublicationSlidesFragment(String url) {
		// TODO Auto-generated constructor stub
		image_url = url;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            			 Bundle savedInstanceState) {
		

        View itemView = inflater.inflate(R.layout.image_detailpublication, container, false);
	    
	    image = (ImageView) itemView.findViewById(R.id.imageView);
	    
	    //ImageView image = new ImageView(getActivity());
	    task = new BitmapWorkerTask(image, false);
	    task.execute(image_url);
	    //image.setImageResource(imageResourceId);
	    //LinearLayout layout = new LinearLayout(getActivity());
	    //layout.setLayoutParams(new LayoutParams());

	    //layout.setGravity(Gravity.CENTER);
	    //layout.addView(picture);

	    //return layout;
	    return itemView;
	}

}
