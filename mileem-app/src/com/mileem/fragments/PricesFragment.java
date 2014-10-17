package com.mileem.fragments;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.mileem.R;
import com.mileem.IPlaceableFragment;
import com.mileem.RangeSeekBar;
import com.mileem.RangeSeekBar.OnRangeSeekBarChangeListener;

public class PricesFragment extends Fragment implements IPlaceableFragment {

	
	private RangeSeekBar<Long> seekBarPrice;
	private BootstrapButton bb_ars, bb_usd, bb_allprices;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				
		View rootView = inflater.inflate(R.layout.fragment_prices, container, false);
		
		bb_ars = (BootstrapButton) rootView.findViewById(R.id.bb_ars);
		bb_usd = (BootstrapButton) rootView.findViewById(R.id.bb_usd);
		bb_allprices = (BootstrapButton) rootView.findViewById(R.id.bb_allprices);
		
		setUpRangeSeekBarPrice();
		ViewGroup seekBarPrice_layout = (ViewGroup) rootView.findViewById(R.id.RSeekBarPrices);
		seekBarPrice_layout.addView(seekBarPrice);
		
		return rootView;
		
	}
	
	private void setUpRangeSeekBarPrice(){

		long MIN_PRICE = 0;
		long MAX_PRICE = 10000000;
		final DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
//		price_from.setText("De: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(MIN_PRICE));
//        price_to.setText("A: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(MAX_PRICE));
		
		
		seekBarPrice = new RangeSeekBar<Long>(MIN_PRICE, MAX_PRICE, this.getActivity());
		seekBarPrice.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Long>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Long minValue, Long maxValue) {
//		                price_from.setText("De: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(minValue));
//		                price_to.setText("A: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(maxValue));
		        }
		});
	}
	
	@Override
	public int getContainer() {
		return R.id.edit_mode_fragment_container_small;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}

}
