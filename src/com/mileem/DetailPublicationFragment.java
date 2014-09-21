package com.mileem;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mileem.R;
import com.mileem.model.Publication;

public class DetailPublicationFragment extends Fragment{

	private Publication publication;
	
	public DetailPublicationFragment(Publication publication) {
		this.publication = publication;
	}
	
	@Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_detailpublication, container, false);

	}
}
