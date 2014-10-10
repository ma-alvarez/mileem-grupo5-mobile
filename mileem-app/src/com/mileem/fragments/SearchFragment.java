package com.mileem.fragments;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.Fx;
import com.mileem.MainActivity;
import com.mileem.RangeSeekBar;
import com.mileem.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.pixate.freestyle.PixateFreestyle;

public class SearchFragment extends Fragment{
	
	private String TAG = this.getClass().getSimpleName();
	private Button button_neighbourhoods;
	private Button button_search;
	private Button button_pub_date_from;
	private Button button_pub_date_to;
	private String[] zones;
	private Spinner sp_tran_type;
	private Spinner sp_prop_type;
	private Spinner sp_rooms;
	private Spinner sp_orderby;
	private NeighbourhoodsDialog n_dialog;
	private DatePickerFragment date_picker_from;
	private DatePickerFragment date_picker_to;
	private RangeSeekBar<Integer> seekBarPrice;
	private RangeSeekBar<Integer> seekBarArea;
	private ViewGroup advancedSearch_layout;
	private TextView price_from;
	private TextView price_to;
	private TextView sup_from_to;
	private TextView advanced_search;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		
		//Spinners
		sp_tran_type = (Spinner) rootView.findViewById(R.id.spinner_t_operacion);
		sp_prop_type = (Spinner) rootView.findViewById(R.id.spinner_t_prop);
		sp_rooms = (Spinner) rootView.findViewById(R.id.spinner_ambientes);
		sp_orderby = (Spinner) rootView.findViewById(R.id.spinner_orden);
		
		//Boton Localidades
		button_neighbourhoods = (Button) rootView.findViewById(R.id.button_localidades);
		setUpButtonNeighbourhoods();
		
		//Boton Busqueda
		button_search = (Button) rootView.findViewById(R.id.button_buscar);
		setUpButtonSearch();
		
		//Dialog Localidades
		zones = getResources().getStringArray(R.array.neighbourhoods);
		n_dialog = new NeighbourhoodsDialog();
		
		//Textos precio de/a
		price_from = (TextView) rootView.findViewById(R.id.precio_de);
		price_to = (TextView) rootView.findViewById(R.id.precio_hasta);
		
		//SeekBar de rango de precios
		setUpRangeSeekBarPrice();
		ViewGroup seekBarPrice_layout = (ViewGroup) rootView.findViewById(R.id.RSeekBarPrecios);
		seekBarPrice_layout.addView(seekBarPrice);
		
		//Busqueda avanzada
		advancedSearch_layout = (ViewGroup) rootView.findViewById(R.id.busqueda_avanzada);
		advanced_search = (TextView) rootView.findViewById(R.id.text_busq_avanzada);
		setUpAdvancedSearch();
		
		//Textos Sup de/a
		sup_from_to = (TextView) rootView.findViewById(R.id.sup_de_a);
		
		//SeekBar de rango de Superficie
		setUpRangeSeekBarArea();
		ViewGroup seekBarSup_layout = (ViewGroup) rootView.findViewById(R.id.RSeekBarSup);
		seekBarSup_layout.addView(seekBarArea);
		
		//Botones Fecha de Publicacion
		button_pub_date_from = (Button) rootView.findViewById(R.id.button_fecha_pub_from);
		setUpButtonDatePubFrom();
		
		button_pub_date_to = (Button) rootView.findViewById(R.id.button_fecha_pub_to);
		setUpButtonDatePubTo();
		
		//Date Pickers
		date_picker_from = new DatePickerFragment();
		date_picker_to = new DatePickerFragment();
		
		return rootView;
	}

	private void setUpButtonSearch(){
		button_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				PublicationsFragment fragment = new PublicationsFragment();
				String queryParams = buildQuery();
				fragment.AppendAdditionalParameters(queryParams);
				
		    	// update the main content by replacing fragments
		        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
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
			states = new boolean[zones.length];
			last_states = new boolean[zones.length];
		}

		public String getSelectedZones(){
			StringBuilder sb = new StringBuilder();

			if(localidades_seleccionadas.size() > 0){
				sb.append(ConfigManager.ZONE);
				for(int i=0; i < localidades_seleccionadas.size(); i++){
					if(i > 0) sb.append("-");
					sb.append(localidades_seleccionadas.get(i).replaceAll(" ", "_").toLowerCase());
				}
			}
			return sb.toString();
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			for(int i=0 ; i < states.length; i++){
				states[i] = last_states[i];
			}
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Set the dialog title
			builder.setTitle(R.string.eleccion_localidades)
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
	            		   if(states[i])
	            			   localidades_seleccionadas.add(zones[i]);
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
	
	private void setUpRangeSeekBarPrice(){

		int MIN_PRICE = 0;
		int MAX_PRICE = 10000000;
		final DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
		price_from.setText("De: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(MIN_PRICE));
        price_to.setText("A: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(MAX_PRICE));
		
		
		seekBarPrice = new RangeSeekBar<Integer>(MIN_PRICE, MAX_PRICE, this.getActivity());
		seekBarPrice.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
		                price_from.setText("De: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(minValue));
		                price_to.setText("A: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(maxValue));
		        }
		});
	}
	
	private void setUpRangeSeekBarArea(){

		int MIN_AREA = 0;
		int MAX_AREA = 1000;
		final DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
		sup_from_to.setText("De: " + df.format(MIN_AREA) + " A: " + df.format(MAX_AREA));
		
		
		seekBarArea = new RangeSeekBar<Integer>(MIN_AREA, MAX_AREA, this.getActivity());
		seekBarArea.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
		        	sup_from_to.setText("De: " + df.format(minValue) + " A: " + df.format(maxValue) + " m2");
		        }
		});
	}

	private void setUpAdvancedSearch(){
		advancedSearch_layout.setVisibility(View.GONE);
		advanced_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(advancedSearch_layout.isShown()){
					Fx.slide_up(getActivity(), advancedSearch_layout);
					advancedSearch_layout.setVisibility(View.GONE);
				}
				else{
					advancedSearch_layout.setVisibility(View.VISIBLE);
					Fx.slide_down(getActivity(), advancedSearch_layout);
				}			
			}
		});
	}
	
	private void setUpButtonDatePubFrom(){
		button_pub_date_from.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				date_picker_from.show(getFragmentManager(), getTag());
			}
		});
	}
	
	private void setUpButtonDatePubTo(){
		button_pub_date_to.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				date_picker_to.show(getFragmentManager(), getTag());
			}
		});
	}

	private class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {

		private Calendar state;
		
		public DatePickerFragment(){
			state = Calendar.getInstance();
		}
		
		public String getSelectedDate(){
			DecimalFormat df = new DecimalFormat("00");
			String date = Integer.toString(state.get(Calendar.YEAR))
					+ df.format(state.get(Calendar.MONTH)+1)
					+ Integer.toString(state.get(Calendar.DAY_OF_MONTH));
			return date;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new DatePickerDialog(getActivity(), this, state.get(Calendar.YEAR), state.get(Calendar.MONTH), state.get(Calendar.DAY_OF_MONTH));
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			state.set(year, month, day);
		}
	}
	
	private String buildQuery(){
		StringBuilder sb = new StringBuilder();

		sb.append(ConfigManager.TRAN_TYPE);
		sb.append(ConfigManager.TRAN_TYPE_OPT[sp_tran_type.getSelectedItemPosition()]);
		sb.append(ConfigManager.PROP_TYPE);
		sb.append(ConfigManager.PROP_TYPE_OPT[sp_prop_type.getSelectedItemPosition()]);
		sb.append(n_dialog.getSelectedZones());
		sb.append(ConfigManager.PRICE_FROM);
		sb.append(seekBarPrice.getSelectedMinValue());
		sb.append(ConfigManager.PRICE_TO);
		sb.append(seekBarPrice.getSelectedMaxValue());
		
		if(advancedSearch_layout.isShown()){
			sb.append(ConfigManager.ORDER_BY);
			sb.append(ConfigManager.ORDER_BY_OPT[sp_orderby.getSelectedItemPosition()]);
			sb.append(ConfigManager.SUP_FROM);
			sb.append(seekBarArea.getSelectedMinValue());
			sb.append(ConfigManager.SUP_TO);
			sb.append(seekBarArea.getSelectedMaxValue());
			sb.append(ConfigManager.ROOMS);
			sb.append(ConfigManager.ROOMS_OPT[sp_rooms.getSelectedItemPosition()]);
			sb.append(ConfigManager.PUB_TIME_FROM);
			sb.append(date_picker_from.getSelectedDate());
			sb.append(ConfigManager.PUB_TIME_TO);
			sb.append(date_picker_to.getSelectedDate());
			
		} else {
			sb.append(ConfigManager.ORDER_BY);
			sb.append(ConfigManager.ORDER_BY_OPT[0]);
		}
		
		
		Log.d(TAG, sb.toString());
		return sb.toString();
	}
}
