package com.mileem;

import com.example.mileem.R;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PublicationsFragment extends ListFragment {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ListAdapter myListAdapter = new ArrayAdapter<String>(
//				getActivity(),
//				android.R.layout.simple_list_item_1,
//				month);
//		setListAdapter(myListAdapter);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.listview_main, container, false);
		
	}
	
	 @Override
	 public void onListItemClick(ListView l, View v, int position, long id) {
	  // TODO Auto-generated method stub
	  Toast.makeText(
	    getActivity(), 
	    getListView().getItemAtPosition(position).toString(), 
	    Toast.LENGTH_LONG).show();
	 }
}
