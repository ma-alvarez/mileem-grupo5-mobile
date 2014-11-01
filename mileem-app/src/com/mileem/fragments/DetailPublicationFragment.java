package com.mileem.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mileem.ConfigManager;
import com.mileem.R;
import com.mileem.PublicationSlidesFragmentAdapter;
import com.mileem.model.Publication;
import com.mileem.util.Twitt_Sharing;
import com.mileem.fragments.PublicationMapFragment;
import com.viewpagerindicator.CirclePageIndicator;

public class DetailPublicationFragment extends Fragment{
	
	private String TAG = this.getClass().getSimpleName();
	private Publication publication;
	private PublicationSlidesFragmentAdapter adapter;
	private View detailView;
    private ViewPager pager;
    private CirclePageIndicator indicator;
    private ImageButton viewMap;
    private BootstrapButton call;
    private BootstrapButton mail;
    
    //Attributes for facebook
    private BootstrapButton facebookButton;
    private String formattedPrice;
    private UiLifecycleHelper uiHelper;
    
    //Attributes for twitter
    private BootstrapButton twitterButton;
	public final String consumer_key = "w6oD9dAh7zYHpQIicPTVfyhbf";
	public final String secret_key = "7YTdOstEdn75OEVJRFmgRp2CU5FQnr7bpHIXoiGBv7fECfrEU8";
	File casted_image;
	String string_img_url = null , string_msg = null;
	
	
    public DetailPublicationFragment(Publication publication) {
		this.publication = publication;
		this.detailView = null;
		this.adapter = null;
		this.pager = null;
		this.call = null;
		this.mail = null;
		this.facebookButton = null;
	}
	
	@Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			
		
		if (detailView == null){
			
			detailView = inflater.inflate(R.layout.fragment_detailpublication,
						 container, false);
	
	        adapter = new PublicationSlidesFragmentAdapter(getActivity().getSupportFragmentManager(),publication.getUrls_image());
	        
	        pager = (ViewPager) detailView.findViewById(R.id.pager);
	        pager.setAdapter(adapter);
	        
	        indicator = (CirclePageIndicator)detailView.findViewById(R.id.indicator);
	        indicator.setViewPager(pager);
		   
	        // Locate the TextViews in layout
	        TextView main_text = (TextView) detailView.findViewById(R.id.main_desc);
	        TextView price = (TextView) detailView.findViewById(R.id.price);
	        TextView sec_text = (TextView) detailView.findViewById(R.id.sec_text);
	        
	        TextView area = (TextView) detailView.findViewById(R.id.area);
	        TextView age = (TextView) detailView.findViewById(R.id.age);
	        TextView expenses = (TextView) detailView.findViewById(R.id.expenses);
	        
	
	        // Capture position and set results to the TextViews
	        main_text.setText(publication.getProperty_type() + " | " + publication.getTransaction_type() + " | " +
	        			 Integer.toString(publication.getNumber_of_rooms()) + " Amb.");
	        
	        DecimalFormat df = new DecimalFormat("#,###,###,##0" );
	        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "AR"));
	        symbols.setDecimalSeparator(',');
	        symbols.setGroupingSeparator('.');
	        df.setDecimalFormatSymbols(symbols);
	        price.setText(publication.getCurrency() + " " + df.format(publication.getPrice()));
	        formattedPrice = price.getText().toString();
	        sec_text.setText(publication.getAddress() + " | " + publication.getZone());
	      	        
	        
	        area.setText("Superficie: " + Integer.toString(publication.getArea()) + " m2");
	        age.setText("Antiguedad: " + Math.round(publication.getAge()) + " años");  
	        expenses.setText("Expensas: "  + publication.getCurrency() + " " + df.format(publication.getExpenses())); 
	       
	        final String STATIC_MAP_API_ENDPOINT = 
	                "http://maps.google.com/maps/api/staticmap?" +
	                "center=" + publication.getLatitude() + "," + publication.getLongitude() + "&" +
	                "markers=" + publication.getLatitude() + "," + publication.getLongitude() + "&" +
	                "size=640x480&" +
	                "zoom=14&" +
	                "scale=2&" +
	                "sensor=false";
	        //Carga de imagen estatica del mapa
	        AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>(){
	            @Override
	            protected Bitmap doInBackground(Void... params) {
	                Bitmap bmp = null;
	                HttpClient httpclient = new DefaultHttpClient();   
	                HttpGet request = new HttpGet(STATIC_MAP_API_ENDPOINT); 
	                InputStream in = null;
	                try {
	                    in = httpclient.execute(request).getEntity().getContent();
	                    bmp = BitmapFactory.decodeStream(in);
	                    in.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                return bmp;
	            }
	            protected void onPostExecute(Bitmap bmp) {
	                if (bmp!=null) {
	                    final ImageButton iv = (ImageButton) detailView.findViewById(R.id.locationMapSnapshot);
	                    iv.setImageBitmap(bmp);
	                }
	            }
	        };
	        setImageFromUrl.execute();
	        
	        
	        // Botón para ver el mapa
	        viewMap = (ImageButton) detailView.findViewById(R.id.locationMapSnapshot);
	        viewMap.setOnClickListener(new OnClickListener() {
	        	 
				@Override
				public void onClick(View arg0) {
					
					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
					fragmentManager.beginTransaction().
					replace(R.id.container,new PublicationMapFragment(publication)).
					addToBackStack("detalle").commit(); 				
	 
				}
	 
			});
	        
	        
	        //contact_layout = (ViewGroup) detailView.findViewById(R.id.contact_layout);
			//contact = (BootstrapButton) detailView.findViewById(R.id.contact);
			//setUpContact();
			
			call = (BootstrapButton) detailView.findViewById(R.id.callButton);
	         
			call.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
	 
					/*Toast.makeText(getActivity(),
						"CallButton is clicked!", Toast.LENGTH_SHORT).show();*/
				   
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"+publication.getPhone()));
					startActivity(callIntent);
	 
				}
	 
			});
	        
			mail = (BootstrapButton) detailView.findViewById(R.id.mailButton);
	 		mail.setOnClickListener(new OnClickListener() {
				 
				@Override
				public void onClick(View arg0) {
	 			   /*Toast.makeText(getActivity(),
					"MailButton is clicked!", Toast.LENGTH_SHORT).show();*/
				   sendEmail();
	 			}
	 		});
			
			uiHelper = new UiLifecycleHelper(this.getActivity(), null);
		    uiHelper.onCreate(savedInstanceState);
			
		    facebookButton = (BootstrapButton) detailView.findViewById(R.id.facebookButton);
		    facebookButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sharePublicationOnFacebook();
				}
			});
		    
		    twitterButton = (BootstrapButton) detailView.findViewById(R.id.twitterButton);
		    twitterButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sharePublicationOnTwitter();
				}
			});
		}
		else{
			 ((ViewGroup)detailView.getParent()).removeView(detailView);
		}
        return detailView;

	}
	

	
//	private void setUpContact() {
//		contact_layout.setVisibility(View.GONE);
//		contact.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if(contact_layout.isShown()){
//					Fx.slide_up(getActivity(), contact_layout);
//					contact_layout.setVisibility(View.GONE);
//				}
//				else{
//					contact_layout.setVisibility(View.VISIBLE);
//					Fx.slide_down(getActivity(), contact_layout);
//				}			
//			}
//		});
//	}
	
	protected void sendEmail() {
	      Log.i("Envio de Correo", "");

	      String[] TO = {publication.getUser_email()};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("message/rfc822");

	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Consulta sobre anuncio");
	      emailIntent.putExtra(Intent.EXTRA_TEXT, "Escriba su mensaje");

	      try {
	         startActivity(Intent.createChooser(emailIntent, "Enviar Correo..."));
	         //finish();
	         Log.i("Envio de correo finalizado...", "");
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(getActivity(), 
	         "No hay un cliente de correo configurado.", Toast.LENGTH_SHORT).show();
	      }
	}
	
	
	protected void sharePublicationOnTwitter() {
		if (isNetworkAvailable()) {
			Twitt_Sharing twitt = new Twitt_Sharing(this.getActivity(), consumer_key, secret_key);
			string_img_url = ConfigManager.URL_SERVER + publication.getUrl_Image(0);
			string_msg = publication.getTransaction_type() +  " de " + 
        			publication.getProperty_type() + " | " + 
					publication.getAddress() + " | " +
					publication.getZone() + " | " +
        			formattedPrice + " " +
        			"#mileem";
			// here we have web url image so we have to make it as file to upload
			String_to_File(string_img_url);
			// Now share both message & image to sharing activity
			//TODO: Ver por que falla la subida de la imagen.
			//twitt.shareToTwitter(string_msg, casted_image);
			twitt.shareToTwitter(string_msg, null);
		} else {
			showToast("No Network Connection Available !!!");
		}
	}

	// when user will click on twitte then first that will check that is
	// internet exist or not
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	// this function will make your image to file
	public File String_to_File(String img_url) {
		try {
			File rootSdDirectory = Environment.getExternalStorageDirectory();
			casted_image = new File(rootSdDirectory, "attachment.jpg");
			if (casted_image.exists()) {
				casted_image.delete();
			}
			casted_image.createNewFile();
			FileOutputStream fos = new FileOutputStream(casted_image);
			URL url = new URL(img_url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			InputStream in = connection.getInputStream();
			byte[] buffer = new byte[1024];
			int size = 0;
			while ((size = in.read(buffer)) > 0) {
				fos.write(buffer, 0, size);
			}
			
//			Bitmap bm = ShrinkBitmap(img_url, 50, 50);
//			bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);
			
			fos.close();
		} catch (Exception e) {
			System.out.print(e);
		}
		return casted_image;
	}
	
	public Bitmap ShrinkBitmap(String file, int width, int height) {
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
	
	protected void sharePublicationOnFacebook() {
		Log.i("Attempting to share publication on facebook...", "");
	    // Add code to print out the key hash
	    try {
	        PackageInfo info = this.getActivity().getPackageManager().getPackageInfo(
	                "com.facebook.samples.hellofacebook", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this.getActivity())
        	.setLink("https://www.google.com.ar")
        	.setCaption("Encontrá tu próxima propiedad con Mileem.")
			.setName(
					publication.getAddress() + " | " +
		        	publication.getZone() )
        	.setPicture(ConfigManager.URL_SERVER + publication.getUrl_Image(0))
        	.setDescription(
        			publication.getTransaction_type() + " de " + 
        			publication.getProperty_type() + " | " + 
        			formattedPrice + " " +
        			"#mileem")
   			.build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
		
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
}
