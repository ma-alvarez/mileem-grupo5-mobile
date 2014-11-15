package com.mileem.fragments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.mileem.ConfigManager;
import com.mileem.HttpUtils;
import com.mileem.JSONResponse;
import com.mileem.R;

public class ZonasAledanasFragment extends Fragment {

	private String TAG = this.getClass().getSimpleName();
	private WebView webView;
	private ProgressBar progressBar;
	private Spinner zone_spinner;
	private String last_selected = "";
	private int[] avg;
	private String[] zones;
	private boolean notAvailable = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_zonasaledanas, container, false); 
		
        webView = (WebView)rootView.findViewById(R.id.web);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(new myWebClient());
        
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress1);
        zone_spinner = (Spinner) rootView.findViewById(R.id.spinner1);
        String[] zones = getResources().getStringArray(R.array.neighbourhoods);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
        	     this.getActivity(),
        	     android.R.layout.simple_spinner_item,
        	     new ArrayList<CharSequence>(Arrays.asList(zones)));
        adapter.remove("Todas");
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        
        zone_spinner.setAdapter(adapter);		 
        
        zone_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selected = parent.getItemAtPosition(position).toString();
				if (!selected.equals(last_selected)){
					new GetChartDataTask().execute();	
				}
				last_selected = selected;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
        
        new GetChartDataTask().execute();
		return rootView;
		
	}
	

	public class WebAppInterface {

		
		@JavascriptInterface
		public int getCount() {
			return zones.length;
		}
		
		@JavascriptInterface
		public String getZoneAt(int idx) {
			return zones[idx];
		}
		
		@JavascriptInterface
		public int getAvgAt(int idx) {
			return avg[idx];
		}

		@JavascriptInterface
		public String getBarrio() {
			return zone_spinner.getSelectedItem().toString();
		}
		
		@JavascriptInterface
		public boolean notEnoughData() {
			if (notAvailable) return true;
			int sum = 0;
			for(int i=0; i < avg.length;i++){
				sum += avg[i];
			}
			return (sum == 0) ? true : false;
		}
		
	}
	
	class GetChartDataTask extends AsyncTask<Void, Void, Void>{
		private JSONResponse jResponse;
		
		@Override
		protected Void doInBackground(Void... params) {
			 File dir = Environment.getExternalStorageDirectory();
		        String fileName = dir + "/" + "IP.txt";
		        File f = new File(fileName);
		        if (f.exists()){
		        	try {
						FileReader fis = new FileReader(f);
						BufferedReader bufRead = new BufferedReader(fis, 100);
						String ip_line = bufRead.readLine();
						ConfigManager.URL_SERVER = ip_line;
						ConfigManager.URL_ZONECOMPARISON = ConfigManager.URL_SERVER + "/zoneComparison";
						bufRead.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        }
		    	
		    	jResponse = HttpUtils.getJSONfromURL(ConfigManager.URL_ZONECOMPARISON + 
		    			ConfigManager.ZONE2 + zone_spinner.getSelectedItem().toString().replace(" ", "_").toLowerCase());
		    	if(!jResponse.getResult().isEmpty()){
		    		String result = jResponse.getResult();
		    		try {
		    			JSONObject jsonobject = new JSONObject(result);
		    			Iterator<String> iter = jsonobject.keys();
		    			avg = new int[jsonobject.length()];
		    			zones = new String[jsonobject.length()];
		    			int idx = 0;
		    			while (iter.hasNext()) {
		    				String key = iter.next();
		    				zones[idx] = key;
		    				avg[idx] = jsonobject.getInt(key);
		    				idx++;
		    			}

	    				notAvailable = false;
    				} catch (JSONException e) {
    					Log.e(TAG, "Error in parsing JSON object.");
    					notAvailable = true;
    					e.printStackTrace();
    				}
		    	}
		    	return null;
		}

		@Override
		protected void onPostExecute(Void args) {
	        webView.getSettings().setJavaScriptEnabled(true); 
	        webView.loadUrl("file:///android_asset/" + "chart_zonasaledanas.html");
		}

	}

	private class myWebClient extends WebViewClient
	{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			progressBar.setVisibility(View.VISIBLE);
			view.loadUrl(url);
			return true;

		}
		@Override
		public void onPageFinished(WebView webview, String url){
			super.onPageFinished(webview, url);
			progressBar.setVisibility(View.GONE);
		}
	}

}
