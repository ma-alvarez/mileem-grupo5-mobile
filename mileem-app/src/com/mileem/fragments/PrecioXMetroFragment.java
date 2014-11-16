package com.mileem.fragments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mileem.ConfigManager;
import com.mileem.HttpUtils;
import com.mileem.JSONResponse;
import com.mileem.R;

public class PrecioXMetroFragment extends Fragment {

	private String TAG = this.getClass().getSimpleName();
	private ProgressBar progressBar;
	private long usd_price = 0, ars_price = 0, exchange_rate = 0;
	private BootstrapButton zone_name, text_ars_price, text_usd_price;
	private TextView text_exchange_rate;
	private Spinner zone_spinner;
	private String title_exchange_rate = "";
	private String last_selected = "";
	
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_preciopromedio, container, false);
        
		zone_name = (BootstrapButton) rootView.findViewById(R.id.text_nombre_barrio);
		text_ars_price = (BootstrapButton) rootView.findViewById(R.id.text_ars_price);
		text_exchange_rate = (TextView) rootView.findViewById(R.id.text_title_exchangerate);
		text_usd_price = (BootstrapButton) rootView.findViewById(R.id.text_usd_price);
		
		title_exchange_rate = text_exchange_rate.getText().toString();
		
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
					new GetAveragePriceTask().execute();
					progressBar.setVisibility(View.VISIBLE);
				}
				last_selected = selected;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		return rootView;
		
	}
	
	class GetAveragePriceTask extends AsyncTask<Void, Void, Void>{
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
						ConfigManager.URL_EXCHANGERATE = ConfigManager.URL_SERVER + "/quotation";
						ConfigManager.URL_AVERAGEUSDPRICE = ConfigManager.URL_SERVER + "/averageByZone";
						bufRead.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		        }
		        
		        jResponse = HttpUtils.getJSONfromURL(ConfigManager.URL_EXCHANGERATE, false);
		        if(!jResponse.getResult().isEmpty()){
		        	String result = jResponse.getResult();
		        	try {
						JSONObject jsonobject = new JSONObject(result);
						exchange_rate = jsonobject.getInt("quotation");
					} catch (JSONException e) {
						Log.e(TAG, "Error in parsing JSON object: EXCHANGE RATE");
						e.printStackTrace();
					}
		        }
		        jResponse = HttpUtils.getJSONfromURL(ConfigManager.URL_AVERAGEUSDPRICE + 
		        		ConfigManager.ZONE2 + zone_spinner.getSelectedItem().toString().replace(" ", "_").toLowerCase(), false);
		        if(!jResponse.getResult().isEmpty()){
		    		String result = jResponse.getResult();
		    		try {
		    			JSONObject jsonobject = new JSONObject(result);
		    			usd_price = jsonobject.getInt("promedio");
		    			ars_price = usd_price * exchange_rate;
		    			
    				} catch (JSONException e) {
    					Log.e(TAG, "Error in parsing JSON object: AVERAGE USD PRICE");
    					e.printStackTrace();
    				}
		    	}
		    	return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			
	        DecimalFormat df = new DecimalFormat("#,###,###,##0" );
	        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "AR"));
	        symbols.setDecimalSeparator(',');
	        symbols.setGroupingSeparator('.');
	        df.setDecimalFormatSymbols(symbols);
	        
			zone_name.setText(zone_spinner.getSelectedItem().toString());
			text_ars_price.setText("ARS " + df.format(ars_price));
			text_usd_price.setText("USD " + df.format(usd_price));
			
			String title_exchange_rate_aux = title_exchange_rate.concat(Long.toString(exchange_rate));
			text_exchange_rate.setText(title_exchange_rate_aux);
			
			progressBar.setVisibility(View.GONE);
		}

	}

}
