package login;



import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canarywharfguide.R;
import com.example.canarywharfguide.R.id;
import com.example.canarywharfguide.R.layout;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class loginfragment extends Fragment
{  
   public loginfragment()
   {
	   
   }
   EditText username,password;
   Button login;
   Dialog progressDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    View view=inflater.inflate(R.layout.login_fragment, container, false);
	    username=(EditText)view.findViewById(R.id.editText1);
	    password=(EditText)view.findViewById(R.id.editText2);
	    login=(Button)view.findViewById(R.id.button1);
	    login.setOnClickListener(new View.OnClickListener()
	    {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			new LoginProgressDialog().execute();	
			}
		});
		return container;
	}
    class LoginProgressDialog extends AsyncTask<Void,Void,Void>
    {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			progressDialog=ProgressDialog.show(getActivity(),"Log In", "Please be patient.....",true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected Void doInBackground(Void... arg0)
		{
			// TODO Auto-generated method stub
			Parse.initialize(getActivity(),"GL93V7bl3dKwmLnK66KKGej22Y3249VkYK6gorxe", "nZx6jiTxFbV3SwoBYllYtMBGTsApshDU1LYcrrfD");
			ParseUser.enableAutomaticUser();
			ParseACL defaultACL = new ParseACL();
			// Optionally enable public read access.
			// defaultACL.setPublicReadAccess(true);
			ParseACL.setDefaultACL(defaultACL, true);
		   String uname=username.getText().toString();
		   String pass=password.getText().toString();
		   ParseQuery<ParseObject> query=ParseQuery.getQuery("canarywharf_userdetails");
		   query.whereEqualTo("username", uname);
		   query.getFirstInBackground(new GetCallback<ParseObject>()
				   {
					@Override
					public void done(ParseObject arg0, ParseException arg1) {
						// TODO Auto-generated method stub
					    if(arg0==null)
					    {
					    	Toast.makeText(getActivity(), "Invalid Credentails", Toast.LENGTH_SHORT).show();
					    }
					    else
					    {
					    	Toast.makeText(getActivity(), "Login Done", Toast.LENGTH_SHORT).show();
					    }
					}
				   });
			return null;
		}
    	
    }
}
