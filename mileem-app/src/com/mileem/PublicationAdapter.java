package com.mileem;

import android.view.View;

import com.mileem.ListPublicacionesViewAdapter.ViewHolder;
import com.mileem.model.Publication;

public abstract class PublicationAdapter {
	
	protected Publication mPublication;
	
	public abstract int getLayoutId();
	public abstract void addImages(ViewHolder v, View view);
	
	public Publication getPublication(){
		return mPublication;
	}
	
	public void setPublication(Publication p){
		mPublication = p;
	}
}
