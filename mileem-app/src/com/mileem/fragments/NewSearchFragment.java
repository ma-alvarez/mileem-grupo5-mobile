package com.mileem.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.Fx;
import com.mileem.IPlaceableFragment;

public class NewSearchFragment extends Fragment {

	private FrameLayout mMainContainer;
	private BootstrapButton bb_operation, bb_house_type, bb_zones, bb_price, bb_advanced_search;
	private RelativeLayout movableGroup;
	private View search_bar;
	private ArrayList<View> sliding_views;
	private ArrayList<IPlaceableFragment> fragments;
	private boolean editMode = false;
	private Fx animator;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_newsearch, container, false);
		
		retrieveViews(rootView);

		animator = new Fx(mMainContainer);
		
		sliding_views = new ArrayList<View>(); 
		sliding_views.add(bb_operation);
		sliding_views.add(bb_house_type);
		sliding_views.add(bb_zones);
		sliding_views.add(bb_price);
		sliding_views.add(bb_advanced_search);
		sliding_views.add(search_bar);
		
		for(int i=0; i < sliding_views.size(); i++){
			sliding_views.get(i).setOnClickListener(new myOnclickListener(i));
		}
		
		fragments = new ArrayList<IPlaceableFragment>();
		
		IPlaceableFragment pFragment = new FragmentTransactionType();
		fragments.add(pFragment);
		pFragment = new FragmentHousingType();
		fragments.add(pFragment);
		pFragment = new FragmentListZones();
		fragments.add(pFragment);
		pFragment = new PricesFragment();
		fragments.add(pFragment);
		pFragment = new AdvancedSearchFragment();
		fragments.add(pFragment);
		
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        for(IPlaceableFragment fragment : fragments){
            fragmentManager.beginTransaction()
            .add(fragment.getContainer(),fragment.getFragment())
            .hide(fragment.getFragment())
            .commit();
        }

		return rootView;

	}
	
	private void retrieveViews(View rootView) {
		mMainContainer = (FrameLayout) rootView.findViewById(R.id.main_container);
		movableGroup = (RelativeLayout) rootView.findViewById(R.id.scrollview_container);
		search_bar = rootView.findViewById(R.id.barra_buscar);

		
		bb_operation = (BootstrapButton) rootView.findViewById(R.id.bb_operation_type);
		bb_house_type = (BootstrapButton) rootView.findViewById(R.id.bb_housing_type);
		bb_zones = (BootstrapButton) rootView.findViewById(R.id.bb_zones);
		bb_price = (BootstrapButton) rootView.findViewById(R.id.bb_price);
		bb_advanced_search = (BootstrapButton) rootView.findViewById(R.id.bb_advanced_search);
		
	}

	private class myOnclickListener implements OnClickListener{
		int i, sw = 0;

		public myOnclickListener(int index){
			i = index;
		}
		
		@Override
		public void onClick(View v) {

			if(fragments.size() > i){
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				View containerView = getActivity().findViewById(fragments.get(i).getContainer());

				if(sw % 2 != 0){
					fragmentManager.beginTransaction()
					.hide(fragments.get(i).getFragment())
					.commit();
					contraer(v,containerView,i);
					editMode = false;
				}else{
					fragmentManager.beginTransaction()
					.show(fragments.get(i).getFragment())
					.commit();
					expander(v,containerView,i);
					editMode = true;
				}
				sw++;

			}
		}
	}
	
	private void expander(View v, View containerView, int index){

		animator.focusOn(v, movableGroup);
		int dp = (int) getResources().getDimension(R.dimen.margin);
		for(int i= index; i < sliding_views.size();i++){
			
			if(i + 1 < sliding_views.size())
				animator.fadeOutToBottom(sliding_views.get(i + 1), containerView, dp);
		}
		
		animator.slideInToTop(containerView);
		containerView.setVisibility(View.VISIBLE);
	}
	
	private void contraer(View v, View containerView, int index){
		
		animator.slideOutToBottom(containerView);
		int dp = (int) getResources().getDimension(R.dimen.margin);
		for(int i= index; i < sliding_views.size();i++){
			
			if(i + 1 < sliding_views.size())
				animator.fadeInToTop(sliding_views.get(i + 1),containerView,dp);
		}
		animator.unfocus(v, movableGroup);	
	}

}
