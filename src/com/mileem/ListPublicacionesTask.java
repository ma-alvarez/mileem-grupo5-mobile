package com.mileem;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mileem.R;
import com.mileem.model.Publication;

public class ListPublicacionesTask extends AsyncTask<Void, Void, Void> {

	private String TAG = this.getClass().getSimpleName();
	private PublicationsFragment pFragment;
    private ProgressDialog mProgressDialog;
    private ArrayList<Publication> publications;
	private JSONResponse jResponse;
	private ListPublicacionesViewAdapter adapter;
	private String query;

    
    public ListPublicacionesTask(PublicationsFragment fragment) {
		this.pFragment = fragment;
		query = fragment.getPublicationsQuery();
	}
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        // Create a progressdialog
//        mProgressDialog = new ProgressDialog(pFragment.getActivity());
//        // Set progressdialog title
//        mProgressDialog.setTitle("MiLEEM");
//        // Set progressdialog message
//        mProgressDialog.setMessage("Cargando Publicaciones...");
//        mProgressDialog.setIndeterminate(false);
//        // Show progressdialog
//        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

    	jResponse = HttpUtils.getJSONfromURL(ConfigManager.URL_SEARCH + query);
    	publications = new ArrayList<Publication>();

    	if(jResponse.getError().isEmpty()){
    		JSONArray jsonarray = jResponse.getjArray();

    			for (int i = 0; i < jsonarray.length(); i++) {
    				Publication publication = null;
    				
    				try {
						JSONObject jsonobject = jsonarray.getJSONObject(i);
	    				publication = new Publication();
	    				loadPublication(publication, jsonobject);
	    				
    				} catch (JSONException e) {
    					Log.e(TAG, "Error in parsing JSON object " + i);
    					e.printStackTrace();
					}

    				publications.add(publication);
    			}

    	}
    	return null;
    }

    @Override
    protected void onPostExecute(Void args) {
    	
    	pFragment.setPublicaciones(publications);
//    	PublicationsFragment publicationFragment = new PublicationsFragment();

    	// Locate the listview in listview_main.xml
    	//listview = (ListView) ((Activity)ctx).findViewById(R.id.listview);
//    	// Pass the results into ListViewAdapter.java
        adapter = new ListPublicacionesViewAdapter(pFragment.getActivity(), publications);
    	
        pFragment.setListAdapter(adapter);
    	// Close the progressdialog
//    	mProgressDialog.dismiss();
    	
    	if(!jResponse.getError().isEmpty()){
    		Toast.makeText(pFragment.getActivity(), jResponse.getError(),Toast.LENGTH_LONG).show();
    	}
    }

    private void loadPublication(Publication publication,JSONObject jsonobject){
    	
    	try {
    		publication.setTransaction_type(jsonobject.getString(Publication.TRAN_TYPE));
    		publication.setProperty_type(jsonobject.getString(Publication.PROP_TYPE));
    		publication.setAddress(jsonobject.getString(Publication.ADDRESS));
    		publication.setZone(jsonobject.getString(Publication.ZONE));
    		publication.setNumber_of_rooms(jsonobject.getInt(Publication.NUM_OF_ROOMS));
    		publication.setArea(jsonobject.getInt(Publication.AREA));
    		publication.setPhone(jsonobject.getString(Publication.PHONE));
    		publication.setPrice(jsonobject.getDouble(Publication.PRICE));
    		publication.setExpenses(jsonobject.getDouble(Publication.EXPENSES));
    		publication.setAge(jsonobject.getDouble(Publication.AGE));
    		JSONArray attachments = jsonobject.getJSONArray(Publication.ATTACHMENTS);
    		
    		for(int i=0; i < attachments.length(); i++){
    			JSONObject image = attachments.getJSONObject(i);
    			JSONObject url = image.getJSONObject(Publication.URL_IMAGE);
    			publication.addUrl_Image(url.getString(Publication.URL));
    		}

		} catch (JSONException e) {
			Log.e(TAG, "Error in parsing JSON");
			e.printStackTrace();
		} 
    }

}
