package com.mileem.tasks;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.mileem.ConfigManager;
import com.mileem.HttpUtils;
import com.mileem.JSONResponse;
import com.mileem.fragments.PricesFragment;


public class QuotationTask extends AsyncTask<Void, Void, Void> {

	private String TAG = this.getClass().getSimpleName();
	private PricesFragment pFragment;
    private NoResultsDialog mNoticeDialog;
    private String quotation;
	private JSONResponse jResponse;
    
    public QuotationTask(PricesFragment fragment) {
		this.pFragment = fragment;
	}

    @Override
    protected Void doInBackground(Void... params) {
    	
    	
    	jResponse = HttpUtils.getJSONfromURL(ConfigManager.URL_QUOTATION, true);

    	if(jResponse.getError().isEmpty()){
    		
    		try{
				JSONObject jsonobject = jResponse.getJobject();
				
				quotation = jsonobject.getString("quotation");
				Log.e(TAG, quotation);
			} catch (JSONException e) {
				Log.e(TAG, "Error in parsing JSON object ");
				e.printStackTrace();
			} 				
    			
    	}
    	return null;
    }

    @Override
    protected void onPostExecute(Void args) {
    	
    	if (quotation == null || quotation.isEmpty()){
    		mNoticeDialog = new NoResultsDialog();
    		mNoticeDialog.show(pFragment.getActivity().getSupportFragmentManager(), 
    							"NoResultsDialogFragment");  		
    	}
    	else{
    		pFragment.setQuotation(quotation);
    		FragmentTransaction ft = pFragment.getActivity().getSupportFragmentManager().beginTransaction();
    		ft.detach(pFragment);
    		ft.attach(pFragment);
    		ft.commit();
    		if(!jResponse.getError().isEmpty()){
    			Toast.makeText(pFragment.getActivity(), jResponse.getError(),Toast.LENGTH_LONG).show();
    		}
    	}
    }

   
    
    private class NoResultsDialog extends DialogFragment {
        	
    	@Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("No se encuentra la cotización")
                   .setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                    	   dialog.dismiss();
                    	   QuotationTask.this.pFragment.getFragmentManager().popBackStack();
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
    

}
