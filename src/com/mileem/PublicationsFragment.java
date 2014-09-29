package com.mileem;

import java.util.ArrayList;

import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
//import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mileem.R;
import com.mileem.model.Publication;

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

		new ListPublicacionesTask(this).execute();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("PublicationsFragment", "Click"); 
		/*Toast.makeText(
				getActivity(), 
				getListView().getItemAtPosition(position).toString(), 
				Toast.LENGTH_LONG).show();
		 */
		
		Publication publication = publicaciones.get(position);
		
		//FragmentManager fragmentManager = getActivity().getFragmentManager();
		//fragmentManager.beginTransaction().
		//replace(R.id.container, new DetailPublicationFragment(publication)).
		//addToBackStack("publicaciones").commit();
		
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction().
		replace(R.id.container,new DetailPublicationFragment(publication)).
		addToBackStack("publicaciones").commit(); 
	}
	
	public void setPublicaciones(ArrayList<Publication> list){
		publicaciones = list;
	}
	
	public String getPublicationsQuery(){
		return query;
	}
}
