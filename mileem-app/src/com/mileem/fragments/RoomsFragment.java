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

public class RoomsFragment extends Fragment implements IPlaceableFragment {

	private BootstrapButton bb_1room, bb_2room, bb_3room, bb_4room, bb_allrooms;
	private int button_index;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);
		
		bb_1room = (BootstrapButton) rootView.findViewById(R.id.bb_1room);
		bb_2room = (BootstrapButton) rootView.findViewById(R.id.bb_2room);
		bb_3room = (BootstrapButton) rootView.findViewById(R.id.bb_3room);
		bb_4room = (BootstrapButton) rootView.findViewById(R.id.bb_4room);
		bb_allrooms = (BootstrapButton) rootView.findViewById(R.id.bb_allrooms);
		
		setButtonsListeners();
		
		return rootView;
		
	}
	
	private void setButtonsListeners(){
		bb_1room.setOnTouchListener(new myOnTouchListener(0));
		bb_2room.setOnTouchListener(new myOnTouchListener(1));
		bb_3room.setOnTouchListener(new myOnTouchListener(2));
		bb_4room.setOnTouchListener(new myOnTouchListener(3));
		bb_allrooms.setOnTouchListener(new myOnTouchListener(4));

		setDefault();
	}
	
	private class myOnTouchListener implements OnTouchListener{

		private int index;
		
		public myOnTouchListener(int idx){
			index = idx;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			bb_1room.setPressed(false);
			bb_2room.setPressed(false);
			bb_3room.setPressed(false);
			bb_4room.setPressed(false);
			bb_allrooms.setPressed(false);
			
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
		return button_index < 4 ? ConfigManager.ROOMS.concat(ConfigManager.ROOMS_OPT[button_index]) : "";
	}

	@Override
	public void setDefault() {
		bb_1room.setPressed(false);
		bb_2room.setPressed(false);
		bb_3room.setPressed(false);
		bb_4room.setPressed(false);
		bb_allrooms.setPressed(true);
		button_index = 4;
	}
}
