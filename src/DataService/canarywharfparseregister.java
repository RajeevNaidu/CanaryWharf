package DataService;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
public class canarywharfparseregister extends Application
{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this,"GL93V7bl3dKwmLnK66KKGej22Y3249VkYK6gorxe", "nZx6jiTxFbV3SwoBYllYtMBGTsApshDU1LYcrrfD");
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}
   
}
