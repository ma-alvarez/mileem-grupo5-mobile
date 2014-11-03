package com.mileem.util.twitter;

import java.io.File;

import com.mileem.R;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class HelperMethods {
	private static final String TAG = "HelperMethods";

	public HelperMethods() {
	}

	public static void postToTwitter(Context context, final Activity callingActivity, final String message, final TwitterCallback postResponse) {
		if (!TwitterLoginActivity.isActive(context)) {
			postResponse.onFinsihed(false);
			return;
		}
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(context.getResources()
				.getString(R.string.twitter_consumer_key));
		configurationBuilder.setOAuthConsumerSecret(context.getResources()
				.getString(R.string.twitter_consumer_secret));
		configurationBuilder.setOAuthAccessToken(TwitterLoginActivity
				.getAccessToken((context)));
		configurationBuilder.setOAuthAccessTokenSecret(TwitterLoginActivity
				.getAccessTokenSecret(context));
		Configuration configuration = configurationBuilder.build();
		final Twitter twitter = new TwitterFactory(configuration).getInstance();
		new Thread(new Runnable() {
			private double x;

			@Override
			public void run() {
				boolean success = true;
				try {
					x = Math.random();
					twitter.updateStatus(message + " " + x);
				} catch (TwitterException e) {
					e.printStackTrace();
					success = false;
				}
				final boolean finalSuccess = success;
				callingActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						postResponse.onFinsihed(finalSuccess);
					}
				});
			}
		}).start();
	}

	public static void postToTwitterWithImage(Context context,
			final Activity callingActivity, final String imageUrl,
			final String message, final TwitterCallback postResponse) {
		if (!TwitterLoginActivity.isActive(context)) {
			postResponse.onFinsihed(false);
			return;
		}
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(context.getResources()
				.getString(R.string.twitter_consumer_key));
		configurationBuilder.setOAuthConsumerSecret(context.getResources()
				.getString(R.string.twitter_consumer_secret));
		configurationBuilder.setOAuthAccessToken(TwitterLoginActivity
				.getAccessToken((context)));
		configurationBuilder.setOAuthAccessTokenSecret(TwitterLoginActivity
				.getAccessTokenSecret(context));
		Configuration configuration = configurationBuilder.build();
		final Twitter twitter = new TwitterFactory(configuration).getInstance();
		
		
		final File file = new File(imageUrl);
		
		new Thread(new Runnable() {
			//private double x;

			@Override
			public void run() {
				boolean success = true;
				try {
					//x = Math.random();
					if (file.exists()) {
						StatusUpdate status = new StatusUpdate(message);
						status.setMedia(file);
						twitter.updateStatus(status);
					} else {
						Log.d(TAG, "----- Invalid File: " + file.getAbsolutePath());
						success = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					success = false;
				}
				final boolean finalSuccess = success;
				callingActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						postResponse.onFinsihed(finalSuccess);
					}
				});
			}
		}).start();
	}

	public static abstract class TwitterCallback {
		public abstract void onFinsihed(Boolean success);
	}
}
