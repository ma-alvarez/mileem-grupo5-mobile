package com.mileem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.mileem.R;
import com.mileem.model.Publication;
import com.mileem.tasks.BitmapWorkerTask;


public class ListPublicacionesViewAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;

    private ArrayList<Publication> lista_publicaciones;
    private Publication publicacion = null;


    public ListPublicacionesViewAdapter(Context context,
    		ArrayList<Publication> arraylist) {
    	this.context = context;
    	lista_publicaciones = arraylist;

    }

    @Override
	public int getCount() {
		return lista_publicaciones.size();
	}

	@Override
	public Object getItem(int position) {
		return lista_publicaciones.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	//@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView price;
        TextView address;
        TextView propTypeAndZone;
        TextView transactionTypeAndRooms;
        ImageView icon;

        inflater = (LayoutInflater) context
        		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, parent, false);

        publicacion = lista_publicaciones.get(position);
        // Locate the TextViews in listview_item.xml
        price = (TextView) itemView.findViewById(R.id.price_itemlist);
        address = (TextView) itemView.findViewById(R.id.text_address_itemlist);
        //FontAwesomeText tv1 = (FontAwesomeText) itemView.findViewById(R.id.text_address_itemlist);
        propTypeAndZone = (TextView) itemView.findViewById(R.id.text_proptype_zone_itemlist);
        transactionTypeAndRooms = (TextView) itemView.findViewById(R.id.text_transType_rooms_itemlist);
        
        // Locate the ImageView in listview_item.xml
        icon = (ImageView) itemView.findViewById(R.id.house_thumbnail);

        // Capture position and set results to the TextViews
        address.setText(publicacion.getAddress());
        propTypeAndZone.setText(publicacion.getProperty_type() + " | " + publicacion.getZone());
        transactionTypeAndRooms.setText(publicacion.getTransaction_type() + " | " + Integer.toString(publicacion.getNumber_of_rooms()) + " Amb.");
        
        DecimalFormat df = new DecimalFormat("#,###,###,##0" );
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "AR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        price.setText( publicacion.getCurrency() + " " + df.format(publicacion.getPrice()));
        
        if(publicacion.getUrls_image().size() > 0)
        	loadBitmap(icon, publicacion.getUrl_Image(0));
        
        return itemView;
	}
	
	public void loadBitmap(ImageView imageView, String url) {
	    BitmapWorkerTask task = new BitmapWorkerTask(imageView, true);
	    task.execute(url);
	}

}
