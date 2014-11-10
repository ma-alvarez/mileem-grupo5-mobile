package com.mileem.fragments;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mileem.HttpUtils;
import com.mileem.JSONResponse;
import com.mileem.R;
import com.mileem.ConfigManager;
import com.mileem.IPlaceableFragment;
import com.mileem.RangeSeekBar;
import com.mileem.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.mileem.tasks.ListPublicacionesTask;
import com.mileem.tasks.QuotationTask;

public class PricesFragment extends Fragment implements IPlaceableFragment {

	
	private RangeSeekBar<Long> seekBarPrice;
	private String quotation;
	private BootstrapButton bb_ars, bb_usd, bb_allprices, bb_price_from, bb_price_to, bb_quotation;

	public PricesFragment() {
		super();
		new QuotationTask(this).execute();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_prices, container, false);
		
		bb_ars = (BootstrapButton) rootView.findViewById(R.id.bb_ars);
		bb_usd = (BootstrapButton) rootView.findViewById(R.id.bb_usd);
		bb_allprices = (BootstrapButton) rootView.findViewById(R.id.bb_allprices);
		bb_price_from = (BootstrapButton) rootView.findViewById(R.id.bb_price_from);
		bb_price_to = (BootstrapButton) rootView.findViewById(R.id.bb_price_to);
		bb_quotation = (BootstrapButton) rootView.findViewById(R.id.bb_quotation);
		String message = "Cotización: 1 $USD = " + quotation + " $ARS";
		Log.e("PriceFragmet", message);
		bb_quotation.setText(message);
		//bb_quotation.setText("Cotización: 1 $USD = 8,53 $ARS");
		
		setUpRangeSeekBarPrice();
		ViewGroup seekBarPrice_layout = (ViewGroup) rootView.findViewById(R.id.RSeekBarPrices);
		seekBarPrice_layout.addView(seekBarPrice);
		
		setButtonsListeners();
		
		return rootView;
		
	}
	
	private void setButtonsListeners(){
		bb_ars.setOnTouchListener(new myOnTouchListener());
		bb_usd.setOnTouchListener(new myOnTouchListener());
		bb_ars.setPressed(true);
		
		bb_allprices.setPressed(true);
		bb_allprices.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				setDefault();
				return true;
			}
		});
	}
	
	private void setUpRangeSeekBarPrice(){

		long MIN_PRICE = 0;
		long MAX_PRICE = 10000000;
		final DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
		
		
		seekBarPrice = new RangeSeekBar<Long>(MIN_PRICE, MAX_PRICE, this.getActivity());
		seekBarPrice.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Long>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Long minValue, Long maxValue) {
		                bb_price_from.setText(df.format(minValue));
		                bb_price_to.setText(df.format(maxValue));
		           
		                bb_allprices.setPressed(false);
		                if((minValue.longValue() == bar.getAbsoluteMinValue().longValue()) && (maxValue.longValue() == bar.getAbsoluteMaxValue().longValue())){
			                bb_price_from.setText("min");
			                bb_price_to.setText("max");
		                	bb_allprices.setPressed(true);
		                }
		        }
		});
	}
	
	private class myOnTouchListener implements OnTouchListener{
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			bb_ars.setPressed(false);
			bb_usd.setPressed(false);
			
			v.setPressed(true);
			
			return true;
		}
	}
	
	@Override
	public int getTargetContainer() {
		return R.id.edit_mode_fragment_container_medium;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}

	public String toString(){
		if(bb_allprices.isPressed()){
			return "";
		}else{
			StringBuilder sb = new StringBuilder();
			sb.append(ConfigManager.PRICE_FROM);
			sb.append(seekBarPrice.getSelectedMinValue());
			sb.append(ConfigManager.PRICE_TO);
			sb.append(seekBarPrice.getSelectedMaxValue());
			return sb.toString();
		}
	}

	@Override
	public void setDefault() {
		seekBarPrice.setSelectedMinValue(seekBarPrice.getAbsoluteMinValue());
		seekBarPrice.setSelectedMaxValue(seekBarPrice.getAbsoluteMaxValue());
		bb_price_from.setText("min");
		bb_price_to.setText("max");
		bb_allprices.setPressed(true);
		//bb_quotation.setText("Cotización: 1 $USD = 8,53 $ARS");
	}
	
	public void setQuotation(String quota){
		this.quotation = quota;
	}

}
