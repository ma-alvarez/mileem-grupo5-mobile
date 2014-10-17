package com.mileem.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mileem.R;
import com.mileem.IPlaceableFragment;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class DatePickerFragment extends Fragment implements IPlaceableFragment{
	
	private CalendarPickerView calendar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

			View view = inflater.inflate(R.layout.fragment_calendar_picker,
	                container, false);
			
			calendar = (CalendarPickerView) view.findViewById(R.id.calendar_view);
			
			final Calendar nextYear = Calendar.getInstance();
		    nextYear.add(Calendar.YEAR, 1);
		    
			 Calendar today = Calendar.getInstance();
		        ArrayList<Date> dates = new ArrayList<Date>();
		        today.add(Calendar.DATE, 3);
		        dates.add(today.getTime());
		        today.add(Calendar.DATE, 5);
		        dates.add(today.getTime());
		        calendar.init(new Date(), nextYear.getTime()) //
		            .inMode(SelectionMode.RANGE) //
		            .withSelectedDates(dates);
			
			return view;
	}

	@Override
	public int getTargetContainer() {
		return R.id.advanced_fragment_container_full;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
}
