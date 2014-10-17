package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.IPlaceableFragment;

public class RoomsFragment extends Fragment implements IPlaceableFragment {

	private BootstrapButton bb_1room, bb_2room, bb_3room, bb_4room, bb_allrooms;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);
		
		bb_1room = (BootstrapButton) rootView.findViewById(R.id.bb_1room);
		bb_2room = (BootstrapButton) rootView.findViewById(R.id.bb_2room);
		bb_3room = (BootstrapButton) rootView.findViewById(R.id.bb_3room);
		bb_4room = (BootstrapButton) rootView.findViewById(R.id.bb_4room);
		bb_allrooms = (BootstrapButton) rootView.findViewById(R.id.bb_allrooms);
		
		return rootView;
		
	}
	
	
	@Override
	public int getContainer() {
		return R.id.advanced_fragment_container_small;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}

}
