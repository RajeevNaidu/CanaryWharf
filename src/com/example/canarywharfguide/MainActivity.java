package com.example.canarywharfguide;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperToast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements LocationSource
{
	
	class MyInfoWindowAdapter implements InfoWindowAdapter{

        private final View myContentsView;
  
  MyInfoWindowAdapter()
  {
   myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
  }
  
  @Override
  public View getInfoContents(Marker marker) 
  {
   
            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());
            ImageView map_image=(ImageView)myContentsView.findViewById(R.id.snippetImage);
            Drawable drawable = getResources().getDrawable(getResources()
                    .getIdentifier(marker.getTitle().toLowerCase().trim(), "drawable", getPackageName()));
            map_image.setImageDrawable(drawable);		
            return myContentsView;
  }

  @Override
  public View getInfoWindow(Marker marker) {
   // TODO Auto-generated method stub
   return null;
  }
  
 }

	GPSTracker gps;
	GoogleMap googlemap;
	SeekBar seekbar;
	TextView tvMiles;
	ActionBarDrawerToggle mDrawerToggle;
   boolean mapwindow=false;
   DrawerLayout option_drawer;
   ArrayList<DataItem> option_arraylist;
   ArrayList<String> map_data;
   ListView option_list;
   CustomDrawerAdapter adapter;
   static int position=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		ActionBar actionbar=getActionBar();
		actionbar.setTitle("Let's Rock & Roll...");
		actionbar.setSubtitle("The App Expert");
		
        option_drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        option_list=(ListView)findViewById(R.id.left_drawer);
        seekbar=(SeekBar)findViewById(R.id.seekbar);
        tvMiles=(TextView)findViewById(R.id.tv_miles);
        seekbar.setMax(100);
       
        option_arraylist=new ArrayList<DataItem>();
        String[] options=getResources().getStringArray(R.array.options);
		option_arraylist.add(new DataItem(options[0]));
		option_arraylist.add(new DataItem(options[1]));
		option_arraylist.add(new DataItem(options[2]));
		option_arraylist.add(new DataItem(options[3]));
		option_arraylist.add(new DataItem(options[4]));
		option_arraylist.add(new DataItem(options[5]));
		option_arraylist.add(new DataItem(options[6]));
		option_arraylist.add(new DataItem(options[7]));
		option_arraylist.add(new DataItem(options[8]));
		CustomDrawerAdapter adapter=new CustomDrawerAdapter(this,R.layout.each_row,option_arraylist);
		option_list.setAdapter(adapter);
		 
		 mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                option_drawer,         /* DrawerLayout object */
	                R.drawable.ic_launcher,  /* nav drawer icon to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description */
	                R.string.drawer_close  /* "close drawer" description */
	                ) {

	            /** Called when a drawer has settled in a completely closed state. */
	            public void onDrawerClosed(View view)
	            {
	                super.onDrawerClosed(view);
	                getActionBar().setTitle("Canary Guide");
	            }

	            /** Called when a drawer has settled in a completely open state. */
	            public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                getActionBar().setTitle("Canary Options");
	            }
	        };

	        // Set the drawer toggle as the DrawerListener
	        option_drawer.setDrawerListener(mDrawerToggle);

	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
		
		option_list.setOnItemClickListener(new OnItemClickListener() {
        @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
        {
        	position=arg2;
        	
    		
        	//Intent intent_map=new Intent(getApplicationContext(),MapActivity.class);
        	//startActivity(intent_map);
        	//overridePendingTransition(R.anim.activity_slide_in,R.anim.activity_slide_out);
        	
        
        	double latitude;
        	double longitude;
            position=arg2;
		// TODO Auto-generated method stub
        	//Show_Map map=new Show_Map();
        	System.out.println(arg2+" position selected");
        	Globalclassvariable gs=(Globalclassvariable)getApplicationContext();
        	gs.setPosition(arg2);
        	
    			googlemap=((MapFragment)getFragmentManager().findFragmentById(R.id.mapfragment)).getMap();
    		    googlemap.clear();
    			if(googlemap!=null)
    			{
    				System.out.println("in map fragment start");
    				
    				googlemap.setMapType(googlemap.MAP_TYPE_NORMAL);
    				// googlemap.setMapType(googlemap.MAP_TYPE_HYBRID);
    				// googlemap.setMapType(googlemap.MAP_TYPE_SATELLITE);
    				// googlemap.setMapType(googlemap.MAP_TYPE_TERRAIN);
    				// googlemap.setMapType(googlemap.MAP_TYPE_NONE);
                    // Showing / hiding your current location
    				googlemap.setMyLocationEnabled(true);
    				
                    gps=new GPSTracker(getApplicationContext());   				
                   if(gps.canGetLocation)
                   {
                	latitude=gps.getLatitude();
                	longitude=gps.getLongitude();
                	Circle circle=googlemap.addCircle(new CircleOptions().center(new LatLng(latitude,longitude))
                			                                             .radius(100)
                			                                             .strokeColor(Color.RED)
                			                                             .fillColor(Color.TRANSPARENT));
                   }
    				// Enable / Disable zooming controls
    				googlemap.getUiSettings().setZoomControlsEnabled(true);

    				// Enable / Disable my location button
    				googlemap.getUiSettings().setMyLocationButtonEnabled(true);

    				// Enable / Disable Compass icon
    				googlemap.getUiSettings().setCompassEnabled(true);

    				// Enable / Disable Rotate gesture
    				googlemap.getUiSettings().setRotateGesturesEnabled(true);

    				DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
    	        	dbhelper.getReadableDatabase();
    	            ArrayList<String> map_data=new ArrayList<String>();
    	            map_data=dbhelper.readMapData(arg2);
    	            if(map_data.size()==0)
    	            {
    	            	//Toast.makeText(getApplicationContext(), "No Map to display",Toast.LENGTH_SHORT).show();
    	            	SuperToast toast=new SuperToast(getApplicationContext());
    	                toast.setDuration(SuperToast.Duration.MEDIUM);
    	                toast.setIcon(R.drawable.ic_nomap, SuperToast.IconPosition.LEFT);
    	                toast.setAnimations(SuperToast.Animations.FLYIN);
    	                toast.setText("No Maps Found");
    	                toast.setBackground(R.color.dark_gray);
    	                toast.show();
    	            }
    	            else
    	            {
    		
    	            float color=color=returnMarkerColor(arg2);
    				System.out.println(map_data+" data read at show_map");
                    for(String each_location:map_data)
                    {
                    	String[] location=each_location.split(" ");
                    	double lat=Double.parseDouble(location[0]);
                    	double lng=Double.parseDouble(location[1]);
    				    //double latitude = 17.385044;
    				    //double longitude = 78.486671;
                    	//reference for retriving image from drawable resources
                        String image_ref="";
                        image_ref=gs.getMarkerImage(location[5].trim());
                    	MarkerOptions marker=new MarkerOptions().position(new LatLng(lat,lng)).snippet(location[7]).title(location[5]);
                    	marker.icon(BitmapDescriptorFactory.fromResource(gs.getMarkerIcon(arg2)));
                    	
                    	googlemap.setInfoWindowAdapter(new MyInfoWindowAdapter());
                    	
                    	googlemap.addMarker(marker);
                    	
                    }
    	          }
    			}
    			else
    			{
    				Toast.makeText(getApplicationContext(),"Error while loading Maps", Toast.LENGTH_SHORT).show();
    			}
    		
    		//}
    		 
    		 
	    }
	    

});
         
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				Globalclassvariable gs=(Globalclassvariable)getApplicationContext();
				System.out.println("seek bar entered");
					double latitude;
					double longitude;
					Location mylocation=new Location("mylocation");
					LatLng mylatlng=null;
	    			googlemap=((MapFragment)getFragmentManager().findFragmentById(R.id.mapfragment)).getMap();
	    			//seting zoom level for seek bar progress
	    			int zoomlevel=0;
                	//with in 1 0miles
                	//with in 11 to 25 miles
                	if((progress>=0)&(progress<=2))
                	{
                		zoomlevel=11;
                	}
                	else if((progress>2)&(progress<10))
                	{
                		zoomlevel=10;
                	}
                	else if((progress>10)&(progress<25))
                	{
                		zoomlevel=9;
                	}
                	else if(progress>25&progress<50)
                	{
                		zoomlevel=8;
                	}
                	else 
                	{
                		zoomlevel=7;
                	}
                	
	    				//refreshing all map for every progress change
	    			googlemap.clear();
	    		
	    			if(googlemap!=null)
	    			{
	    				System.out.println("in map fragment start seekbar");
	    				
	    				googlemap.setMapType(googlemap.MAP_TYPE_NORMAL);
	    				// googlemap.setMapType(googlemap.MAP_TYPE_HYBRID);
	    				// googlemap.setMapType(googlemap.MAP_TYPE_SATELLITE);
	    				// googlemap.setMapType(googlemap.MAP_TYPE_TERRAIN);
	    				// googlemap.setMapType(googlemap.MAP_TYPE_NONE);

	    				// Showing / hiding your current location
	    				googlemap.setMyLocationEnabled(true);
	    				
	                    gps=new GPSTracker(getApplicationContext());   	
	                    double meters=(progress*1609.344);
	                   if(gps.canGetLocation)
	                   {
	                	latitude=gps.getLatitude();
	                	longitude=gps.getLongitude();
	                	mylatlng=new LatLng(latitude,longitude);
	                	Circle circle=googlemap.addCircle(new CircleOptions().center(mylatlng)
	                			                                             .radius(meters)
	                			                                             .strokeColor(Color.RED)
	                			                                             .fillColor(Color.TRANSPARENT));
	                	mylocation.setLatitude(latitude);
	                	mylocation.setLongitude(longitude);
	                	}
	                   //setting the text view for seekbar to diaply miles
	                   int xPos = ((seekbar.getRight() - seekbar.getLeft()) * seekbar.getProgress()) / seekbar.getMax();
	                   tvMiles.setPadding(xPos, 0, 0, 0);
	                   tvMiles.setText(progress+" miles");
	                   
	                   
	    				// Enable / Disable zooming controls
	    				googlemap.getUiSettings().setZoomControlsEnabled(true);

	    				// Enable / Disable my location button
	    				googlemap.getUiSettings().setMyLocationButtonEnabled(true);

	    				// Enable / Disable Compass icon
	    				googlemap.getUiSettings().setCompassEnabled(true);

	    				// Enable / Disable Rotate gesture
	    				googlemap.getUiSettings().setRotateGesturesEnabled(true);

	    				DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
	    	        	dbhelper.getReadableDatabase();
	    	            ArrayList<String> map_data=new ArrayList<String>();
	    	            map_data=dbhelper.readMapData(position);
	    				
	    				System.out.println(map_data+" data read at show_map");
	    				Location temp_loc=new Location("temp");
                    	float dist=0;
                    	float color=returnMarkerColor(position);
	    				for(String each_location:map_data)
	                    {
	                    	String[] location=each_location.split(" ");
	                    	double lat=Double.parseDouble(location[0]);
	                    	double lng=Double.parseDouble(location[1]);
	                    	temp_loc.setLatitude(lat);
	                    	temp_loc.setLongitude(lng);
	                    	dist=mylocation.distanceTo(temp_loc);
	                    	if(dist<meters)
	                    	{
	                    	  
	                    		MarkerOptions marker=new MarkerOptions().position(new LatLng(lat,lng)).snippet(location[7]).title(location[5]);
		                    	marker.icon(BitmapDescriptorFactory.fromResource(gs.getMarkerIcon(position)));
		                    	googlemap.addMarker(marker);
		                    	
		                    	googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, zoomlevel));             	
		                    		
	                    	}
	    				    //double latitude = 17.385044;
	    				    //double longitude = 78.486671;
	                     	
	                    }
	    			}
	    			else
	    			{
	    				Toast.makeText(getApplicationContext(),"Error while loading Maps", Toast.LENGTH_SHORT).show();
	    			}
	    		

				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
        	
        });
        
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

	    // Inflate the menu; this adds items to the action bar if it is present.
		if(true)
		{
			getMenuInflater().inflate(R.menu.main, menu);
				
		}
		else
		{
			
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_map) 
		{
			return true;
		}
		else if(id==R.id.action_list)
		{
			
			
			
		}
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
		      return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void activate(OnLocationChangedListener arg0) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
		
	}
	 public float returnMarkerColor(int pos)
	  {
		  float color=0;
		  switch(pos)
		  {
		  case 0:
			    color=BitmapDescriptorFactory.HUE_AZURE;
			    break;
		  case 1:
			    color=BitmapDescriptorFactory.HUE_BLUE;
			    break;
		  case 2:
			    color=BitmapDescriptorFactory.HUE_CYAN;
			    break;
		  case 3:
			    color=BitmapDescriptorFactory.HUE_GREEN;
			    break;
		  case 4:
			    color=BitmapDescriptorFactory.HUE_MAGENTA;
			    break;
		  case 5:
			    color=BitmapDescriptorFactory.HUE_ORANGE;
			    break;
		  case 6:
			    color=BitmapDescriptorFactory.HUE_ROSE;
			    break;
		  case 7:
			    color=BitmapDescriptorFactory.HUE_VIOLET;
			    break;
		  case 8:
			    color=BitmapDescriptorFactory.HUE_YELLOW;
			    break;
		
		  
		  }
		  return color;
	  }

private class ReadData extends AsyncTask<String,String,String>
{
	 private ProgressDialog pDialog;
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
			
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pDialog.cancel();
		
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
    	dbhelper.getReadableDatabase();
        map_data=new ArrayList<String>();
        map_data=dbhelper.readMapData(Integer.parseInt(params[0].trim()));
        return "Success";
	}
	
}

}
