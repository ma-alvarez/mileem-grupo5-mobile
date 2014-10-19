package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.IPlaceableFragment;

public class OrderTypeFragment extends Fragment implements IPlaceableFragment{
	
	private BootstrapButton bb_order_relevance, bb_order_date, bb_order_price;
	private int button_index;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_order_type,
	                container, false);
			
			bb_order_relevance = (BootstrapButton) rootView.findViewById(R.id.bb_order_relevance);
			bb_order_date = (BootstrapButton) rootView.findViewById(R.id.bb_order_date);
			bb_order_price = (BootstrapButton) rootView.findViewById(R.id.bb_order_price);
			
			setButtonsListeners();
			
			return rootView;
	}

	private void setButtonsListeners(){
		bb_order_relevance.setOnTouchListener(new myOnTouchListener(0));
		bb_order_date.setOnTouchListener(new myOnTouchListener(1));
		bb_order_price.setOnTouchListener(new myOnTouchListener(2));
		
		setDefault();
	}
	
	private class myOnTouchListener implements OnTouchListener{

		private int index;
		
		public myOnTouchListener(int idx){
			index = idx;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			bb_order_relevance.setPressed(false);
			bb_order_date.setPressed(false);
			bb_order_price.setPressed(false);
			
			v.setPressed(true);
			button_index = index;
			
			return true;
		}
		
	}
	
	@Override
	public int getTargetContainer() {
		return R.id.advanced_fragment_container_small;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
	
	public String toString(){
		return ConfigManager.ORDER_BY.concat(ConfigManager.ORDER_BY_OPT[button_index]);
	}

	@Override
	public void setDefault() {
		bb_order_date.setPressed(false);
		bb_order_price.setPressed(false);
		bb_order_relevance.setPressed(true);
		button_index = 0;
	}
}
