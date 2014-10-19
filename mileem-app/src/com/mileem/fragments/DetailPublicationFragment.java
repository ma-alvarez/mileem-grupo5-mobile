package com.mileem.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mileem.R;
import com.mileem.Fx;
import com.mileem.PublicationSlidesFragmentAdapter;
import com.mileem.model.Publication;
import com.mileem.fragments.PublicationMapFragment;

public class DetailPublicationFragment extends Fragment{

	private Publication publication;
	private PublicationSlidesFragmentAdapter adapter;
	private View detailView;
    private ViewPager pager;
    private ViewGroup contact_layout;
    private Button contact;
    private ImageButton viewMap;
    private ImageButton call;
    private ImageButton mail;
	
	public DetailPublicationFragment(Publication publication) {
		this.publication = publication;
		this.detailView = null;
		this.adapter = null;
		this.pager = null;
		this.call = null;
		this.mail = null;
	}
	
	@Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			
		
		if (detailView == null){
			
			detailView = inflater.inflate(R.layout.fragment_detailpublication,
						 container, false);
	
	        adapter = new PublicationSlidesFragmentAdapter(getActivity().getSupportFragmentManager(),publication.getUrls_image());
	        
	        pager = (ViewPager) detailView.findViewById(R.id.pager);
	        pager.setAdapter(adapter);
		   
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
	        
	        price.setText(publication.getCurrency() + " " + publication.getPrice());
	        
	        sec_text.setText(publication.getAddress() + " | " + publication.getZone());
	      
	        
	        
	        area.setText("Superficie: " + Integer.toString(publication.getArea()) + " m2");
	        age.setText("Antiguedad: " + publication.getAge() + " Años");  
	        expenses.setText("Expensas: "  + publication.getCurrency() + " " + Double.toString(publication.getExpenses())); 
	       
	        
	        // Botón para ver el mapa
	        viewMap = (ImageButton) detailView.findViewById(R.id.viewMap);
	        viewMap.setOnClickListener(new OnClickListener() {
	        	 
				@Override
				public void onClick(View arg0) {
	 
	//				Toast.makeText(getActivity(),
	//					"ViewMap is clicked!", Toast.LENGTH_SHORT).show();
					
					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
					fragmentManager.beginTransaction().
					replace(R.id.container,new PublicationMapFragment(publication)).
					addToBackStack("detalle").commit(); 				
	 
				}
	 
			});
	        
	        
	        contact_layout = (ViewGroup) detailView.findViewById(R.id.contact_layout);
			contact = (Button) detailView.findViewById(R.id.contact);
			setUpContact();
			
			call = (ImageButton) detailView.findViewById(R.id.callButton);
	         
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
	        
			mail = (ImageButton) detailView.findViewById(R.id.mailButton);
	 	
			mail.setOnClickListener(new OnClickListener() {
				 
				@Override
				public void onClick(View arg0) {
	 
				   /*Toast.makeText(getActivity(),
					"MailButton is clicked!", Toast.LENGTH_SHORT).show();*/
				   sendEmail();
	 
				}
	 
			}); 
		}
		else{
			 ((ViewGroup)detailView.getParent()).removeView(detailView);
		}
        return detailView;

	}
	
	private void setUpContact() {
		contact_layout.setVisibility(View.GONE);
		contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(contact_layout.isShown()){
					Fx.slide_up(getActivity(), contact_layout);
					contact_layout.setVisibility(View.GONE);
				}
				else{
					contact_layout.setVisibility(View.VISIBLE);
					Fx.slide_down(getActivity(), contact_layout);
				}			
			}
		});
	}
		

	protected void sendEmail() {
	      Log.i("Envio de Correo", "");

	      String[] TO = {publication.getUser_email()};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");

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
}
