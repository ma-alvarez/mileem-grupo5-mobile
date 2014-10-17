package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.IPlaceableFragment;

public class FragmentHousingType extends Fragment implements IPlaceableFragment{
	
	private BootstrapButton bb_housing_house, bb_housing_appartment, bb_housing_all;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_housing_type,
	                container, false);
			
			bb_housing_house = (BootstrapButton) rootView.findViewById(R.id.bb_housing_house);
			bb_housing_appartment = (BootstrapButton) rootView.findViewById(R.id.bb_housing_appartment);
			bb_housing_all = (BootstrapButton) rootView.findViewById(R.id.bb_housing_all);
			
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
