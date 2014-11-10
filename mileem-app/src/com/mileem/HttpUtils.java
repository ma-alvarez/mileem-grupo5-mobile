package com.mileem;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUtils {
	private static String TAG = "HttpUtils";

   public static JSONResponse getJSONfromURL(String url,Boolean simpleJSON) {
       InputStream is = null;
       String result = "";
       JSONArray jArray = null;
       JSONResponse jResponse = new JSONResponse();

       // Download JSON data from URL
       try {
           HttpClient httpclient = new DefaultHttpClient();
           HttpGet httpget = new HttpGet(url);
           HttpResponse response = httpclient.execute(httpget);
           HttpEntity entity = response.getEntity();
           is = entity.getContent();

       } catch (Exception e) {
    	   jResponse.setError(e.getMessage());
           Log.e("log_tag", "Error in http connection " + e.toString());
       }

       // Convert response to string
       try {
           BufferedReader reader = new BufferedReader(new InputStreamReader(
                   is, "utf-8"), 8);
           StringBuilder sb = new StringBuilder();
           String line = null;
           while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
           }
           is.close();
           result = sb.toString();
       } catch (Exception e) {
           Log.e(TAG, "Error converting result " + e.toString());
       }
       
       if(!result.isEmpty()){
    	   try {
    		   if ( simpleJSON ){
    			   jResponse.setJobject(new JSONObject(result));
    		   }
    		   else {
    			   jArray = new JSONArray(result);
        		   jResponse.setjArray(jArray);
    		   }		   
    		   
    	   } catch (JSONException e) {
    		   jResponse.setError(e.getMessage());
    		   Log.e(TAG, "Error parsing data " + e.toString());
    	   }
       }
       return jResponse;
   }

   public static Bitmap getIconFromURL(String src){
	   try {
		   URL url = new URL(src);
		   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		   connection.setDoInput(true);
		   connection.connect();
		   InputStream input = connection.getInputStream();
		   Bitmap myBitmap = BitmapFactory.decodeStream(input);

		   myBitmap = Bitmap.createScaledBitmap(myBitmap, 100, 100, false);
		   return myBitmap;
	   } catch (IOException e) {
		   e.printStackTrace();
		   Log.e(TAG, "Error fetching img from URL:  " + src);
		   return null;
	   }

   }
   
   public static Bitmap getBitmapFromURL(String src){
	   try {
		   URL url = new URL(src);
		   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		   connection.setDoInput(true);
		   connection.connect();
		   InputStream input = connection.getInputStream();
		   Bitmap myBitmap = BitmapFactory.decodeStream(input);

		   myBitmap = Bitmap.createScaledBitmap(myBitmap, 300, 200, false);
		   return myBitmap;
	   } catch (IOException e) {
		   e.printStackTrace();
		   Log.e(TAG, "Error fetching img from URL:  " + src);
		   return null;
	   }

   }
   
   public static void getFileFromURLandStoreIt(String src, File file){
	   try {
		   URL url = new URL(src);
		   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		   connection.setDoInput(true);
		   connection.connect();
		   InputStream input = connection.getInputStream();
		   
		   OutputStream stream = new BufferedOutputStream(new FileOutputStream(file)); 
		   int bufferSize = 1024;
		   byte[] buffer = new byte[bufferSize];
		   int len = 0;
		   while ((len = input.read(buffer)) != -1) {
		       stream.write(buffer, 0, len);
		   }
		   if(stream!=null)
		       stream.close();

	   } catch (IOException e) {
		   e.printStackTrace();
		   Log.e(TAG, "Error fetching img from URL:  " + src);
	   }

   }
}
