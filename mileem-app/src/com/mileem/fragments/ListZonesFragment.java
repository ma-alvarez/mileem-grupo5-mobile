package com.mileem.fragments;

import com.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.IPlaceableFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListZonesFragment extends Fragment implements IPlaceableFragment{

	private ListView listView;
	private ArrayAdapter<String> adapter;
	private boolean allareas;
	
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
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
				if(checkedItems.get(0)){ //"todas" esta o estaba checked
					int pos0 = checkedItems.indexOfKey(0);
					if(position == 0){ // se apreto en "todas"

						for (int i = 0; i < checkedItems.size(); i++) {
							if(i != pos0)
								listView.setItemChecked(checkedItems.keyAt(i), false);
						}
						allareas = true;
						
					} else { //se apreto otra localidad
						listView.setItemChecked(0, false);
						allareas = false;
					}
				} else {
					if(position == 0){
						listView.setItemChecked(0, true);
						allareas = false;
					}
				}

			}
		});
		

		return view;
		
	}
	
	public void onViewCreated(View view, Bundle savedInstanceState){
		listView.setItemChecked(0, true); //meto esto aca porque en oncreateview no andaba.
		allareas = true;
	}

	@Override
	public int getTargetContainer() {
		return R.id.edit_mode_fragment_container_full;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
	
	public String toString(){
		if(allareas){
			return "";
		}else{
			StringBuilder sb = new StringBuilder();
			SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
			int count = 0;
			for (int i = 0; i < checkedItems.size(); i++) {
				int position = checkedItems.keyAt(i);
				if (checkedItems.valueAt(i)){
					if(count > 0) {
						sb.append("-");
					} else {
						sb.append(ConfigManager.ZONE);
					}
					sb.append(adapter.getItem(position).replaceAll(" ", "_").toLowerCase());
					count++;
				}
					
			}
			return sb.toString();
		}
	}

	@Override
	public void setDefault() {
		listView.setItemChecked(0, true);
		allareas = true;
		for (int i= 1; i < listView.getAdapter().getCount(); i++){
			listView.setItemChecked(i, false);
		}

	}
}
