package com.mileem.fragments;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.IPlaceableFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTransactionType extends Fragment implements IPlaceableFragment{
	
	private BootstrapButton bb_operation_buy, bb_operation_rent, bb_operation_all;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_operation_type,
	                container, false);
			
			bb_operation_buy = (BootstrapButton) rootView.findViewById(R.id.bb_operation_buy);
			bb_operation_rent = (BootstrapButton) rootView.findViewById(R.id.bb_operation_rent);
			bb_operation_all = (BootstrapButton) rootView.findViewById(R.id.bb_operation_all);
			
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
