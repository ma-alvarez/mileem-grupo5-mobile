package com.mileem.fragments;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mileem.R;
import com.mileem.IPlaceableFragment;
import com.mileem.RangeSeekBar;
import com.mileem.RangeSeekBar.OnRangeSeekBarChangeListener;

public class PricesFragment extends Fragment implements IPlaceableFragment {

	
	private RangeSeekBar<Integer> seekBarPrice;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				
		View rootView = inflater.inflate(R.layout.fragment_prices, container, false);
		
		setUpRangeSeekBarPrice();
		ViewGroup seekBarPrice_layout = (ViewGroup) rootView.findViewById(R.id.RSeekBarPrices);
		seekBarPrice_layout.addView(seekBarPrice);
		
		return rootView;
		
	}
	
	private void setUpRangeSeekBarPrice(){

		int MIN_PRICE = 0;
		int MAX_PRICE = 10000000;
		final DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
//		price_from.setText("De: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(MIN_PRICE));
//        price_to.setText("A: " + MainActivity.CURRENCY_SYMBOL + " " + df.format(MAX_PRICE));
		
		
		seekBarPrice = new RangeSeekBar<Integer>(MIN_PRICE, MAX_PRICE, this.getActivity());
		seekBarPrice.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
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
