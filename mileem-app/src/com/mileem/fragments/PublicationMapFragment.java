package com.mileem.fragments;

import com.mileem.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mileem.PublicationSlidesFragmentAdapter;
import com.mileem.model.Publication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PublicationMapFragment extends Fragment{

	private GoogleMap googleMap;
	private Publication publication;
	private View mapView;
	private SupportMapFragment mapFragment;
	private SupportMapFragment mSupportMapFragment;
	
	public PublicationMapFragment(Publication publication) {
		super();
		this.googleMap = null;
		this.mapView = null;
		this.publication = publication;	
					
    }
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	// Save UI state changes to the savedInstanceState.
	// This bundle will be passed to onCreate, onCreateView, and
	// onCreateView if the parent Activity is killed and restarted.
	super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    SupportMapFragment f = (SupportMapFragment) getFragmentManager()
	                                         .findFragmentById(R.id.map);
	    if (f != null) 
	        getFragmentManager().beginTransaction().remove(f).commit();
	}			
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		  super.onCreateView(inflater, container, savedInstanceState);
		  
		  if (mapView == null) {
			  
			  mapView = inflater.inflate(R.layout.fragment_mapview, container, false); 
	      
			  mapFragment = (SupportMapFragment) getFragmentManager().
	    		  								 findFragmentById(R.id.map);
	      
		      if (mapFragment == null) {
			       FragmentManager fragmentManager = getFragmentManager();
			       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			       mapFragment = SupportMapFragment.newInstance();
			       fragmentTransaction.replace(R.id.map, mapFragment).commit();
		      }
		      else
		      {
		          if (googleMap == null){
		        	  googleMap = mapFragment.getMap();
		          }
		    	  
		          double lat = publication.getLatitude();
		          double lng = publication.getLongitude();
		          LatLng coordinate = new LatLng(lat, lng);
		          
		          // create marker
		          MarkerOptions marker = new MarkerOptions().position(coordinate).title(publication.getAddress());
		          
		          // change color
		          marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		          // adding marker
		          googleMap.addMarker(marker);
		          	          
		          // adding zoom	          
		          googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));
		          
		       if (googleMap == null)
		       {
		    	   Toast.makeText(getActivity(),
	                       "No se pudo crear el mapa", Toast.LENGTH_SHORT)
	                       .show();
		       }
		      }
		  }
		  else{
			  ((ViewGroup)mapView.getParent()).removeView(mapView);
		  }
	      return mapView;	
		
		
	}
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }


//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//		
//		View view = inflater.inflate(R.layout.fragment_mapview, container, false);
//        if (mapFragment == null){
//        	mapFragment = SupportMapFragment.newInstance();	
//        }
//		if (googleMap == null){
//			googleMap = mapFragment.getMap();
//			Toast.makeText(getActivity(),
//                  "crear el mapa", Toast.LENGTH_SHORT)
//                  .show();
//		}
//        
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.add(R.id.map, mapFragment).commit();
//        
//        double lat = publication.getLatitude();
//        double lng = publication.getLongitude();
//        LatLng coordinate = new LatLng(lat, lng);
//        
//        // create marker
//        MarkerOptions marker = new MarkerOptions().position(coordinate).title(publication.getAddress());
//         
//        // adding marker
//        googleMap.addMarker(marker);
//        	          
//        // adding zoom	          
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));
//        
//        
//        
//        return view;
//		
//		
//	}
	
}
