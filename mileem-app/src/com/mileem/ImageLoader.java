package com.mileem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;

public class ImageLoader {

	private static LruCache<String,Bitmap> memoryCache;
	private static FileCache fileCache;
	private static Map<ImageView,String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView,String>());
	private static Context context;
	private static int px = 100;
	private static int py = 100;
	private static Drawable mStubDrawable;

	public ImageLoader(Context context) {
		init(context);
	}

	public static void init(Context ctx) {
		context = ctx;
		fileCache = new FileCache(context);
		// Get memory class of this device, exceeding this amount will throw an
		// OutOfMemory exception.
		final int memClass = ((ActivityManager) context.getSystemService(
				Context.ACTIVITY_SERVICE)).getMemoryClass();
		// 1/8 of the available mem
		final int cacheSize = 1024 * 1024 * memClass / 8;
		memoryCache = new LruCache<String,Bitmap>(cacheSize);

		mStubDrawable = context.getResources().getDrawable(R.drawable.ic_noimage);
	}

	public static void displayImage(String url, ImageView imageView, int _px, int _py) {
		px = _px;
		py = _py;
		imageViews.put(imageView, url);
		Bitmap bitmap = null;
		if (url != null )
			bitmap = (Bitmap) memoryCache.get(url);
		if (bitmap != null) {
			//the image is in the LRU Cache, we can use it directly
			imageView.setImageBitmap(bitmap);
		} else {
			//the image is not in the LRU Cache
			//set a default drawable a search the image
			imageView.setImageDrawable(mStubDrawable);
			if (url != null )
				queuePhoto(url, imageView);
		}
	}

	private static void queuePhoto(String url, ImageView imageView) {
		new LoadBitmapTask().execute(url, imageView);
	}

	/**
	 * Search for the image in the device, then in the web
	 * @param url
	 * @return
	 */
	private static Bitmap getBitmap(String url) {
		Bitmap ret = null;
		//from SD cache
		File f = fileCache.getFile(url);
		if (f.exists()) {
			try {
				ret = decodeSampledBitmapFromResourceMemOpt(new FileInputStream(f),px,py);
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			//ret = decodeFile(f);
			if (ret != null)
				return ret;
		}

		//from web
		try {
			//your requester will fetch the bitmap from the web and store it in the phone using the fileCache
			HttpUtils.getFileFromURLandStoreIt(ConfigManager.URL_SERVER + url,fileCache.getFile(url));
			File storedFile = fileCache.getFile(url);	
			ret = decodeSampledBitmapFromResourceMemOpt(new FileInputStream(storedFile),px,py);

			return ret;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Bitmap decodeSampledBitmapFromResourceMemOpt(
            InputStream inputStream, int reqWidth, int reqHeight) {

        byte[] byteArr = new byte[0];
        byte[] buffer = new byte[1024];
        int len;
        int count = 0;

        try {
            while ((len = inputStream.read(buffer)) > -1) {
                if (len != 0) {
                    if (count + len > byteArr.length) {
                        byte[] newbuf = new byte[(count + len) * 2];
                        System.arraycopy(byteArr, 0, newbuf, 0, count);
                        byteArr = newbuf;
                    }

                    System.arraycopy(buffer, 0, byteArr, count, len);
                    count += len;
                }
            }

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(byteArr, 0, count, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth,
                    reqHeight);
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;


            return BitmapFactory.decodeByteArray(byteArr, 0, count, options);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        // Calculate ratios of height and width to requested height and width
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);

	        // Choose the smallest ratio as inSampleSize value, this will guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }


	    return inSampleSize;
	}

	private static class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	private static boolean imageViewReused(PhotoToLoad photoToLoad) {
		//tag used here
		String tag = (String) imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	private static class LoadBitmapTask extends AsyncTask<Object, Void, TransitionDrawable> {
		private PhotoToLoad mPhoto;

		@Override
		protected TransitionDrawable doInBackground(Object... params) {
			mPhoto = new PhotoToLoad((String) params[0], (ImageView) params[1]);

			if (imageViewReused(mPhoto))
				return null;
			Bitmap bmp = getBitmap(mPhoto.url);
			if (bmp == null)
				return null;
			memoryCache.put(mPhoto.url, bmp);

			// TransitionDrawable let you to make a crossfade animation between 2 drawables
			// It increase the sensation of smoothness
			TransitionDrawable td = null;
			if (bmp != null) {
				Drawable[] drawables = new Drawable[2];
				drawables[0] = mStubDrawable;
				drawables[1] = new BitmapDrawable(context.getResources(), bmp);
				td = new TransitionDrawable(drawables);
				td.setCrossFadeEnabled(true); //important if you have transparent bitmaps
			}

			return td;
		}

		@Override
		protected void onPostExecute(TransitionDrawable td) {
			if (imageViewReused(mPhoto)) {
				//imageview reused, just return
				return;
			}
			if (td != null) {
				// bitmap found, display it !
				mPhoto.imageView.setImageDrawable(td);
				mPhoto.imageView.setVisibility(View.VISIBLE);

				//a little crossfade
				td.startTransition(200);
			} else {
				//bitmap not found, display the default drawable
				mPhoto.imageView.setImageDrawable(mStubDrawable);
			}
		}
	}
}

