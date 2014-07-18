package com.example.canarywharfguide;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
//databsae name
	private static String DataBaseName="_maps";
//table name    
	private static String TableName="_maps_Table";
//columns name 
	private static String tableid="id";
	private static String latitude="latitude";
	private static String longitude="longitude";
	private static String address1="address1";
	private static String address2="address2";
	private static String postcode="postcode";
	private static String name="name";
	private static String type="type";
	private static String website="website";
	private static String image="image";
	private static int version=20;
	
	
	private ArrayList<String> map_data;
	public DatabaseHelper(Context context) 
	{
		super(context, DataBaseName, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
	   
		try
		{
			String create_map="CREATE TABLE "
		            + TableName+ "(" 
					+ tableid + " INTEGER PRIMARY KEY AUTOINCREMENT,"
		            + latitude + " TEXT," 
					+ longitude + " TEXT,"
					+ address1 + " TEXT,"
					+ address2 + " TEXT,"
					+ postcode + " TEXT,"
					+ name + " TEXT,"
					+ type + " TEXT,"
					+ website + " TEXT"+")";
			arg0.execSQL(create_map);
			System.out.println("table created");
			//post office
			ContentValues content=new ContentValues();
            content.put(latitude, "51.5044032");
            content.put(longitude, "-0.020915199999990364");
            content.put(address1, "StoreAddress:5ChancellorPassage");
            content.put(address2, "CanaryWharf");
            content.put(postcode, "E144PA ");
            content.put(name, "PostOffice");
            content.put(type, "Postoffices");
            content.put(website, "http://www.canarywharf.com/visitus/ServicesandAmenities/Services--Amenities-Directory-/The--Post--Office/");
            long id=arg0.insert(TableName, null, content);
            if(id==-1)
            {
            	System.out.println("Insertion 1 succcess");
            }
            else 
            {
            	System.out.println("Insertion1 Failed");
            }
            //canary wharf dlr station
            ContentValues content1=new ContentValues();
            content1.put(latitude, "51.513347");
            content1.put(longitude, "-0.08900010000002112");
            content1.put(address1, "StoreAddress:Monument&TowerHill");
            content1.put(address2, "CanaryWharf");
            content1.put(postcode, "EC3V3LA ");
            content1.put(name, "CanaryWharfDLRStation");
            content1.put(type, "Dlrstations");
            content1.put(website, "http://www.tfl.gov.uk");
            id=arg0.insert(TableName, null, content1);
            if(id==-1)
            {
            	System.out.println("Insertion 2 success");
            }
            else 
            {
            	System.out.println("Insertion 2 Failed");
            }

            //west ferry dlr station
            ContentValues content2=new ContentValues();
            content2.put(latitude, "51.5095681");
            content2.put(longitude, "-0.02804100000003018");
            content2.put(address1, "StoreAddress:LimeHouse");
            content2.put(address2, "WestFerry");
            content2.put(postcode, "E148AD");
            content2.put(name, "WestFerryDLRStation");
            content2.put(type, "Dlrstations");
            content2.put(website, "http://www.tfl.gov.uk");
            id=arg0.insert(TableName, null, content2);
            if(id==-1)
            {
            	System.out.println("Insertion 2 success");
            }
            else 
            {
            	System.out.println("Insertion 2 Failed");
            }

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
             
		arg0.execSQL("DROP TABLE IF EXISTS "+TableName);
		onCreate(arg0);
		
	}
	
   public ArrayList<String> readMapData(int pos)
   {
	   System.out.println(pos+" position read in the dbHelper");
	   System.out.println("in dbhelper read statred");
      SQLiteDatabase database=this.getReadableDatabase();
      System.out.println("select stmt started");
      Cursor cursor=database.rawQuery("select * from _maps_Table", null);
      System.out.println("select ended");
      map_data=new ArrayList<String>();
      String type=pos+"";
      System.out.println(cursor.getCount()+" size of cursor");
      cursor.moveToFirst();
      do
      {
    	if(cursor.getString(cursor.getColumnIndex("type")).trim().equals(getType(pos).trim()))
    	{
    	System.out.println(cursor.getColumnIndex("type")+" position read from database");
        String each_map;
    	each_map=cursor.getString(cursor.getColumnIndex("latitude")).trim();
    	each_map=each_map+" "+cursor.getString(cursor.getColumnIndex("longitude")).trim();
    	each_map=each_map+" "+cursor.getString(cursor.getColumnIndex("address1")).trim();
    	each_map=each_map+" "+cursor.getString(cursor.getColumnIndex("address2")).trim();
    	each_map=each_map+" "+cursor.getString(cursor.getColumnIndex("postcode")).trim();
    	each_map=each_map+" "+cursor.getString(cursor.getColumnIndex("name")).trim();
    	each_map=each_map+" "+cursor.getString(cursor.getColumnIndex("type")).trim();
    	each_map=each_map+" "+cursor.getString(cursor.getColumnIndex("website")).trim();
        map_data.add(each_map);
        System.out.println(map_data+" data retrieved for type selected");
        
    	}  
   }while(cursor.moveToNext());
      cursor.close();
      return map_data;
      
   }
   public String getType(int pos)
   {
	   String type="";
	   switch(pos)
	   {
	   case 0:
		     type="Restaurants";
		     break;
	   case 1:
		     type="Bars";
		     break;
	   case 2:
		     type="Pubs";
		     break;
	   case 3:
		     type="Dlrstations";
		     break;
	   case 4:
		     type="Underground";
		     break;
	   case 5:
		     type="Atms";
		     break;
	   case 6:
		     type="Postoffices";
		     break;
	   case 7:
		     type="Banks";
		     break;
	   case 8:
		     type="Superstores";
		     break;
			  
	   }
	   return type;
   }
  
}
