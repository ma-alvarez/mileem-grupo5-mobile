package com.mileem.fragments;

import java.util.ArrayList;

import android.animation.TimeInterpolator;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.IPlaceableFragment;
import com.mileem.LayerEnablingAnimatorListener;

public class NewSearchFragment extends Fragment {

	private final Rect mTmpRect = new Rect();

	private FrameLayout mMainContainer, mEditModeContainer, mEditFragmentContainer;
	private BootstrapButton bb_operation, bb_house_type, bb_zones, bb_price;
	private RelativeLayout mFirstGroup;
	private View search_bar;
	private ArrayList<View> sliding_views;
	private ArrayList<IPlaceableFragment> fragments;
	private boolean editMode = false;

	private TimeInterpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
	private int ANIMATION_DURATION = 300;
	private int mHalfHeight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.newsearch_fragment, container, false);
		
		retrieveViews(rootView);
		mHalfHeight = 200;
		//mEditModeContainer.setTranslationY(mHalfHeight);
		mEditFragmentContainer.setAlpha(0f);
		
		fragments = new ArrayList<IPlaceableFragment>();
		
		IPlaceableFragment f_ejemplo = new FragmentTransactionType();
		fragments.add(f_ejemplo);
		IPlaceableFragment operation_fragment = new FragmentHousingType();
		fragments.add(operation_fragment);
		f_ejemplo = new FragmentDatePicker();
		fragments.add(f_ejemplo);
		
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
		mFirstGroup = (RelativeLayout) rootView.findViewById(R.id.scrollview_container);
		search_bar = rootView.findViewById(R.id.barra_buscar);
		
		
		sliding_views = new ArrayList<View>(); 
		
		bb_operation = (BootstrapButton) rootView.findViewById(R.id.btnBig);
		bb_house_type = (BootstrapButton) rootView.findViewById(R.id.tv2);
		bb_zones = (BootstrapButton) rootView.findViewById(R.id.tv3);
		bb_price = (BootstrapButton) rootView.findViewById(R.id.tv4);
		
		sliding_views.add(bb_operation);
		sliding_views.add(bb_house_type);
		sliding_views.add(bb_zones);
		sliding_views.add(bb_price);
		sliding_views.add(search_bar);
		
		for(int i=0; i < sliding_views.size(); i++){
			sliding_views.get(i).setOnClickListener(new myOnclickListener(i));
		}


//		mEditModeContainer = (FrameLayout) rootView.findViewById(R.id.edit_mode_container_main);
		mEditFragmentContainer = (FrameLayout) rootView.findViewById(R.id.edit_mode_fragment_container_full);
		
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
		focusOn(v, mFirstGroup, true);
		for(int i= index; i < sliding_views.size();i++){
			
			if(i + 1 < sliding_views.size())
				fadeOutToBottom(sliding_views.get(i + 1), containerView, true);
		}
		
		//stickTo(mFirstSpacer, viewFrom, true);
		slideInToTop(containerView, true);
		containerView.setVisibility(View.VISIBLE);
	}
	
	private void contraer(View v, View containerView, int index){
		slideOutToBottom(containerView, true);
		//unstickFrom(mFirstSpacer, viewFrom, true);
		for(int i= index; i < sliding_views.size();i++){
			
			if(i + 1 < sliding_views.size())
				fadeInToTop(sliding_views.get(i + 1),containerView,true);
		}
		unfocus(v, mFirstGroup, true);
	}

	private void focusOn(View v, View movableView, boolean animated) {

		v.getDrawingRect(mTmpRect);
		mMainContainer.offsetDescendantRectToMyCoords(v, mTmpRect);

		movableView.animate().
				translationY(-mTmpRect.top).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(movableView)).
				start();
	}

	private void unfocus(View v, View movableView, boolean animated) {
		movableView.animate().
				translationY(0).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(movableView)).
				start();
	}

	private void fadeOutToBottom(View v, View containerView, boolean animated) {
		int dp = (int) getResources().getDimension(R.dimen.margin);
		v.animate().
				//translationYBy(mHalfHeight).
		translationYBy(containerView.getHeight()+ dp).
				//alpha(0).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(v)).
				start();
	}

	private void fadeInToTop(View v, View containerView, boolean animated) {		
		int dp = (int) getResources().getDimension(R.dimen.margin);
		v.animate().
				//translationYBy(-mHalfHeight).
				translationYBy(-(containerView.getHeight()+dp)).
				//alpha(1).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(v)).
				start();
	}

	private void slideInToTop(View v, boolean animated) {
		v.animate().
				translationY(0).
				alpha(1).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setListener(new LayerEnablingAnimatorListener(v)).
				setInterpolator(ANIMATION_INTERPOLATOR);
	}

	private void slideOutToBottom(View v, boolean animated) {
		v.animate().
				translationY(mHalfHeight * 2).
				alpha(0).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setListener(new LayerEnablingAnimatorListener(v)).
				setInterpolator(ANIMATION_INTERPOLATOR);
	}

	private void stickTo(View v, View viewToStickTo, boolean animated) {
		v.getDrawingRect(mTmpRect);
		mMainContainer.offsetDescendantRectToMyCoords(v, mTmpRect);
		
		v.animate().
				translationY(viewToStickTo.getHeight() - mTmpRect.top).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setInterpolator(ANIMATION_INTERPOLATOR).
				start();
	}

	private void unstickFrom(View v, View viewToStickTo, boolean animated) {
		v.animate().
				translationY(0).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(viewToStickTo)).
				start();
	}
}
