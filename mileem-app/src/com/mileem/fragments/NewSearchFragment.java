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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.LayerEnablingAnimatorListener;

public class NewSearchFragment extends Fragment {

	private final Rect mTmpRect = new Rect();

	private FrameLayout mMainContainer, mEditModeContainer, mEditFragmentContainer;
	private TextView mTv2, mTv3;
	private BootstrapButton mTv1;
	private RelativeLayout mFirstGroup;
	private ArrayList<View> buttons;
	private ArrayList<Fragment> fragments;

	private TimeInterpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
	private int ANIMATION_DURATION = 300;
	private int mHalfHeight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_test, container, false);
		
		retrieveViews(rootView);
		mHalfHeight = 400;
		mEditModeContainer.setTranslationY(mHalfHeight);
		mEditModeContainer.setAlpha(0f);
		
		fragments = new ArrayList<Fragment>();
		
		Fragment f_ejemplo = new FragmentEjemplo();
		fragments.add(f_ejemplo);
		Fragment operation_fragment = new FragmentOperationType();
		fragments.add(operation_fragment);
		f_ejemplo = new FragmentDatePicker();
		fragments.add(f_ejemplo);
		
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        for(Fragment fragment : fragments){
            fragmentManager.beginTransaction()
            .add(R.id.edit_mode_fragment_container,fragment)
            .hide(fragment)
            .commit();
        }

		return rootView;

	}
	
	private void retrieveViews(View rootView) {
		mMainContainer = (FrameLayout) rootView.findViewById(R.id.main_container);
		mFirstGroup = (RelativeLayout) rootView.findViewById(R.id.scrollview_container);

		buttons = new ArrayList<View>(); 
		
		mTv1 = (BootstrapButton) rootView.findViewById(R.id.btnBig);
		mTv2 = (TextView) rootView.findViewById(R.id.tv2);
		mTv3 = (TextView) rootView.findViewById(R.id.tv3);
		
		buttons.add(mTv1);
		buttons.add(mTv2);
		buttons.add(mTv3);
		
		for(int i=0; i < buttons.size(); i++){
			buttons.get(i).setOnClickListener(new myOnclickListener(i));
		}


		mEditModeContainer = (FrameLayout) rootView.findViewById(R.id.edit_mode_container);
		mEditFragmentContainer = (FrameLayout) rootView.findViewById(R.id.edit_mode_fragment_container);
		
	}

	private class myOnclickListener implements OnClickListener{
		int i, sw = 0;

		public myOnclickListener(int index){
			i = index;
		}
		
		@Override
		public void onClick(View v) {
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			if(sw % 2 != 0){
				fragmentManager.beginTransaction()
				.hide(fragments.get(i))
				.commit();
				contraer(v,i);
			}else{
				fragmentManager.beginTransaction()
				.show(fragments.get(i))
				.commit();
				expander(v,i);
			}
			sw++;

		}

	}
	
	private void expander(View v, int index){
		focusOn(v, mFirstGroup, true);
		for(int i= index; i < buttons.size();i++){
			
			if(i + 1 < buttons.size())
				fadeOutToBottom(buttons.get(i + 1), true);
		}

		//stickTo(mFirstSpacer, viewFrom, true);
		slideInToTop(mEditModeContainer, true);
		mEditModeContainer.setVisibility(View.VISIBLE);
	}
	
	private void contraer(View v, int index){
		slideOutToBottom(mEditModeContainer, true);
		//unstickFrom(mFirstSpacer, viewFrom, true);
		for(int i= index; i < buttons.size();i++){
			
			if(i + 1 < buttons.size())
				fadeInToTop(buttons.get(i + 1), true);
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

	private void fadeOutToBottom(View v, boolean animated) {
		
		LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.edit_mode_fragment);
		
		v.animate().
				//translationYBy(mHalfHeight).
		translationYBy(mEditFragmentContainer.getHeight()+ll.getPaddingTop()).
				//alpha(0).
				setDuration(animated ? ANIMATION_DURATION : 0).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(v)).
				start();
	}

	private void fadeInToTop(View v, boolean animated) {
		LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.edit_mode_fragment);
		
		v.animate().
				//translationYBy(-mHalfHeight).
				translationYBy(-(mEditFragmentContainer.getHeight()+ll.getPaddingTop())).
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
