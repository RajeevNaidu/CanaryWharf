package com.example.canarywharfguide;

import java.util.ArrayList;

import android.app.Application;

public class Globalclassvariable extends Application
{
  private ArrayList<String> map_data=new ArrayList<String>();
  DatabaseHelper dbhelper;
  int pos;
  public void setPosition(int p)
  {
	  this.pos=p;
  }
  public int getPosition()
  {
	  return pos;
  }
  public void setMapData(ArrayList<String> temp)
  {
	  this.map_data=temp;
  }
public ArrayList<String> getMapData()
{
	return this.map_data;
}
public void setDbhelper(DatabaseHelper dbhelper)
{
	this.dbhelper=dbhelper;
}
public DatabaseHelper getDbhelper()
{
	return this.dbhelper;
}

public int getMarkerIcon(int pos )
{
	int id=0;

	 switch(pos)
	   {
	   case 0:
		     id=R.drawable.restaurant;
		     break;
	   case 1:
		   id=R.drawable.bar;
		     break;
	   case 2:
		   id=R.drawable.cocktail;
		     break;
	   case 3:
		   id=R.drawable.subway;
		     break;
	   case 4:
		   id=R.drawable.train;
		     break;
	   case 5:
		   id=R.drawable.atm;
		     break;
	   case 6:
		   id=R.drawable.postal;
		     break;
	   case 7:
		   id=R.drawable.bankpound;
		     break;
	   case 8:
		   id=R.drawable.supermarket;
		     break;
			  
	   }
	   
	
	return id;
}
public String getMarkerImage(String ref)
{
	String image="ic_"+ref+".png";
	return image;
}

  
}
