package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mileem.R;
import com.mileem.IPlaceableFragment;

public class FragmentHousingType extends Fragment implements IPlaceableFragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

//		container.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50));
			View view = inflater.inflate(R.layout.fragment_housing_type,
	                container, false);
			return view;
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
