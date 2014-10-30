package com.mileem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mileem.model.Publication;
import com.mileem.tasks.BitmapWorkerTask;


public class ListPublicacionesViewAdapter extends BaseAdapter {

    private Context context;
    //private ImageLoader mImageLoader;

    private ArrayList<PublicationAdapter> publication_adapters;


    public ListPublicacionesViewAdapter(Context context,
    		ArrayList<PublicationAdapter> adapters) {
    	this.context = context;
    	publication_adapters = adapters;
    	//mImageLoader = new ImageLoader(context);
    }

    @Override
	public int getCount() {
		return publication_adapters.size();
	}

	@Override
	public Object getItem(int position) {
		return publication_adapters.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	static class ViewHolder{
		public TextView price;
        public TextView address;
        public TextView propTypeAndZone;
        public TextView transactionTypeAndRooms;
        public ImageView icon;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        PublicationAdapter pub_adapter = publication_adapters.get(position);
        Publication publicacion = pub_adapter.getPublication();
        
        if(convertView == null || convertView.getId() != pub_adapter.getLayoutId()){

        	LayoutInflater inflater = (LayoutInflater) context
        			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        	convertView = inflater.inflate(pub_adapter.getLayoutId(), parent, false);
        	viewHolder = new ViewHolder();
        	
        	viewHolder.price = (TextView) convertView.findViewById(R.id.price_itemlist);
        	viewHolder.address = (TextView) convertView.findViewById(R.id.text_address_itemlist);
        	viewHolder.propTypeAndZone = (TextView) convertView.findViewById(R.id.text_proptype_zone_itemlist);
        	viewHolder.transactionTypeAndRooms = (TextView) convertView.findViewById(R.id.text_transType_rooms_itemlist);
        	viewHolder.icon = (ImageView) convertView.findViewById(R.id.house_thumbnail);

        	convertView.setTag(viewHolder);
        }else{
        	viewHolder = (ViewHolder) convertView.getTag();
        }

        

        // Capture position and set results to the TextViews
        viewHolder.address.setText(publicacion.getAddress());
        viewHolder.propTypeAndZone.setText(publicacion.getProperty_type() + " | " + publicacion.getZone());
        viewHolder.transactionTypeAndRooms.setText(publicacion.getTransaction_type() + " | " + Integer.toString(publicacion.getNumber_of_rooms()) + " Amb.");
        
        DecimalFormat df = new DecimalFormat("#,###,###,##0" );
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "AR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        viewHolder.price.setText( publicacion.getCurrency() + " " + df.format(publicacion.getPrice()));
        
        if(publicacion.getUrls_image().size() > 0){
        	//loadBitmap( viewHolder.icon, publicacion.getUrl_Image(0));
        	ImageLoader.displayImage(publicacion.getUrl_Image(0), viewHolder.icon, 100,100);
        }
        	
        
        return convertView;
	}
	
	public void loadBitmap(ImageView imageView, String url) {
	    BitmapWorkerTask task = new BitmapWorkerTask(imageView, true);
	    task.execute(url);
	}

}
