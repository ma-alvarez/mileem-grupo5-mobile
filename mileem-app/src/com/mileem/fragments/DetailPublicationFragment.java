package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mileem.R;
import com.mileem.PublicationSlidesFragmentAdapter;
import com.mileem.model.Publication;

public class DetailPublicationFragment extends Fragment{

	private Publication publication;
	private PublicationSlidesFragmentAdapter adapter;
    private ViewPager pager;
    //private PageIndicator indicator;
	
	public DetailPublicationFragment(Publication publication) {
		this.publication = publication;
	}
	
	@Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_detailpublication,
                container, false);

        adapter = new PublicationSlidesFragmentAdapter(getActivity().getSupportFragmentManager(),publication.getUrls_image());
        
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);
	   
        // Locate the TextViews in layout
        TextView type = (TextView) view.findViewById(R.id.type_op);
        TextView address = (TextView) view.findViewById(R.id.address);
        TextView zone = (TextView) view.findViewById(R.id.zone);
        TextView area = (TextView) view.findViewById(R.id.area);
        TextView age = (TextView) view.findViewById(R.id.age);
        TextView rooms = (TextView) view.findViewById(R.id.number_of_rooms);

        // Capture position and set results to the TextViews
        type.setText(publication.getProperty_type());
        address.setText(publication.getAddress());
        zone.setText(publication.getZone());
        area.setText(Integer.toString(publication.getArea()) + " m2");
        age.setText(publication.getAge() + " Años");      
        rooms.setText(Integer.toString(publication.getNumber_of_rooms()) + " Ambientes");
        
        return view;
		//return inflater.inflate(R.layout.fragment_detailpublication, container, false);

	}
}
