package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.IPlaceableFragment;

public class OperationTypeFragment extends Fragment implements IPlaceableFragment{
	
	private BootstrapButton bb_operation_buy, bb_operation_rent, bb_operation_all;
	private int button_index;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_operation_type,
	                container, false);
			
			bb_operation_buy = (BootstrapButton) rootView.findViewById(R.id.bb_operation_buy);
			bb_operation_rent = (BootstrapButton) rootView.findViewById(R.id.bb_operation_rent);
			bb_operation_all = (BootstrapButton) rootView.findViewById(R.id.bb_operation_all);
			
			setButtonsListeners();
			
			return rootView;
	}
	
	private void setButtonsListeners(){
		bb_operation_buy.setOnTouchListener(new myOnTouchListener(0));
		bb_operation_rent.setOnTouchListener(new myOnTouchListener(1));
		bb_operation_all.setOnTouchListener(new myOnTouchListener(2));
		
		setDefault();
	}
	
	private class myOnTouchListener implements OnTouchListener{

		private int index;
		
		public myOnTouchListener(int idx){
			index = idx;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			bb_operation_buy.setPressed(false);
			bb_operation_rent.setPressed(false);
			bb_operation_all.setPressed(false);
			
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

		return button_index < 2 ? ConfigManager.OPE_TYPE.concat(ConfigManager.OPE_TYPE_OPT[button_index]) : "";
	}

	@Override
	public void setDefault() {
		bb_operation_buy.setPressed(false);
		bb_operation_rent.setPressed(false);
		bb_operation_all.setPressed(true);
		button_index = 2;
	}
}
