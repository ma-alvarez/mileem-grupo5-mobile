package com.mileem.fragments;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.IPlaceableFragment;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class DatePickerFragment extends Fragment implements IPlaceableFragment{
	
	private CalendarPickerView calendar;
	private BootstrapButton bb_alldates;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_calendar_picker,
				container, false);

		calendar = (CalendarPickerView) view.findViewById(R.id.calendar_view);

		final Calendar previousYear = Calendar.getInstance();
		previousYear.add(Calendar.YEAR, -1);
		Calendar tomorrow = Calendar.getInstance();
		Date hoy = new Date();
		tomorrow.setTime(hoy);
		tomorrow.add(Calendar.DATE, 1);

		calendar.init(previousYear.getTime(),tomorrow.getTime())  //
		.inMode(SelectionMode.RANGE)
		.withSelectedDate(hoy);

		
		calendar.setOnDateSelectedListener(new OnDateSelectedListener() {
			
			@Override
			public void onDateUnselected(Date date) {
				
			}
			
			@Override
			public void onDateSelected(Date date) {
				bb_alldates.setPressed(false);
			}
		});

		bb_alldates = (BootstrapButton) view.findViewById(R.id.bb_alldates);
		setButtonListener();

		return view;
	}
	
	private void setButtonListener() {
		bb_alldates.setPressed(true);
		bb_alldates.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				setDefault();
				return true;
			}
		});
	}
	
	private String changeDateToYYYYMMDD(Date old){
		DecimalFormat df = new DecimalFormat("00");
		Calendar cal = Calendar.getInstance();
		cal.setTime(old);
		String date = Integer.toString(cal.get(Calendar.YEAR))
				+ df.format(cal.get(Calendar.MONTH)+1)
				+ df.format(cal.get(Calendar.DAY_OF_MONTH));
		return date;
	}

	
	@Override
	public int getTargetContainer() {
		return R.id.advanced_fragment_container_full;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
	
	public String toString(){
		if(bb_alldates.isPressed()){
			return "";
		}else{
			StringBuilder sb = new StringBuilder();
			sb.append(ConfigManager.PUB_TIME_FROM);
			sb.append(changeDateToYYYYMMDD(calendar.getSelectedDates().get(0)));
			sb.append(ConfigManager.PUB_TIME_TO);
			sb.append(changeDateToYYYYMMDD(calendar.getSelectedDates().get(calendar.getSelectedDates().size()-1)));
			return sb.toString();
		}
	}

	@Override
	public void setDefault() {
		bb_alldates.setPressed(true);
		calendar.selectDate(new Date());
	}
}
