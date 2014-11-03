package com.mileem.fragments;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.widget.FacebookDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mileem.ConfigManager;
import com.mileem.R;
import com.mileem.PublicationSlidesFragmentAdapter;
import com.mileem.R;
import com.mileem.model.Publication;
import com.mileem.util.Util;
import com.mileem.util.twitter.HelperMethods;
import com.mileem.util.twitter.HelperMethods.TwitterCallback;
import com.mileem.util.twitter.TwitterLoginActivity;
import com.mileem.fragments.PublicationMapFragment;
import com.squareup.picasso.Picasso;


public class DetailPublicationFragment extends Fragment{
	
	private String TAG = this.getClass().getSimpleName();
	private Publication publication;
	private PublicationSlidesFragmentAdapter adapter;
	private View detailView;
    private ViewPager pager;
    //private CirclePageIndicator indicator;
    private ViewGroup contact_layout;
    private BootstrapButton contact;
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
	public final String accessToken = "2853897964-X5AxG2wibWnqDqebX3o2nnkBRylFxhxo7hHDfsi";
	public final String accessTokenSecret = "DE1urljEmejZlO2CDgDYiXFHPn1SiM59jZ2rUoah5O4FA";
	File casted_image;
	String string_img_url = null , string_msg = null;
	private AlertDialog mAlertBuilder;
	private LayoutInflater layoutInflater;
	ImageView twitterImageView;
	
	public static final DetailPublicationFragment newInstance(Publication p){
		DetailPublicationFragment fragment = new DetailPublicationFragment();
	    fragment.setArguments(p.getBundle());
	    return fragment ;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.publication = new Publication(getArguments());
		this.detailView = null;
		this.adapter = null;
		this.pager = null;
		this.call = null;
		this.mail = null;
		this.facebookButton = null;
		this.twitterButton = null;
		
	}
	
	@Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		layoutInflater = inflater;
		
		if (detailView == null){
			
			detailView = inflater.inflate(R.layout.fragment_detailpublication,
						 container, false);
	
	        adapter = new PublicationSlidesFragmentAdapter(getActivity().getSupportFragmentManager(),publication.getListImagesURL(),publication.getUrl_video());
	        
	        pager = (ViewPager) detailView.findViewById(R.id.pager);
	        pager.setAdapter(adapter);
	        
//	        indicator = (CirclePageIndicator)detailView.findViewById(R.id.indicator);
//	        indicator.setViewPager(pager);
		   
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
	        
	        
	        // Boton para ver el mapa
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
					/*Toast.makeText(getActivity(),"CallButton is clicked!", Toast.LENGTH_SHORT).show();*/
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
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("¿Desea compartir la publicación en Twitter?")
		       .setTitle("Compartir en Twitter");
		// Add the buttons
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   if (Util.isNetworkAvailable(getActivity())) {
		       			if (TwitterLoginActivity.isActive(getActivity())) {
		       				try {

		       					mAlertBuilder = new AlertDialog.Builder(getActivity()).create();
		       					mAlertBuilder.setCancelable(false);
		       					mAlertBuilder.setTitle(R.string.please_wait_title);
		       					View view = layoutInflater.inflate(R.layout.view_loading, null);
		       					((TextView) view.findViewById(R.id.messageTextViewFromLoading)).setText(getString(R.string.posting_image_message));
		       					mAlertBuilder.setView(view);
		       					mAlertBuilder.show();
		       					
		       					//Download image to device.
		       					String imageName = Util.random();
		       					Util.downloadFile(getActivity(), 
		       							ConfigManager.URL_SERVER + publication.getImageURLAtIndex(0),
		       							imageName);
		       					Thread.sleep(2000);
		       					
		       					String inFilename = Environment.getExternalStorageDirectory()
		       		                    + "/Download/" + imageName;
		       					String outFilename = Environment.getExternalStorageDirectory()
		       		                    + "/Download/" + "_" + imageName;
		       					
		       					Log.d(TAG, "In Filename: " + inFilename);
		       					Log.d(TAG, "Out Filename: " + outFilename);
		       					
		       					File file = new File(inFilename); 
		       		            if (!file.isDirectory())
		       		                file.mkdir();
		       					FileInputStream fileInputStream = new FileInputStream(file);
		       					Bitmap bmp = BitmapFactory.decodeStream(fileInputStream);
		       					FileOutputStream out = new FileOutputStream(outFilename);
		       					bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
		       					
		       					string_msg = publication.getTransaction_type() +  " de " + 
		       							publication.getProperty_type() + " | " + 
		       							publication.getAddress() + " | " +
		       							publication.getZone() + " | " +
		       							formattedPrice + " " +
		       							"#mileem";
		       					
		       					HelperMethods.postToTwitterWithImage(getActivity().getApplicationContext(), 
		       							getActivity(), outFilename, string_msg , new TwitterCallback() {

		       						@Override
		       						public void onFinsihed(Boolean response) {
		       							mAlertBuilder.dismiss();
		       							Log.d(TAG, "----------------response----------------" + response);
		       							Toast.makeText(getActivity(), getString(R.string.image_posted_on_twitter), Toast.LENGTH_SHORT).show();
		       						}
		       					});

		       				} catch (Exception ex) {
		       					Log.e(TAG, ex.toString());
		       					Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
		       				}
		       			}else{
		       				startActivity(new Intent(getActivity(), TwitterLoginActivity.class));
		       			}
		       			
		       			
		       		} else {
		       			Util.showToast(getActivity(), "No dispones de conexión a Internet para compartir la publicación.");
		       		}
		           }
		       });
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User cancelled the dialog
		           }
		       });
		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	protected void sharePublicationOnFacebook() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("¿Desea compartir la publicación en Facebook?")
		       .setTitle("Compartir en Facebook");
		// Add the buttons
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   Log.i(TAG,"Publicando en facebook...");
		       		//TODO: Remove this
		       		Util.printKeyHashes(getActivity());
		       		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
		               	.setLink("https://www.mileem.com.ar")
		               	.setCaption("Encontrá tu próxima propiedad con Mileem.")
		       			.setName(
		       					publication.getAddress() + " | " +
		       		        	publication.getZone() )
		       		    //Si la imagen vive en localhost, no se va a ver en FB.
		               	.setPicture(ConfigManager.URL_SERVER + publication.getImageURLAtIndex(0))
		       		    //Para probar, usar una imagen publica como esta:
		               	//.setPicture("http://images01.olx.cl/ui/2/75/50/20737550_1.jpg")
		               	.setDescription(
		               			publication.getTransaction_type() + " de " + 
		               			publication.getProperty_type() + " | " + 
		               			formattedPrice + " " +
		               			"#mileem")
		          			.build();
		       		uiHelper.trackPendingDialogCall(shareDialog.present());
		           }
		       });
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User cancelled the dialog
		           }
		       });
		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
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
	        	Util.showToast(getActivity(), "La publicación fue compartida con éxito en Facebook.");
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
