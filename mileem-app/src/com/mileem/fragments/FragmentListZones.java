package com.mileem.fragments;

import com.mileem.R;
import com.mileem.IPlaceableFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentListZones extends Fragment implements IPlaceableFragment{

	ListView listView;
	ArrayAdapter<String> adapter;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_zones_listview,
                container, false);
		
		listView = (ListView) view.findViewById(R.id.list_zones);

		String[] zones = getResources().getStringArray(R.array.neighbourhoods);
		adapter = new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_list_item_multiple_choice, zones);

		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setAdapter(adapter);

		return view;
		
	}

	@Override
	public int getContainer() {
		return R.id.edit_mode_fragment_container_full;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
}
