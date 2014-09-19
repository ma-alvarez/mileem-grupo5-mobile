package com.mileem;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mileem.R;

public class PublicationsFragment extends ListFragment {

	private Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ListAdapter myListAdapter = new ArrayAdapter<String>(
//				getActivity(),
//				android.R.layout.simple_list_item_1,
//				month);
//		setListAdapter(myListAdapter);
		new ListPublicacionesTask(this).execute();
	}

//	@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//
//		return inflater.inflate(R.layout.listview_main, container, false);
//
//	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
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
