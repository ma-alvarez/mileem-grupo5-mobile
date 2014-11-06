package com.mileem.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.Fx;
import com.mileem.IPlaceableFragment;

public class NewSearchFragment extends Fragment {

	private String TAG = this.getClass().getSimpleName();
	private FrameLayout mMainContainer;
	private BootstrapButton bb_operation, bb_housing_type, bb_zones, bb_price, bb_advanced_search, bb_search, bb_clean_filters;
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
		setEditButtons();
		setBarButtons();
		setEditFragments();
		
		animator = new Fx(mMainContainer);

		return rootView;

	}
	
	private void retrieveViews(View rootView) {
		mMainContainer = (FrameLayout) rootView.findViewById(R.id.main_container);
		movableGroup = (RelativeLayout) rootView.findViewById(R.id.scrollview_container);
		search_bar = rootView.findViewById(R.id.barra_buscar);

		
		bb_operation = (BootstrapButton) rootView.findViewById(R.id.bb_operation_type);
		bb_housing_type = (BootstrapButton) rootView.findViewById(R.id.bb_housing_type);
		bb_zones = (BootstrapButton) rootView.findViewById(R.id.bb_zones);
		bb_price = (BootstrapButton) rootView.findViewById(R.id.bb_price);
		bb_advanced_search = (BootstrapButton) rootView.findViewById(R.id.bb_advanced_search);
		
		bb_search = (BootstrapButton) rootView.findViewById(R.id.bb_search);
		bb_clean_filters = (BootstrapButton) rootView.findViewById(R.id.bb_clean_filters);
		
	}
	
	private void setEditButtons(){
		sliding_views = new ArrayList<View>(); 
		sliding_views.add(bb_operation);
		sliding_views.add(bb_housing_type);
		sliding_views.add(bb_zones);
		sliding_views.add(bb_price);
		sliding_views.add(bb_advanced_search);
		
		for(int i=0; i < sliding_views.size(); i++){
			sliding_views.get(i).setOnClickListener(new myOnclickListener(i));
		}
	}
	
	private void setBarButtons(){
		bb_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PublicationsFragment fragment = new PublicationsFragment();
				String queryParams = buildQuery();
				fragment.AppendAdditionalParameters(queryParams);
				
		    	// update the main content by replacing fragments
		        FragmentManager fragmentManager = getFragmentManager();
		        fragmentManager.beginTransaction()
		        .replace(R.id.container, fragment)
		        .addToBackStack("busqueda")
		        .commit();
		        
		        int count = fragmentManager.getBackStackEntryCount();
			}
		});
		
		bb_clean_filters.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for(int i=0; i < fragments.size(); i++){
					fragments.get(i).setDefault();
				}
			}
		});
	}
	
	private void setEditFragments(){
		fragments = new ArrayList<IPlaceableFragment>();
		
		IPlaceableFragment pFragment = new OperationTypeFragment();
		fragments.add(pFragment);
		pFragment = new HousingTypeFragment();
		fragments.add(pFragment);
		pFragment = new ListZonesFragment();
		fragments.add(pFragment);
		pFragment = new PricesFragment();
		fragments.add(pFragment);
		pFragment = new AdvancedSearchFragment();
		fragments.add(pFragment);
		
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        for(IPlaceableFragment fragment : fragments){
            fragmentManager.beginTransaction()
            .add(fragment.getTargetContainer(),fragment.getFragment())
            .hide(fragment.getFragment())
            .commit();
        }
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
			View containerView = getActivity().findViewById(fragments.get(i).getTargetContainer());

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
				
				containerView = getActivity().findViewById(fragments.get(editModeIndex).getTargetContainer());
				fragmentManager.beginTransaction()
				.hide(fragments.get(editModeIndex).getFragment())
				.commit();
				ViewPropertyAnimator vp = contraer(sliding_views.get(editModeIndex),containerView,editModeIndex);
	
				if(editModeIndex != i){ //si fue otro boton, abro la edicion de este boton
					containerView = getActivity().findViewById(fragments.get(i).getTargetContainer());
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
	
	private String buildQuery(){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < fragments.size(); i++){
			sb.append(fragments.get(i).toString());
		}
		
		Log.d(TAG, sb.toString());
		return sb.toString();
	}

}
