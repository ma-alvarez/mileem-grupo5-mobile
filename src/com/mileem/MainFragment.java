package com.mileem;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mileem.R;
import com.mileem.RangeSeekBar.OnRangeSeekBarChangeListener;

public class MainFragment extends Fragment{

	private String TAG = this.getClass().getSimpleName();
	private Button button_neighbourhoods;
	private Button button_search;
	private String[] localidades;
	private NeighbourhoodsDialog n_dialog;
	private RangeSeekBar<Integer> seekBar;
	private TextView price_from;
	private TextView price_to;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		
		button_neighbourhoods = (Button) rootView.findViewById(R.id.button_localidades);
		setUpButtonNeighbourhoods();
		
		button_search = (Button) rootView.findViewById(R.id.button_buscar);
		setUpButtonSearch();
		
		localidades = getResources().getStringArray(R.array.neighbourhoods);
		
		n_dialog = new NeighbourhoodsDialog();
		
		price_from = (TextView) rootView.findViewById(R.id.precio_de);
		price_to = (TextView) rootView.findViewById(R.id.precio_hasta);
		
		setUpRangeSeekBar();
		ViewGroup layout = (ViewGroup) rootView.findViewById(R.id.seekBar1);
		layout.addView(seekBar);
		

		
		return rootView;
	}
	
	private void setUpButtonSearch(){
		button_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment fragment = new PublicationsFragment();
				
		    	// update the main content by replacing fragments
		        FragmentManager fragmentManager = getFragmentManager();
		        fragmentManager.beginTransaction()
		        .replace(R.id.container, fragment)
		        .addToBackStack("busqueda")
		        .commit();
			}
		});
	}

	private void setUpButtonNeighbourhoods(){
		button_neighbourhoods.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				n_dialog.show(getFragmentManager(), getTag());
			}
		});
	}

	private class NeighbourhoodsDialog extends DialogFragment{

		private boolean[] states;
		private boolean[] last_states;
		private ArrayList<String> localidades_seleccionadas;
		
		public NeighbourhoodsDialog(){
			localidades_seleccionadas = new ArrayList<String>();
			states = new boolean[localidades.length];
			last_states = new boolean[localidades.length];
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			for(int i=0 ; i < states.length; i++){
				states[i] = last_states[i];
			}
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Set the dialog title
			builder.setTitle(R.string.eleccion_localidades)
			// Specify the list array, the items to be selected by default (null for none),
			// and the listener through which to receive callbacks when items are selected
			.setMultiChoiceItems(R.array.neighbourhoods, states,
					new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which,
						boolean isChecked) {
				}
			})
		    // Set the action buttons
	           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {

	            	   localidades_seleccionadas.clear();
	            	   
	            	   for(int i=0 ; i < states.length; i++){
	            		   last_states[i] = states[i];
	            		   localidades_seleccionadas.add(localidades[i]);
	            	   }
	               }
	           })
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {

	               }
	           });

	    return builder.create();

		}
		
	}
	
	private void setUpRangeSeekBar(){

		final DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
		price_from.setText("De: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(0));
        price_to.setText("A: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(1000));
		
		
		seekBar = new RangeSeekBar<Integer>(0, 1000, this.getActivity());
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
		                price_from.setText("De: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(minValue));
		                price_to.setText("A: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(maxValue));
		        }
		});
	}
}
