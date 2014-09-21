package com.mileem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUtils {
	private static String TAG = "HttpUtils";

   public static JSONResponse getJSONfromURL(String url) {
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
                   is, "iso-8859-1"), 8);
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

    		   jArray = new JSONArray(result);
    		   jResponse.setjArray(jArray);
    		   
    	   } catch (JSONException e) {
    		   jResponse.setError(e.getMessage());
    		   Log.e(TAG, "Error parsing data " + e.toString());
    	   }
       }
       return jResponse;
   }

   public static Bitmap getBitmapFromURL(String src){
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
   
   private static Bitmap decodeSampledBitmapFromResource(InputStream stream,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeStream(stream, null, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeStream(stream, null, options);
	}
   
   public static int calculateInSampleSize(
           BitmapFactory.Options options, int reqWidth, int reqHeight) {
   // Raw height and width of image
   final int height = options.outHeight;
   final int width = options.outWidth;
   int inSampleSize = 1;

   if (height > reqHeight || width > reqWidth) {

       final int halfHeight = height / 2;
       final int halfWidth = width / 2;

       // Calculate the largest inSampleSize value that is a power of 2 and keeps both
       // height and width larger than the requested height and width.
       while ((halfHeight / inSampleSize) > reqHeight
               && (halfWidth / inSampleSize) > reqWidth) {
           inSampleSize *= 2;
       }
   }

   return inSampleSize;
}
}
