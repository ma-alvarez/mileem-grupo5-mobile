package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.IPlaceableFragment;

public class HousingTypeFragment extends Fragment implements IPlaceableFragment{
	
	private BootstrapButton bb_housing_house, bb_housing_appartment, bb_housing_all;
	private int button_index;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_housing_type,
	                container, false);
			
			bb_housing_house = (BootstrapButton) rootView.findViewById(R.id.bb_housing_house);
			bb_housing_appartment = (BootstrapButton) rootView.findViewById(R.id.bb_housing_appartment);
			bb_housing_all = (BootstrapButton) rootView.findViewById(R.id.bb_housing_all);
			
			setButtonsListeners();
			
			return rootView;
	}
	
	private void setButtonsListeners(){
		bb_housing_house.setOnTouchListener(new myOnTouchListener(0));
		bb_housing_appartment.setOnTouchListener(new myOnTouchListener(1));
		bb_housing_all.setOnTouchListener(new myOnTouchListener(2));
		
		setDefault();
	}
	
	private class myOnTouchListener implements OnTouchListener{

		private int index;
		
		public myOnTouchListener(int idx){
			index = idx;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			bb_housing_house.setPressed(false);
			bb_housing_appartment.setPressed(false);
			bb_housing_all.setPressed(false);
			
			v.setPressed(true);
			button_index = index;
			
			return true;
		}
		
	}

	@Override
	public int getTargetContainer() {
		return R.id.edit_mode_fragment_container_small;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
	
	public String toString(){
		return button_index < 2 ? ConfigManager.HOUS_TYPE.concat(ConfigManager.HOUS_TYPE_OPT[button_index]) : "";
	}

	@Override
	public void setDefault() {
		bb_housing_house.setPressed(false);
		bb_housing_appartment.setPressed(false);
		bb_housing_all.setPressed(true);
		button_index = 2;
	}
}
