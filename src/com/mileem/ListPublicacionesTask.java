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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mileem.R;
import com.mileem.model.Publication;

public class ListPublicacionesTask extends AsyncTask<Context, Void, Void> {

	private String TAG = this.getClass().getSimpleName();
	private Context ctx;
    private ProgressDialog mProgressDialog;
    private ArrayList<Publication> publications;
	private JSONResponse jResponse;
	private ListView listview;
	//private ArrayAdapter<Publication> adapter;
	private ListPublicacionesViewAdapter adapter;
	//private ArrayList<HashMap<String, String>> arraylist;

    
    public ListPublicacionesTask(Context ctx) {
		this.ctx = ctx;
	}
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(ctx);
        // Set progressdialog title
        mProgressDialog.setTitle("MiLEEM");
        // Set progressdialog message
        mProgressDialog.setMessage("Cargando Publicaciones...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Context... params) {

    	jResponse = JSONutils.getJSONfromURL(ConfigManager.URL_ALLPUBLICATIONS);
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
    	
    	PublicationsFragment publicationFragment = new PublicationsFragment();
    	
    	FragmentManager fragmentManager = ((Activity)ctx).getFragmentManager();
        fragmentManager.beginTransaction().
        add(R.id.container, publicationFragment)
        .commit();
        

    	// Locate the listview in listview_main.xml
    	//listview = (ListView) ((Activity)ctx).findViewById(R.id.listview);
//    	// Pass the results into ListViewAdapter.java
        adapter = new ListPublicacionesViewAdapter(ctx, publications);
    	//adapter = new ArrayAdapter<Publication>(ctx, android.R.layout.simple_list_item_1, publications);
//    	// Set the adapter to the ListView
//    	listview.setAdapter(adapter);
    	
        publicationFragment.setListAdapter(adapter);
    	// Close the progressdialog
    	mProgressDialog.dismiss();
    	
    	if(!jResponse.getError().isEmpty()){
    		Toast.makeText(ctx, jResponse.getError(),Toast.LENGTH_LONG).show();
    	}
    }

    private void loadPublication(Publication publication,JSONObject jsonobject){
    	
    	try {
    		publication.setTransaction_type(jsonobject.getString(Publication.TRAN_TYPE));
    		publication.setProperty_type(jsonobject.getString(Publication.PROP_TYPE));
    		publication.setAddress(jsonobject.getString(Publication.ADDRESS));
    		publication.setNumber_of_rooms(jsonobject.getInt(Publication.NUM_OF_ROOMS));
    		publication.setArea(jsonobject.getInt(Publication.AREA));
    		publication.setPhone(jsonobject.getString(Publication.PHONE));
    		publication.setPrice(jsonobject.getDouble(Publication.PRICE));
    		publication.setExpenses(jsonobject.getDouble(Publication.EXPENSES));
    		publication.setAge(jsonobject.getDouble(Publication.AGE));


		} catch (JSONException e) {
			Log.e(TAG, "Error in parsing JSON");
			e.printStackTrace();
		} 
    }

}
