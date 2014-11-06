package com.mileem.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.mileem.ListPublicacionesViewAdapter;
import com.mileem.PublicationAdapter;
import com.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.model.Publication;
import com.mileem.tasks.ListPublicacionesTask;

public class PublicationsFragment extends ListFragment {

	private String TAG = this.getClass().getSimpleName();
	private String query;
	private String page;
	private String count;
	private String order_type;
	
	private Activity activity;
	private ArrayList<Publication> publicaciones;

	public PublicationsFragment(){
		page = "1";
		count = ConfigManager.COUNT_OPT[2];
		order_type = ConfigManager.ORDER_TYPE_OPT[0]; //desc
		
		query = buildSimpleQuery();
	}
	
	private String buildSimpleQuery(){
		StringBuilder sb = new StringBuilder();
		sb.append(ConfigManager.PAGE);
		sb.append(page);
		sb.append(ConfigManager.COUNT);
		sb.append(count);
		sb.append(ConfigManager.ORDER_TYPE);
		sb.append(order_type);
		return sb.toString();
	}
	
	public void AppendAdditionalParameters(String queryParams){
		StringBuilder sb = new StringBuilder(query);
		sb.append(queryParams);
		query = sb.toString();
		Log.d(TAG, query);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null){
			new ListPublicacionesTask(this).execute();
		} else {
	    	ArrayList<Bundle> list_bundles = savedInstanceState.getParcelableArrayList("publications");
	    	publicaciones = new ArrayList<Publication>();
	    	for(Bundle b: list_bundles){
	    		publicaciones.add(new Publication(b));
	    	}
	    	
	    	ArrayList<PublicationAdapter> list_adapters = new ArrayList<PublicationAdapter>();
	    	for(Publication p : publicaciones){
	    		list_adapters.add(PublicationAdapter.newAdapterInstance(p));
	    	}
	    	ListPublicacionesViewAdapter adapter = new ListPublicacionesViewAdapter(activity,list_adapters);
	    	this.setListAdapter(adapter);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

//	    if (savedInstanceState != null) {
//	    	ArrayList<Bundle> list_bundles = savedInstanceState.getParcelableArrayList("publications");
//	    	publicaciones = new ArrayList<Publication>();
//	    	for(Bundle b: list_bundles){
//	    		publicaciones.add(new Publication(b));
//	    	}
//	    	
//	    	ArrayList<PublicationAdapter> list_adapters = new ArrayList<PublicationAdapter>();
//	    	for(Publication p : publicaciones){
//	    		list_adapters.add(PublicationAdapter.newAdapterInstance(p));
//	    	}
//	    	ListPublicacionesViewAdapter adapter = new ListPublicacionesViewAdapter(getActivity(),list_adapters);
//	    	this.setListAdapter(adapter);
//	    }
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if(publicaciones != null){
			//Save the fragment's state here
			ArrayList<Bundle> list_bundles = new ArrayList<Bundle>();
			for (Publication p : publicaciones){
				list_bundles.add(p.getBundle());
			}
			outState.putParcelableArrayList("publications", list_bundles);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		Publication publication = publicaciones.get(position);
		
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction().
		replace(R.id.container,DetailPublicationFragment.newInstance(publication)).
		addToBackStack("publicaciones").commit(); 
	}
	
	public void setPublicaciones(ArrayList<Publication> list){
		publicaciones = list;
	}
	
	public String getPublicationsQuery(){
		return query;
	}
	
	
}
