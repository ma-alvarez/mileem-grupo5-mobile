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
	
	public static PublicationAdapter newAdapterInstance(Publication pub){
		
		PublicationAdapter pAdapter = null;
		if(pub.getRelevance().equalsIgnoreCase("1")){
			pAdapter = new PublicationFreeAdapter();
		}else{
			if(pub.getRelevance().equalsIgnoreCase("2")){
				pAdapter = new PublicationBasicAdapter();
			}else{
				pAdapter = new PublicationPremiumAdapter();
			}
		}
		pAdapter.setPublication(pub);
		return pAdapter;
	}
}
