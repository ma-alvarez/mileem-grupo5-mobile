package com.mileem;

import com.mileem.model.Publication;

public abstract class PublicationAdapter {
	
	private Publication mPublication;
	
	public abstract int getLayoutId();
	
	public Publication getPublication(){
		return mPublication;
	}
	
	public void setPublication(Publication p){
		mPublication = p;
	}
}
