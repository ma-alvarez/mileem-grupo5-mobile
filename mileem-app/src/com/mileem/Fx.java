package com.mileem;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.example.mileem.R;


public class Fx {
	
	private ViewGroup mainContainer;
	private TimeInterpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
	private int ANIMATION_DURATION = 300;
	private int mHalfHeight;
	private final Rect mTmpRect = new Rect();
	
	public Fx(ViewGroup container){
		mainContainer = container;
		mHalfHeight = 100;
	}
	
	public void focusOn(View v, View movableView) {

		v.getDrawingRect(mTmpRect);
		mainContainer.offsetDescendantRectToMyCoords(v, mTmpRect);

		movableView.animate().
				translationY(-mTmpRect.top).
				setDuration(ANIMATION_DURATION).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(movableView)).
				start();
	}

	public void unfocus(View v, View movableView) {
		movableView.animate().
				translationY(0).
				setDuration(ANIMATION_DURATION).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(movableView)).
				start();
	}
	
	public void fadeOutToBottom(View v, View containerView, int offset) {
		
		v.animate().
				translationYBy(containerView.getHeight() + offset).
				setDuration(ANIMATION_DURATION).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(v)).
				start();
	}

	public void fadeInToTop(View v, View containerView, int offset) {		
		
		v.animate().
				translationYBy(-(containerView.getHeight() + offset)).
				setDuration(ANIMATION_DURATION).
				setInterpolator(ANIMATION_INTERPOLATOR).
				setListener(new LayerEnablingAnimatorListener(v)).
				start();
	}
	
	public void slideInToTop(View v) {
		v.animate().
				translationY(0).
				alpha(1).
				setDuration(ANIMATION_DURATION).
				setListener(new LayerEnablingAnimatorListener(v)).
				setInterpolator(ANIMATION_INTERPOLATOR);
	}

	public void slideOutToBottom(View v) {
		v.animate().
				translationY(mHalfHeight * 2).
				alpha(0).
				setDuration(ANIMATION_DURATION).
				setListener(new LayerEnablingAnimatorListener(v)).
				setInterpolator(ANIMATION_INTERPOLATOR);
	}
	
	public static void slide_down(Context ctx, View v){
		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
		if(a != null){
			a.reset();
			if(v != null){
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}
	
	public static void slide_up(Context ctx, View v){
		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
		if(a != null){
			a.reset();
			if(v != null){
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}
}
