package com.mileem.fragments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mileem.ConfigManager;
import com.mileem.HttpUtils;
import com.mileem.JSONResponse;
import com.mileem.R;

public class AmbXBarrioFragment extends Fragment {

	private String TAG = this.getClass().getSimpleName();
	private WebView webView;
	private Spinner zone_spinner;
	private String last_selected = "";
	private int num1, num2, num3, num4;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_ambxbarrio, container, false);
		
        webView = (WebView)rootView.findViewById(R.id.web);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        zone_spinner = (Spinner) rootView.findViewById(R.id.spinner1);
        String[] zones = getResources().getStringArray(R.array.neighbourhoods);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
        	     this.getActivity(),
        	     android.R.layout.simple_spinner_item,
        	     new ArrayList<CharSequence>(Arrays.asList(zones)));
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//        		this.getActivity(), R.array.neighbourhoods, android.R.layout.simple_spinner_item );
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
		@JavascriptInterface
		public int getNum4() {
			return num4;
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
						ConfigManager.URL_ROOMSBYZONE = ConfigManager.URL_SERVER + "/roomsByZone";
						bufRead.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        }
		    	
		    	jResponse = HttpUtils.getJSONfromURL(ConfigManager.URL_ROOMSBYZONE + 
		    			ConfigManager.ZONE2 + zone_spinner.getSelectedItem().toString().replace(" ", "\b"));
		    	if(!jResponse.getResult().isEmpty()){
		    		String result = jResponse.getResult();
    				try {
						JSONObject jsonobject = new JSONObject(result);
						num1 = jsonobject.getInt("amb1");
						num2 = jsonobject.getInt("amb2");
						num3 = jsonobject.getInt("amb3");
						num4 = jsonobject.getInt("amb4");
	    				
    				} catch (JSONException e) {
    					Log.e(TAG, "Error in parsing JSON object.");
    					e.printStackTrace();
    				}
		    	}
		    	return null;
		}

		@Override
		protected void onPostExecute(Void args) {
	        webView.getSettings().setJavaScriptEnabled(true); 
	        webView.loadUrl("file:///android_asset/chart.html");
		}

	}

}
