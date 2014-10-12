package com.mileem.fragments;

import com.example.mileem.R;
import com.mileem.IPlaceableFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentEjemplo extends Fragment implements IPlaceableFragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

			View view = inflater.inflate(R.layout.ejemplo,
	                container, false);
			return view;
	}

	@Override
	public int getContainer() {
		return R.id.edit_mode_fragment_container_full;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
}
