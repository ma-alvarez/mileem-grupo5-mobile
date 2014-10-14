package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mileem.R;
import com.mileem.IPlaceableFragment;

public class AreaFragment extends Fragment implements IPlaceableFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_area, container, false);
		return rootView;
		
	}
	
	
	@Override
	public int getContainer() {
		return R.id.edit_mode_fragment_container_small;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}

}
