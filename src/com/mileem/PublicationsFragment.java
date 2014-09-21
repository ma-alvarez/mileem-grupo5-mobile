package com.mileem;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mileem.R;
import com.mileem.model.Publication;

public class PublicationsFragment extends ListFragment {

	private Activity activity;
	private ArrayList<Publication> publicaciones;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new ListPublicacionesTask(this).execute();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		Toast.makeText(
				getActivity(), 
				getListView().getItemAtPosition(position).toString(), 
				Toast.LENGTH_LONG).show();

		Publication publication = publicaciones.get(position);
		
		FragmentManager fragmentManager = getActivity().getFragmentManager();
		fragmentManager.beginTransaction().
		replace(R.id.container, new DetailPublicationFragment(publication)).
		addToBackStack("publicaciones").commit(); 
	}
	
	public void setPublicaciones(ArrayList<Publication> list){
		publicaciones = list;
	}
}
