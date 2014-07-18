package DataService;

import android.app.Activity;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseObject;

public abstract class Dataservice 
{
   Dataservice()
   {
	   
   }
   public abstract boolean register(String username,String password,String email,String fname,String mname,String lname);
}
