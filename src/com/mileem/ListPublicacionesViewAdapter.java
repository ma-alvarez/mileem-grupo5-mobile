package com.mileem;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

import com.example.mileem.R;
import com.mileem.model.Publication;


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
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	//@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        TextView address;
        TextView price;
        TextView rooms;
        ImageView icon;

        inflater = (LayoutInflater) context
        		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, parent, false);

        publicacion = lista_publicaciones.get(position);
        // Locate the TextViews in listview_item.xml
        address = (TextView) itemView.findViewById(R.id.address);
        price = (TextView) itemView.findViewById(R.id.price);
        rooms = (TextView) itemView.findViewById(R.id.rooms);

        // Locate the ImageView in listview_item.xml
        icon = (ImageView) itemView.findViewById(R.id.house_thumbnail);

        // Capture position and set results to the TextViews
        address.setText(publicacion.getAddress());
        DecimalFormat df = new DecimalFormat( "#,###,###,##0" );
        
        //TODO: currency type must be setted in the model and sent in the json
        price.setText( MainActivity.CURRENCY_SYMBOL + " " + df.format(publicacion.getPrice()));
        rooms.setText(Integer.toString(publicacion.getNumber_of_rooms()) + " Amb.");
		
		loadBitmap(icon, publicacion.getUrl_Image(0));
		
        // Capture position and set results to the ImageViewger
        // Passes flag images URL into ImageLoader.class
        //imageLoader.DisplayImage(resultp.get(MainActivity.FLAG), flag);
        // Capture ListView item click
        itemView.setOnClickListener(new OnClickListener() {

        	@Override
        	public void onClick(View arg0) {
        		Log.v( this.getClass().toString() , "Click on");
        		//Intent intent = new Intent(context, SingleItemView.class);
        		// Get the position
        		//resultp = data.get(position);
        		//Intent intent = new Intent(context, SingleItemView.class);
        		// Pass all data rank
        		//intent.putExtra("rank", resultp.get(MainActivity.RANK));
        		// Pass all data country
        		//intent.putExtra("country", resultp.get(MainActivity.COUNTRY));
        		// Pass all data population
        		//intent.putExtra("population",resultp.get(MainActivity.POPULATION));
        		// Pass all data flag
        		//intent.putExtra("flag", resultp.get(MainActivity.FLAG));
        		// Start SingleItemView Class
        		//context.startActivity(intent);

        	}
        });
        return itemView;
	}
	
	public void loadBitmap(ImageView imageView, String url) {
	    BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	    task.execute(url);
	}

}
