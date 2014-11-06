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
import com.mileem.R;
import com.mileem.Fx;
import com.mileem.IPlaceableFragment;

public class AdvancedSearchFragment extends Fragment implements
		IPlaceableFragment {

	
	private FrameLayout mMainContainer;
	private BootstrapButton bb_rooms, bb_area, bb_date, bb_order;
	private RelativeLayout movableGroup;
	private ArrayList<IPlaceableFragment> fragments;
	private ArrayList<View> sliding_views;
	private Integer editModeIndex = null;
	private Fx animator;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				
		View rootView = inflater.inflate(R.layout.fragment_advanced_search, container, false);
		
		retrieveViews(rootView);
		setEditButtons();
		setEditFragments();
		
		animator = new Fx(mMainContainer);
		
		
		return rootView;
	}
	
	private void retrieveViews(View rootView) {
		mMainContainer = (FrameLayout) rootView.findViewById(R.id.main_container_advanced);
		movableGroup = (RelativeLayout) rootView.findViewById(R.id.scrollview_advanced_container);
		
		bb_rooms = (BootstrapButton) rootView.findViewById(R.id.bb_rooms);
		bb_area = (BootstrapButton) rootView.findViewById(R.id.bb_area);
		bb_date = (BootstrapButton) rootView.findViewById(R.id.bb_date);
		bb_order = (BootstrapButton) rootView.findViewById(R.id.bb_order);
	}
	
	private void setEditButtons(){
		sliding_views = new ArrayList<View>(); 
		sliding_views.add(bb_rooms);
		sliding_views.add(bb_area);
		sliding_views.add(bb_date);
		sliding_views.add(bb_order);
		
		for(int i=0; i < sliding_views.size(); i++){
			sliding_views.get(i).setOnClickListener(new myOnclickListener(i));
		}
	}
	
	private void setEditFragments(){
		fragments = new ArrayList<IPlaceableFragment>();
		IPlaceableFragment pFragment = new RoomsFragment();
		fragments.add(pFragment);
		pFragment = new AreaFragment();
		fragments.add(pFragment);
		pFragment = new DatePickerFragment();
		fragments.add(pFragment);
		pFragment = new OrderTypeFragment();
		fragments.add(pFragment);
		
        //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		FragmentManager fragmentManager = getChildFragmentManager();
        for(IPlaceableFragment fragment : fragments){
            fragmentManager.beginTransaction()
            .add(fragment.getTargetContainer(),fragment.getFragment())
            .hide(fragment.getFragment())
            .commit();
        }
	}
	
	@Override
	public int getTargetContainer() {
		return R.id.edit_mode_fragment_container_full;
	}

	@Override
	public Fragment getFragment() {
		return this;
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
		return animator.unfocus(v, movableGroup);	
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < fragments.size(); i++){
			sb.append(fragments.get(i).toString());
		}
		return sb.toString();
		
	}

	@Override
	public void setDefault() {
		for(int i=0; i < fragments.size(); i++){
			fragments.get(i).setDefault();
		}
	}

}
