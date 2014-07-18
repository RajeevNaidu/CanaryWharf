package com.example.canarywharfguide;

import java.util.ArrayList;


import com.github.johnpersano.supertoasts.SuperToast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MapActivity extends Activity
{
	private GoogleMap googlemap;
	SeekBar seekbar;
	 GPSTracker gps;
	 TextView tvMiles;
	 int position =-1;
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}

	@Override
	public void onContextMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onContextMenuClosed(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_fragment);
		getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
		R.animator.card_flip_left_in, R.animator.card_flip_left_out).replace(R.id.mapfragment,new MapFragment()).commit();
		//write the code to handle the map
		ActionBar actionbar=getActionBar();
		actionbar.setTitle("Let's Rock & Roll...");
		actionbar.setSubtitle("The App Expert");
		seekbar=(SeekBar)findViewById(R.id.seekbar);
        tvMiles=(TextView)findViewById(R.id.tv_miles);
        seekbar.setMax(100);
        onShowMap();
        
		///////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
   }
	
	//method to show Map
	public void onShowMap()
	{
		
		double latitude;
    	double longitude;
        position=MainActivity.position;
	// TODO Auto-generated method stub
    	//Show_Map map=new Show_Map();
    	System.out.println(position+" position selected");
    	Globalclassvariable gs=(Globalclassvariable)getApplicationContext();
    	gs.setPosition(position);
    	
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
	            map_data=dbhelper.readMapData(position);
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
		
	            float color=color=returnMarkerColor(position);
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
                	marker.icon(BitmapDescriptorFactory.fromResource(gs.getMarkerIcon(position)));
                	
                	googlemap.addMarker(marker);
                	
                }
	          }
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Error while loading Maps", Toast.LENGTH_SHORT).show();
			}
		
		

     
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
    				/*try {
						Thread.currentThread().sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
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

	
}
