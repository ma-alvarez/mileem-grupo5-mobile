package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.mileem.R;

public class PrecioXMetroFragment extends Fragment {

	private WebView webView;
	private int num1=1, num2=2, num3=3;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_ambxbarrio, container, false);
		
        webView = (WebView)rootView.findViewById(R.id.web);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        
        webView.getSettings().setJavaScriptEnabled(true); 
        webView.loadUrl("file:///android_asset/chart.html");
		
		return rootView;
		
	}
	

	public class WebAppInterface {

		@JavascriptInterface
		public int getNum1() {
			return num1;
		}

		@JavascriptInterface
		public int getNum2() {
			return num2;
		}

		@JavascriptInterface
		public int getNum3() {
			return num3;
		}
	}

}
