package com.mileem.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageUtils {

	public static Bitmap ShrinkBitmap(String file, int width, int height) {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) height);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) width);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}

		bmpFactoryOptions.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
		return bitmap;
	}

	// this function will make your image to file
	public static File String_to_File(String img_url, File casted_image) {
		try {
			File rootSdDirectory = Environment.getExternalStorageDirectory();
			casted_image = new File(rootSdDirectory, "attachment.jpg");
			if (casted_image.exists()) {
				casted_image.delete();
			}
			casted_image.createNewFile();
			FileOutputStream fos = new FileOutputStream(casted_image);
			URL url = new URL(img_url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			InputStream in = connection.getInputStream();
			byte[] buffer = new byte[1024];
			int size = 0;
			while ((size = in.read(buffer)) > 0) {
				fos.write(buffer, 0, size);
			}
			// Bitmap bm = ShrinkBitmap(img_url, 50, 50);
			// bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);
			fos.close();
			return casted_image;
		} catch (Exception e) {
			System.out.print(e);
		}
		return casted_image;
	}
	// put the downloaded file here
	private final String PATH = "/data/data/com.mileem.imagedownloader/"; 

	public void DownloadFromUrl(String imageURL, String fileName) {
		try {
			URL url = new URL(imageURL);
			File file = new File(fileName);

			long startTime = System.currentTimeMillis();
			Log.d("ImageUtils", "download begining");
			Log.d("ImageUtils", "download url:" + url);
			Log.d("ImageUtils", "downloaded file name:" + fileName);
			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
			Log.d("ImageUtils", "download ready in"
							+ ((System.currentTimeMillis() - startTime) / 1000)
							+ " sec");

		} catch (IOException e) {
			Log.d("ImageUtils", "Error: " + e);
		}

	}

}
