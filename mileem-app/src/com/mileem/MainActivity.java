package com.mileem;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.mileem.fragments.AmbXBarrioFragment;
import com.mileem.fragments.NavigationDrawerFragment;
import com.mileem.fragments.NewSearchFragment;
import com.mileem.fragments.PrecioXMetroFragment;
import com.mileem.fragments.ZonasAledanasFragment;
import com.pixate.freestyle.PixateFreestyle;

public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, PopupMenu.OnMenuItemClickListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    public static String CURRENCY_SYMBOL = "ARS$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        PixateFreestyle.init(this);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        

        if (savedInstanceState == null){
        	ImageLoader.init(this);
        	FragmentManager fragmentManager = getSupportFragmentManager();
        	fragmentManager.beginTransaction()
        	.replace(R.id.container,new NewSearchFragment())
        	.commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
      

    }


    public void restoreActionBar() {
//        ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            // Only show items in the action bar relevant to this screen
//            // if the drawer is not showing. Otherwise, let the drawer
//            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
//            restoreActionBar();
            return true;
//        }
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
        	getSupportFragmentManager().popBackStack();
        }
        if (id == R.id.statistics) {
        	View anchor = findViewById(R.id.statistics);
        	showPopup(anchor);
            return true;
        }
        return true;
    }
    
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.statistics_popup, popup.getMenu());
        popup.show();
    }

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		FragmentManager fragmentManager = getSupportFragmentManager(); 
		switch (item.getItemId()) {
	        case R.id.ambientes_barrios:
	        	fragmentManager.beginTransaction()
	        	.replace(R.id.container,new AmbXBarrioFragment())
	        	.addToBackStack("busqueda")
	        	.commit();
	            return true;
	        case R.id.precio_metro:
	        	fragmentManager.beginTransaction()
	        	.replace(R.id.container,new PrecioXMetroFragment())
	        	.addToBackStack("busqueda")
	        	.commit();
	            return true;
	        case R.id.zonas_cercanas:
	        	fragmentManager.beginTransaction()
	        	.replace(R.id.container,new ZonasAledanasFragment())
	        	.addToBackStack("busqueda")
	        	.commit();   
	            return true;    
	        default:
	            return false;
	    }

	}

}
