package com.mileem.fragments;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.IPlaceableFragment;
import com.mileem.RangeSeekBar;
import com.mileem.RangeSeekBar.OnRangeSeekBarChangeListener;

public class AreaFragment extends Fragment implements IPlaceableFragment {

	private RangeSeekBar<Integer> seekBarArea;
	private BootstrapButton bb_allareas;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_area, container, false);
		
		bb_allareas = (BootstrapButton) rootView.findViewById(R.id.bb_allareas);
		
		setUpRangeSeekBarArea();
		ViewGroup seekBarSup_layout = (ViewGroup) rootView.findViewById(R.id.rangeSeekBarArea);
		seekBarSup_layout.addView(seekBarArea);
		
		return rootView;
		
	}
	
	private void setUpRangeSeekBarArea(){

		int MIN_AREA = 0;
		int MAX_AREA = 1000;
		final DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
//		sup_from_to.setText("De: " + df.format(MIN_AREA) + " A: " + df.format(MAX_AREA));
		
		
		seekBarArea = new RangeSeekBar<Integer>(MIN_AREA, MAX_AREA, this.getActivity());
		seekBarArea.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
//		        	sup_from_to.setText("De: " + df.format(minValue) + " A: " + df.format(maxValue) + " m2");
		        }
		});
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
