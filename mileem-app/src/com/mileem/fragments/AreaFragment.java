package com.mileem.fragments;

import java.text.DecimalFormat;

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
import com.mileem.RangeSeekBar;
import com.mileem.RangeSeekBar.OnRangeSeekBarChangeListener;

public class AreaFragment extends Fragment implements IPlaceableFragment {

	private RangeSeekBar<Integer> seekBarArea;
	private BootstrapButton bb_allareas, bb_area_from, bb_area_to;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_area, container, false);
		
		bb_allareas = (BootstrapButton) rootView.findViewById(R.id.bb_allareas);
		bb_area_from = (BootstrapButton) rootView.findViewById(R.id.bb_area_from);
		bb_area_to = (BootstrapButton) rootView.findViewById(R.id.bb_area_to);
		
		setUpRangeSeekBarArea();
		ViewGroup seekBarSup_layout = (ViewGroup) rootView.findViewById(R.id.rangeSeekBarArea);
		seekBarSup_layout.addView(seekBarArea);
		
		setButtonsListeners();
		
		return rootView;
		
	}
	
	private void setButtonsListeners(){
	
		bb_allareas.setPressed(true);
		bb_allareas.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				setDefault();
				return true;
			}
		});
	}
	
	private void setUpRangeSeekBarArea(){

		int MIN_AREA = 0;
		int MAX_AREA = 1000;
		final DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
		
		seekBarArea = new RangeSeekBar<Integer>(MIN_AREA, MAX_AREA, this.getActivity());
		seekBarArea.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
	                bb_area_from.setText(df.format(minValue));
	                bb_area_to.setText(df.format(maxValue));
	           
	                bb_allareas.setPressed(false);
	                if((minValue.longValue() == bar.getAbsoluteMinValue().longValue()) && (maxValue.longValue() == bar.getAbsoluteMaxValue().longValue())){
		                bb_area_from.setText("min");
		                bb_area_to.setText("max");
	                	bb_allareas.setPressed(true);
	                }
		        }
		});
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
		if(bb_allareas.isPressed()){
			return "";
		}else{
			StringBuilder sb = new StringBuilder();
			sb.append(ConfigManager.SUP_FROM);
			sb.append(seekBarArea.getSelectedMinValue());
			sb.append(ConfigManager.SUP_TO);
			sb.append(seekBarArea.getSelectedMaxValue());
			return sb.toString();
		}
	}

	@Override
	public void setDefault() {
		seekBarArea.setSelectedMinValue(seekBarArea.getAbsoluteMinValue());
		seekBarArea.setSelectedMaxValue(seekBarArea.getAbsoluteMaxValue());
		bb_area_from.setText("min");
		bb_area_to.setText("max");
		bb_allareas.setPressed(true);
	}

}
