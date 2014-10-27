package com.mileem.tasks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.mileem.ConfigManager;
import com.mileem.HttpUtils;
import com.mileem.JSONResponse;
import com.mileem.ListPublicacionesViewAdapter;
import com.mileem.fragments.PublicationsFragment;
import com.mileem.model.Publication;
import com.mileem.model.PublicationBasic;
import com.mileem.model.PublicationFree;
import com.mileem.model.PublicationPremium;

public class ListPublicacionesTask extends AsyncTask<Void, Void, Void> {

	private String TAG = this.getClass().getSimpleName();
	private PublicationsFragment pFragment;
    private NoResultsDialog mNoticeDialog;
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
						String relevance = jsonobject.getString(Publication.RELEVANCE);
						
						if(relevance == "1"){
							publication = new PublicationFree();
						}else{
							if(relevance == "2"){
								publication = new PublicationBasic();
							}else{
								publication = new PublicationPremium();
							}
						}
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
    	
    	if (publications.isEmpty()){
    		/*Toast.makeText(
    			pFragment.getActivity(), 
				"No hay Publicaciones", 
				Toast.LENGTH_LONG).show();*/
    		
    		mNoticeDialog = new NoResultsDialog();
    		mNoticeDialog.show(pFragment.getActivity().getSupportFragmentManager(), 
    							"NoResultsDialogFragment");  		
    	}
    	else{
    		pFragment.setPublicaciones(publications);

//    	// Pass the results into ListViewAdapter.java
    		adapter = new ListPublicacionesViewAdapter(pFragment.getActivity(), publications);
    	
    		pFragment.setListAdapter(adapter);
    	
    		if(!jResponse.getError().isEmpty()){
    			Toast.makeText(pFragment.getActivity(), jResponse.getError(),Toast.LENGTH_LONG).show();
    		}
    	}
    }

    private void loadPublication(Publication publication,JSONObject jsonobject){
    	
    	try {
    		publication.setRelevance(jsonobject.getString(Publication.RELEVANCE));
    		publication.setTransaction_type(jsonobject.getString(Publication.TRAN_TYPE));
    		publication.setProperty_type(jsonobject.getString(Publication.PROP_TYPE));
    		publication.setAddress(jsonobject.getString(Publication.ADDRESS));
    		publication.setZone(jsonobject.getString(Publication.ZONE));
    		publication.setNumber_of_rooms(jsonobject.getInt(Publication.NUM_OF_ROOMS));
    		publication.setArea(jsonobject.getInt(Publication.AREA));
    		publication.setLatitude(jsonobject.getDouble(Publication.LATITUDE));
    		publication.setLongitude(jsonobject.getDouble(Publication.LONGITUDE));
    		publication.setCurrency(jsonobject.getString(Publication.CURRENCY));
    		publication.setPrice(jsonobject.getDouble(Publication.PRICE));
    		publication.setExpenses(jsonobject.getDouble(Publication.EXPENSES));
    		publication.setAge(jsonobject.getDouble(Publication.AGE));
    		JSONObject user = jsonobject.getJSONObject(Publication.USER);
    		
    		publication.setUser_email(user.getString(Publication.USER_EMAIL));
    		publication.setPhone(user.getString(Publication.USER_PHONE));
    		
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
    
    private class NoResultsDialog extends DialogFragment {
        	
    	@Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("No hay publicaciones")
                   .setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                    	   dialog.dismiss();
                    	   ListPublicacionesTask.this.pFragment.getFragmentManager().popBackStack();
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
    

}
