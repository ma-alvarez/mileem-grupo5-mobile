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
import com.mileem.IPlaceableFragment;

public class OrderTypeFragment extends Fragment implements IPlaceableFragment{
	
	private BootstrapButton bb_order_relevance, bb_order_date, bb_order_price;
	
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
		bb_order_relevance.setOnTouchListener(new myOnTouchListener());
		bb_order_date.setOnTouchListener(new myOnTouchListener());
		bb_order_price.setOnTouchListener(new myOnTouchListener());
		
	}
	
	private class myOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			bb_order_relevance.setPressed(false);
			bb_order_date.setPressed(false);
			bb_order_price.setPressed(false);
			
			v.setPressed(true);
			return true;
		}
		
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
