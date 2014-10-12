package com.mileem.tasks;

import java.lang.ref.WeakReference;

import com.mileem.ConfigManager;
import com.mileem.HttpUtils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private String url;
    private Boolean icon;

    public BitmapWorkerTask(ImageView imageView, Boolean icon) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.icon = icon;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
    	url = params[0];
    	Bitmap bitmap;
    	if (this.icon){
    		bitmap = HttpUtils.getIconFromURL(ConfigManager.URL_SERVER + url);
    	}
    	else {
    		bitmap = HttpUtils.getBitmapFromURL(ConfigManager.URL_SERVER + url);
    	}
		return bitmap;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}