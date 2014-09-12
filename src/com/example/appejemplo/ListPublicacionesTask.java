package com.example.appejemplo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ListPublicacionesTask extends AsyncTask<Context, Void, Void> {

	private Context ctx;
    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, String>> arraylist;
    private JSONObject jsonobject;
	private JSONArray jsonarray;
    
    public ListPublicacionesTask(Context ctx) {
		this.ctx = ctx;
	}
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        // Create a progressdialog
//        mProgressDialog = new ProgressDialog(ctx);
//        // Set progressdialog title
//        mProgressDialog.setTitle("MiLEEM");
//        // Set progressdialog message
//        mProgressDialog.setMessage("Cargando Publicaciones...");
//        mProgressDialog.setIndeterminate(false);
//        // Show progressdialog
//        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Context... params) {
    	
    	arraylist = new ArrayList<HashMap<String, String>>();
    	jsonobject = JSONutils.getJSONfromURL("http://www.androidbegin.com/tutorial/jsonparsetutorial.txt");

    	return null;
    }

    @Override
    protected void onPostExecute(Void args) {
//    	// Locate the listview in listview_main.xml
//    	listview = (ListView) findViewById(R.id.listview);
//    	// Pass the results into ListViewAdapter.java
//    	adapter = new ListViewAdapter(ctx, arraylist);
//    	// Set the adapter to the ListView
//    	listview.setAdapter(adapter);
//    	// Close the progressdialog
//    	mProgressDialog.dismiss();
    	Toast.makeText(ctx, jsonobject.toString(),Toast.LENGTH_SHORT).show();
    }

}
