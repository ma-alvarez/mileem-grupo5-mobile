package com.mileem.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
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
	private Integer editModeIndex = null;
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
		//sliding_views.add(search_bar);
		
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
		private int i;

		public myOnclickListener(int index){
			i = index;
		}
		
		private class Expander implements Runnable{
			
			private View v;
			private View containerView;
			private int index;
			
			public Expander(View view, View container, int idx){
				v = view;
				containerView = container;
				index = idx;
			}
			@Override
			public void run() {
				expander(v, containerView, index);
				
			}
		};
		
		@Override
		public void onClick(View v) {

			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			View containerView = getActivity().findViewById(fragments.get(i).getContainer());

			if(editModeIndex == null){ // no hay nada en edicion, expander
				fragmentManager.beginTransaction()
				.show(fragments.get(i).getFragment())
				.commit();
				expander(v,containerView,i);

				//				for(int i=0; i < sliding_views.size(); i++){
				//					if( i != this.i)
				//						sliding_views.get(i).setClickable(false);
				//				}
				editModeIndex = i;
			}else{ //sin importar que boton se apreto, colapso la edicion abierta
				
				containerView = getActivity().findViewById(fragments.get(editModeIndex).getContainer());
				fragmentManager.beginTransaction()
				.hide(fragments.get(editModeIndex).getFragment())
				.commit();
				ViewPropertyAnimator vp = contraer(sliding_views.get(editModeIndex),containerView,editModeIndex);
	
				if(editModeIndex != i){ //si fue otro boton, abro la edicion de este boton
					containerView = getActivity().findViewById(fragments.get(i).getContainer());
					fragmentManager.beginTransaction()
					.show(fragments.get(i).getFragment())
					.commit();
					//expander(v,containerView,i);
					vp.withEndAction(new Expander(v,containerView,i));
					//				for(int i=0; i < sliding_views.size(); i++){
					//						sliding_views.get(i).setClickable(true);
					//				}
					editModeIndex = i;
				}else{
					editModeIndex = null;
				}
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
		animator.fadeOutToBottom(search_bar, containerView, dp);
		
		animator.slideInToTop(containerView);
		containerView.setVisibility(View.VISIBLE);
	}
	
	ViewPropertyAnimator contraer(View v, View containerView, int index){
		
		animator.slideOutToBottom(containerView);
		int dp = (int) getResources().getDimension(R.dimen.margin);
		for(int i= index; i < sliding_views.size();i++){
			
			if(i + 1 < sliding_views.size())
				animator.fadeInToTop(sliding_views.get(i + 1),containerView,dp);
		}
		animator.fadeInToTop(search_bar,containerView,dp);
		return animator.unfocus(v, movableGroup);	
	}

}
